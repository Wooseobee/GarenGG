<template>
    <div class="container">
        <transition name="fade">
        <div v-show="showIntro" class="intro-container section-title">
        <!-- 로고 이미지 또는 텍스트 -->
        <img src="@/assets/icon.png" alt="듀오챔피언추천 로고" :style="{ width: 100 + 'px', height: 100 + 'px'}">
        <h1> 듀오 챔피언 추천 </h1>
        </div>
      </transition>
      <div v-show="showIntro != true" class="section-title">
        <h1>듀오 챔피언 추천</h1>
        <div class="input-group mb-3">
            <input v-model="searchValue" type="text" class="form-control" placeholder="챔피언 입력" aria-label="Recipient's username"
                aria-describedby="button-addon2" />
        </div>

        <div class="image-container col">
            <!-- 이미지 표시 -->
            <div v-for="(champion, index) in filteredChampions" :key="'image-' + index" class="image-container">
                <img class="rounded"  :class="{'highlighted': selectedChampion.id === champion.id}"  :src="champion.url" @click="selectChampion(champion)" :style="{ width: champion.int + 'px', height: champion.int + 'px' }">
            </div>
        </div>

        <div v-if="selectedChampion.id != undefined" class="section-title">
            <h1>{{ selectedChampion.name }}</h1>
        </div>

        <!-- <div v-if="selectedChampion.id != undefined" class="role-selector">
            <label v-for="role in ['TOP', 'JUNGLE', 'MID', 'BOT', 'SUP']" :key="role">
            <input type="radio" v-model="selectedPosition" :value="role"> {{ role }}
            </label>
        </div> -->

        <div v-if="selectedChampion.id != undefined" class="position-selector">
            <div v-for="(positionImage, index) in filteredPositionImages" :key="index" class="position-image-container">
                <img :src="positionImage.url" @click="selectPosition(positionImage)" :class="{'selected highlighted': selectedPosition === positionImage}" class="position-image">
                <div :class="{'selected' : selectedPosition === positionImage }">{{ positionImage.position }}</div>
            </div>
        </div>

        <button v-if='selectedChampion.id != undefined && selectedPosition != ""' @click="searchDuoChamp()">
            추천!
        </button>
    </div>
    </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import { useChampionStore} from "@/stores/championStore.js"
import Header from "@/components/common/Header.vue"
import topImage from '@/assets/Position_Diamond-Top.png';
import jungleImage from '@/assets/Position_Diamond-Jungle.png';
import midImage from '@/assets/Position_Diamond-Mid.png';
import bottomImage from '@/assets/Position_Diamond-Bot.png';
import utilityImage from '@/assets/Position_Diamond-Support.png';
import router from '@/router';
const showIntro = ref(true);
const championStore = useChampionStore();
const searchValue = ref("");
// 챔피언 데이터
const champions = ref([]);
const selectedPosition = ref(""); // 선택된 포지션을 위한 ref 추가
const selectedChampion = ref({});

const positionImages = ref([
    { position: "TOP", url: topImage },
    { position: "JUNGLE", url: jungleImage },
    { position: "MIDDLE", url: midImage },
    { position: "BOTTOM", url: bottomImage },
    { position: "UTILITY", url: utilityImage }
]);

const filteredChampions = computed(() => {
    console.log("selectedChampion.value.id: "+selectedChampion.value.id)
    // selectedIndex.value가 -1이 아닌 경우 선택된 챔피언만을 포함하는 배열 반환
    if (selectedChampion.value.id != undefined) {
        // selectedChampion.int = 1000;
        return [selectedChampion.value];
    }
    // 그렇지 않은 경우, searchValue에 따라 필터링
    if (!searchValue.value) {
        return champions.value; // 검색어가 없으면 전체 목록 반환
    }
    return champions.value.filter((item) =>{
        let tmp = "";
        if( /^[A-Za-z]+$/.test(searchValue.value)){
            tmp = searchValue.value.toLowerCase();
        }
        else tmp = searchValue.value;
        console.log(tmp)
       return  item.id.toLowerCase().includes(tmp) || item.name.includes(tmp)
    }
    );
});

const filteredPositionImages = computed( () => {
    if(selectedPosition.value == ""){
        return positionImages.value;
    }
    else{
        return [selectedPosition.value];
    }
});

function selectChampion(champion){
    if(selectedChampion.value.id == undefined){
        selectedChampion.value = champion;
        selectedChampion.value.int = 500;
    }
    else{
        selectedChampion.value.int = 100;
        selectedChampion.value = {};
    }
}

function selectPosition(positionImage) {
    console.log("click!, position  : ", positionImage);
    if(selectedPosition.value == ""){
        selectedPosition.value = positionImage;
        console.log("selectedPosition.value : ", selectedPosition.value)
    }
    else{
        selectedPosition.value = "";
        console.log("disable")
    }
}

function searchDuoChamp(){
    // console.log(selectedChampion.id)
    if(selectedChampion.value.id == undefined || selectedPosition.value == "" ){
        alert("잘못된 요청입니다.")
        return;
    }
    // 예시: DuoRecommendationResult로 라우팅하는 메소드나 함수 내에서
    router.push({
    name: 'DuoRecommendationResult',
    query: {
        name: selectedChampion.value.name, // 챔피언 이름
        position: selectedPosition.value.position // 역할
    }
});
    // router.push("/result",selectedPosition.position, selectedChampion.value.name+"/");
    console.log("click")
}

//페이지 들어올 때, 챔피언 이미지 받아오기
onMounted(() => {
    champions.value =  championStore.championIds.map((championId, index) => ({
        id : championId,
        url : championStore.championSquareImgUrls[index],
        name : championStore.championNames[index],
        int : 100
    }));

    
    //챔피언 Id(영어이름같은거) 달라고 해서 받기

    //url 세팅해서 
    
})

setTimeout(() => {
  showIntro.value = false;

}, 100); // 3초 후 로고 숨김

</script>

<style scoped>
.role-selector{
    display : flex;
    flex-wrap : wrap;
    justify-content: center;
    gap : 10px;
}
.image-container{
    display : flex;
    flex-wrap : wrap;
    justify-content: center;
    gap : 10px;
}

.position-selector{
    display : flex;
    flex-wrap : wrap;
    justify-content: center;
    font-size : 20px;
    gap : 10px;
}

.selected{
    width: 200px;
    height: 200px;
    font-size : 50px;
}

.container {
    margin-top: 30px;
}

.btn {
    margin-left: 20px;
}

.row {
    margin-left: 70px;
}

.rounded {
    border-radius: 10%;
    overflow: hidden;
    display: inline-block;
}

.input-group {}

.highlighted {
    border: 2px solid #c89b3c; /* 강조 테두리 스타일 */
    border-radius: 10px; /* 이미지 모서리의 둥근 정도 조절 */
    box-shadow: 0 0 8px 2px rgba(255, 105, 180, 0.6), 
                0 0 20px 5px rgba(255, 20, 147, 0.4); /* 블러 처리와 그라데이션 효과 */
}


/* intro setting */


</style>
