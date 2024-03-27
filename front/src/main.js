import "bootstrap/dist/css/bootstrap.min.css"
import 'bootstrap-icons/font/bootstrap-icons.css'
import "bootstrap"

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { useChampionStore } from '@/stores/championStore';
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
const championStore = useChampionStore();
championStore.initialize(); // 스토어 초기화 함수 호출
app.mount('#app')
