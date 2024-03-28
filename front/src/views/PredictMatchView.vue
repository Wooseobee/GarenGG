<template>
  <div class="app-container">
    <Header />
    <!-- 노란색 네모에 해당하는 버튼 -->
    <div class="game-container">
      <div class="match-prediction">
        <!-- 좌측 팀 리스트 -->
        <div class="team-button" @click="selectTeam(teamOnePlayers)">
          <TeamList
            :players="teamOnePlayers"
            :isRightTeam="false"
            :currentHint="currentHint"
          />
        </div>
        <div class="match-info">
          <div v-if="currentHint >= 3" class="matchTime">{{ matchTime }}</div>
          <div class="round">{{ currentRound }}/{{ totalRounds }}</div>
          <div class="vs">VS</div>
          <div class="hint" @click="showHint">
            <span>{{ currentHint }}/{{ totalHints }}</span> 힌트보기
          </div>
        </div>
        <!-- 우측 팀 리스트 -->
        <div class="team-button" @click="selectTeam(teamTwoPlayers)">
          <TeamList
            :players="teamTwoPlayers"
            :isRightTeam="true"
            :currentHint="currentHint"
          />
        </div>
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
import { onMounted, ref } from "vue";
import Header from "@/components/common/Header.vue";
import TeamList from "@/views/TeamList.vue";
import RankView from "@/components/common/RankView.vue";
import { randomMatch, playGame } from "@/api/randomMatch.js";
import { useChampionStore } from "@/stores/championStore";
import { v4 as uuidv4 } from "uuid";
import { useRouter } from "vue-router";

const router = useRouter();
const currentRound = ref(1);
const totalRounds = 5;
const currentHint = ref(0);
const totalHints = 4;
const showRankModal = ref(false);
const matchData = ref(null);
const teamOnePlayers = ref([]);
const teamTwoPlayers = ref([]);
const championStore = useChampionStore();
const score = ref(100);
const usedHints = ref(0);
const showNicknameModal = ref(true);
const nickname = ref("");
const rank = ref([]);
const uuid = ref("");
const correctAnswer = ref(false); // 정답 여부
const showAnswerFeedback = ref(false); // 정답 피드백 표시 여부
const matchTime = ref("0");
const nicknameInput = ref(null);
const key = ref();
const iv = ref();
const decoder = new TextDecoder();

const calculateGameTime = (seconds) => {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = Math.floor(seconds % 60);

  return `${minutes}분 ${remainingSeconds}초`;
};

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

async function decryptMessage(ciphertext) {
  const decryptedCiphertext = await window.crypto.subtle.decrypt(
    { name: "AES-GCM", iv: iv.value },
    key.value,
    ciphertext
  );
  return decryptedCiphertext;
}

async function importKey(arrayBuffer) {
  return window.crypto.subtle.importKey(
    "raw", // 키의 형식
    arrayBuffer, // 키 데이터
    {
      // 사용할 알고리즘 정보
      name: "AES-GCM",
    },
    false, // 키를 내보낼 수 있는지 여부
    ["decrypt"] // 키 사용 목적
  );
}

const decryptData = async (data) => {
  return decoder.decode(
    await decryptMessage(
      Uint8Array.from(atob(data), (c) => c.charCodeAt(0)).buffer
    )
  );
};

const fetchMatchData = async () => {
  try {
    currentHint.value = 0;
    const response = await randomMatch();
    matchData.value = response.data;

    const secretKeyBuffer = Uint8Array.from(
      atob(response.data.secretKey),
      (c) => c.charCodeAt(0)
    ).buffer;
    const importedKey = await importKey(secretKeyBuffer);
    key.value = importedKey;
    iv.value = Uint8Array.from(atob(response.data.iv), (c) =>
      c.charCodeAt(0)
    ).buffer;

    const time = parseInt(await decryptData(response.data.gameDuration));
    matchTime.value = calculateGameTime(time);
    // 승리 팀과 패배 팀을 분류하기 위한 임시 배열
    const winTeam = [];
    const loseTeam = [];

    // API 응답에서 각 참가자를 순회하며 승리 여부에 따라 분류
    for (const participant of response.data.participants) {
      let idx = 0;

      const championId = await decryptData(participant.championName);

      for (let index = 0; index < championStore.championIds.length; index++) {
        if (championId === championStore.championIds[index]) {
          idx = index;
          break;
        }
      }

      const enemyMissingPings = await decryptData(
        participant.enemyMissingPings
      );
      const individualPosition = await decryptData(
        participant.individualPosition
      );
      const summonerName = await decryptData(participant.summonerName);
      const riotIdTagline = await decryptData(participant.riotIdTagline);
      const kills = await decryptData(participant.kills);
      const deaths = await decryptData(participant.deaths);
      const firstBloodKill = JSON.parse(
        await decryptData(participant.firstBloodKill)
      );
      const win = JSON.parse(await decryptData(participant.win));

      const participantData = {
        enemyMissingPings,
        championId,
        championName: championStore.championNames[idx],
        imgUrl: championStore.championSquareImgUrls[idx],
        individualPosition,
        summonerName,
        riotIdTagline,
        kills,
        deaths,
        firstBloodKill,
        win,
      };

      if (win) {
        winTeam.push(participantData);
      } else {
        loseTeam.push(participantData);
      }
    }

    if (Math.random() < 0.5) {
      teamOnePlayers.value = winTeam; // 승리 팀을 teamOnePlayers에 할당
      teamTwoPlayers.value = loseTeam; // 패배 팀을 teamTwoPlayers에 할당
    } else {
      teamOnePlayers.value = loseTeam; // 패배 팀을 teamOnePlayers에 할당
      teamTwoPlayers.value = winTeam; // 승리 팀을 teamTwoPlayers에 할당
    }
  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  nicknameInput.value.focus();
  fetchMatchData();
});

const showHint = () => {
  if (showAnswerFeedback.value) return;
  if (currentHint.value < totalHints) {
    currentHint.value++;
    usedHints.value++;
  }
};

const selectTeam = async (team) => {
  if (showAnswerFeedback.value) return;
  correctAnswer.value = team[0].win; // 승리 팀을 선택했는지 여부
  showAnswerFeedback.value = true; // 정답 피드백을 보여주기

  currentHint.value = 4;
  await new Promise((resolve) => setTimeout(resolve, 5000));

  showAnswerFeedback.value = false; // 피드백 숨김

  if (team[0].win) {
    score.value -= usedHints.value * 4;
  } else {
    score.value -= 20;
  }
  if (currentRound.value < totalRounds) {
    currentRound.value++;
    fetchMatchData();
  } else {
    uuid.value = uuidv4();
    const response = await playGame({
      gameId: 1,
      nickname: nickname.value,
      score: score.value,
      uuid: uuid.value,
    });
    showRankModal.value = true;
    rank.value = response.data;
  }
  usedHints.value = 0;
  currentHint.value = 0;
};

const closeModal = () => {
  window.location.reload();
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

/* .upcoming-match-header {
  text-align: center;
  font-size: 1.5rem;
  color: #ffffff;
  padding: 20px 0;
} */
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
