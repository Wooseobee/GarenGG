<template>
  <div class="member" data-aos="fade-up" data-aos-delay="200">
    <div class="member-img">
      <img src="@/assets/garen.avif" class="img-fluid" alt="" />
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.member {
  width: 100%; /* 전체 폭을 사용합니다. */
  max-width: 300px; /* 최대 너비 제한 */
  margin: auto; /* 자동 마진으로 중앙 정렬 */
}

.member-img {
  position: relative;
}

.member-img img {
  width: 100%; /* 이미지가 member-img 컨테이너를 꽉 채우도록 */
  display: block; /* 이미지가 div 안에서 꽉 차도록 */
  margin-bottom: 20px;
}

.member-info h4,
.member-info p {
  margin-bottom: 10px; /* 제목과 문단 사이의 마진 */
}

.text-center {
  text-align: center; /* 텍스트 중앙 정렬 */
}
</style>
