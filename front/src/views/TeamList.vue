<template>
  <div class="team-list">
    <PlayerCard
      v-for="player in players"
      :key="player.id"
      :player="player"
      :isRightTeam="isRightTeam"
      :currentHint="currentHint"
      :tier="tier"
    />
  </div>
</template>

<script setup>
import PlayerCard from "@/views/PlayerCard.vue";
const props = defineProps({
  players: Array,
  isRightTeam: Boolean, // 각 팀이 오른쪽 팀인지 결정
  currentHint: Number,
  tier: String,
});
</script>

<style scoped>
.team-list {
  display: flex;
  flex-direction: column;
}
</style>
