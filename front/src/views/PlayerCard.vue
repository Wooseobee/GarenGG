<template>
  <div class="player-card" :class="{ 'reverse-order': isRightTeam }">
    <img
      :src="getPositionImage(player.individualPosition)"
      alt="position"
      class="position-image"
    />
    <img
      :src="player.imgUrl"
      alt="champion"
      class="champion-image"
      width="90px"
      height="auto"
    />
    <div class="player-info">
      <h5>{{ player.championName }}</h5>
      <p>{{ player.summonerName }} #{{ player.riotIdTagline }}</p>
    </div>
    <div class="player-stats">
      <div v-if="currentHint >= 2 && player.firstBloodKill" class="firstBlood">
        !FirstBlood!
      </div>
      <div v-if="currentHint >= 1" class="enemyMissingPings">
        미아핑 : {{ player.enemyMissingPings }}
      </div>
      <div v-if="currentHint >= 4" class="kd">
        K/D : {{ player.kills }} / {{ player.deaths }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  player: Object,
  isRightTeam: Boolean, // 오른쪽 팀 여부를 결정하는 새로운 prop
  currentHint: Number,
});

// 이미지 경로를 처리하는 메서드
const getPositionImage = (path) => {
  switch (path) {
    case "TOP":
      path = "Top";
      break;
    case "JUNGLE":
      path = "Jungle";
      break;
    case "MIDDLE":
      path = "Mid";
      break;
    case "BOTTOM":
      path = "Bot";
      break;
    case "UTILITY":
      path = "Support";
      break;
    default:
      break;
  }
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

.player-stats {
  display: flex;
  flex-direction: column;
  margin: 10px;
}

.enemyMissingPings {
  color: #0ac8b9;
}

.firstBlood {
  color: #ff0000;
}

.kd {
  color: #c8aa6e;
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
  color: #f0e6d2;
}

.player-info p {
  margin: 0;
}
.player-card.reverse-order {
  flex-direction: row-reverse; /* 순서를 바꾸는 flex 속성 */
}
</style>
