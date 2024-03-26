<template>
  <div class="rank-view">
    <h3>ENTER YOUR NICKNAME!</h3>
    <div class="rank-table">
      <div class="rank-table-header">
        <span class="header-rank">RANK</span>
        <span class="header-nickname">NICKNAME</span>
        <span class="header-score">SCORE</span>
      </div>
      <ul>
        <li v-for="entry in rankEntries" :key="entry.rank" class="rank-entry">
          <span class="rank">{{ entry.rank }}</span>
          <span class="nickname">{{ entry.nickname }}</span>
          <span class="score">{{ entry.score }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";

const props = defineProps({
  score: {
    type: Number,
    required: true,
  },
  nickname: {
    type: String,
    default: "",
  },
});
const rankEntries = ref([
  // 예시 데이터, 실제 데이터는 백엔드 시스템으로부터 로드할 수 있음
  {
    rank: "1st",
    score: "1130913",
    nickname: "AAA",
  },
  {
    rank: "2nd",
    score: "1130913",
    nickname: "AAA",
  },
  {
    rank: "3rd",
    score: "1130913",
    nickname: "AAA",
  },
]);
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
}
.rank-table-header {
  display: flex;
  justify-content: space-between;
  font-weight: bold;
  padding-bottom: 10px;
}

.header-rank,
.header-nickname,
.header-score {
  width: 100px; /* 적절한 너비로 조정 */
}
.rank-entry {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.score,
.stage,
.time,
.difficulty {
  width: 100px; /* 적절한 너비로 조정 */
}
</style>
