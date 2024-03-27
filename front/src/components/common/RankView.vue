<template>
  <div class="rank-view">
    <h3>RANKING !</h3>
    <ul class="rank-table">
      <li class="rank-table-header">
        <span class="header-rank">RANK</span>
        <span class="header-nickname">NICKNAME</span>
        <span class="header-score">SCORE</span>
        <span class="header-time">TIME</span>
      </li>
      <li
        v-for="entry in rankEntries"
        :key="entry.rank"
        :class="['rank-entry', { 'my-game-entry': entry.uuid === props.uuid }]"
      >
        <span class="rank">{{ entry.rank }}</span>
        <span class="nickname">{{ entry.nickname }}</span>
        <span class="score">{{ entry.score }}</span>
        <span class="time">{{ entry.time }}</span>
      </li>
      <!--여기에 게임을 한 유저의 등수와 닉네임, 점수, 게임 종료 시간을 보여주고 싶어 어떻게 하면 좋을까?-->
    </ul>
    <button @click="startNewGame" class="new-game-button">
      새로운 게임 시작하기
    </button>
  </div>
</template>

<script setup>
import { defineProps, ref, watchEffect } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({
  score: {
    type: Number,
    required: true,
  },
  nickname: {
    type: String,
    default: "",
  },
  rank: {
    type: Array,
    default: () => [], // rank에 기본값으로 빈 배열을 지정
  },
  uuid: {
    type: String,
  },
});
const rankEntries = ref([]);

watchEffect(() => {
  if (props.rank && props.rank.length > 0) {
    rankEntries.value = props.rank.map((entry, index) => {
      const dateOnly = entry.createdAt.split("T")[0];
      const rankIndex = index + 1;
      const rankSuffix = getRankSuffix(rankIndex);
      console.log(props.uuid);

      return {
        rank: `${rankIndex}${rankSuffix}`,
        nickname: entry.nickname,
        score: entry.score.toString(),
        time: dateOnly,
        uuid: entry.uuid,
      };
    });
  }
});

function getRankSuffix(index) {
  const j = index % 10,
    k = index % 100;
  if (j === 1 && k !== 11) {
    return "st";
  }
  if (j === 2 && k !== 12) {
    return "nd";
  }
  if (j === 3 && k !== 13) {
    return "rd";
  }
  return "th";
}

const startNewGame = () => {
  window.location.reload();
};
</script>

<style scoped>
.rank-view {
  background-color: black;
  color: white;
  padding: 20px;
  text-align: center;
}

.rank-table {
  list-style: none;
  padding: 0;
  width: 100%; /* 테이블의 전체 너비 사용 */
  margin: 0; /* 마진 제거 */
}

.rank-table-header,
.rank-entry {
  display: flex;
  justify-content: space-between; /* 공간을 균등하게 분배 */
  padding: 5px 0; /* 위 아래 패딩 설정 */
}

.rank-table-header {
  font-weight: bold;
  border-bottom: 1px solid white; /* 구분선 추가 */
}

.header-rank,
.header-nickname,
.header-score,
.header-time,
.rank,
.nickname,
.score,
.time {
  min-width: 100px; /* 최소 너비 설정 */
  text-align: center; /* 텍스트 왼쪽 정렬 */
}

.rank-entry {
  align-items: center; /* 요소들을 세로로 중앙 정렬 */
  text-align: right; /* 텍스트 오른쪽 정렬 */
}

.new-game-button {
  padding: 10px 20px;
  margin-top: 20px;
  background-color: ffffff;
  color: #091428;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
}

.new-game-button:hover {
  background-color: #ffffff;
}

.my-game-entry {
  border: 2px solid #d0d0d0; /* 네모 박스의 테두리 설정 */
  border-radius: 5px; /* 테두리의 모서리를 둥글게 처리 */
  font-weight: bold; /* 텍스트 굵기 강조 */
}
</style>
