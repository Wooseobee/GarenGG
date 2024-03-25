<template>
  <div>
    <transition name="fade">
    <div v-if="showIntro" class="intro-container section-title">
      <!-- 로고 이미지 또는 텍스트 -->
      <!-- <img src="@/assets/your-logo.png" alt="듀오챔피언추천 로고"> -->
      <h1> 듀ㅡ챔ㅡ추 </h1>
    </div>
  </transition>
    <transition name="fade">
      <div class="content" v-if="!showIntro">
        <!-- 기존 내용 -->
        <Header />
        <div class="container section-title" data-aos="fade-up">
          <h1>듀오 챔피언 추천</h1>
          <div>
            <RouterView></RouterView>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import Header from "@/components/common/Header.vue";
import router from '@/router';

const showIntro = ref(true);

setTimeout(() => {
  showIntro.value = false;
  router.push({
    name : "DuoRecommendationSearch"
  });
}, 3000); // 3초 후 로고 숨김
</script>

<style scoped>
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
.fade-enter-active, .fade-leave-active {
  transition: opacity 2s ease;
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
  opacity: 0;
}

.intro-container {
  /* 로고 컨테이너 스타일 */
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* 화면 전체 높이 */
}

/* 기존 스타일 */
</style>