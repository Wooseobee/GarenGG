<template>
  <div>
    <div
      :id="`carouselExampleCaptions`"
      class="carousel slide"
      data-bs-ride="carousel"
    >
      <!-- Inner -->
      <div class="carousel-inner">
        <div
          v-for="(skinGroup, groupIndex) in skinGroups"
          :key="groupIndex"
          class="carousel-item"
          :class="{ active: groupIndex === 0 }"
        >
          <div class="d-flex justify-content-center">
            <div
              v-for="(skin, index) in skinGroup"
              :key="index"
              class="carousel-item-container"
            >
              <img
                :src="getSkinImageURL(skin.num)"
                class="d-block w-100 carousel-image"
                alt=""
              />

              <div class="carousel-caption d-none d-md-block">
                <h5>{{ skin.name }}</h5>
                <!-- Add any other relevant information here -->
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Controls -->
      <button
        class="carousel-control-prev"
        type="button"
        :data-bs-target="`#carouselExampleCaptions`"
        data-bs-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button
        class="carousel-control-next"
        type="button"
        :data-bs-target="`#carouselExampleCaptions`"
        data-bs-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>

      <!-- Indicators -->

      <div class="carousel-indicators">
        <button
          v-for="(skinGroup, groupIndex) in skinGroups"
          :key="groupIndex"
          type="button"
          :data-bs-target="`#carouselExampleCaptions`"
          :data-bs-slide-to="groupIndex"
          :class="{ active: groupIndex === 0 }"
          :aria-label="`Slide ${groupIndex + 1}`"
        ></button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  skins: {
    type: Array,
    required: true,
  },
  getSkinImageURL: {
    type: Function,
    required: true,
  },
});

const skinGroups = [];
for (let i = 0; i < props.skins.length; i += 5) {
  skinGroups.push(props.skins.slice(i, i + 5));
}
</script>

<style scoped>
.carousel-image {
  max-width: 100%; /* 이미지가 부모 요소의 너비를 넘지 않도록 설정 */
  max-height: 660px; /* 이미지의 최대 높이를 설정 */
  margin: auto;
  padding: 0 10px;
}
.carousel-item-container {
  position: relative; /* 상대 위치 설정 */
  max-width: 260px;
}
.carousel-caption {
  position: absolute; /* 절대 위치 설정 */
  bottom: 10; /* 아래로부터의 거리 */
  left: 0; /* 왼쪽으로부터의 거리 */
  right: 0; /* 오른쪽으로부터의 거리 */
  /* background-color: rgba(0, 0, 0, 0.5); */
  color: white; /* 글자색 설정 */
  padding: 10px; /* 여백 설정 */
  word-wrap: break-word; /* 긴 단어가 캡션을 넘어가지 않도록 줄 바꿈 */
  word-break: keep-all; /* 중단점을 space로*/
}
</style>
