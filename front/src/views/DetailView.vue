<template>
  <div>
    <Header />
    <Team_Comp :champions="champions" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed, onBeforeMount } from "vue";
import Header from "@/components/common/Header.vue";
import Team_Comp from "@/components/team_comp/Team_Comp.vue";
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute()
const router = useRoute()
const champions = ref([
  {
    id:"Aatrox",
    name:"아트록스"
  },
  {
    id:"Jhin",
    name:"진"
  }
])

// onMounted(() => {

//   const summonerName = route.query.summonerName
//   const tagLine = route.query.tagLine

//   if (!summonerName || !tagLine) {
//     router.push({ name: 'champ-rec' })
//     return;
//   }

//   const URL = `/api/championRecommendation/${summonerName}-${tagLine}`
//   axios.get(URL)
//   .then((res) => {
//     champions.value = res.data.map(champ => ({
//         id: champ.id,
//         name: champ.name
//       }));
//   })
//   .catch((error) => {
//     console.log(error)
//   })
// })

</script>

<style lang="scss" scoped>

</style>
