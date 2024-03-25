<template>
    <div>
        <div>{{ name }}</div>
        <div>{{ position }}</div>
    </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
const name = ref("");
const position = ref("");
const route = useRoute();

onMounted(()=>{
    name.value = route.query.name;
    position.value = route.query.position;
})
</script>
<style scoped>

</style>