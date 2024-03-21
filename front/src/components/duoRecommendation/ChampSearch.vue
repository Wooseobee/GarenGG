<template>
    <div class="container">
        <div class="role-selector">
            <label v-for="role in ['TOP', 'JUNGLE', 'MID', 'BOT', 'SUP']" :key="role">
                <input type="radio" v-model="selectedPosition" :value="role"> {{ role }}
            </label>
        </div>

        <div class="input-group mb-3">
            <input v-model="searchValue" type="text" class="form-control" placeholder="챔피언 입력" aria-label="Recipient's username"
                aria-describedby="button-addon2" />
            <button class="btn btn-primary" type="button" id="button-addon2">가렌</button>
        </div>
    </div>

    <div>
        <div class="row">
            <div class="image-container col">
                <!-- 이미지 표시 -->
                <div v-for="(champion, index) in filteredChampions" :key="'image-' + index" class="image-container">
                    <img class="rounded" :src="champion.url" :style="{ width: champion.int + 'px', height: champion.int + 'px' }">
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import { useChampionStore} from "@/stores/championStore.js"

const championStore = useChampionStore();
const searchValue = ref("");
// 챔피언 데이터
const champions = ref([]);
const selectedPosition = ref(""); // 선택된 포지션을 위한 ref 추가
// searchValue에 따라 imageItems를 필터링하는 computed 속성
const filteredChampions = computed(() => {
    if (!searchValue.value) {
        return champions.value; // 검색어가 없으면 전체 목록 반환
    }
    console.log("searchValue : "+searchValue.value);
    console.log("champion.value[0].id : "+champions.value[0].id);
    return champions.value.filter((item) =>
        item.id.includes(searchValue.value) || item.name.includes(searchValue.value)
    );
});

//페이지 들어올 때, 챔피언 이미지 받아오기
onMounted(() => {
    console.log("onMounted 호출이요");
    console.log("isInitialized? : "+championStore.isInitialized);
    console.log(championStore.championIds[0])
    champions.value =  championStore.championIds.map((championId, index) => ({
        id : championId,
        url : championStore.championSquareImgUrls[index],
        name : championStore.championNames[index],
        int : 100
    }));

    
    //챔피언 Id(영어이름같은거) 달라고 해서 받기

    //url 세팅해서 
    
})


</script>

<style scoped>

.image-container{
    display : flex;
    flex-wrap : wrap;
    gap : 10px;

}
.container {
    margin-top: 130px;
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
</style>
