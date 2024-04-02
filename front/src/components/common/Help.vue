<template>
  <div @mouseover="showModal" @mouseleave="closeModal">
    <button>
      <img src="@/assets/help.png" alt="" />
    </button>
  </div>
  <div
    v-if="isVisible"
    class="modal-container"
    :style="{ top: modalTop + 'px', left: modalLeft + 'px' }"
  >
    <div class="modal-content">
      <p>{{ modalContent }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";
const props = defineProps({
  modalContent: {
    type: String,
    required: true,
  },
});
const isVisible = ref(false);
const modalTop = ref(0);
const modalLeft = ref(0);
const showModal = () => {
  isVisible.value = true;
};
const closeModal = () => {
  isVisible.value = false;
};
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

.modat-content p {
  margin-bottom: 0;
}

button {
  background-color: #091428;
  border: 0;
  max-width: 32px;
  max-height: 32px;
}
button img {
  height: 32px;
  width: 32px;
  margin-left: 10px;
}
</style>
