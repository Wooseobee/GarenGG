import { defineStore } from "pinia";
import { championInfo } from "@/api/champion.js";

export const useChampionStore = defineStore("champion", {
  state: () => ({
    championNames: [],
    championIds: [],
    championSquareImgUrls: [],
  }),
  actions: {
    initialize() {
      //champion id, 이름정보 가져오기
      championInfo(
        ({ data }) => {
          // console.log(data);
          this.championNames = data.map((item) => item.name);
          this.championIds = data.map((item) => item.id);
          this.championSquareImgUrls = data.map(
            (item) =>
              "https://ddragon.leagueoflegends.com/cdn/14.4.1/img/champion/" +
              item.id +
              ".png"
          );
          // console.log("championName: " + this.championNames);
          // console.log("championIds: " + this.championIds);
          // console.log("championIds: " + this.championSquareImgUrls);
        },
        (err) => {
          console.log(err);
        }
      );
      //champion id 기반으로 이미지 url 완성하기
    },
  },
});
