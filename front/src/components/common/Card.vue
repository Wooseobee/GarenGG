<template>
  <div
    class="card"
    :class="{ flipped: isFlipped, shaking: isShaking, lightburst: isLight }"
    @click="toggleCard"
  >
    <div
      class="face face-front"
      @mouseenter="startShaking"
      @mouseleave="stopShaking"
    >
      <!-- card content -->

      <div class="image-container">
        <img src="@/assets/cardback.jpg" alt="heropy" />
      </div>
    </div>
    <div class="face face-back">
      <div class="image-container">
        <img :src="champImageUrl" alt="champ" />
      </div>
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
// 카드 뒤집기
const isLight = ref(false);
const isFlipped = ref(false);
const toggleCard = () => {
  // if (!isFlipped.value) {
  // isFlipped.value = true;
  // } else {
  //   router.push({
  //     name: "champ-detail",
  //     params: { champname: props.champname },
  //   });
  // }
  isLight.value = true;
  setTimeout(() => {
    isFlipped.value = !isFlipped.value;
    isLight.value = false;
  }, 300);
};

// 카드 흔들기
const isShaking = ref(false);
const startShaking = () => {
  isShaking.value = true;
};

const stopShaking = () => {
  isShaking.value = false;
};
const champImageUrl = `https://ddragon.leagueoflegends.com/cdn/img/champion/loading/${props.champname}_0.jpg`;
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
  /* background-color: #091428; */
  background-color: #fff;
  /* test */
  border: 4px solid transparent; /* 테두리 초기값 설정 */
  animation: goldGlow 2s infinite alternate; /* 황금빛 효과 애니메이션 */
  /* test end */
}
/* test */
.card::before {
  content: ""; /* 가상 요소에 내용 없음 */
  position: absolute; /* 절대적 위치 설정 */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 0) 0%,
    rgba(255, 255, 255, 0.5) 25%,
    rgba(255, 255, 255, 0.8) 50%,
    rgba(255, 255, 255, 0.5) 75%,
    rgba(255, 255, 255, 0) 100%
  ); /* 빛 무리 효과 설정 */
  animation: lightBurst 0.5s ease-out forwards; /* 애니메이션 설정 */
  opacity: 0; /* 초기 투명도 설정 */
  pointer-events: none; /* 이벤트 허용하지 않음 */
}
/* test end */
@keyframes goldGlow {
  0% {
    border-color: #c8aa6e; /* 황금빛으로 설정 */
    box-shadow: 0 0 10px 0 #c8aa6e; /* 황금빛 그림자 추가 */
  }
  50% {
    border-color: #c89b3c;
    box-shadow: 0 0 10px 0 #c89b3c;
  }
  100% {
    border-color: #785a28; /* 황금빛 강조 */
    box-shadow: 0 0 20px 0 #785a28; /* 강조된 황금빛 그림자 */
  }
}
/* 카드 흔들기 */
@keyframes shake {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(0.98);
    opacity: 0.7;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

.shaking {
  animation: shake 3s ease-in-out infinite alternate;
}
/* 카드 흔들기 끝 */

/* 카드 빛무리 */
@keyframes lightBurst {
  0% {
    transform: scale(0);
    opacity: 1;
  }
  100% {
    transform: scale(2);
    opacity: 0;
  }
}
.lightburst {
  position: absolute;
  width: 100%;
  height: 100%;
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 0) 0%,
    rgba(255, 255, 255, 0.5) 25%,
    rgba(255, 255, 255, 0.8) 50%,
    rgba(255, 255, 255, 0.5) 75%,
    rgba(255, 255, 255, 0) 100%
  );
  animation: lightBurst 0.5s ease-out forwards;
  opacity: 0;
  pointer-events: none;
}
/* 카드 빛무리 끝 */
.card .face {
  /* test */

  /* testend */
  width: 100%;
  height: 100%;
  /* border: 0px solid #091428; */
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 1s;
  position: absolute;
  backface-visibility: hidden; /*1_로고의 뒷면이나, 뒷카드의 앞면 둘다 숨겨져 있어야 함.*/
  background-color: #091428;
}
.card .face-front {
  transform: rotateY(0deg);
}
.flipped {
  transform: rotateY(180deg);
}
.card .face-back {
  transform: rotateY(
    180deg
  ); /*2_뒷카드는 뒤집혀있어야 하므로 Y축으로 180도 돌려서 안보이게 처리*/
}
.image-container {
  width: calc(100% - 20px); /* Adjust as needed */
  height: 100%;
  padding: 0 10px; /* Adjust padding as needed */
  display: flex;
  justify-content: center;
  align-items: center;
}
.card .face-back img,
.card .face-front img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  /* filter: grayscale(); */
  /* opacity: 0.7; */
}
</style>
