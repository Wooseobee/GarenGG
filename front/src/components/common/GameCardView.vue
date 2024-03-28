<template>
  <div class="member-img">
    <img src="@/assets/garen.avif" class="img-fluid" alt="" />
  </div>
</template>

<script setup></script>

<style scoped>
.member-img {
  position: relative;
  border: 4px solid #c89b3c;
  box-shadow: 0 15px 35px -10px rgba(0, 0, 0, 0.2);
  display: flex;
  justify-content: center;
  align-items: center;
  max-width: 308px; /* 컨테이너의 최대 너비를 설정 */
  margin: auto; /* 컨테이너를 중앙에 배치 */
}

.member-img img {
  max-width: 100%; /* 이미지의 최대 너비를 컨테이너 너비에 맞춤 */
  height: 560px; /* 이미지의 높이를 자동으로 조절하여 비율을 유지 */
  transition: transform 0.3s ease-in-out, filter 0.3s ease-in-out;
}

.member-img:hover img {
  filter: brightness(0.4); /* 이미지 밝기를 줄여 흐려지게 표현 */
}
@media (max-width: 1024px) {
  .member-img {
    margin: 0 auto; /* 모바일 화면에서도 중앙 정렬 */
    max-width: 200px; /* 모바일 화면에서의 컨테이너 최대 너비 조정 */
  }
}
</style>
