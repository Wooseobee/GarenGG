import { defineStore } from "pinia";
import { championInfo } from "@/api/champion.js";

export const useChampionStore = defineStore("champion", {
  state: () => ({
    championKeys: [],
    championNames: [],
    championIds: [],
    championSquareImgUrls: [],
    isInitialized: false,
  }),
  actions: {
    async initialize() {
      //champion id, 이름정보 가져오기
      await championInfo()
        .then(({ data }) => {
          this.championKeys = data.map((item) => item.championKey);
          this.championNames = data.map((item) => item.name);
          this.championIds = data.map((item) => item.id);
          this.championSquareImgUrls = data.map(
            (item) =>
              "https://ddragon.leagueoflegends.com/cdn/14.4.1/img/champion/" +
              item.id +
              ".png"
          );
          this.isInitialized = true;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
});
