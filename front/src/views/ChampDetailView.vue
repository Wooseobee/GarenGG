<template>
  <div>
    <ChampDetail :champname="champname" />
  </div>
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
