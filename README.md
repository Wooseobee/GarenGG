# GarenGG

[//]: # "<div align=center>"
[//]: # '<img src="images/MainPage.png" height=600>'
[//]: # "</div>"

## :book: 목차

- [프로젝트 소개](#walking-프로젝트-소개)
- [팀원 구성](#construction_worker-팀원-구성)
- [개발 기간](#calendar-개발-기간)
- [기술 스택](#hammer_and_wrench-기술-스택)
- [아키텍처](#building_construction-아키텍처)
- [문서](#memo-문서)
- [역할 분담](#pushpin-역할-분담)
- [기능 시연](#movie_camera-기능-시연)
- [구동 방법](#computer-구동-방법)
- [프로젝트 구조](#package-프로젝트-구조)

## :walking: 프로젝트 소개

- GarenGG는 Leagueoflegend 데이터를 가공해 챔피언을 추천해주고, 데이터 기반의 게임을 제공하는 서비스입니다.

## :construction_worker: 팀원 구성

|                                   김진용                                   |                                   신우섭                                   |                                   유광우                                    |                                   이준범                                   |                                   임덕기                                    |
| :------------------------------------------------------------------------: | :------------------------------------------------------------------------: | :-------------------------------------------------------------------------: | :------------------------------------------------------------------------: | :-------------------------------------------------------------------------: |
| <img src="https://avatars.githubusercontent.com/u/88269663?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/87111673?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/129749206?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/77481223?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/130431922?v=4" width=150> |
|               [@jinyong3512](https://github.com/jinyong3512)               |                 [@Wooseobee](https://github.com/Wooseobee)                 |                [@godsun7892](https://github.com/godsun7892)                 |                     [@bum19](https://github.com/bum19)                     |                    [@DKIMDK](https://github.com/DKIMDK)                     |

## :calendar: 개발 기간

2024.02.19 - 2024.04.04 (7주)

## :hammer_and_wrench: 기술 스택

#### Front-end

<img alt="JavaScript" src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"> <img alt="vue.js" src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white"> <img alt="bootstrap" src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">

#### Back-end

<img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/> <img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=Spring Boot&logoColor=white"/>

#### Database

<img alt="MySQL" src ="https://img.shields.io/badge/mysql-4479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white"/>

#### 버전 및 이슈관리

<img alt="gitlab" src ="https://img.shields.io/badge/gitlab-FC6D26.svg?&style=for-the-badge&logo=gitlab&logoColor=white"/>

#### 협업 툴

<img alt="discord" src ="https://img.shields.io/badge/discord-5865F2.svg?&style=for-the-badge&logo=discord&logoColor=white"/> <img alt="jira" src ="https://img.shields.io/badge/jira-0052CC.svg?&style=for-the-badge&logo=jira&logoColor=white"/> <img alt="notion" src ="https://img.shields.io/badge/notion-000000.svg?&style=for-the-badge&logo=notion&logoColor=white"/> <img alt="mattermost" src ="https://img.shields.io/badge/mattermost-0058CC.svg?&style=for-the-badge&logo=mattermost&logoColor=white"/>

#### 서비스 배포 환경

<img alt="AWS Lightsail" src ="https://img.shields.io/badge/AWS Lightsail-FF9900.svg?&style=for-the-badge&logo=amazonaws&logoColor=white"/> <img alt="docker" src ="https://img.shields.io/badge/docker-2496ED.svg?&style=for-the-badge&logo=docker&logoColor=white"/> <img alt="nginx" src ="https://img.shields.io/badge/nginx-009639.svg?&style=for-the-badge&logo=nginx&logoColor=white"/>

#### CI/CD

<img alt="jenkins" src ="https://img.shields.io/badge/jenkins-D24939.svg?&style=for-the-badge&logo=jenkins&logoColor=white"/>

## :building_construction: 아키텍처

<img src="images/architecture.jpeg">

## :memo: 문서

- [ERD](https://www.erdcloud.com/d/jLadrX3ydo7e4itgG)
- [API 명세서](https://wooseobee.notion.site/API-e888e85f74bf4b99b1ad45d5ce3247ff)
- [코드 및 커밋 컨벤션](https://wooseobee.notion.site/eef83eaf766c488aa092abe76ea8fe81?pvs=74)

## :pushpin: 역할 분담

- 김진용

- 신우섭
  - Infra
    - 서버 구축
    - Jenkins Front CI/CD 구축
  - UI
    - 경기 승패 맞추기
    - 랭킹
  - Data
    - 유저별 최근 100게임 수집
    - 이번 시즌 게임의 조합 통계 수집
- 유광우
  - Recommend
    - 데이터 정제
    - 빅데이터 기반 사용자 맞춤 챔피언 추천
  - UI
    - 챔피언 추천
    - 채팅
  - Chat
    - stomp 통신으로 실시간 채팅 구현
    - 채팅 로그 저장
- 이준범
- 임덕기
  - Recommend
    - 데이터 전처리
    - 잠재요인 협업 필터링 구현
  - UI
    - 레이아웃 및 디자인 구성
    - 카드 컴포넌트 및 뒤집기 이펙트
    - 챔피언 상세 정보 페이지

## :movie_camera: 기능 시연

<details>
<summary>챔피언 추천</summary>
<img src="images/champion_recommend.gif" width="711" height="400">
</details>

<details>
<summary>듀오 챔피언 추천</summary>
<img src="images/duo_champion_recommend.gif" width="711" height="400">
</details>

<details>
<summary>승리팀 맞추기</summary>
<img src="images/game1.gif" width="711" height="400">
</details>

<details>
<summary>소리 듣고 챔피언 맞추기</summary>
<img src="images/game2.gif" width="711" height="400">
</details>

<details>
<summary>채팅</summary>
<img src="images/chatting.gif" width="711" height="400">
</details>

## :computer: 구동 방법

1. Clone Project

```bash
git clone https://lab.ssafy.com/s10-bigdata-recom-sub2/S10P22A605.git
```

2. change path to /frontend & npm install

```bash
npm i
```

3. frontend start

```bash
npm run dev
```

4. change path to /backend/src/main

```bash
mkdir resources
```

5. change path to /backend/src/main/resources

```bash
cd resources
```

6. create application.yml

```bash
server:
  port: {spring boot application port number}

spring:
  datasource:
    driver-class-name: {database class name}
    url: {MySQL connect url}
    username: {MySQL username}
    password: {MySQL password}

  data:
    mongodb:
      uri: mongodb://root:ssafy605@j10a605.p.ssafy.io:27017/a605?authSource=admin&maxPoolSize=1000
```

## :package: 프로젝트 구조

<details>
<summary>Frontend</summary>

```
│  .gitignore
│  index.html
│  jsconfig.json
│  package-lock.json
│  package.json
│  README.md
│  vite.config.js
│
├─dist
│  │  favicon.ico
│  │  index.html
│  │
│  └─assets
│          garen-background-b-6up9Rx.gif
│          garen-CYJABbWg.avif
│          index-DlLCHQLJ.js
│          index-LgcA8gP7.css
│          logo2-B59Rspx5.png
│          riotlogo-BQ8qyXlm.png
│
├─node_modules
│  │  .package-lock.json
│
├─public
│      favicon.ico
│
└─src
    │  App.vue
    │  main.js
    │
    ├─api
    │      champion.js
    │      duoRecommend.js
    │      predictRandomMatch.js
    │      randomMatch.js
    │
    ├─assets
    │  │
    │  ├─emblem
    │  │
    │  ├─garenimage
    │  │
    │  ├─pick-voice
    │  │
    │  └─sounds
    │
    ├─components
    │  ├─champ
    │  │      ChampDetail.vue
    │  │      MyChamp.vue
    │  │
    │  ├─chat
    │  │      Chat.vue
    │  │
    │  ├─common
    │  │      Card.vue
    │  │      Carousel.vue
    │  │      GameCardView.vue
    │  │      Header.vue
    │  │      Help.vue
    │  │      RankView.vue
    │  │      SkillModal.vue
    │  │      Youtube.vue
    │  │
    │  ├─duoRecommendation
    │  │      DuoRecommendationResult.vue
    │  │      DuoRecommendationSearch.vue
    │  │
    │  └─team_comp
    │          Team_Comp.vue
    │
    ├─fonts
    │
    ├─router
    │      index.js
    │
    ├─stores
    │      backGroundStore.js
    │      championStore.js
    │
    ├─utils
    │      http-commons.js
    │
    └─views
            ChampDetailView.vue
            ChampRecView.vue
            DetailView.vue
            DuoRecommendationView.vue
            HomeView.vue
            PlayerCard.vue
            PlaygroundView.vue
            PredictChampionView.vue
            PredictMatchView.vue
            TeamList.vue
```

</details>
<details>
<summary>Backend</summary>

```
├─java
│  └─gg
│      └─garen
│          └─back
│              │  BackApplication.java
│              │
│              ├─champion
│              │  ├─controller
│              │  │      ChampionController.java
│              │  │
│              │  ├─dto
│              │  │      ChampionDto.java
│              │  │
│              │  ├─entity
│              │  │      Champion.java
│              │  │      Image.java
│              │  │      Info.java
│              │  │      Stats.java
│              │  │
│              │  ├─repository
│              │  │      ChampionRepository.java
│              │  │
│              │  └─service
│              │          ChampionUtils.java
│              │
│              ├─championPrediction
│              │  ├─controller
│              │  │      ChampionPredictionController.java
│              │  │
│              │  ├─dto
│              │  │      ResponseGetChampionPredictionStartDto.java
│              │  │
│              │  └─service
│              │          ChampionPredictionService.java
│              │
│              ├─championRecommendation
│              │  ├─controller
│              │  │      ChampionRecommendationController.java
│              │  │
│              │  ├─dto
│              │  │  ├─request
│              │  │  │      RequestPredictNotDto.java
│              │  │  │
│              │  │  └─response
│              │  │          ResponseGetChampionRecommendationDto.java
│              │  │
│              │  └─service
│              │          AsyncService.java
│              │          ChampionRecommendationService.java
│              │          ChampionRecommendationServiceImpl.java
│              │
│              ├─chatting
│              │  ├─config
│              │  │      WebSocketConfig.java
│              │  │
│              │  ├─controller
│              │  │      ChatController.java
│              │  │
│              │  ├─dto
│              │  │      ChatDto.java
│              │  │
│              │  ├─entity
│              │  │      Chat.java
│              │  │
│              │  ├─repository
│              │  │      ChatRepository.java
│              │  │
│              │  └─service
│              │          ChatService.java
│              │
│              ├─common
│              │  ├─audit
│              │  │      Auditable.java
│              │  │
│              │  ├─controller
│              │  │      GameController.java
│              │  │
│              │  ├─domain
│              │  │  ├─mongo
│              │  │  │      Info.java
│              │  │  │      MatchInfo.java
│              │  │  │      Metadata.java
│              │  │  │      Participant.java
│              │  │  │
│              │  │  └─mysql
│              │  │          ApiKey.java
│              │  │          Game.java
│              │  │          PlayerInfoTest.java
│              │  │          Ranking.java
│              │  │
│              │  ├─dto
│              │  │      RankResponseDto.java
│              │  │      SaveGameRequestDto.java
│              │  │      UserScoreRequestDto.java
│              │  │
│              │  ├─repository
│              │  │      ApiKeyRepository.java
│              │  │      GameRepository.java
│              │  │      PlayerInfoTestRepository.java
│              │  │      RankingRepository.java
│              │  │      UserMatchRepository.java
│              │  │
│              │  └─service
│              │          ApiKeyUtils.java
│              │          GameService.java
│              │
│              ├─duoRecommendation
│              │  ├─controller
│              │  │      DuoRecommendationController.java
│              │  │
│              │  ├─dto
│              │  │      DuoRecommendationDto.java
│              │  │
│              │  ├─entity
│              │  │      DuoRecord.java
│              │  │
│              │  ├─repository
│              │  │      DuoRecommendationRepository.java
│              │  │
│              │  └─service
│              │          DuoRecommendationService.java
│              │          DuoRecommendationServiceImpl.java
│              │          DuoRecommendationServiceTestImpl.java
│              │
│              ├─global
│              │      SpringAsyncConfig.java
│              │      WebConfig.java
│              │
│              └─matchPrediction
│                  ├─controller
│                  │      MatchPredictionController.java
│                  │
│                  ├─dto
│                  │      ParticipantDto.java
│                  │      RandomMatchResponseDto.java
│                  │
│                  └─service
│                          DetectDuplicateMatchService.java
│                          MatchPredictionService.java
│
└─resources
        application.yml
```

</details>

<details>
<summary>Crawling</summary>

```
├─apiKeyCrawling
│  │  .gitignore
│  │  build.gradle
│  │  gradlew
│  │  gradlew.bat
│  │  settings.gradle
│  │
│  ├─gradle
│  │  └─wrapper
│  │          gradle-wrapper.jar
│  │          gradle-wrapper.properties
│  │
│  └─src
│      ├─main
│      │  └─java
│      │      └─org
│      │          └─example
│      │              └─apikeycrawling
│      │                  │  ApiKeyCrawlingApplication.java
│      │                  │
│      │                  ├─component
│      │                  │      ApiKeyCrawlingComponent.java
│      │                  │      PlayerInfoTestAsyncService.java
│      │                  │      PlayerInfoTestCrawlingComponent.java
│      │                  │      PlayerMostCrawlingAsyncService.java
│      │                  │      PlayerMostCrawlingComponent.java
│      │                  │
│      │                  ├─dto
│      │                  │      AccountDto.java
│      │                  │      LeagueEntryDto.java
│      │                  │      LeagueListDto.java
│      │                  │      MatchDto.java
│      │                  │      SummonerDto.java
│      │                  │
│      │                  ├─entity
│      │                  │  ├─mongo
│      │                  │  │      PlayerMatch.java
│      │                  │  │      PlayerMost.java
│      │                  │  │
│      │                  │  └─mysql
│      │                  │          ApiKey.java
│      │                  │          PlayerInfoTest.java
│      │                  │
│      │                  ├─global
│      │                  │      GlobalConstants.java
│      │                  │      MyStartupTask.java
│      │                  │      SpringAsyncConfig.java
│      │                  │
│      │                  └─repository
│      │                          ApiKeyRepository.java
│      │                          PlayerInfoTestCustomRepository.java
│      │                          PlayerInfoTestCustomRepositoryImpl.java
│      │                          PlayerInfoTestRepository.java
│      │                          PlayerMatchRepository.java
│      │                          PlayerMostRepository.java
│      │
│      └─test
│          └─java
│              └─org
│                  └─example
│                      └─apikeycrawling
│                              ApiKeyCrawlingApplicationTests.java
│
├─getMatches
│  │  .gitignore
│  │  build.gradle
│  │  gradlew
│  │  gradlew.bat
│  │  settings.gradle
│  │
│  ├─.gradle
│  │  │  file-system.probe
│  │  │
│  │  ├─8.5
│  │  │  │  gc.properties
│  │  │  │
│  │  │  ├─checksums
│  │  │  │      checksums.lock
│  │  │  │      md5-checksums.bin
│  │  │  │      sha1-checksums.bin
│  │  │  │
│  │  │  ├─dependencies-accessors
│  │  │  │      dependencies-accessors.lock
│  │  │  │      gc.properties
│  │  │  │
│  │  │  ├─executionHistory
│  │  │  │      executionHistory.bin
│  │  │  │      executionHistory.lock
│  │  │  │
│  │  │  ├─fileChanges
│  │  │  │      last-build.bin
│  │  │  │
│  │  │  ├─fileHashes
│  │  │  │      fileHashes.bin
│  │  │  │      fileHashes.lock
│  │  │  │      resourceHashesCache.bin
│  │  │  │
│  │  │  └─vcsMetadata
│  │  ├─buildOutputCleanup
│  │  │      buildOutputCleanup.lock
│  │  │      cache.properties
│  │  │      outputFiles.bin
│  │  │
│  │  └─vcs-1
│  │          gc.properties
│  │
│  ├─.idea
│  │  │  .gitignore
│  │  │  compiler.xml
│  │  │  gradle.xml
│  │  │  jarRepositories.xml
│  │  │  misc.xml
│  │  │  modules.xml
│  │  │  vcs.xml
│  │  │  workspace.xml
│  │  │
│  │  └─modules
│  │          getMatches.main.iml
│  │
│  ├─build
│  │  ├─classes
│  │  │  └─java
│  │  │      └─main
│  │  │          └─org
│  │  │              └─example
│  │  │                  └─getmatches
│  │  │                      │  GetMatchesApplication.class
│  │  │                      │
│  │  │                      ├─audit
│  │  │                      │      Auditable.class
│  │  │                      │
│  │  │                      ├─config
│  │  │                      │      AsyncConfig.class
│  │  │                      │
│  │  │                      ├─controller
│  │  │                      │      GetMatchController.class
│  │  │                      │
│  │  │                      ├─domain
│  │  │                      │  │  Choice.class
│  │  │                      │  │
│  │  │                      │  ├─mongo
│  │  │                      │  │      Info.class
│  │  │                      │  │      MatchInfo.class
│  │  │                      │  │      Metadata.class
│  │  │                      │  │      Participant.class
│  │  │                      │  │
│  │  │                      │  └─mysql
│  │  │                      │          Champion$ChampionBuilder.class
│  │  │                      │          Champion.class
│  │  │                      │          DuoRecord.class
│  │  │                      │          DuoRecordMatch.class
│  │  │                      │          DuoRecordMatchKey.class
│  │  │                      │          Image$ImageBuilder.class
│  │  │                      │          Image.class
│  │  │                      │          Info$InfoBuilder.class
│  │  │                      │          Info.class
│  │  │                      │          Stats$StatsBuilder.class
│  │  │                      │          Stats.class
│  │  │                      │
│  │  │                      ├─repository
│  │  │                      │      ChampionRepository.class
│  │  │                      │      DuoRecordMatchRepository.class
│  │  │                      │      DuoRecordRepository.class
│  │  │                      │      UserMatchRepository.class
│  │  │                      │
│  │  │                      └─service
│  │  │                              AsyncService.class
│  │  │                              CombinationService.class
│  │  │                              GetMatchService$Choice.class
│  │  │                              GetMatchService.class
│  │  │                              RenewChampionService.class
│  │  │
│  │  ├─generated
│  │  │  └─sources
│  │  │      ├─annotationProcessor
│  │  │      │  └─java
│  │  │      │      └─main
│  │  │      └─headers
│  │  │          └─java
│  │  │              └─main
│  │  ├─resources
│  │  │  └─main
│  │  │          application.yml
│  │  │
│  │  └─tmp
│  │      └─compileJava
│  │          │  previous-compilation-data.bin
│  │          │
│  │          └─compileTransaction
│  │              ├─backup-dir
│  │              └─stash-dir
│  │                      GetMatchController.class.uniqueId3
│  │                      GetMatchService$Choice.class.uniqueId1
│  │                      GetMatchService.class.uniqueId0
│  │                      MatchInfo.class.uniqueId4
│  │                      UserMatchRepository.class.uniqueId2
│  │
│  ├─gradle
│  │  └─wrapper
│  │          gradle-wrapper.jar
│  │          gradle-wrapper.properties
│  │
│  └─src
│      ├─main
│      │  ├─java
│      │  │  └─org
│      │  │      └─example
│      │  │          └─getmatches
│      │  │              │  GetMatchesApplication.java
│      │  │              │
│      │  │              ├─audit
│      │  │              │      Auditable.java
│      │  │              │
│      │  │              ├─config
│      │  │              │      AsyncConfig.java
│      │  │              │
│      │  │              ├─controller
│      │  │              │      GetMatchController.java
│      │  │              │
│      │  │              ├─domain
│      │  │              │  │  Choice.java
│      │  │              │  │
│      │  │              │  ├─mongo
│      │  │              │  │      Info.java
│      │  │              │  │      MatchInfo.java
│      │  │              │  │      Metadata.java
│      │  │              │  │      Participant.java
│      │  │              │  │
│      │  │              │  └─mysql
│      │  │              │          Champion.java
│      │  │              │          DuoRecord.java
│      │  │              │          DuoRecordMatch.java
│      │  │              │          DuoRecordMatchKey.java
│      │  │              │          Image.java
│      │  │              │          Info.java
│      │  │              │          Stats.java
│      │  │              │
│      │  │              ├─repository
│      │  │              │      ChampionRepository.java
│      │  │              │      DuoRecordMatchRepository.java
│      │  │              │      DuoRecordRepository.java
│      │  │              │      UserMatchRepository.java
│      │  │              │
│      │  │              └─service
│      │  │                      AsyncService.java
│      │  │                      CombinationService.java
│      │  │                      GetMatchService.java
│      │  │                      RenewChampionService.java
│      │  │
│      │  └─resources
│      │          application.yml
│      │
│      └─test
│          └─java
│              └─org
│                  └─example
│                      └─getmatches
│                              GetMatchesApplicationTests.java
│
├─getUserMatches
│  │  .gitignore
│  │  build.gradle
│  │  gradlew
│  │  gradlew.bat
│  │  settings.gradle
│  │
│  ├─.gradle
│  │  │  file-system.probe
│  │  │
│  │  ├─8.5
│  │  │  │  gc.properties
│  │  │  │
│  │  │  ├─checksums
│  │  │  │      checksums.lock
│  │  │  │      md5-checksums.bin
│  │  │  │      sha1-checksums.bin
│  │  │  │
│  │  │  ├─dependencies-accessors
│  │  │  │      dependencies-accessors.lock
│  │  │  │      gc.properties
│  │  │  │
│  │  │  ├─executionHistory
│  │  │  │      executionHistory.bin
│  │  │  │      executionHistory.lock
│  │  │  │
│  │  │  ├─fileChanges
│  │  │  │      last-build.bin
│  │  │  │
│  │  │  ├─fileHashes
│  │  │  │      fileHashes.bin
│  │  │  │      fileHashes.lock
│  │  │  │      resourceHashesCache.bin
│  │  │  │
│  │  │  └─vcsMetadata
│  │  ├─buildOutputCleanup
│  │  │      buildOutputCleanup.lock
│  │  │      cache.properties
│  │  │      outputFiles.bin
│  │  │
│  │  └─vcs-1
│  │          gc.properties
│  │
│  ├─.idea
│  │  │  .gitignore
│  │  │  compiler.xml
│  │  │  gradle.xml
│  │  │  jarRepositories.xml
│  │  │  misc.xml
│  │  │  modules.xml
│  │  │  vcs.xml
│  │  │  workspace.xml
│  │  │
│  │  └─modules
│  │          getUserMatches.main.iml
│  │
│  ├─build
│  │  ├─classes
│  │  │  └─java
│  │  │      └─main
│  │  │          └─org
│  │  │              └─example
│  │  │                  └─getusermatches
│  │  │                      │  GetUserMatchesApplication.class
│  │  │                      │
│  │  │                      ├─config
│  │  │                      │      AppConfig.class
│  │  │                      │      AsyncConfig.class
│  │  │                      │
│  │  │                      ├─controller
│  │  │                      │      UserMatchController.class
│  │  │                      │
│  │  │                      ├─domain
│  │  │                      │      ApiKey$ApiKeyBuilder.class
│  │  │                      │      ApiKey.class
│  │  │                      │      Info.class
│  │  │                      │      MatchInfo.class
│  │  │                      │      Metadata.class
│  │  │                      │      Participant.class
│  │  │                      │      PlayerInfo.class
│  │  │                      │
│  │  │                      ├─repository
│  │  │                      │      ApiKeyRepository.class
│  │  │                      │      CustomMatchInfoRepository.class
│  │  │                      │      CustomMatchInfoRepositoryImpl.class
│  │  │                      │      UserMatchRepository.class
│  │  │                      │      UserRepository.class
│  │  │                      │
│  │  │                      └─service
│  │  │                              APIKeyService.class
│  │  │                              SaveMatchService.class
│  │  │                              UserMatchService.class
│  │  │
│  │  ├─generated
│  │  │  └─sources
│  │  │      ├─annotationProcessor
│  │  │      │  └─java
│  │  │      │      └─main
│  │  │      └─headers
│  │  │          └─java
│  │  │              └─main
│  │  ├─resources
│  │  │  └─main
│  │  │          application.yml
│  │  │
│  │  └─tmp
│  │      └─compileJava
│  │          │  previous-compilation-data.bin
│  │          │
│  │          └─compileTransaction
│  │              ├─backup-dir
│  │              └─stash-dir
│  │                      CustomMatchInfoRepository.class.uniqueId5
│  │                      CustomMatchInfoRepositoryImpl.class.uniqueId2
│  │                      MatchInfo.class.uniqueId0
│  │                      SaveMatchService.class.uniqueId6
│  │                      UserMatchController.class.uniqueId4
│  │                      UserMatchRepository.class.uniqueId3
│  │                      UserMatchService.class.uniqueId1
│  │
│  ├─gradle
│  │  └─wrapper
│  │          gradle-wrapper.jar
│  │          gradle-wrapper.properties
│  │
│  └─src
│      ├─main
│      │  ├─java
│      │  │  └─org
│      │  │      └─example
│      │  │          └─getusermatches
│      │  │              │  GetUserMatchesApplication.java
│      │  │              │
│      │  │              ├─config
│      │  │              │      AppConfig.java
│      │  │              │      AsyncConfig.java
│      │  │              │
│      │  │              ├─controller
│      │  │              │      UserMatchController.java
│      │  │              │
│      │  │              ├─domain
│      │  │              │      ApiKey.java
│      │  │              │      Info.java
│      │  │              │      MatchInfo.java
│      │  │              │      Metadata.java
│      │  │              │      Participant.java
│      │  │              │      PlayerInfo.java
│      │  │              │
│      │  │              ├─repository
│      │  │              │      ApiKeyRepository.java
│      │  │              │      CustomMatchInfoRepository.java
│      │  │              │      CustomMatchInfoRepositoryImpl.java
│      │  │              │      UserMatchRepository.java
│      │  │              │      UserRepository.java
│      │  │              │
│      │  │              └─service
│      │  │                      APIKeyService.java
│      │  │                      SaveMatchService.java
│      │  │                      UserMatchService.java
│      │  │
│      │  └─resources
│      │          application.yml
│      │
│      └─test
│          └─java
│              └─org
│                  └─example
│                      └─getusermatches
│                              GetUserMatchesApplicationTests.java
│
└─riotApiCrawling
    │  build.gradle
    │  Dockerfile
    │  gradlew
    │  gradlew.bat
    │  settings.gradle
    │
    ├─gradle
    │  └─wrapper
    │          gradle-wrapper.jar
    │          gradle-wrapper.properties
    │
    └─src
        ├─main
        │  └─java
        │      └─com
        │          └─example
        │              └─riotApiCrawling
        │                  │  RiotApiCrawlingApplication.java
        │                  │
        │                  ├─apiKey
        │                  │  ├─entity
        │                  │  │      ApiKey.java
        │                  │  │
        │                  │  └─repository
        │                  │          ApiKeyRepository.java
        │                  │
        │                  ├─audit
        │                  │      Auditable.java
        │                  │
        │                  ├─config
        │                  │      AsyncConfig.java
        │                  │
        │                  └─userCrawl
        │                      ├─controller
        │                      │      UserRiotApiController.java
        │                      │
        │                      ├─dto
        │                      │      PlayerInfoDto.java
        │                      │      RequestDto.java
        │                      │
        │                      ├─entity
        │                      │      PlayerInfo.java
        │                      │
        │                      ├─repository
        │                      │      UserRiotApiRepository.java
        │                      │
        │                      └─service
        │                              AsyncUserService.java
        │                              UserRiotApiService.java
        │                              UserRiotApiServiceImpl.java
        │
        └─test
            └─java
                └─com
                    └─example
                        └─riotApiCrawling
                                RiotApiCrawlingApplicationTests.java
```

</details>
