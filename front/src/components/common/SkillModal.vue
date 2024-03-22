<template>
  <div
    v-if="isVisible"
    class="modal-container"
    :style="{ top: modalTop + 'px', left: modalLeft + 'px' }"
    @mouseleave="closeModal"
  >
    <div class="modal-content">
      <h2>{{ name }}</h2>
      <p>{{ tooltip }}</p>
      <p>{{ description }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";

const { name, description } = defineProps({
  name: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  tooltip: {
    type: String,
  },
});

const isVisible = ref(true);
const modalTop = ref(0);
const modalLeft = ref(0);

const closeModal = () => {
  isVisible.value = false;
};

// 모달의 위치를 마우스 위치에 따라 동적으로 조정
window.addEventListener("mousemove", (event) => {
  modalTop.value = event.clientY + 20; // 마우스 위치에서 약간 아래로
  modalLeft.value = event.clientX + 20; // 마우스 위치에서 약간 오른쪽으로
});
</script>

<style scoped>
.modal-container {
  position: fixed;
  z-index: 9999;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: #0a323c;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

h2 {
  margin-top: 0;
}

p {
  margin-bottom: 0;
}
</style>
