import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import ChampView from "@/views/ChampView.vue";
import PlaygroundView from "@/views/PlaygroundView.vue";
import CombinationView from "@/views/CombinationView.vue";
import DetailView from "@/views/DetailView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/champ",
      name: "champ",
      component: ChampView,
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
  ],
});

export default router;
