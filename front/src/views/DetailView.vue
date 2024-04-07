<template>
  <div class="champRec">
    <img
      v-if="backGroundStore.backgroundImageUrl != ''"
      class="background-img"
      :src="backGroundStore.backgroundImageUrl"
      alt=""
      data-aos="fade-in"
    />
    <Header />
    <div v-if="champions.length === 0" class="loading-container">
      <img src="@/assets/ornn-loading.gif" alt="로딩 중" data-aos="fade-in" />
      <p>AI가 분석 중입니다...</p>
    </div>
    <!-- 조건부 렌더링을 사용하여 Team_Comp와 안내 문구를 같이 보여줌 -->
    <template v-else>
      <div>
        <Team_Comp :champions="champions" />
        <div class="info-container">
          <p class="info-text">카드를 클릭하면 상세 정보를 볼 수 있습니다</p>
          <Help
            :modalContent="modalContent"
            :modalContent2="modalContent2"
            :modalContent3="modalContent3"
          />
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import Header from "@/components/common/Header.vue";
import Team_Comp from "@/components/team_comp/Team_Comp.vue";
import Help from "@/components/common/Help.vue";
import { useRoute, useRouter } from "vue-router";
import { localAxios } from "@/utils/http-commons";
import { useBackGroundStore } from "@/stores/backGroundStore.js";

const modalContent = `유저의 최근 솔로랭크 100판을 기준으로 승률, 판수, 티어를 고려한 자체 알고리즘으로 새로운 챔피언을 추천합니다.`;
const modalContent2 = `잠재 요인 협업 필터링 방식을 사용하였으며 특이값 분해로 잠재 요인을 찾아 플레이 기록이 없는 챔피언의 점수를 유추합니다.`;
const modalContent3 = `DB에는 현재 시즌 티어가 있는 유저 270만명의 모스트 챔피언 정보를 저장했습니다.
자동화된 프로세스로 Riot Open API key 5000개를 발급받아 5000개의 스레드를 사용하여 동시에 API 호출하는 방식으로 데이터를 수집했습니다.
`;

const route = useRoute();
const router = useRouter();
const champions = ref([]);
const backGroundStore = useBackGroundStore();

// 배경 이미지 스타일을 계산된 속성으로 정의
const backgroundStyle = computed(() => {
  return {
    backgroundImage: `url(${backGroundStore.backgroundImageUrl})`,
    backgroundSize: "cover",
    backgroundPosition: "center",
    backgroundAttachment: "fixed",
  };
});

onMounted(async () => {
  //배경화면설정.
  backGroundStore.updateBackgroundImage("");

  const summonerName = route.query.summonerName;
  const tagLine = route.query.tagLine;

  if (!summonerName || !tagLine) {
    router.push({ name: "champ-rec" });
    return;
  }

  try {
    const response = await localAxios.get(
      `/championRecommendation/${summonerName}-${tagLine}`
    );
    // console.log(response);

    // 성공적으로 데이터를 받아온 경우
    champions.value = response.data.championRecommendationDatas.map(
      (champ) => ({
        id: champ.id,
        name: champ.name,
      })
    );
  } catch (error) {
    console.error("API 호출 중 오류 발생:", error);
    alert(error.response.data.errorMessage);
    router.push({ name: "champ-rec" });
  }
});
</script>

<style scoped>
.champRec {
  --default-color: #091428;
  --default-color: #010a13;
  --default-color-rgb: 255, 255, 255;
  --background-color: #010a13;
  --background-color-rgb: 0, 0, 0;
  /* width: 100%; */
  /* min-height: 100vh; */
  /* position: relative; */
  /* padding: 160px 0 80px 0; */
  display: flex;
  align-items: flex-start;
  align-items: center;
  justify-content: center;
}

.champRec .background-img {
  position: absolute;
  inset: 0;
  display: block;
  width: 100%;
  height: 100%;
  -o-object-fit: cover;
  object-fit: cover;
  z-index: 1;
}

/* .champRec:before {
  content: "";
  background: rgba(var(--background-color-rgb), 0.5);
  position: absolute;
  inset: 0;
  z-index: 2;
} */

.champRec .container {
  position: relative;
  /* margin-left: 150px; */
  z-index: 3;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  align-content: center;
  height: 100vh; /* 전체 화면 높이 */
}

/* 로딩중 텍스트 스타일 */
.loading-container p {
  font-size: 24px; /* 글씨 크기를 24px로 설정 */
  margin-top: 20px; /* 이미지와 텍스트 사이의 여백 */
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
