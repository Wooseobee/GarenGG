<template>
  <div
    v-if="isVisible"
    class="modal-container"
    :style="{ top: modalTop + 'px', left: modalLeft + 'px' }"
    @mouseleave="closeModal"
  >
    <div class="modal-content">
      <h2>{{ name }}</h2>
      <p>{{ costBurn }}</p>
      <p>{{ cooldownBurn }}</p>
      <p>{{ rangeBurn }}</p>
      <p v-if="tooltip">
        {{ tooltip }} <br /><br />[?]로 표시된 값은 Riot API에서 제공하지 않는
        데이터입니다.<br />정확한 값은 LoL 클라이언트에서 확인하실 수
        있습니다.<br /><br />
      </p>
      <p>{{ description }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";

const props = defineProps({
  name: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  costBurn: {
    type: String,
  },
  cooldownBurn: {
    type: String,
  },
  rangeBurn: {
    type: String,
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
  max-width: 500px;
}

.modal-content {
  background-color: #010a13;
  border: 1px solid #c89b3c;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}
.modal-content h2 {
  color: #c89b3c;
  margin-top: 0;
}

p {
  margin-bottom: 0;
}
</style>
