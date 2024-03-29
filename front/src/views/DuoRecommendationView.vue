<template>
  <div>
      <div class="content" >
        <!-- 기존 내용 -->
        <Header />
          <div>
            <RouterView></RouterView>
          </div>
      </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import Header from "@/components/common/Header.vue";
import router from '@/router';

</script>

<style >
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
  transition: opacity 1.0s ease;
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