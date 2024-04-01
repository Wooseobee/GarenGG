<template>
  <div>
    <Header />
    <section id="hero" class="hero">
      <img src="@/assets/garen-background.gif" alt="" data-aos="fade-in" />

      <section id="team" class="team">
        <div class="container justify-content-center">
          <div class="row gy-5">
            <div
              class="col-lg-4 col-md-6 member align-content-center"
              data-aos="fade-up"
              data-aos-delay="100"
            >
              <RouterLink :to="{ name: 'champ-rec' }">
                <GameCardView />
                <!-- <div class="member-img">
                  <img src="@/assets/garen.avif" class="img-fluid" alt="" />
                  <div class="social">
                  </div>
                </div> -->
                <div class="member-info text-center">
                  <h4>챔피언 추천</h4>
                  <p>승리하기 위한 새로운 챔피언을 찾아보세요</p>
                </div>
              </RouterLink>
            </div>

            <div
              class="col-lg-4 col-md-6 member"
              data-aos="fade-up"
              data-aos-delay="200"
            >
              <RouterLink :to="{ name: 'DuoRecommendation' }">
                <GameCardView />
                <!-- <div class="member-img">
                  <img src="@/assets/garen.avif" class="img-fluid" alt="" />
                  <div class="social"></div>
                </div> -->
                <div class="member-info text-center">
                  <h4>듀오 추천</h4>
                  <p>어떤 챔피언과 함께 하면 좋을지 알아보세요</p>
                </div>
              </RouterLink>
            </div>

            <div
              class="col-lg-4 col-md-6 member"
              data-aos="fade-up"
              data-aos-delay="300"
            >
              <RouterLink :to="{ name: 'playground' }">
                <GameCardView />
                <!-- <div class="member-img">
                  <img src="@/assets/garen.avif" class="img-fluid" alt="" />
                  <div class="social"></div>
                </div> -->
                <div class="member-info text-center">
                  <h4>놀이터</h4>
                  <p>큐잡을때 심심할 때</p>
                </div>
              </RouterLink>
            </div>
          </div>
        </div>
      </section>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter, RouterLink } from "vue-router";
import Header from "@/components/common/Header.vue";
import GameCardView from "@/components/common/GameCardView.vue";

const router = useRouter();
</script>

<style scoped>
/* Team Section - Home Page
------------------------------*/
.team {
  --background-color: #c89b3c;
  /* padding-top: 100px; */
}

.team .member {
  position: relative;
  display: flex;
}

.team .member .member-img {
  margin: 0 40px;
  /* border-radius: 50%; */
  overflow: hidden;
  position: relative;
  border: 4px solid var(--background-color);
  box-shadow: 0 15px 35px -10px rgba(0, 0, 0, 0.2);
}

@media (max-width: 1024px) {
  .team .member .member-img {
    margin: 0 60px;
  }
}

/* .team .member .member-img img {
  width: 308px;
  height: 560px;
  position: relative;
  z-index: 1;
} */

.team .member .member-img .social {
  position: absolute;
  inset: 0;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2;
  padding-bottom: 20px;
  transition: 0.3s;
  visibility: hidden;
  opacity: 0;
}

.team .member .member-img .social a {
  transition: 0.3s;
  color: var(--contrast-color);
  font-size: 20px;
  margin: 0 8px;
}

.team .member .member-img .social a:hover {
  color: var(--accent-color);
}

.team .member .member-info {
  margin-top: 30px;
}

.team .member .member-info h4 {
  font-weight: 700;
  margin-bottom: 6px;
  font-size: 18px;
  color: #c89b3c;
}

.team .member .member-info span {
  font-style: italic;
  display: block;
  font-size: 15px;
  color: rgba(var(--default-color-rgb), 0.6);
  margin-bottom: 10px;
}

.team .member .member-info p {
  margin-bottom: 0;
  font-size: 14px;
  color: rgba(var(--default-color-rgb), 0.6);
}

.team .member:hover .member-img .social {
  padding-bottom: 0;
  visibility: visible;
  opacity: 1;
}

.hero {
  --default-color: #ffffff;
  --default-color-rgb: 255, 255, 255;
  --background-color: #000000;
  --background-color-rgb: 0, 0, 0;
  width: 100%;
  min-height: 100vh;
  position: relative;
  padding: 160px 0 80px 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.hero img {
  position: absolute;
  inset: 0;
  display: block;
  width: 100%;
  height: 100%;
  -o-object-fit: cover;
  object-fit: cover;
  z-index: 1;
}

.hero:before {
  content: "";
  background: rgba(var(--background-color-rgb), 0.5);
  position: absolute;
  inset: 0;
  z-index: 2;
}

.hero .container {
  position: relative;
  /* margin-left: 150px; */
  z-index: 3;
}

.hero h2 {
  color: white;
  margin: 0;
  font-size: 58px;
  font-weight: 700;
}

.hero p {
  color: white;
  margin: 5px 0 0 0;
  font-size: 25px;
}

.hero .btn {
  width: 66px;
  background-color: #005a82;
}

.hero .sign-up-form {
  width: 110%;
  height: 80px;
  margin-top: 20px;
  padding: 10px;
  border-radius: 7px;
  background: #fff;
  box-shadow: 0 15px 30px 0 rgba(0, 0, 0, 0.1);
}

.hero .sign-up-form .form-control {
  height: 100%;
  border: none;
}

.hero .sign-up-form .form-control:active,
.hero .sign-up-form .form-control:focus {
  outline: none;
  box-shadow: none;
}

.hero .sign-up-form input[type="submit"] {
  box-shadow: 0 15px 30px 0 rgba(0, 0, 0, 0.2);
  background-color: var(#87ceeb);
  border-color: var(#87ceeb);
  padding: 8px 20px 10px 20px;
  border-radius: 7px;
  color: var(--contrast-color);
}

@media (max-width: 768px) {
  .hero h2 {
    font-size: 32px;
  }

  .hero p {
    font-size: 18px;
  }
}
</style>
