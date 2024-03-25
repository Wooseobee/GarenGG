<template>
  <div class="app-container">
    <Header />
    <div class="round">{{ currentRound }}/{{ totalRounds }}</div>
    <div class="matchTime">경기시간</div>
    <!-- 노란색 네모에 해당하는 버튼 -->
    <div class="game-container">
      <div class="match-prediction">
        <!-- 좌측 팀 리스트 -->
        <div class="team-button" @click="selectTeam('teamOne')">
          <TeamList :players="teamOnePlayers" :isRightTeam="false" />
        </div>
        <!-- 중앙 VS -->
        <div class="vs">VS</div>
        <!-- 우측 팀 리스트 -->
        <div class="team-button" @click="selectTeam('teamTwo')">
          <TeamList :players="teamTwoPlayers" :isRightTeam="true" />
        </div>
        <div class="betting-button" @click="showHint()">
          <span> {{ currentHint }}/{{ totalHints }} </span>
          힌트보기
        </div>
        <!-- 파란색 네모에 해당하는 버튼 -->
      </div>
    </div>
    <!-- 모달 창 구현 -->
    <div v-if="showRankModal" class="rank-modal">
      <div class="rank-modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <RankView :nickname="nickname" :rankData="rankData" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import Header from "@/components/common/Header.vue";
import TeamList from "@/views/TeamList.vue";
import RankView from "@/components/common/RankView.vue";

const currentRound = ref(1);
const totalRounds = 10;
const currentHint = ref(1);
const totalHints = 4;
const showRankModal = ref(false);

const showHint = () => {
  if (currentHint.value < totalHints) {
    currentHint.value++;
  } else {
    // 모든 라운드가 완료된 경우, 필요한 로직을 여기에 추가
    console.log("All rounds completed");
  }
};

const selectTeam = (team) => {
  if (currentRound.value < totalRounds) {
    currentRound.value++;
  } else {
    // 모든 라운드가 완료된 경우, 필요한 로직을 여기에 추가
    showRankModal.value = true;
    console.log("All hints completed");
  }
};

const closeModal = () => {
  // 모달을 숨김
  showRankModal.value = false;
  // 라운드와 힌트를 초기 값으로 리셋
  // currentRound.value = 1;
  // currentHint.value = 1;
  // 추가적으로 게임을 재시작하는 로직을 여기에 포함할 수 있음
  // 예를 들어, 게임 데이터를 초기화하거나, 새 게임을 시작하기 위한 API 호출 등
};

const teamOnePlayers = [
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Top",
    image: "Position_Diamond-Top.png",
    status: "Destiny fan",
  },
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Jungle",
    image: "logo2.png",
    status: "Destiny fan",
  },
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Mid",
    image: "atrox.png",
    status: "Destiny fan",
  },
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Bot",
    image: "atrox.png",
    status: "Destiny fan",
  },
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Support",
    image: "atrox.png",
    status: "Destiny fan",
  },
];
const teamTwoPlayers = [
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Top",
    image: "Position_Diamond-Top.png",
    status: "Destiny fan",
  },
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Jungle",
    image: "logo2.png",
    status: "Destiny fan",
  },
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Mid",
    image: "atrox.png",
    status: "Destiny fan",
  },
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Bot",
    image: "atrox.png",
    status: "Destiny fan",
  },
  {
    id: 1,
    name: "아트록스",
    individualPosition: "Support",
    image: "atrox.png",
    status: "Destiny fan",
  },
];
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  height: 100vh; /* 전체 화면 높이 */
}

.round {
  position: absolute;
  left: 50%; /* 화면의 중앙 */
  transform: translateX(-50%); /* 정확한 중앙 정렬을 위해 */
  top: 120px;
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
    ),
    url("@/assets/arcade-background.png"); /* 예시 배경 */
  background-size: cover;
  box-shadow: 0 0 10px 10px rgba(0, 255, 255, 0.75); /* 네온 효과 */
  border: 1px solid #00ffea; /* 네온 테두리 */
  border-radius: 15px; /* 둥근 모서리 */
}

.vs {
  margin: 0 20px;
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
  background-color: #ffcc00; /* 노란색 배경 */
  color: #000; /* 텍스트 색상 */
  cursor: pointer; /* 클릭 가능한 요소로 표시 */
  visibility: hidden;
}

.betting-button {
  position: absolute;
  bottom: 120px; /* 위치 조정 */
  left: 50%; /* 화면의 중앙 */
  transform: translateX(-50%); /* 정확한 중앙 정렬을 위해 */
  padding: 10px 20px;
  background-color: #0000ff; /* 파란색 배경 */
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
</style>
