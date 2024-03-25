<template>
  <div class="card" :class="{ flipped: isFlipped }" @click="toggleCard">
    <div class="face face-front">
      <img src="@/assets/riotlogo.png" alt="heropy" />
    </div>
    <div class="face face-back">
      <img :src="champImageUrl" alt="champ" />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
const props = defineProps({
  champname: {
    type: String,
    required: true,
  },
});
const isFlipped = ref(false);
const toggleCard = () => {
  if (!isFlipped.value) {
    isFlipped.value = true;
  } else {
    router.push({ name: "champ-detail", params: { champname: champname } });
  }
  // isFlipped.value = !isFlipped.value;
};
const champImageUrl = `https://ddragon.leagueoflegends.com/cdn/img/champion/loading/${champname}_0.jpg`;
</script>

<style scoped>
.cards {
  padding: 20px;
}
.card {
  width: 200px;
  height: 300px;
  margin-left: 15px;
  margin-right: 15px;
  position: relative;
  float: left;
  perspective: 600px; /*3_카드가 돌아갈때 원근법을 주기 위해-앞 뒷면 돌아가는 걸 보기 위해 부모에게 적용*/
  transform-style: preserve-3d;
  transition: transform 0.5s;
}
.card:last-child {
  margin-right: 0;
}
.card .face {
  width: 200px;
  height: 300px;
  border: 0px solid #091428;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 1s;
  position: absolute;
  backface-visibility: hidden; /*1_로고의 뒷면이나, 뒷카드의 앞면 둘다 숨겨져 있어야 함.*/
  background-color: #fff;
}
.card .face-front {
  transform: rotateY(0deg);
}
.card.flipped {
  transform: rotateY(180deg);
}
.card .face-back {
  transform: rotateY(
    180deg
  ); /*2_뒷카드는 뒤집혀있어야 하므로 Y축으로 180도 돌려서 안보이게 처리*/
}

.card .face-front img {
  width: 90px;
  /* filter: grayscale(); */
  /* opacity: 0.7; */
}
.card .face-back img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
