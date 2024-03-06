<template>
    <div class="container">
        <div class="input-group mb-3">
            <input type="text" class="form-control" placeholder="플레이어 이름#KR1" aria-label="Recipient's username"
                aria-describedby="button-addon2" />
            <button class="btn btn-primary" type="button" id="button-addon2">가렌</button>
        </div>
    </div>
    <div>
        <div class="row">
            <div class="col-4">
                <!-- 챔피언 정보 표 -->
                <table class="table table-bordered">
                    <thead class="table-light">
                        <tr>
                            <th>챔피언</th>
                            <th>KDA</th>
                            <th>승률</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in items" :key="index">
                            <td><img :src="item.url" :style="{width: item.int + 'px', height: item.int + 'px'}"></td>
                            <td>{{ item.String }}</td>
                            <td>{{ item.int1 }}</td>
                            <td>{{ item.int2 }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-6">
                <!-- 이미지 표시 -->
                <div v-for="(imageData, index) in imageItems" :key="'image-' + index" class="image-container">
                    <img class="rounded" :src="imageData.url" :style="{ width: imageData.int + 'px', height: imageData.int + 'px' }">
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const items = ref([
    { String: "문자열1", int1: 100, int2: 200 },
    { String: "문자열2", int1: 300, int2: 400 },
    // 더 많은 데이터...
])

// 이미지 데이터 예시
const imageItems = ref([
    { url: "https://ddragon.leagueoflegends.com/cdn/14.4.1/img/champion/Aatrox.png", int: 100 },
    { url: "https://ddragon.leagueoflegends.com/cdn/14.4.1/img/champion/Ahri.png", int: 150 },
    // 더 많은 이미지 데이터...
])

// 데이터 개수 제한 (최대 5개)
const limitedItems = items.value.slice(0, 5)
</script>

<style scoped>
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
