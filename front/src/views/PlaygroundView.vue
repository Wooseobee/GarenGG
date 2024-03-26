<template>
  <div>
    <Header />
    <div class="container">
      <div class="row">
        <RouterLink :to="{ name: 'predictmatch' }" class="link-wrapper">
          <GameCardView />
          <div class="member-info text-center">
            <h4>경기 승패 맞추기2</h4>
            <p>누가 이겼는지 맞춰보세요~2</p>
          </div>
        </RouterLink>
        <Card :champname="'Garen'" />
      </div>
    </div>
  </div>
</template>

<script setup>
import Header from "@/components/common/Header.vue";
import GameCardView from "@/components/common/GameCardView.vue";
import Card from "@/components/common/Card.vue";
import { RouterLink } from "vue-router";
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  flex-wrap: wrap; /* 카드들이 넘칠 경우 다음 줄로 */
  gap: 20px; /* 카드 간격 */
  padding-top: 10%; /* 헤더와의 여백 */
}

.row {
  width: 100%;
  display: flex;
  justify-content: center; /* 모든 카드들을 중앙 정렬 */
  align-items: flex-start; /* 세로 방향 시작점 정렬 */
  flex-wrap: wrap; /* 줄바꿈을 허용 */
}

.link-wrapper {
  text-decoration: none; /* 링크의 밑줄 제거 */
  color: inherit; /* 링크의 기본 색상을 상속 */
  margin: 0; /* 링크 주위의 마진을 제거 */
  max-width: 300px; /* 최대 너비 제한 */
  width: 100%; /* 반응형을 위한 너비 설정 */
}

@media (max-width: 768px) {
  .row {
    justify-content: center; /* 화면이 작아지면 중앙 정렬 */
  }
}
</style>
