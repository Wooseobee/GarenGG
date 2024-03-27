<template>
  <div>
    <Header />
    <section id="hero" class="hero">
      <img src="@/assets/garen-background.gif" alt="" data-aos="fade-in" />

      <div class="container">
        <div class="row">
          <RouterLink :to="{ name: 'predictmatch' }" class="link-wrapper">
            <GameCardView />
            <div class="member-info text-center">
              <h4>경기 승패 맞추기2</h4>
              <p>누가 이겼는지 맞춰보세요~2</p>
            </div>
          </RouterLink>
        </div>
      </div>
    </section>
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

.hero {
  --default-color: #ffffff;
  --default-color-rgb: 255, 255, 255;
  --background-color: #000000;
  --background-color-rgb: 0, 0, 0;
  width: 100%;
  min-height: 100vh;
  position: relative;
  padding: 160px 0 80px 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.hero img {
  position: absolute;
  inset: 0;
  display: block;
  width: 100%;
  height: 100%;
  -o-object-fit: cover;
  object-fit: cover;
  z-index: 1;
}

.hero:before {
  content: "";
  background: rgba(var(--background-color-rgb), 0.5);
  position: absolute;
  inset: 0;
  z-index: 2;
}

.hero .container {
  position: relative;
  margin-left: 150px;
  z-index: 3;
}

.hero h2 {
  color: white;
  margin: 0;
  font-size: 58px;
  font-weight: 700;
}

.hero p {
  color: white;
  margin: 5px 0 0 0;
  font-size: 25px;
}

.hero .btn {
  width: 66px;
  background-color: #005a82;
}

.hero .sign-up-form {
  width: 110%;
  height: 80px;
  margin-top: 20px;
  padding: 10px;
  border-radius: 7px;
  background: #fff;
  box-shadow: 0 15px 30px 0 rgba(0, 0, 0, 0.1);
}

.hero .sign-up-form .form-control {
  height: 100%;
  border: none;
}

.hero .sign-up-form .form-control:active,
.hero .sign-up-form .form-control:focus {
  outline: none;
  box-shadow: none;
}

.hero .sign-up-form input[type="submit"] {
  box-shadow: 0 15px 30px 0 rgba(0, 0, 0, 0.2);
  background-color: var(#87ceeb);
  border-color: var(#87ceeb);
  padding: 8px 20px 10px 20px;
  border-radius: 7px;
  color: var(--contrast-color);
}

@media (max-width: 768px) {
  .hero h2 {
    font-size: 32px;
  }

  .hero p {
    font-size: 18px;
  }
}
</style>
