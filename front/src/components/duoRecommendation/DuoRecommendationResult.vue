<template>
  <div class="container">
    <div class="position-selector">
      <div
        v-for="(positionImage, index) in filteredPositionImages"
        :key="index"
        class="position-image-container"
      >
        <img
          :src="positionImage.url"
          @click="selectPosition(positionImage)"
          :class="{
            'selected highlighted effecting':
              selectedPosition === positionImage.position,
          }"
          class="position-image"
        />
        <div :class="{ selected: selectedPosition === positionImage.position }">
          {{ positionImage.position }}
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="cards col-md-12">
        <div class="cards d-flex flex-wrap justify-content-center unselected">
          <div
            v-for="(champiionsPerPosition, idx) in resultChampions"
            :key="idx"
          >
            <div v-show="selectedPosition == champiionsPerPosition.position">
              <card
                v-for="(champion, index) in champiionsPerPosition.champions"
                :key="index"
                :champname="champion.id"
                :champKoreanName="champion.name"
                :rank="index + 1"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { onMounted, ref, computed } from "vue";
import { useRoute } from "vue-router";
import { duoRecommendationChampion } from "@/api/duoRecommend";
import Card from "@/components/common/Card.vue";
import topImage from "@/assets/Position_Diamond-Top.png";
import jungleImage from "@/assets/Position_Diamond-Jungle.png";
import midImage from "@/assets/Position_Diamond-Mid.png";
import bottomImage from "@/assets/Position_Diamond-Bot.png";
import utilityImage from "@/assets/Position_Diamond-Support.png";
import { useChampionStore } from "@/stores/championStore";
import { useBackGroundStore } from "@/stores/backGroundStore";
const route = useRoute();
const championStore = useChampionStore();
const { championNames, championIds } = championStore;

const resultChampions = ref([]);
const selectedPosition = ref("");
const backgroundStore = useBackGroundStore();
const positionImages = ref([
  { position: "TOP", url: topImage },
  { position: "JUNGLE", url: jungleImage },
  { position: "MIDDLE", url: midImage },
  { position: "BOTTOM", url: bottomImage },
  { position: "UTILITY", url: utilityImage },
]);

const filteredPositionImages = computed(() => {
  console.log("dfsdf", route.query.position);
  return positionImages.value.filter(
    (positionImage) => positionImage.position !== route.query.position
  );
});

function selectPosition(positionImage) {
  console.log("click!, position  : ", positionImage);
  if (selectedPosition.value != positionImage.position) {
    selectedPosition.value = positionImage.position;
    console.log("selectedPosition.value : ", selectedPosition.value);
  } else {
    console.log("유지");
  }
}

onMounted(() => {
  const params = {
    name: route.query.name,
    position: route.query.position,
  };

  duoRecommendationChampion(
    params,
    ({ data }) => {
      //id찾기
      let id;
      for (let i = 0; i < championNames.length; i++) {
        if (championNames[i] == route.query.name) {
          id = championIds[i];
          break;
        }
      }
      // ID값으로 배경설정
      backgroundStore.updateBackgroundImage(id);
      console.log(
        "결과 발표. id : ",
        id,
        "이고, 백그라운드 : ",
        backgroundStore.backgroundImageUrl
      );
      // console.log(data);
      resultChampions.value = data.reduce((acc, { position, champion, id }) => {
        // 해당 position이 이미 누적 객체에 존재하는지 확인
        const existingGroup = acc.find((group) => group.position === position);
        if (existingGroup) {
          // 이미 존재한다면, 현재 챔피언을 해당 포지션 그룹에 추가
          existingGroup.champions.push({ name: champion, id });
        } else {
          // 존재하지 않는다면, 새로운 포지션 그룹을 만들고 현재 챔피언을 추가
          acc.push({
            position,
            champions: [{ name: champion, id }],
          });
        }
        return acc;
      }, []);

      if (resultChampions.value.length > 1)
        selectedPosition.value = resultChampions.value[0].position;

      console.log("챔피언불러오기완료. ", resultChampions.value);
      console.log("champions.value[0] ", resultChampions.value[0]);
      console.log("시작 포지션 설정 완료. ", selectedPosition.value);
    },
    (err) => {
      console.log("듀챔추가져오는중 에러발생");
    }
  );
});
</script>
<style scoped>
/* 로고 */
.container {
  z-index: 2;
}
.selected {
  width: 100px;
  height: 100px;
  font-size: 50px;
}

/* 카드 설정 */
.cards {
  margin-top: 10px; /* 필요한 경우 조절하세요 */
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
  z-index: 2;
}

.position-image-container {
  display: flex; /* flexbox 레이아웃 활성화 */
  flex-direction: column; /* 항목들을 세로로 나열 */
  align-items: center; /* 세로축 중앙 정렬 */
  border-radius: 50%;
}

/* .unselected{
    display : none; 
} */

/* 뒤에 빛나는설정 */
.effecting {
  animation: goldGlow 2s infinite alternate,
    bounce 3s ease-in-out infinite alternate;
  border-radius: 50%;
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
