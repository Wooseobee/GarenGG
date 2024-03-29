<template>
  <div>
    <Header />
    <div v-if="champions.length === 0" class="loading-container">
      <img src="@/assets/ornn-loading.gif" alt="로딩 중" data-aos="fade-in" />
      <p>AI가 분석 중입니다...</p>
    </div>
    <!-- 조건부 렌더링을 사용하여 Team_Comp와 안내 문구를 같이 보여줌 -->
    <template v-else>
      <Team_Comp :champions="champions" />
      <p class="info-text">카드를 클릭하면 상세 정보를 볼 수 있습니다</p>
    </template>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Header from "@/components/common/Header.vue";
import Team_Comp from "@/components/team_comp/Team_Comp.vue";
import { useRoute, useRouter } from "vue-router";
import { localAxios } from "@/utils/http-commons";

const route = useRoute();
const router = useRouter();
const champions = ref([]);

onMounted(async () => {
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
    console.log(response);

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
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
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
</style>
