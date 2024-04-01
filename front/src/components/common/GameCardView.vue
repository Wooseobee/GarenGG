<template>
  <div class="member-img">
    <img
      :src="getRandomImage(randomSkinNum)"
      class="img-fluid"
      :alt="randomSkinNum"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
onMounted(() => {
  randomSkinNum.value = getRandomSkinNum();
});
const skinNum = ref([0, 1, 2, 3, 4, 5, 6, 10, 11, 13, 14, 22, 23, 24, 33]);
const randomSkinNum = ref(0);
const randomImagePath = ref("./src/assets/garenimage/Garen_23.jpg");

const getRandomSkinNum = () => {
  return String(
    skinNum.value[Math.floor(Math.random() * skinNum.value.length)]
  );
};
const getRandomImage = (num) => {
  return new URL(`../../assets/garenimage/Garen_${num}.jpg`, import.meta.url);
};
</script>

<style scoped>
.member-img {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  max-width: 308px; /* 컨테이너의 최대 너비를 설정 */
  margin: auto; /* 컨테이너를 중앙에 배치 */
}

.member-img img {
  box-shadow: 0 15px 35px -10px rgba(0, 0, 0, 0.2);
  border: 1px solid #c89b3c;
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
