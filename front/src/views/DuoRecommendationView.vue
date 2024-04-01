<template>
  <div class="duo">
    <img
      v-if="backGroundStore.backgroundImageUrl != ''"
      class="background-img"
      :src="backGroundStore.backgroundImageUrl"
      alt=""
      data-aos="fade-in"
    />
    <video v-else autoplay muted loop class="background-img">
      <source src="@\assets\background-video-m-01.mp4" type="video/mp4" />
    </video>
    <div class="content">
      <Header />
      <div>
        <RouterView></RouterView>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, computed } from "vue";
import Header from "@/components/common/Header.vue";
import router from "@/router";
import { useBackGroundStore } from "@/stores/backGroundStore.js";
const backGroundStore = useBackGroundStore();
// const backgroundImageUrl = ref(""); // 배경 이미지 URL을 저장할 반응형 데이터 속성

// 배경 이미지를 동적으로 설정하는 메소드
// function updateBackgroundImage(url) {

// 배경 이미지 스타일을 계산된 속성으로 정의
const backgroundStyle = computed(() => {
  return {
    backgroundImage: `url(${backGroundStore.backgroundImageUrl})`,
    backgroundSize: "cover",
    backgroundPosition: "center",
    backgroundAttachment: "fixed",
  };
});

onMounted(() => {
  backGroundStore.updateBackgroundImage("");
  console.log(
    "backGroundStore.backgroundImageUrl :",
    backGroundStore.backgroundImageUrl
  );
});
</script>

<style>
.duo {
  /* --default-color: #091428; */
  /* --default-color: #010a13; */
  --default-color-rgb: 255, 255, 255;
  /* --background-color: #010a13; */
  /* --background-color-rgb: 0, 0, 0; */
  width: 100%;
  min-height: 100vh;
  position: relative;
  padding: 160px 0 80px 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.duo .background-img {
  position: absolute;
  inset: 0;
  display: block;
  width: 100%;
  height: 100%;
  -o-object-fit: cover;
  object-fit: cover;
  z-index: 1;
}

.duo:before {
  content: "";
  background: rgba(var(--background-color-rgb), 0.5);
  position: absolute;
  inset: 0;
  z-index: 2;
}

.duo .container {
  position: relative;
  /* margin-left: 150px; */
  z-index: 3;
}

/* 배경 설정 */
/* .background-img {
  position: absolute;
  inset: 0;
  display: block;
  width: 100%;
  height: 100%;
  -o-object-fit: cover;
  object-fit: cover;
  z-index: 0;
  pointer-events: none;
} */

.section-title {
  text-align: center;
  padding-bottom: 60px;
}

.section-title h1 {
  font-size: 100px;
  font-weight: 700;
  position: relative;
}

.section-title h1:before,
.section-title h1:after {
  content: "";
  width: 200px;
  height: 2px;
  background: #c89b3c;
  /* background: var(--accent-color);   */
  display: inline-block;
}

.section-title h1:before {
  margin: 0 15px 10px 0;
}

.section-title h1:after {
  margin: 0 0 10px 15px;
}

.section-title p {
  margin-bottom: 0;
}

/* intro setting */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1s ease;
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
  opacity: 0;
}

.intro-container {
  /* 로고 컨테이너 스타일 */
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column; /* 요소들을 수직 방향으로 정렬 */
  height: 100vh; /* 화면 전체 높이 */
}

/* 기존 스타일 */
</style>
