<template>
  <div
    class="card"
    :class="{ flipped: isFlipped, effecting: isEffect && !isFlipped }"
    @click="toggleCard"
  >
    <div
      class="face face-front"
      @mouseenter="startEffect"
      @mouseleave="stopEffect"
    >
      <!-- card content -->
      <!-- <audio hidden="true" ref="audio">
        <source :src="audiopath" type="audio/ogg" />
      </audio> -->
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
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useChampionStore } from "@/stores/championStore.js";
const championStore = useChampionStore();
const router = useRouter();
const props = defineProps({
  champname: {
    type: String,
    required: true,
  },
});
const { championKeys, championIds } = championStore;

// champinfo 객체 생성

// 소리
const audiolink = computed ( () =>{
  const champinfo = {};
  for (let i = 0; i < championKeys.length; i++) {
  champinfo[championIds[i]] = championKeys[i];
  }
  const champkey = champinfo[props.champname];
  return `https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/ko_kr/v1/champion-choose-vo/${champkey}.ogg`
}
);
const playSound = (sound) => {
  if (sound) {
    const audio = new Audio(sound);
    audio.play();
  }
};
// 쓰레기 코드
// const audio = ref(null);
// const playFlipSound = async () => {
//   try {
//     console.log(champkey);
//     console.log(audio.value);
//     audio.value?.play();
//   } catch (error) {
//     console.error('에러 발생:', error);
//   }
// };
//
// 카드 뒤집기
const isFlipped = ref(false);
const toggleCard = () => {
  isEffect.value = false;
  if (!isFlipped.value) {
    setTimeout(() => {
      isFlipped.value = !isFlipped.value;
    }, 500);
    playSound(audiolink.value);
  } else {
    router.push({
      name: "champ-detail",
      params: { champname: props.champname },
    });
  }
};

// 마우스오버 효과
const isEffect = ref(false);
const startEffect = () => {
  isEffect.value = true;
};

const stopEffect = () => {
  isEffect.value = false;
};
const champImageUrl = computed( () =>{
  return `https://ddragon.leagueoflegends.com/cdn/img/champion/loading/${props.champname}_0.jpg`});
</script>

<style scoped>
.cards {
  padding: 20px;
}
.card {
  width: 308px;
  height: 560px;
  margin-left: 15px;
  margin-right: 15px;
  position: relative;
  float: left;
  perspective: 600px; /*3_카드가 돌아갈때 원근법을 주기 위해-앞 뒷면 돌아가는 걸 보기 위해 부모에게 적용*/
  transform-style: preserve-3d;
  transition: transform 0.5s;
  background-color: #091428;
  display: flex;
  justify-content: center;
  align-items: center;
}
.effecting {
  animation: goldGlow 2s infinite alternate,
    bounce 3s ease-in-out infinite alternate;
}

@keyframes goldGlow {
  0% {
    border-color: #c8aa6e;
    box-shadow: 0 0 20px 0 rgba(200, 170, 110, 0.7),
      0 0 30px 0 rgba(200, 170, 110, 0.5), 0 0 40px 0 rgba(200, 170, 110, 0.3);
  }
  40% {
    border-color: #c89b3c;
    box-shadow: 0 0 20px 0 rgba(200, 155, 60, 0.7),
      0 0 30px 0 rgba(200, 155, 60, 0.5), 0 0 40px 0 rgba(200, 155, 60, 0.3);
  }
  100% {
    border-color: #785a28;
    box-shadow: 0 0 20px 0 rgba(120, 90, 40, 0.7),
      0 0 30px 0 rgba(120, 90, 40, 0.5), 0 0 40px 0 rgba(120, 90, 40, 0.3);
  }
}
/* 카드 바운스 */
@keyframes bounce {
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

/* 카드 바운스 끝 */

.card .face {
  /* test */

  /* testend */
  width: 100%;
  height: 100%;
  /* border: 0px solid #091428; */
  /* border-radius: 10px; */
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 1s;
  position: absolute;
  backface-visibility: hidden; /*1_로고의 뒷면이나, 뒷카드의 앞면 둘다 숨겨져 있어야 함.*/
  /* background-color: red; */
  background-color: #091428;
}
.card .face-front {
  transform: rotateY(0deg);
}
/* test */

.flipped {
  transform: rotateY(180deg);
}
.card .face-back {
  transform: rotateY(
    180deg
  ); /*2_뒷카드는 뒤집혀있어야 하므로 Y축으로 180도 돌려서 안보이게 처리*/
}
.image-container {
  max-width: 100%; /* Adjust as needed */
  max-height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.card .face-back img,
.card .face-front img {
  width: 308px;
  height: 560px;
  object-fit: cover;
  /* filter: grayscale(); */
  /* opacity: 0.7; */
}
</style>
