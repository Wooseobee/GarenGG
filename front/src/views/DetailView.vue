<template>
  <div>
    <Header />
    <Team_Comp :champions="champions" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Header from "@/components/common/Header.vue";
import Team_Comp from "@/components/team_comp/Team_Comp.vue";
import { useRoute, useRouter } from 'vue-router';
import { localAxios } from "@/utils/http-commons";

const route = useRoute()
const router = useRouter()
const champions = ref([])

onMounted(async () => {
  const summonerName = route.query.summonerName;
  const tagLine = route.query.tagLine;

  if (!summonerName || !tagLine) {
    router.push({ name: 'champ-rec' });
    return;
  }

  try {
    const response = await localAxios.get(`/championRecommendation/${summonerName}-${tagLine}`);
    console.log(response);

    if (response.status === 200) {
      // 성공적으로 데이터를 받아온 경우
      champions.value = response.data.championRecommendationDatas.map(champ => ({
        id: champ.id,
        name: champ.name,
        imageUrl: champ.imageUrl, // 이미지 URL 추가 예시
        winRate: champ.winRate // 승률 추가 예시
      }));
    } else {
      // 상태 코드가 200이 아닌 경우 에러 처리
      console.log(response);
      console.error(response.data.championRecommendationDatas.errorMessage);
      alert(response.data.championRecommendationDatas.errorMessage);
      router.push({ name: 'champ-rec' });
    }
  } catch (error) {
    // 네트워크 에러나 서버 에러 처리
    console.error("API 호출 중 오류 발생:", error);
    alert("API 호출 중 오류 발생: " + error.response.data.errorMessage); // 에러 메시지 보여주기
    router.push({ name: 'champ-rec' });
  }
});

</script>

<style lang="scss" scoped>
</style>