<template>
  <div class="card">
    <div class="face face-front">
      <img src="@/assets/riotlogo.png" alt="heropy" />
    </div>
    <div class="face face-back">
      <img src="@/assets/logo2.png" alt="" />
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.cards {
  padding: 20px;
}
.card {
  width: 150px;
  height: 220px;
  margin-right: 15px;
  position: relative;
  float: left;
  perspective: 600px; /*3_카드가 돌아갈때 원근법을 주기 위해-앞 뒷면 돌아가는 걸 보기 위해 부모에게 적용*/
}
.card:last-child {
  margin-right: 0;
}
.card .face {
  width: 150px;
  height: 220px;
  border: 1px solid lightgray;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 1s;
  position: absolute;
  backface-visibility: hidden; /*1_로고의 뒷면이나, 뒷카드의 앞면 둘다 숨겨져 있어야 함.*/
  background-color: #fff;
}
.card .face-front {
  transform: rotateY(0deg);
  background-image: url("https://heropy.blog/css/images/pattern.png");
}
.card:hover .face-front {
  transform: rotateY(-180deg);
}
.card .face-back {
  transform: rotateY(
    180deg
  ); /*2_뒷카드는 뒤집혀있어야 하므로 Y축으로 180도 돌려서 안보이게 처리*/
}
.card:hover .face-back {
  transform: rotateY(0deg);
}

.card .face-front img {
  width: 90px;
  filter: grayscale();
  opacity: 0.7;
}
.card .face-back img {
  height: 120px;
}
</style>
