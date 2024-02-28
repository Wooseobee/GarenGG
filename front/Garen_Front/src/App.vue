<template>
  <div class="background_gif">
    <div class="background_overlay"></div>
    <div class="main">
      <Header/>
      <RouterView/>
    </div>
  </div>
</template>

<script setup>
import Header from "@/components/common/Header.vue";
</script>

<style scoped>
.background_gif {
  /* 전체 페이지를 커버하도록 설정 */
  width: 100vw;
  height: 100vh;
  /* 배경 이미지를 담을 자식 요소에 대한 위치 설정 */
  position: relative;
}

.background_overlay {
  /* 배경 이미지로 PNG 파일 지정 */
  background-image: url('@/assets/garen-background.gif');
  /* 배경 이미지가 컨테이너에 맞게 조정되도록 설정 */
  background-size: cover;
  /* 배경 이미지가 페이지 스크롤에 따라 움직이지 않도록 설정 */
  background-attachment: fixed;
  /* 전체 요소를 커버하도록 설정 */
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  /* 배경 이미지에만 투명도 적용 */
  opacity: 0.6;
  /* z-index를 사용하여 배경을 내용 뒤로 보냄 */
  z-index: -1;
}

.main {
  /* 내용에는 투명도 적용하지 않음 */
  opacity: 1;
  /* 필요한 경우, z-index를 조정하여 내용을 더 부각시킴 */
  position: relative;
  z-index: 1;
}
</style>
