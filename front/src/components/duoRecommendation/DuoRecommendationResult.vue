<template>
  <div class="container">
    <!-- <div v-if="selectedChampion.id != undefined" class="position-selector"> -->
        <div class="position-selector">
            <div v-for="(positionImage, index) in filteredPositionImages" :key="index" class="position-image-container">
                <img :src="positionImage.url" @click="selectPosition(positionImage)" :class="{'selected highlighted effecting': selectedPosition === positionImage.position}" class="position-image">
                <div :class="{'selected' : selectedPosition === positionImage }">{{ positionImage.position }}</div>
            </div>
        </div>
    <div class="row justify-content-center">
      <div class="cards col-md-12">
        <div class="cards d-flex flex-wrap justify-content-center">
          <card
            v-for="(champion, index) in transformedChampions"
            :key="index"
            :champname="champion.id"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { onMounted, ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import { duoRecommendationChampion } from '@/api/duoRecommend';
import Card from "@/components/common/Card.vue"
import topImage from '@/assets/Position_Diamond-Top.png';
import jungleImage from '@/assets/Position_Diamond-Jungle.png';
import midImage from '@/assets/Position_Diamond-Mid.png';
import bottomImage from '@/assets/Position_Diamond-Bot.png';
import utilityImage from '@/assets/Position_Diamond-Support.png';
const name = ref("");
const position = ref("");
const route = useRoute();
const champions = ref([]);
const selectedPosition = ref("");

// 여기에 포지션에 맞게 바꿔서 보여주면 되겟따
const transformedChampions = computed(() => {
  for(let i = 0; i < champions.value.length; i++){
    console.log(champions.value[i].position ," ",selectedPosition.value);
  }  
  return champions.value.filter((champ) => champ.position === selectedPosition.value);
});

const positionImages = ref([
    { position: "TOP", url: topImage },
    { position: "JUNGLE", url: jungleImage },
    { position: "MIDDLE", url: midImage },
    { position: "BOTTOM", url: bottomImage },
    { position: "UTILITY", url: utilityImage }
]);

const filteredPositionImages = computed( () => {
    console.log("dfsdf",route.query.position);
    return positionImages.value.filter((positionImage) => positionImage.position !==  route.query.position);
})

function selectPosition(positionImage){
    console.log("click!, position  : ", positionImage);
    if(selectedPosition.value != positionImage.position){
        selectedPosition.value = positionImage.position;
        console.log("selectedPosition.value : ", selectedPosition.value)
    }
    else{
        console.log("유지")
    }
}

onMounted(()=>{
    name.value = route.query.name;
    position.value = route.query.position;
    const params = {
        name : name.value,
        position : position.value,
    }
    duoRecommendationChampion(params,({data})=>{
        // console.log(data);
        champions.value = data.map((champ) => ({
            position : champ.position,
            name : champ.champion,
            id : champ.id
        }));

        if(champions.value.length > 1)
            selectedPosition.value = champions.value[0].position;

        console.log("챔피언불러오기완료. ",champions.value);
        console.log("시작 포지션 설정 완료. ",selectedPosition.value);
    },(err)=>{
        console.log("듀챔추가져오는중 에러발생")
    })

})
</script>
<style scoped>

/* 카드 설정 */
.cards {
  margin-top: 100px; /* 필요한 경우 조절하세요 */
  flex-wrap: wrap;
}

@media (min-width: 768px) {
  .cards {
    flex-wrap: nowrap;
  }
}

.position-selector {
    display: flex; /* flexbox 레이아웃 활성화 */
    flex-direction: row; /* 항목들을 가로로 나열 */
    justify-content: center; /* 중앙 정렬 */
    flex-wrap: wrap; /* 필요시 줄바꿈 */
    gap: 10px; /* 이미지 사이의 간격 */
}

.position-image-container {
    display: flex; /* flexbox 레이아웃 활성화 */
    flex-direction: column; /* 항목들을 세로로 나열 */
    align-items: center; /* 세로축 중앙 정렬 */
}


/* 뒤에 빛나는설정 */
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

</style>