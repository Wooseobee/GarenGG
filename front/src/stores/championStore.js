import { defineStore } from "pinia";
import { championInfo } from "@/api/champion.js"

export const useChampionStore = defineStore("champion", {
  state: () => ({
    championNames : [],
    championIds : [],
    championSquareImgUrls : [],
    isInitialized : false,
  }),
    actions: {
       async initialize() {
            //champion id, 이름정보 가져오기
            console.log("initialize 호출")
            await championInfo()
            .then( ({data}) =>{
            this.championNames = data.map(item => item.name);
            this.championIds = data.map(item => item.id);
            this.championSquareImgUrls = data.map(item => "https://ddragon.leagueoflegends.com/cdn/14.4.1/img/champion/" + item.id+".png")
            this.isInitialized = true;
            console.log("initialize ㅊ호출 ㄹ완료")
            })
            .catch((err)=> {
            console.log(err);
            });
    },
}    
});
