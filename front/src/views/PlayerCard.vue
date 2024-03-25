<template>
  <div class="player-card" :class="{ 'reverse-order': isRightTeam }">
    <img
      :src="getImage(player.individualPosition)"
      alt="hi"
      class="player-image"
    />
    <div class="player-info">
      <h5>{{ player.name }}</h5>
      <p>{{ player.role }}</p>
      <p>{{ player.status }}</p>
    </div>
    <div class="status-div">?</div>
    <div class="isFirstKill">?</div>
    <div class="kd">K/D</div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  player: Object,
  isRightTeam: Boolean, // 오른쪽 팀 여부를 결정하는 새로운 prop
});

// 이미지 경로를 처리하는 메서드
const getImage = (path) => {
  return new URL(`../assets/Position_Diamond-${path}.png`, import.meta.url)
    .href;
};
</script>

<style scoped>
.player-card {
  display: flex;
  align-items: center;
  margin-bottom: 10px; /* 카드 간격 */
}

.isFirstKill {
  /* visibility: hidden; */
  margin: 5px;
}

.kd {
  /* visibility: hidden; */
}
.status-div {
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  background-color: #ff0000; /* Red background */
  color: white; /* White text */
  cursor: pointer; /* Pointer cursor on hover */
  outline: none; /* Remove default focus outline */
  margin: 10px; /* 카드 간격 */
}
.status-button:hover {
  background-color: #ff4d4d; /* Lighter red on hover */
}
.player-image {
  width: 60px; /* 이미지 크기 */
  height: 60px;
  border-radius: 50%; /* 이미지 원형 */
  margin-right: 10px; /* 이미지와 정보 사이 간격 */
}

.player-info {
  flex: 1;
}

.player-info h5 {
  margin: 0;
}

.player-info p {
  margin: 0;
}
.player-card.reverse-order {
  flex-direction: row-reverse; /* 순서를 바꾸는 flex 속성 */
}
</style>
