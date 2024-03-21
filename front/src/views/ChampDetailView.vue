<template>
  <div>챔피언 디테일 페이지</div>
  <ChampDetail :champname="champname" />
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
const route = useRoute();
import axios from "axios";
import ChampDetail from "@/components/champ/ChampDetail.vue";
const champname = ref(route.params.champname);

onMounted(() => {});
</script>

<style scoped></style>
