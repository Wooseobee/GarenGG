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
    <div class="name-entry">
      <input type="text" v-model="nickname" placeholder="ABC" maxlength="10" />
      <button @click="enterName">ENTER</button>
      <button @click="clearName">CLEAR</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const nickname = ref("");
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

const enterName = () => {
  // 사용자 이름을 순위 데이터에 추가하는 로직
};

const clearName = () => {
  userName.value = "";
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
.nickname,
.stage,
.time,
.difficulty {
  width: 100px; /* 적절한 너비로 조정 */
}

.name-entry {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.name-entry input {
  text-align: center;
}

.name-entry button {
  margin-left: 10px;
}
</style>
