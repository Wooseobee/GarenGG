import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import ChampRecView from "@/views/ChampRecView.vue";
import PlaygroundView from "@/views/PlaygroundView.vue";
import CombinationView from "@/views/CombinationView.vue";
import DetailView from "@/views/DetailView.vue";
import ChampDetailView from "@/views/ChampDetailView.vue";
import PredictMatchView from "@/views/PredictMatchView.vue";
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
      path: "/combination",
      name: "combination",
      component: CombinationView,
    },
    {
      path: "/playground",
      name: "playground",
      component: PlaygroundView,
    },
    {
      path: "/predictmatch",
      name: "predictmatch",
      component: PredictMatchView,
    },
  ],
});

export default router;
