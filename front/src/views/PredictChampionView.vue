<template>
  <div class="app-container">
    <Header />

    <!-- 노란색 네모에 해당하는 버튼 -->z
    <div class="game-container">
      <!-- champion-prediction 내부 -->
      <div class="champion-prediction">
        <audio ref="audioPlayer" :src="audioSrc"></audio>
        <button @click="playSound">Play Sound</button>
      </div>

      {{ championStore.championNames[0] }}

      <div class="champion-options">
        <button
          v-for="(option, index) in answerOptions"
          :key="index"
          @click="userAnswer = option"
          class="option-button"
        >
          {{ championStore.championNames[option] }}
        </button>
      </div>
    </div>
    <!-- 모달 창 구현 -->
    <div v-if="showRankModal" class="rank-modal">
      <div class="rank-modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <RankView
          :score="score"
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
import RankView from "@/components/common/RankView.vue";
import { v4 as uuidv4 } from "uuid";

//////////////////////////////////////////////////////////////////

import { onMounted, ref, computed } from "vue";
import { useRouter } from "vue-router";
import Header from "@/components/common/Header.vue";
import { useChampionStore } from "@/stores/championStore";

const showRankModal = ref(false);
const rank = ref([]);
const uuid = ref("");
const correctAnswer = ref(false); // 정답 여부
const showAnswerFeedback = ref(false); // 정답 피드백 표시 여부
const userAnswer = ref("");

/////////////////////////////////////////////////////////

const nicknameInput = ref(null);
const shuffledIndexes = ref([]);
const currentRound = ref(0);
const championStore = useChampionStore();
const nickname = ref("");
const showNicknameModal = ref(true);
const router = useRouter();
const audioPlayer = ref(null);
const answerOptions = ref([]);

/////////////////////////////////////////////////////////

onMounted(() => {
  nicknameInput.value.focus();

  console.log(championStore.championKeys);

  // 인덱스 배열을 생성합니다. 예: [0, 1, 2, ..., length-1]
  const indexes = Array.from(
    { length: championStore.championKeys.length },
    (_, i) => i
  );
  // Fisher-Yates 셔플 알고리즘을 이용해 배열을 섞습니다.
  shuffledIndexes.value = shuffleArray(indexes);

  console.log(shuffledIndexes.value);

  // 정답 옵션을 설정합니다.
  setAnswerOptions();
});

function setAnswerOptions() {
  const correctKey =
    championStore.championKeys[shuffledIndexes.value[currentRound.value]];
  const otherKeys = championStore.championKeys.filter((k) => k !== correctKey);

  // Fisher-Yates 셔플 알고리즘을 다시 사용하여 다른 키를 섞습니다.
  const shuffledOtherKeys = shuffleArray(otherKeys).slice(0, 4);

  // 정답과 무작위로 선택된 다른 옵션을 결합합니다.
  answerOptions.value = shuffleArray([correctKey, ...shuffledOtherKeys]);
  console.log(answerOptions.value);
}

// Fisher-Yates 셔플 알고리즘을 이용한 배열 섞기
function shuffleArray(array) {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]]; // 배열 요소 교환
  }
  return array;
}

const audioSrc = computed(() => {
  const key =
    championStore.championKeys[shuffledIndexes.value[currentRound.value]];
  console.log("key = " + key);
  return `src/assets/pick-voice/${key}.ogg`;
});

const submitNickname = () => {
  if (nickname.value.trim().length) {
    showNicknameModal.value = false;
  } else {
    alert("Please enter a nickname.");
  }
};

const goBack = () => {
  router.push("/playground");
};

const closeModal = () => {
  window.location.reload();
};

const playSound = () => {
  audioPlayer.value.play();
};

////////////////////////////////////////////////////////////

const checkAnswer = () => {
  if (userAnswer.value === "아리") {
    alert("정답입니다!");
    correctAnswer.value = true; // 정답 여부 업데이트
  } else {
    alert("틀렸습니다.");
    correctAnswer.value = false; // 정답 여부 업데이트
  }
  showAnswerFeedback.value = true; // 정답 피드백 보여주기
};
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
</style>
