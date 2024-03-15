<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="cards col-md-12">
        <div class="cards d-flex flex-wrap justify-content-center">
          <card
            champimage="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"
          />
          <card
            champimage="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Garen_0.jpg"
          />
          <card
            champimage="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Garen_0.jpg"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Card from "../common/Card.vue";
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
