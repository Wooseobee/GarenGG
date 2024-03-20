<template>
  <h2>{{ champname }}</h2>
  <div>챔피언 디테일 페이지</div>
  <div>{{ champData }}</div>
  <div>
    {{ latestPatch }}
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
const champname = ref("");
const latestPatch = ref("");
const getLatestPatch = function () {
  axios({
    method: "get",
    url: "https://ddragon.leagueoflegends.com/api/versions.json",
  })
    .then((res) => {
      latestPatch.value = res.data[0];
    })
    .catch((err) => {
      console.log(err);
    });
};

const champData = ref([]);
const getChampData = function () {
  axios({
    method: "get",
    url: `https://ddragon.leagueoflegends.com/cdn/14.6.1/data/ko_KR/champion/${champname.value}.json`,
  })
    .then((res) => {
      console.log(res);
      champData.value = res.data.data;
    })
    .catch((err) => {
      console.log(err);
    });
};
const route = useRoute();
onMounted(async () => {
  champname.value = route.params.champname;
  await getLatestPatch();
  getChampData();
});
</script>

<style scoped></style>
