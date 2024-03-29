<template>
  <div class="app-container">
    <Header />

    <!-- 모달 창 구현 -->
    <div v-if="showRankModal" class="rank-modal">
      <div class="rank-modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <RankView
          :score="currentRound"
          :nickname="nickname"
          :rank="rank"
          :uuid="uuid"
        />
      </div>
    </div>

    <!-- 닉네임 입력 창 구현 -->
    <div v-if="showNicknameModal" class="nickname-modal">
      <div class="nickname-modal-content">
        <h3>ENTER YOUR NICKNAME!</h3>
        <input
          ref="nicknameInput"
          type="text"
          v-model="nickname"
          placeholder="Your nickname"
          maxlength="10"
          @keyup.enter="submitNickname"
        />
        <button @click="submitNickname">Submit</button>
        <button @click="goBack">return</button>
      </div>
    </div>

    <div v-else class="game-container">
      <div class="game-description">
        <p>대사를 듣고 챔피언을 골라보세요!</p>
      </div>
      <audio ref="audioPlayer" :src="audioSrc"></audio>
      <button class="play-sound-btn" @click="playSound" :disabled="soundPlayed">
        Play Sound
      </button>

      <div class="champion-prediction">
        <div class="champions-container">
          <button
            class="champion-button"
            v-for="candidate in rounds[currentRound].candidateChampions"
            :key="candidate.championKey"
            @click="checkAnswer(candidate.championKey)"
          >
            <img
              class="champion-image"
              :src="`https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${candidate.id}.png`"
              alt=""
            />
          </button>
        </div>
      </div>

      <h2>Time left: {{ timeLeft }} seconds</h2>
      <!-- 기존 게임 컨테이너 내용 -->
    </div>

    <div
      v-if="showAnswerFeedback"
      class="answer-feedback"
      :class="{ correct: correctAnswer, wrong: !correctAnswer }"
    >
      <span>{{ correctAnswer ? "O" : "X" }}</span>
    </div>
  </div>
</template>

<script setup>
//////////////////////////////////////////////////////////////////

import { onMounted, ref, computed, onUnmounted, watch } from "vue";
import { useRouter } from "vue-router";
import Header from "@/components/common/Header.vue";
import { localAxios } from "../utils/http-commons";
import { v4 as uuidv4 } from "uuid";
import RankView from "@/components/common/RankView.vue";

/////////////////////////////////////////////////////////

const showNicknameModal = ref(true);
const nicknameInput = ref(null);
const nickname = ref("");
const router = useRouter();
const currentRound = ref(0);
const audioPlayer = ref(null);
const rounds = ref([]);
const timerId = ref(null);

const showAnswerFeedback = ref(false);
const correctAnswer = ref(false);
const showRankModal = ref(false);
const timeLeft = ref(5); // 타이머 남은 시간

const uuid = ref("");
const rank = ref([]);
const soundPlayed = ref(false);

/////////////////////////////////////////////////////////

onMounted(async function () {
  nicknameInput.value.focus();

  await getRounds();
});

onUnmounted(() => {
  if (timerId.value) clearTimeout(timerId.value);
});

const submitNickname = () => {
  if (nickname.value.trim().length) {
    showNicknameModal.value = false;
  } else {
    alert("Please enter a nickname.");
  }
};

const getRounds = async function () {
  try {
    const response = await localAxios.get(`/championPrediction/start`);
    rounds.value = response.data.rounds;
  } catch (error) {
    console.error("API 호출 중 오류 발생:", error);
    alert(error.response.data.errorMessage);
    router.push({ name: "championPrediction" });
  }
};

const goBack = () => {
  router.push("/playground");
};

const closeModal = () => {
  window.location.reload();
};

// const audioSrc = computed(() => {
//   if (rounds.value.length > 0 && rounds.value[currentRound.value]) {
//     const key = rounds.value[currentRound.value].answerChampion.championKey;
//     return `/src/assets/pick-voice/${key}.ogg`;
//   }
//   return "";
// });

const audioSrc = computed(() => {
  if (rounds.value.length > 0 && rounds.value[currentRound.value]) {
    const key = rounds.value[currentRound.value].answerChampion.championKey;
    // Webpack 환경에서 require 사용
    // return require(`/src/assets/pick-voice/${key}.ogg`);
    // Vite 환경에서 import.meta.url 사용
    return new URL(`/src/assets/pick-voice/${key}.ogg`, import.meta.url).href;
  }
  return "";
});

const checkAnswer = async (championKey) => {
  if (showAnswerFeedback.value || timerId.value === null) return;

  // 타이머 정지
  clearInterval(timerId.value);
  timerId.value = null; // 타이머 ID 초기화

  const isCorrect =
    championKey === rounds.value[currentRound.value].answerChampion.championKey;
  correctAnswer.value = isCorrect;
  showAnswerFeedback.value = true;

  // 정답 확인 후 처리
  await new Promise((resolve) => setTimeout(resolve, 2000));
  showAnswerFeedback.value = false;

  // 정답 처리
  if (isCorrect) {
    proceedToNextRound();
  } else {
    endGame();
  }
};

const proceedToNextRound = () => {
  if (currentRound.value < rounds.value.length - 1) {
    currentRound.value++;
    // 다음 라운드를 위한 상태 초기화
    correctAnswer.value = false;
    soundPlayed.value = false;
    timeLeft.value = 5; // 타이머 재설정
  } else {
    alert("Congratulations! You've completed the game!");
    endGame();
  }
};

const playGame = async function (body) {
  try {
    const response = await localAxios.post(`/playGame`, body);
    return response;
  } catch (error) {
    console.error("API 호출 중 오류 발생:", error);
    alert(error.response.data.errorMessage);
    router.push({ name: "championPrediction" });
  }
};

onUnmounted(() => {
  if (timerId.value) clearTimeout(timerId.value);
});

const startTimer = () => {
  if (timerId.value) clearTimeout(timerId.value); // 이전 타이머가 있으면 초기화

  timerId.value = setInterval(() => {
    if (timeLeft.value > 0) {
      timeLeft.value -= 1;
    } else {
      clearInterval(timerId.value);
      autoFail();
    }
  }, 1000);
};

const autoFail = async () => {
  showAnswerFeedback.value = true;
  correctAnswer.value = false; // 자동으로 틀린 것으로 처리
  await new Promise((resolve) => setTimeout(resolve, 2000));
  showAnswerFeedback.value = false;
  endGame();
};

const playSound = () => {
  if (showAnswerFeedback.value || timeLeft.value <= 0 || soundPlayed.value)
    return;
  audioPlayer.value.play();
  startTimer();
  soundPlayed.value = true; // Mark sound as played for this round
};

// 기존 checkAnswer 함수에서 endGame 로직 분리
const endGame = async () => {
  uuid.value = uuidv4();
  const response = await playGame({
    gameId: 2,
    nickname: nickname.value,
    score: currentRound.value,
    uuid: uuid.value,
  });
  showRankModal.value = true;
  rank.value = response.data;
};

// currentRound가 변경될 때 실행될 로직
watch(currentRound, () => {
  // 새 라운드가 시작될 때 필요한 상태를 초기화
  correctAnswer.value = false; // 정답 상태 초기화
  soundPlayed.value = false; // 소리 재생 상태 초기화
  // 필요하다면 타이머 관련 로직도 여기에 추가할 수 있습니다.
});
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  height: 100vh; /* 전체 화면 높이 */
}

.round {
  text-align: center;
  font-size: 1.5rem;
  color: #ffffff;
  /* padding-top을 제거하고 flex layout에 맡김 */
}

.game-container {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white; /* 텍스트 색상 */
  /* 게임 화면의 추가적인 스타일링이 여기에 들어감 */
}

.match-prediction {
  width: 100%; /* 전체 너비를 사용 */
  max-width: 1200px; /* 최대 너비 설정 */
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  /* 이곳에 더 많은 스타일을 추가할 수 있음 */
  align-items: center;
  background: radial-gradient(
    circle,
    rgba(0, 0, 0, 1) 0%,
    rgba(0, 0, 0, 1) 100%
  );
  background-size: cover;
  box-shadow: 0 0 10px 10px #005a82; /* 네온 효과 */
  border: 1px solid #005a82; /* 네온 테두리 */
  border-radius: 15px; /* 둥근 모서리 */
}

.vs {
  font-size: 3rem; /* 크기 증가 */
  color: #d00;
  text-shadow: 0 0 10px #d00; /* 텍스트 네온 효과 */
  font-family: "Press Start 2P", cursive; /* 레트로 폰트 */
}

.matchTime {
  position: absolute;
  top: 120px; /* 위치 조정 */
  left: 50%; /* 화면의 중앙 */
  transform: translateX(-50%); /* 정확한 중앙 정렬을 위해 */
  padding: 10px;
  background-color: #005a82; /* 노란색 배경 */
  color: white; /* 텍스트 색상 */
  cursor: pointer; /* 클릭 가능한 요소로 표시 */
}

.hint {
  position: absolute;
  bottom: 20px; /* 위치 조정 */
  left: 50%; /* 화면의 중앙 */
  transform: translateX(-50%); /* 정확한 중앙 정렬을 위해 */
  padding: 10px 20px;
  background-color: #005a82; /* 파란색 배경 */
  color: #fff; /* 텍스트 색상 */
  border-radius: 5px; /* 약간 둥근 모서리 */
  cursor: pointer; /* 클릭 가능한 요소로 표시 */
}
.team-selection {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px; /* 상단 여백 추가 */
}

.team-button {
  cursor: pointer; /* 클릭 가능한 요소로 표시 */
  transition: border 0.3s;
}
.team-button:hover {
  border: 2px solid #00ffea; /* 네온 테두리 색상, 적절한 색상으로 조정 가능 */
}

.rank-modal {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.rank-modal-content {
  width: 80%; /* 모달의 너비를 화면의 80%로 설정 */
  max-width: 640px; /* 최대 너비를 640px로 제한 */
  height: auto; /* 모달의 높이를 내용에 따라 자동으로 조정 */
  max-height: 90%; /* 화면의 90%를 넘지 않는 높이로 제한 */
  padding: 40px; /* 내부 여백을 40px로 설정 */
  background: white;
  border-radius: 10px; /* 모달의 모서리를 둥글게 */
  position: relative;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 모달에 그림자 효과 추가 */
  overflow-y: auto; /* 필요한 경우 내부 스크롤바 활성화 */
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 25px;
  cursor: pointer;
  color: black;
}

.nickname-modal {
  /* 모달 위치 및 스타일링 설정 */
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000; /* z-index를 높여 다른 요소들 위에 오도록 설정 */
}

.nickname-modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  color: black;
}

.answer-feedback {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 20rem;
  animation: fadeInOut 5s ease-in-out;
}

.correct {
  color: green;
}

.wrong {
  color: #d00;
}

@keyframes fadeInOut {
  0%,
  100% {
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
}
.play-sound-btn {
  font-size: 1.5rem; /* 버튼 내 텍스트 크기 증가 */
  padding: 10px 20px; /* 버튼 패딩 증가 */
  margin-bottom: 20px; /* 버튼과 챔피언 이미지들 사이의 간격 조정 */
  cursor: pointer;
  background-color: #4caf50; /* 버튼 배경색 */
  color: white; /* 버튼 텍스트 색상 */
  border: none; /* 테두리 제거 */
  border-radius: 5px; /* 버튼 둥근 모서리 */
}

.champions-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center; /* 이미지들을 중앙 정렬 */
  gap: 20px; /* 이미지들 사이의 간격 */
}

.champion-button {
  border: none; /* 버튼 테두리 제거 */
  background: none; /* 버튼 배경색 제거 */
  cursor: pointer;
}

.champion-image {
  width: 100px; /* 챔피언 이미지 크기 조정 */
  height: 100px; /* 챔피언 이미지 크기 조정 */
  object-fit: cover; /* 이미지 비율 유지 */
}

.game-container h2 {
  margin: 20px 0;
  color: #ffdd57; /* 금색으로 표시 */
  font-size: 2rem; /* 폰트 크기를 크게 */
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* 텍스트에 그림자 추가 */
}

.play-sound-btn {
  font-size: 1.5rem; /* 버튼 내 텍스트 크기 증가 */
  padding: 10px 20px; /* 버튼 패딩 증가 */
  margin-bottom: 20px; /* 버튼과 챔피언 이미지들 사이의 간격 조정 */
  cursor: pointer;
  background-color: #4caf50; /* 버튼 배경색 */
  color: white; /* 버튼 텍스트 색상 */
  border: none; /* 테두리 제거 */
  border-radius: 5px; /* 버튼 둥근 모서리 */
}

.champions-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center; /* 이미지들을 중앙 정렬 */
  gap: 20px; /* 이미지들 사이의 간격 */
}

.champion-button {
  border: none; /* 버튼 테두리 제거 */
  background: none; /* 버튼 배경색 제거 */
  cursor: pointer;
}

.champion-image {
  width: 100px; /* 챔피언 이미지 크기 조정 */
  height: 100px; /* 챔피언 이미지 크기 조정 */
  object-fit: cover; /* 이미지 비율 유지 */
}
</style>
