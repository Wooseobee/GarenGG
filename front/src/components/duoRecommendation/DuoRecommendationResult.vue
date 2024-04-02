<template>
  <div class="container">
    <transition name="fade">
      <div
        v-if="resultChampions == null && showIntro"
        class="intro-container section-title"
      >
        <!-- 로고 이미지 또는 텍스트 -->
        <img
          src="@/assets/icon.png"
          alt="듀오챔피언추천 로고"
          :style="{ width: 100 + 'px', height: 100 + 'px' }"
        />
        <h1>이런!</h1>
        <h2>
          {{ curPosition }} 위치의 {{ curChampion }}.. 듀오 챔피언을 가지고 있지
          않네요.
        </h2>
      </div>
    </transition>
    <div class="intro-container">
      <!-- 뒤로가기 버튼 -->
      <router-link v-if="backButton == true" to="#" @click="goBack">
        <img
          src="@/assets/goback.png"
          alt=""
          style="width: 200px; height: 200"
        />
      </router-link>
    </div>
    <div v-if="resultChampions != null" class="position-selector">
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
                class="cardElement"
              />
            </div>
          </div>
        </div>
      </div>
      <div v-if="resultChampions != null" class="info-container">
        <p class="info-text">카드를 클릭하면 상세 정보를 볼 수 있습니다</p>
        <Help
          :modalContent="modalContent"
          :modalContent2="modalContent2"
          :modalContent3="modalContent3"
        />
      </div>
    </div>
  </div>
</template>
<script setup>
import { onMounted, ref, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { duoRecommendationChampion } from "@/api/duoRecommend";
import Card from "@/components/common/Card.vue";
import topImage from "@/assets/Position_Diamond-Top.png";
import jungleImage from "@/assets/Position_Diamond-Jungle.png";
import midImage from "@/assets/Position_Diamond-Mid.png";
import bottomImage from "@/assets/Position_Diamond-Bot.png";
import utilityImage from "@/assets/Position_Diamond-Support.png";
import { useChampionStore } from "@/stores/championStore";
import { useBackGroundStore } from "@/stores/backGroundStore";
import Help from "@/components/common/Help.vue";
const modalContent = ref("");
const modalContent2 = ref("왼쪽 카드부터 1등 2등, 3등이에요.");
const modalContent3 = ref("게임 횟수, 승률 기반으로 계산했어요.");
const route = useRoute();
const championStore = useChampionStore();
const { championNames, championIds } = championStore;
const showIntro = ref("true");
const backButton = ref("false");
const router = useRouter();
const curChampion = ref("");
const curPosition = ref("");
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

function checkFinalConsonant(str) {
  // 문자열의 마지막 문자를 추출
  const c = str.charAt(str.length - 1);

  // 한글 유니코드 범위 내에서만 작동
  if (c >= "가" && c <= "힣") {
    // '가'로부터의 거리를 이용하여 받침 유무 판단
    const charCode = c.charCodeAt(0) - "가".charCodeAt(0);
    // 한글은 각 자음마다 28개의 문자를 가지고 있음 (21개의 모음 * 28개의 받침)
    // 따라서, 28로 나눈 나머지가 0이면 받침이 없는 것
    return charCode % 28 !== 0;
  } else {
    return false;
  }
}

onMounted(() => {
  curChampion.value = route.query.name;
  curPosition.value = route.query.position;
  if (checkFinalConsonant(curChampion.value)) {
    modalContent.value = `${curChampion.value}이랑 어울리는 챔피언을 포지션 별로 추천해줘요!`;
  } else {
    modalContent.value = `${curChampion.value}랑 어울리는 챔피언을 포지션 별로 추천해줘요!`;
  }

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
      if (!data || data.length == 0) {
        resultChampions.value = null;
        setTimeout(() => {
          showIntro.value = false;
          setTimeout(() => {
            backButton.value = true;
          }, 1200);
        }, 2000); // 3초 후 로고 숨김

        console.log("이걸로행시켜");
        return;
      }
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

      if (resultChampions.value.length >= 1)
        selectedPosition.value = resultChampions.value[0].position;

      console.log(
        "챔피언불러오기완료. resultChampions: ",
        resultChampions.value
      );
      console.log("champions.value[0] ", resultChampions.value[0]);
      console.log("시작 포지션 설정 완료. ", selectedPosition.value);
    },
    (err) => {
      console.log("듀챔추가져오는중 에러발생, err : ", err);
    }
  );
});

const goBack = () => {
  router.go(-1);
};
</script>
<style scoped>
/* 로고 */
.container {
  z-index: 2;
}

/* 카드 설정 */
.cards {
  margin-top: 0px; /* 필요한 경우 조절하세요 */
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
  margin-top: -140px;
  margin-bottom: 0px;
  padding: 0px;
}
.position-image:hover {
  cursor: pointer;
}

.position-image-container {
  display: flex; /* flexbox 레이아웃 활성화 */
  flex-direction: column; /* 항목들을 세로로 나열 */
  align-items: center; /* 세로축 중앙 정렬 */
  border-radius: 50%;
  margin-bottom: 10px;
  padding: 0px;
}
.position-image {
  width: 50px;
  height: 50px;
  margin-top: 0px;
  margin-bottom: 0px;
  padding: 0px;
}
.position-image.selected {
  width: 100px;
  height: 100px;
  font-size: 30px;
}

/* .unselected{
    display : none; 
} */

/* 뒤에 빛나는설정 */
.position-image-container > .effecting {
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

/* 안내 문구 스타일 */
.info-text {
  text-align: center;
  margin-top: 20px; /* Team_Comp 컴포넌트와의 여백 */
}
.info-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px; /* Team_Comp 컴포넌트와의 여백 */
}
</style>
