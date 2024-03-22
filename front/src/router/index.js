import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import ChampRecView from "@/views/ChampRecView.vue";
import PlaygroundView from "@/views/PlaygroundView.vue";
import DuoRecommendationView from "@/views/DuoRecommendationView.vue";
import DetailView from "@/views/DetailView.vue";
import ChampDetailView from "@/views/ChampDetailView.vue";
import { useChampionStore } from "@/stores/championStore";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/:champname/detail",
      name: "champ-detail",
      component: ChampDetailView,
      props: true,
    },

    {
      path: "/champ-rec",
      name: "champ-rec",
      component: ChampRecView,
    },
    {
      path: "/detail",
      name: "detail",
      component: DetailView,
    },
    {
      path: "/duoRecommendation",
      name: "DuoRecommendation",
      component: DuoRecommendationView,
    },
    {
      path: "/playground",
      name: "playground",
      component: PlaygroundView,
    },
  ],
});

router.beforeEach(async (to, from, next) => {
  const championStore = useChampionStore();
  if (!championStore.isInitialized) {
    await championStore.initialize();
  }
  next();
});
export default router;
