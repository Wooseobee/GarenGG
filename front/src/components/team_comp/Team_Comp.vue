<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="cards col-md-12">
        <div class="cards d-flex flex-wrap justify-content-center">
          <card
            v-for="(champion, index) in transformedChampions"
            :key="index"
            :champname="champion.name"
            :cardType="cardType"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Card from "../common/Card.vue";
import { computed, ref } from "vue";

const cardType = ref("champRec");

const props = defineProps({
  champions: Array,
});

const imageURLPrefix =
  "https://ddragon.leagueoflegends.com/cdn/img/champion/loading";

// 받은 champions 배열을 가공하여 새로운 배열을 생성합니다.
const transformedChampions = computed(() => {
  return props.champions.map((champ) => ({
    image: `${imageURLPrefix}/${champ.id}_0.jpg`,
    name: champ.id,
  }));
});
</script>

<style scoped>
.cards {
  margin-top: 100px; /* 필요한 경우 조절하세요 */
  flex-wrap: wrap;
}

@media (min-width: 768px) {
  .cards {
    flex-wrap: nowrap;
  }
}
</style>
