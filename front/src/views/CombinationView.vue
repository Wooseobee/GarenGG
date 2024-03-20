<template>
  <div class="container section-title" data-aos="fade-up">
    <h1>듀오 챔피언 추천</h1>
    <!-- <h1>Duo Champion Recommendation</h1> -->
  </div>
  <div>
    챔피언과 포지션을 선.택. 하세요!
    <!-- <Header /> -->
    <MyChamp />
    <Team_Comp />
  </div>
</template>

<script setup>
import MyChamp from "@/components/champ/MyChamp.vue";
import Team_Comp from "@/components/team_comp/Team_Comp.vue";
import Header from "@/components/common/Header.vue";
</script>

<style scoped>
/*--------------------------------------------------------------
# Section Title
--------------------------------------------------------------*/
.section-title {
  text-align: center;
  padding-bottom: 60px;
}

.section-title h1 {
  font-size: 100px;
  font-weight: 700;
  position: relative;
}

.section-title h1:before,
.section-title h1:after {
  content: "";
  width: 200px;
  height: 2px;
  background: #c89b3c;
  /* background: var(--accent-color);   */
  display: inline-block;
}

.section-title h1:before {
  margin: 0 15px 10px 0;
}

.section-title h1:after {
  margin: 0 0 10px 15px;
}

.section-title p {
  margin-bottom: 0;
}
</style>
