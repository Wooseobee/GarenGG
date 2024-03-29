import { defineStore } from "pinia";

export const useBackGroundStore = defineStore("backGround", {
  state: () => ({
    backgroundImageUrl : null,
    championNames : [],
    championIds : [],
    championSquareImgUrls : [],
    isInitialized : false,
  }),
    actions: {
        updateBackgroundImage(championId) {
            if(championId == ""){
                this.backgroundImageUrl = "";
            }else{
            this.backgroundImageUrl =
              "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" +
              championId +
              "_0.jpg";
            }
        },
    },
});
