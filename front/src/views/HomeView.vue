<template>
  <div class="background">
    <!-- Team Section - Home Page -->
    <section id="team" class="team">
      <!--  Section Title -->
      <!-- <div class="container section-title" data-aos="fade-up">
        <h2>Team</h2>
        <p>
          Necessitatibus eius consequatur ex aliquid fuga eum quidem sint
          consectetur velit
        </p>
      </div> -->
      <!-- End Section Title -->

      <div class="container">
        <div class="row gy-5">
          <div
            class="col-lg-4 col-md-6 member"
            data-aos="fade-up"
            data-aos-delay="100"
          >
            <RouterLink :to="{ name: 'champ-rec' }">
              <div class="member-img">
                <img src="@/assets/garen.avif" class="img-fluid" alt="" />
                <div class="social">
                  <!-- <a href="#"><i class="bi bi-twitter"></i></a>
                    <a href="#"><i class="bi bi-facebook"></i></a>
                    <a href="#"><i class="bi bi-instagram"></i></a>
                    <a href="#"><i class="bi bi-linkedin"></i></a> -->
                </div>
              </div>
              <div class="member-info text-center">
                <h4>챔피언 추천</h4>

                <p>승리하기 위한 새로운 챔피언을 찾아보세요</p>
              </div>
            </RouterLink>
          </div>
          <!-- End Team Member -->

          <div
            class="col-lg-4 col-md-6 member"
            data-aos="fade-up"
            data-aos-delay="200"
          >
            <RouterLink :to="{ name: 'combination' }">
              <div class="member-img">
                <img src="@/assets/garen.avif" class="img-fluid" alt="" />
                <div class="social">
                  <a href="#"><i class="bi bi-twitter"></i></a>
                  <a href="#"><i class="bi bi-facebook"></i></a>
                  <a href="#"><i class="bi bi-instagram"></i></a>
                  <a href="#"><i class="bi bi-linkedin"></i></a>
                </div>
              </div>
              <div class="member-info text-center">
                <h4>듀오 추천</h4>

                <p>어떤 챔피언과 함께 하면 좋을지 알아보세요</p>
              </div>
            </RouterLink>
          </div>
          <!-- End Team Member -->

          <div
            class="col-lg-4 col-md-6 member"
            data-aos="fade-up"
            data-aos-delay="300"
          >
            <RouterLink :to="{ name: 'playground' }">
              <div class="member-img">
                <img src="@/assets/garen.avif" class="img-fluid" alt="" />
                <div class="social">
                  <a href="#"><i class="bi bi-twitter"></i></a>
                  <a href="#"><i class="bi bi-facebook"></i></a>
                  <a href="#"><i class="bi bi-instagram"></i></a>
                  <a href="#"><i class="bi bi-linkedin"></i></a>
                </div>
              </div>
              <div class="member-info text-center">
                <h4>놀이터</h4>
                <!-- <span>CTO</span> -->
                <p>큐잡을때 심심할 때</p>
              </div>
            </RouterLink>
          </div>
          <!-- End Team Member -->
        </div>
      </div>
    </section>
    <!-- End Team Section -->
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter, RouterLink } from "vue-router";

const router = useRouter();
</script>

<style scoped>
/*--------------------------------------------------------------
# General
--------------------------------------------------------------*/
.background {
  background-color: #091428;
  height: 100vh;
}

/* Team Section - Home Page
------------------------------*/
.team {
  --background-color: #c89b3c;
  padding-top: 100px;
}

.team .member {
  position: relative;
}

.team .member .member-img {
  margin: 0 80px;
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

.team .member .member-img img {
  position: relative;
  z-index: 1;
}

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
</style>
