import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCounterStore = defineStore("champ", () => {
  const champNames = ref([]);
  const getChampion = function () {
    axios({
      method: "get",
      url: "https://ddragon.leagueoflegends.com/cdn/14.4.1/data/ko_KR/champion.json",
    })
      .then((res) => {
        console.log(res);
        champNames.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return {
    champNames,
    getChampion,
  };
});
