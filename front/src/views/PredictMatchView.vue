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
            :currentHint="currentHints"
            :tier="tier"
            :finishRound="finishRound"
          />
        </div>
        <div class="match-info">
          <div v-if="currentHints >= 3" class="matchTime">{{ matchTime }}</div>
          <div class="round">{{ currentRound }}/{{ totalRounds }}</div>
          <div class="vs">VS</div>
          <div>
            <img :src="getRankImage()" alt="" width="180px" height="auto" />
          </div>
          <div>{{ tier }}</div>
          <div class="usedHints">{{ usedHints }}/{{ maxHints }}</div>
          <div class="hint" @click="showHint">
            <span>{{ currentHints }}/{{ totalHints }}</span> 힌트보기
          </div>
        </div>
        <!-- 우측 팀 리스트 -->
        <div class="team-button" @click="selectTeam(teamTwoPlayers)">
          <TeamList
            :players="teamTwoPlayers"
            :isRightTeam="true"
            :currentHint="currentHints"
            :tier="tier"
            :finishRound="finishRound"
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
        <div class="description">
          <h3>*게임 설명*</h3>
          <div>
            1. 이번 시즌
            <span class="notice">실제로 진행되었던</span> 게임의 승리 팀을
            맞추는 게임입니다.
          </div>
          <div>2. 최대 100경기가 준비되어 있습니다.</div>
          <div>3. 틀리는 즉시 게임은 종료됩니다.</div>
          <div>4. 조합으로 맞추기 어렵다면 힌트를 보세요.</div>
          <div>
            5. 힌트는 미아핑 > 첫번째 킬 달성 > 게임 진행 시간 > K/D/A 순서로
            제공되며, 힌트를 볼 때마다 점수가 차감됩니다.
          </div>
          <div>6. 최대 사용 가능한 힌트는 20개 입니다.</div>
          <div>7. 양쪽 팀 중 이겼다고 생각하는 팀을 선택해주세요.</div>
        </div>
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
const totalRounds = 100;
const totalHints = 4;
const maxHints = 20;
const usedHints = ref(0);
const showRankModal = ref(false);
const matchData = ref(null);
const teamOnePlayers = ref([]);
const teamTwoPlayers = ref([]);
const championStore = useChampionStore();
const score = ref(0);
const currentHints = ref(0);
const showNicknameModal = ref(true);
const nickname = ref("");
const rank = ref([]);
const uuid = ref("");
const correctAnswer = ref(false); // 정답 여부
const showAnswerFeedback = ref(false); // 정답 피드백 표시 여부
const matchTime = ref("0");
const nicknameInput = ref(null);
const key1 = ref();
const key2 = ref();
const match = ref();
const decoder = new TextDecoder();
const tier = ref("");
const finishRound = ref(false);
const positionOrder = ["TOP", "JUNGLE", "MIDDLE", "BOTTOM", "UTILITY"];

//소리관련
const correctAudioPlayer = ref(null);
const wrongAudioPlayer = ref(null);
const correctSrc = ref(
  new URL("/src/assets/sounds/correct.mp3", import.meta.url).href
);
const wrongSrc = ref(
  new URL("/src/assets/sounds/wrong.mp3", import.meta.url).href
);

function sortByPosition(participants) {
  return participants.sort((a, b) => {
    return (
      positionOrder.indexOf(a.individualPosition) -
      positionOrder.indexOf(b.individualPosition)
    );
  });
}

const getRankImage = () => {
  const tierImg =
    tier.value.charAt(0).toUpperCase() + tier.value.slice(1).toLowerCase();
  return new URL(`../assets/emblem/Rank=${tierImg}.png`, import.meta.url).href;
};

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

async function decryptMessage(ciphertext, key) {
  const decryptedCiphertext = await window.crypto.subtle.decrypt(
    { name: "AES-GCM", iv: match.value },
    key,
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

const decryptData = async (data, key) => {
  return decoder.decode(
    await decryptMessage(
      Uint8Array.from(atob(data), (c) => c.charCodeAt(0)).buffer,
      key
    )
  );
};

const fetchMatchData = async () => {
  try {
    finishRound.value = false;
    currentHints.value = 0;
    const response = await randomMatch();
    matchData.value = response.data;

    const secretKeyBuffer = atob(response.data.matchInfo);
    const [secretKey1, secretKey2] = secretKeyBuffer.split("&&");
    key1.value = await importKey(
      Uint8Array.from(secretKey1, (c) => c.charCodeAt(0)).buffer
    );

    key2.value = await importKey(
      Uint8Array.from(secretKey2, (c) => c.charCodeAt(0)).buffer
    );

    match.value = Uint8Array.from(atob(response.data.match), (c) =>
      c.charCodeAt(0)
    ).buffer;

    const time = parseInt(
      await decryptData(response.data.gameDuration, key1.value)
    );
    tier.value = response.data.tier;
    matchTime.value = calculateGameTime(time);
    // 승리 팀과 패배 팀을 분류하기 위한 임시 배열
    let winTeam = [];
    let loseTeam = [];
    const participantsArray = await decryptData(
      response.data.participants,
      key2.value
    );
    const participants = JSON.parse(participantsArray);

    // API 응답에서 각 참가자를 순회하며 승리 여부에 따라 분류
    for (const participant of participants) {
      let idx = 0;

      const championId = await decryptData(
        participant.championName,
        key1.value
      );

      for (let index = 0; index < championStore.championIds.length; index++) {
        if (championId === championStore.championIds[index]) {
          idx = index;
          break;
        }
      }

      const enemyMissingPings = await decryptData(
        participant.enemyMissingPings,
        key1.value
      );
      const individualPosition = await decryptData(
        participant.individualPosition,
        key1.value
      );
      const nickName = await decryptData(participant.nickName, key1.value);
      const riotIdTagline = await decryptData(
        participant.riotIdTagline,
        key1.value
      );
      const kills = await decryptData(participant.kills, key1.value);
      const deaths = await decryptData(participant.deaths, key1.value);
      const assists = await decryptData(participant.assists, key1.value);
      const firstBloodKill = JSON.parse(
        await decryptData(participant.firstBloodKill, key1.value)
      );
      const win = JSON.parse(await decryptData(participant.win, key1.value));

      const participantData = {
        enemyMissingPings,
        championId,
        championName: championStore.championNames[idx],
        imgUrl: championStore.championSquareImgUrls[idx],
        individualPosition,
        nickName,
        riotIdTagline,
        kills,
        deaths,
        assists,
        firstBloodKill,
        win,
      };

      if (win) {
        winTeam.push(participantData);
      } else {
        loseTeam.push(participantData);
      }
    }

    winTeam = sortByPosition(winTeam);
    loseTeam = sortByPosition(loseTeam);

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
  //정답여부 음성 출력
  correctAudioPlayer.value = new Audio(correctSrc.value);
  wrongAudioPlayer.value = new Audio(wrongSrc.value);

  nicknameInput.value.focus();
  fetchMatchData();
});

const showHint = () => {
  if (showAnswerFeedback.value) return;
  if (usedHints.value >= maxHints) {
    alert("힌트를 모두 사용하셨습니다!");
    return;
  }
  if (currentHints.value < totalHints) {
    currentHints.value++;
    usedHints.value++;
  }
};

const selectTeam = async (team) => {
  if (showAnswerFeedback.value) return;
  correctAnswer.value = team[0].win; // 승리 팀을 선택했는지 여부
  const useHintThisRound = currentHints.value;
  showAnswerFeedback.value = true; // 정답 피드백을 보여주기
  currentHints.value = 4;
  finishRound.value = true;

  //정답처리결과에따른 소리출력
  if (correctAnswer.value) {
    correctAudioPlayer.value.play();
  } else {
    wrongAudioPlayer.value.play();
  }

  await new Promise((resolve) => setTimeout(resolve, 5000));

  if (team[0].win) {
    score.value += 5 - useHintThisRound;
    if (currentRound.value < totalRounds) {
      currentRound.value++;
      await fetchMatchData();

      showAnswerFeedback.value = false; // 피드백 숨김
    } else {
      showRankModal.value = true;
      rank.value = response.data;
    }
  } else {
    uuid.value = "&" + uuidv4();
    const response = await playGame({
      gameId: 1,
      nickname: nickname.value,
      score: score.value,
      uuid: uuid.value,
    });
    showRankModal.value = true;
    rank.value = response.data;
  }
  currentHints.value = 0;
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

.matchTime {
  padding: 10px;
  background-color: #005a82; /* 노란색 배경 */
  color: white; /* 텍스트 색상 */
  cursor: pointer; /* 클릭 가능한 요소로 표시 */
}

.match-info {
  display: flex; /* Flexbox 레이아웃 사용 */
  align-items: center; /* 모든 아이템을 세로 축에서 가운데 정렬 */
  justify-content: space-around; /* 아이템들 사이에 공간을 균등하게 배분 */
  padding: 10px; /* 내부 여백 추가 */
  border-radius: 5px; /* 경계선 둥글게 처리 */
  flex-direction: column;
}

.hint {
  padding: 10px 20px;
  background-color: #005a82; /* 파란색 배경 */
  color: #fff; /* 텍스트 색상 */
  border-radius: 5px; /* 약간 둥근 모서리 */
  cursor: pointer; /* 클릭 가능한 요소로 표시 */
}

.usedHints {
  margin-top: 100px;
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
  text-align: center;
  width: 100%;
  height: auto;
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
  background: #32281e;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  color: black;
}

.nickname-modal-content h3 {
  color: #f0e6d2;
}

.nickname-modal-content input {
  background-color: #f0e6d2;
}

.nickname-modal-content button {
  background-color: #f0e6d2;
  margin-left: 10px;
  margin-bottom: 10px;
}

.description {
  color: #c8aa6e;
}

.answer-feedback {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 20rem;
  /* animation: fadeInOut 5s ease-in-out; */
}

.correct {
  color: green;
}

.wrong {
  color: #d00;
}

.notice {
  color: #d00;
  font-weight: bold;
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
