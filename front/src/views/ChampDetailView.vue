<template>
  <Header :backButton="true" />
  <div class="container">
    <ChampDetail :champname="champname" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
const route = useRoute();
import axios from "axios";
import ChampDetail from "@/components/champ/ChampDetail.vue";
import Header from "@/components/common/Header.vue";
import router from "@/router";
const champname = ref(route.params.champname);
onMounted(() => {});
</script>

<style scoped>
.container {
  margin-top: 5%;
}
</style>
