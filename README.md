# GarenGG

[//]: # (<div align=center>)

[//]: # (<img src="images/MainPage.png" height=600>)

[//]: # (</div>)

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

- GarenGG는 서비스입니다.

## :construction_worker: 팀원 구성

|                                   김진용                                   |                                    신우섭                                     |                                    유광우                                     |                                    이준범                                     |                                   임덕기                                    |
| :------------------------------------------------------------------------: |:--------------------------------------------------------------------------:|:--------------------------------------------------------------------------:|:--------------------------------------------------------------------------:| :-------------------------------------------------------------------------: |
| <img src="https://avatars.githubusercontent.com/u/88269663?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/87111673?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/129749206?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/77481223?v=4" width=150> | <img src="https://avatars.githubusercontent.com/u/130431922?v=4" width=150> |
|               [@jinyong3512](https://github.com/jinyong3512)               |                    [@Wooseobee](https://github.com/Wooseobee)                    |                 [@godsun7892](https://github.com/godsun7892)                 |                 [@bum19](https://github.com/bum19)                 |                    [@DKIMDK](https://github.com/DKIMDK)                     |

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
        - 유저별 최근 100게임 수집
        - 이번 시즌 게임의 조합 통계 수집
    
- 이준범
    
- 임덕기

## :movie_camera: 기능 시연

<details>
<summary>채팅</summary>

- 소모임 가입, 탈퇴시 채팅방 알림 구현
- 소모임원들만 채팅방에 접근할 수 있게 제한
- 가입 이전의 내용은 확인 불가

[//]: # (  <img src="images/소모임/chatting.gif">)
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
│  │
│  ├─.bin
│  │      esbuild
│  │      esbuild.cmd
│  │      esbuild.ps1
│  │      nanoid
│  │      nanoid.cmd
│  │      nanoid.ps1
│  │      parser
│  │      parser.cmd
│  │      parser.ps1
│  │      rollup
│  │      rollup.cmd
│  │      rollup.ps1
│  │      uuid
│  │      uuid.cmd
│  │      uuid.ps1
│  │      vite
│  │      vite.cmd
│  │      vite.ps1
│  │
│  ├─.vite
│  │  └─deps
│  │          @stomp_stompjs.js
│  │          @stomp_stompjs.js.map
│  │          axios.js
│  │          axios.js.map
│  │          bootstrap.js
│  │          bootstrap.js.map
│  │          chunk-2FDUVFJ5.js
│  │          chunk-2FDUVFJ5.js.map
│  │          chunk-34Z2WVG2.js
│  │          chunk-34Z2WVG2.js.map
│  │          chunk-YFT6OQ5R.js
│  │          chunk-YFT6OQ5R.js.map
│  │          crypto-js.js
│  │          crypto-js.js.map
│  │          package.json
│  │          pinia.js
│  │          pinia.js.map
│  │          uuid.js
│  │          uuid.js.map
│  │          vue-router.js
│  │          vue-router.js.map
│  │          vue.js
│  │          vue.js.map
│  │          _metadata.json
│  │
│  ├─@babel
│  │  └─parser
│  │      │  CHANGELOG.md
│  │      │  index.cjs
│  │      │  LICENSE
│  │      │  package.json
│  │      │  README.md
│  │      │
│  │      ├─bin
│  │      │      babel-parser.js
│  │      │
│  │      ├─lib
│  │      │      index.js
│  │      │      index.js.map
│  │      │
│  │      └─typings
│  │              babel-parser.d.ts
│  │
│  ├─@esbuild
│  │  └─win32-x64
│  │          esbuild.exe
│  │          package.json
│  │          README.md
│  │
│  ├─@jridgewell
│  │  └─sourcemap-codec
│  │      │  LICENSE
│  │      │  package.json
│  │      │  README.md
│  │      │
│  │      └─dist
│  │          │  sourcemap-codec.mjs
│  │          │  sourcemap-codec.mjs.map
│  │          │  sourcemap-codec.umd.js
│  │          │  sourcemap-codec.umd.js.map
│  │          │
│  │          └─types
│  │                  sourcemap-codec.d.ts
│  │
│  ├─@popperjs
│  │  └─core
│  │      │  index.d.ts
│  │      │  LICENSE.md
│  │      │  package.json
│  │      │  README.md
│  │      │
│  │      ├─dist
│  │      │  ├─cjs
│  │      │  │      enums.js
│  │      │  │      enums.js.flow
│  │      │  │      enums.js.map
│  │      │  │      popper-base.js
│  │      │  │      popper-base.js.flow
│  │      │  │      popper-base.js.map
│  │      │  │      popper-lite.js
│  │      │  │      popper-lite.js.flow
│  │      │  │      popper-lite.js.map
│  │      │  │      popper.js
│  │      │  │      popper.js.flow
│  │      │  │      popper.js.map
│  │      │  │
│  │      │  ├─esm
│  │      │  │  │  createPopper.js
│  │      │  │  │  enums.js
│  │      │  │  │  index.js
│  │      │  │  │  popper-base.js
│  │      │  │  │  popper-lite.js
│  │      │  │  │  popper.js
│  │      │  │  │  types.js
│  │      │  │  │
│  │      │  │  ├─dom-utils
│  │      │  │  │      contains.js
│  │      │  │  │      getBoundingClientRect.js
│  │      │  │  │      getClippingRect.js
│  │      │  │  │      getCompositeRect.js
│  │      │  │  │      getComputedStyle.js
│  │      │  │  │      getDocumentElement.js
│  │      │  │  │      getDocumentRect.js
│  │      │  │  │      getHTMLElementScroll.js
│  │      │  │  │      getLayoutRect.js
│  │      │  │  │      getNodeName.js
│  │      │  │  │      getNodeScroll.js
│  │      │  │  │      getOffsetParent.js
│  │      │  │  │      getParentNode.js
│  │      │  │  │      getScrollParent.js
│  │      │  │  │      getViewportRect.js
│  │      │  │  │      getWindow.js
│  │      │  │  │      getWindowScroll.js
│  │      │  │  │      getWindowScrollBarX.js
│  │      │  │  │      instanceOf.js
│  │      │  │  │      isLayoutViewport.js
│  │      │  │  │      isScrollParent.js
│  │      │  │  │      isTableElement.js
│  │      │  │  │      listScrollParents.js
│  │      │  │  │
│  │      │  │  ├─modifiers
│  │      │  │  │      applyStyles.js
│  │      │  │  │      arrow.js
│  │      │  │  │      computeStyles.js
│  │      │  │  │      eventListeners.js
│  │      │  │  │      flip.js
│  │      │  │  │      hide.js
│  │      │  │  │      index.js
│  │      │  │  │      offset.js
│  │      │  │  │      popperOffsets.js
│  │      │  │  │      preventOverflow.js
│  │      │  │  │
│  │      │  │  └─utils
│  │      │  │          computeAutoPlacement.js
│  │      │  │          computeOffsets.js
│  │      │  │          debounce.js
│  │      │  │          detectOverflow.js
│  │      │  │          expandToHashMap.js
│  │      │  │          getAltAxis.js
│  │      │  │          getAltLen.js
│  │      │  │          getBasePlacement.js
│  │      │  │          getFreshSideObject.js
│  │      │  │          getMainAxisFromPlacement.js
│  │      │  │          getOppositePlacement.js
│  │      │  │          getOppositeVariationPlacement.js
│  │      │  │          getVariation.js
│  │      │  │          math.js
│  │      │  │          mergeByName.js
│  │      │  │          mergePaddingObject.js
│  │      │  │          orderModifiers.js
│  │      │  │          rectToClientRect.js
│  │      │  │          uniqueBy.js
│  │      │  │          userAgent.js
│  │      │  │          within.js
│  │      │  │
│  │      │  └─umd
│  │      │          enums.js
│  │      │          enums.js.map
│  │      │          enums.min.js
│  │      │          enums.min.js.flow
│  │      │          enums.min.js.map
│  │      │          popper-base.js
│  │      │          popper-base.js.map
│  │      │          popper-base.min.js
│  │      │          popper-base.min.js.flow
│  │      │          popper-base.min.js.map
│  │      │          popper-lite.js
│  │      │          popper-lite.js.map
│  │      │          popper-lite.min.js
│  │      │          popper-lite.min.js.flow
│  │      │          popper-lite.min.js.map
│  │      │          popper.js
│  │      │          popper.js.map
│  │      │          popper.min.js
│  │      │          popper.min.js.flow
│  │      │          popper.min.js.map
│  │      │
│  │      └─lib
│  │          │  createPopper.d.ts
│  │          │  createPopper.js
│  │          │  createPopper.js.flow
│  │          │  enums.d.ts
│  │          │  enums.js
│  │          │  enums.js.flow
│  │          │  index.d.ts
│  │          │  index.js
│  │          │  index.js.flow
│  │          │  popper-base.d.ts
│  │          │  popper-base.js
│  │          │  popper-base.js.flow
│  │          │  popper-lite.d.ts
│  │          │  popper-lite.js
│  │          │  popper-lite.js.flow
│  │          │  popper.d.ts
│  │          │  popper.js
│  │          │  popper.js.flow
│  │          │  types.d.ts
│  │          │  types.js
│  │          │  types.js.flow
│  │          │
│  │          ├─dom-utils
│  │          │      contains.d.ts
│  │          │      contains.js
│  │          │      contains.js.flow
│  │          │      getBoundingClientRect.d.ts
│  │          │      getBoundingClientRect.js
│  │          │      getBoundingClientRect.js.flow
│  │          │      getClippingRect.d.ts
│  │          │      getClippingRect.js
│  │          │      getClippingRect.js.flow
│  │          │      getCompositeRect.d.ts
│  │          │      getCompositeRect.js
│  │          │      getCompositeRect.js.flow
│  │          │      getComputedStyle.d.ts
│  │          │      getComputedStyle.js
│  │          │      getComputedStyle.js.flow
│  │          │      getDocumentElement.d.ts
│  │          │      getDocumentElement.js
│  │          │      getDocumentElement.js.flow
│  │          │      getDocumentRect.d.ts
│  │          │      getDocumentRect.js
│  │          │      getDocumentRect.js.flow
│  │          │      getHTMLElementScroll.d.ts
│  │          │      getHTMLElementScroll.js
│  │          │      getHTMLElementScroll.js.flow
│  │          │      getLayoutRect.d.ts
│  │          │      getLayoutRect.js
│  │          │      getLayoutRect.js.flow
│  │          │      getNodeName.d.ts
│  │          │      getNodeName.js
│  │          │      getNodeName.js.flow
│  │          │      getNodeScroll.d.ts
│  │          │      getNodeScroll.js
│  │          │      getNodeScroll.js.flow
│  │          │      getOffsetParent.d.ts
│  │          │      getOffsetParent.js
│  │          │      getOffsetParent.js.flow
│  │          │      getParentNode.d.ts
│  │          │      getParentNode.js
│  │          │      getParentNode.js.flow
│  │          │      getScrollParent.d.ts
│  │          │      getScrollParent.js
│  │          │      getScrollParent.js.flow
│  │          │      getViewportRect.d.ts
│  │          │      getViewportRect.js
│  │          │      getViewportRect.js.flow
│  │          │      getWindow.d.ts
│  │          │      getWindow.js
│  │          │      getWindow.js.flow
│  │          │      getWindowScroll.d.ts
│  │          │      getWindowScroll.js
│  │          │      getWindowScroll.js.flow
│  │          │      getWindowScrollBarX.d.ts
│  │          │      getWindowScrollBarX.js
│  │          │      getWindowScrollBarX.js.flow
│  │          │      instanceOf.d.ts
│  │          │      instanceOf.js
│  │          │      instanceOf.js.flow
│  │          │      isLayoutViewport.d.ts
│  │          │      isLayoutViewport.js
│  │          │      isLayoutViewport.js.flow
│  │          │      isScrollParent.d.ts
│  │          │      isScrollParent.js
│  │          │      isScrollParent.js.flow
│  │          │      isTableElement.d.ts
│  │          │      isTableElement.js
│  │          │      isTableElement.js.flow
│  │          │      listScrollParents.d.ts
│  │          │      listScrollParents.js
│  │          │      listScrollParents.js.flow
│  │          │
│  │          ├─modifiers
│  │          │      applyStyles.d.ts
│  │          │      applyStyles.js
│  │          │      applyStyles.js.flow
│  │          │      arrow.d.ts
│  │          │      arrow.js
│  │          │      arrow.js.flow
│  │          │      computeStyles.d.ts
│  │          │      computeStyles.js
│  │          │      computeStyles.js.flow
│  │          │      eventListeners.d.ts
│  │          │      eventListeners.js
│  │          │      eventListeners.js.flow
│  │          │      flip.d.ts
│  │          │      flip.js
│  │          │      flip.js.flow
│  │          │      hide.d.ts
│  │          │      hide.js
│  │          │      hide.js.flow
│  │          │      index.d.ts
│  │          │      index.js
│  │          │      index.js.flow
│  │          │      offset.d.ts
│  │          │      offset.js
│  │          │      offset.js.flow
│  │          │      popperOffsets.d.ts
│  │          │      popperOffsets.js
│  │          │      popperOffsets.js.flow
│  │          │      preventOverflow.d.ts
│  │          │      preventOverflow.js
│  │          │      preventOverflow.js.flow
│  │          │
│  │          └─utils
│  │                  computeAutoPlacement.d.ts
│  │                  computeAutoPlacement.js
│  │                  computeAutoPlacement.js.flow
│  │                  computeOffsets.d.ts
│  │                  computeOffsets.js
│  │                  computeOffsets.js.flow
│  │                  debounce.d.ts
│  │                  debounce.js
│  │                  debounce.js.flow
│  │                  detectOverflow.d.ts
│  │                  detectOverflow.js
│  │                  detectOverflow.js.flow
│  │                  expandToHashMap.d.ts
│  │                  expandToHashMap.js
│  │                  expandToHashMap.js.flow
│  │                  getAltAxis.d.ts
│  │                  getAltAxis.js
│  │                  getAltAxis.js.flow
│  │                  getAltLen.d.ts
│  │                  getAltLen.js
│  │                  getAltLen.js.flow
│  │                  getBasePlacement.d.ts
│  │                  getBasePlacement.js
│  │                  getBasePlacement.js.flow
│  │                  getFreshSideObject.d.ts
│  │                  getFreshSideObject.js
│  │                  getFreshSideObject.js.flow
│  │                  getMainAxisFromPlacement.d.ts
│  │                  getMainAxisFromPlacement.js
│  │                  getMainAxisFromPlacement.js.flow
│  │                  getOppositePlacement.d.ts
│  │                  getOppositePlacement.js
│  │                  getOppositePlacement.js.flow
│  │                  getOppositeVariationPlacement.d.ts
│  │                  getOppositeVariationPlacement.js
│  │                  getOppositeVariationPlacement.js.flow
│  │                  getVariation.d.ts
│  │                  getVariation.js
│  │                  getVariation.js.flow
│  │                  math.d.ts
│  │                  math.js
│  │                  math.js.flow
│  │                  mergeByName.d.ts
│  │                  mergeByName.js
│  │                  mergeByName.js.flow
│  │                  mergePaddingObject.d.ts
│  │                  mergePaddingObject.js
│  │                  mergePaddingObject.js.flow
│  │                  orderModifiers.d.ts
│  │                  orderModifiers.js
│  │                  orderModifiers.js.flow
│  │                  rectToClientRect.d.ts
│  │                  rectToClientRect.js
│  │                  rectToClientRect.js.flow
│  │                  uniqueBy.d.ts
│  │                  uniqueBy.js
│  │                  uniqueBy.js.flow
│  │                  userAgent.d.ts
│  │                  userAgent.js
│  │                  userAgent.js.flow
│  │                  within.d.ts
│  │                  within.js
│  │                  within.js.flow
│  │
│  ├─@rollup
│  │  └─rollup-win32-x64-msvc
│  │          package.json
│  │          README.md
│  │          rollup.win32-x64-msvc.node
│  │
│  ├─@stomp
│  │  └─stompjs
│  │      │  index.d.ts
│  │      │  LICENSE
│  │      │  package.json
│  │      │  README.md
│  │      │
│  │      ├─bundles
│  │      │      package.json
│  │      │      stomp.umd.js
│  │      │      stomp.umd.js.map
│  │      │      stomp.umd.min.js
│  │      │
│  │      ├─esm6
│  │      │  │  augment-websocket.d.ts
│  │      │  │  augment-websocket.js
│  │      │  │  augment-websocket.js.map
│  │      │  │  byte.d.ts
│  │      │  │  byte.js
│  │      │  │  byte.js.map
│  │      │  │  client.d.ts
│  │      │  │  client.js
│  │      │  │  client.js.map
│  │      │  │  frame-impl.d.ts
│  │      │  │  frame-impl.js
│  │      │  │  frame-impl.js.map
│  │      │  │  i-frame.d.ts
│  │      │  │  i-frame.js
│  │      │  │  i-frame.js.map
│  │      │  │  i-message.d.ts
│  │      │  │  i-message.js
│  │      │  │  i-message.js.map
│  │      │  │  i-transaction.d.ts
│  │      │  │  i-transaction.js
│  │      │  │  i-transaction.js.map
│  │      │  │  index.d.ts
│  │      │  │  index.js
│  │      │  │  index.js.map
│  │      │  │  parser.d.ts
│  │      │  │  parser.js
│  │      │  │  parser.js.map
│  │      │  │  stomp-config.d.ts
│  │      │  │  stomp-config.js
│  │      │  │  stomp-config.js.map
│  │      │  │  stomp-handler.d.ts
│  │      │  │  stomp-handler.js
│  │      │  │  stomp-handler.js.map
│  │      │  │  stomp-headers.d.ts
│  │      │  │  stomp-headers.js
│  │      │  │  stomp-headers.js.map
│  │      │  │  stomp-subscription.d.ts
│  │      │  │  stomp-subscription.js
│  │      │  │  stomp-subscription.js.map
│  │      │  │  types.d.ts
│  │      │  │  types.js
│  │      │  │  types.js.map
│  │      │  │  versions.d.ts
│  │      │  │  versions.js
│  │      │  │  versions.js.map
│  │      │  │
│  │      │  └─compatibility
│  │      │          compat-client.d.ts
│  │      │          compat-client.js
│  │      │          compat-client.js.map
│  │      │          heartbeat-info.d.ts
│  │      │          heartbeat-info.js
│  │      │          heartbeat-info.js.map
│  │      │          stomp.d.ts
│  │      │          stomp.js
│  │      │          stomp.js.map
│  │      │
│  │      └─src
│  │          │  augment-websocket.ts
│  │          │  byte.ts
│  │          │  client.ts
│  │          │  frame-impl.ts
│  │          │  i-frame.ts
│  │          │  i-message.ts
│  │          │  i-transaction.ts
│  │          │  index.ts
│  │          │  parser.ts
│  │          │  stomp-config.ts
│  │          │  stomp-handler.ts
│  │          │  stomp-headers.ts
│  │          │  stomp-subscription.ts
│  │          │  types.ts
│  │          │  versions.ts
│  │          │
│  │          └─compatibility
│  │                  compat-client.ts
│  │                  heartbeat-info.ts
│  │                  stomp.ts
│  │
│  ├─@types
│  │  └─estree
│  │          flow.d.ts
│  │          index.d.ts
│  │          LICENSE
│  │          package.json
│  │          README.md
│  │
│  ├─@vitejs
│  │  └─plugin-vue
│  │      │  LICENSE
│  │      │  package.json
│  │      │  README.md
│  │      │
│  │      └─dist
│  │              index.cjs
│  │              index.d.cts
│  │              index.d.mts
│  │              index.d.ts
│  │              index.mjs
│  │
│  ├─@vue
│  │  ├─compiler-core
│  │  │  │  index.js
│  │  │  │  LICENSE
│  │  │  │  package.json
│  │  │  │  README.md
│  │  │  │
│  │  │  └─dist
│  │  │          compiler-core.cjs.js
│  │  │          compiler-core.cjs.prod.js
│  │  │          compiler-core.d.ts
│  │  │          compiler-core.esm-bundler.js
│  │  │
│  │  ├─compiler-dom
│  │  │  │  index.js
│  │  │  │  LICENSE
│  │  │  │  package.json
│  │  │  │  README.md
│  │  │  │
│  │  │  └─dist
│  │  │          compiler-dom.cjs.js
│  │  │          compiler-dom.cjs.prod.js
│  │  │          compiler-dom.d.ts
│  │  │          compiler-dom.esm-browser.js
│  │  │          compiler-dom.esm-browser.prod.js
│  │  │          compiler-dom.esm-bundler.js
│  │  │          compiler-dom.global.js
│  │  │          compiler-dom.global.prod.js
│  │  │
│  │  ├─compiler-sfc
│  │  │  │  LICENSE
│  │  │  │  package.json
│  │  │  │  README.md
│  │  │  │
│  │  │  └─dist
│  │  │          compiler-sfc.cjs.js
│  │  │          compiler-sfc.d.ts
│  │  │          compiler-sfc.esm-browser.js
│  │  │
│  │  ├─compiler-ssr
│  │  │  │  LICENSE
│  │  │  │  package.json
│  │  │  │  README.md
│  │  │  │
│  │  │  └─dist
│  │  │          compiler-ssr.cjs.js
│  │  │          compiler-ssr.d.ts
│  │  │
│  │  ├─devtools-api
│  │  │  │  package.json
│  │  │  │
│  │  │  └─lib
│  │  │      ├─cjs
│  │  │      │  │  const.js
│  │  │      │  │  env.js
│  │  │      │  │  index.js
│  │  │      │  │  plugin.js
│  │  │      │  │  proxy.js
│  │  │      │  │  time.js
│  │  │      │  │
│  │  │      │  └─api
│  │  │      │          api.js
│  │  │      │          app.js
│  │  │      │          component.js
│  │  │      │          context.js
│  │  │      │          hooks.js
│  │  │      │          index.js
│  │  │      │          util.js
│  │  │      │
│  │  │      └─esm
│  │  │          │  const.d.ts
│  │  │          │  const.js
│  │  │          │  env.d.ts
│  │  │          │  env.js
│  │  │          │  index.d.ts
│  │  │          │  index.js
│  │  │          │  plugin.d.ts
│  │  │          │  plugin.js
│  │  │          │  proxy.d.ts
│  │  │          │  proxy.js
│  │  │          │  time.d.ts
│  │  │          │  time.js
│  │  │          │
│  │  │          └─api
│  │  │                  api.d.ts
│  │  │                  api.js
│  │  │                  app.d.ts
│  │  │                  app.js
│  │  │                  component.d.ts
│  │  │                  component.js
│  │  │                  context.d.ts
│  │  │                  context.js
│  │  │                  hooks.d.ts
│  │  │                  hooks.js
│  │  │                  index.d.ts
│  │  │                  index.js
│  │  │                  util.d.ts
│  │  │                  util.js
│  │  │
│  │  ├─reactivity
│  │  │  │  index.js
│  │  │  │  LICENSE
│  │  │  │  package.json
│  │  │  │  README.md
│  │  │  │
│  │  │  └─dist
│  │  │          reactivity.cjs.js
│  │  │          reactivity.cjs.prod.js
│  │  │          reactivity.d.ts
│  │  │          reactivity.esm-browser.js
│  │  │          reactivity.esm-browser.prod.js
│  │  │          reactivity.esm-bundler.js
│  │  │          reactivity.global.js
│  │  │          reactivity.global.prod.js
│  │  │
│  │  ├─runtime-core
│  │  │  │  index.js
│  │  │  │  LICENSE
│  │  │  │  package.json
│  │  │  │  README.md
│  │  │  │
│  │  │  └─dist
│  │  │          runtime-core.cjs.js
│  │  │          runtime-core.cjs.prod.js
│  │  │          runtime-core.d.ts
│  │  │          runtime-core.esm-bundler.js
│  │  │
│  │  ├─runtime-dom
│  │  │  │  index.js
│  │  │  │  LICENSE
│  │  │  │  package.json
│  │  │  │  README.md
│  │  │  │
│  │  │  └─dist
│  │  │          runtime-dom.cjs.js
│  │  │          runtime-dom.cjs.prod.js
│  │  │          runtime-dom.d.ts
│  │  │          runtime-dom.esm-browser.js
│  │  │          runtime-dom.esm-browser.prod.js
│  │  │          runtime-dom.esm-bundler.js
│  │  │          runtime-dom.global.js
│  │  │          runtime-dom.global.prod.js
│  │  │
│  │  ├─server-renderer
│  │  │  │  index.js
│  │  │  │  LICENSE
│  │  │  │  package.json
│  │  │  │  README.md
│  │  │  │
│  │  │  └─dist
│  │  │          server-renderer.cjs.js
│  │  │          server-renderer.cjs.prod.js
│  │  │          server-renderer.d.ts
│  │  │          server-renderer.esm-browser.js
│  │  │          server-renderer.esm-browser.prod.js
│  │  │          server-renderer.esm-bundler.js
│  │  │
│  │  └─shared
│  │      │  index.js
│  │      │  LICENSE
│  │      │  package.json
│  │      │  README.md
│  │      │
│  │      └─dist
│  │              shared.cjs.js
│  │              shared.cjs.prod.js
│  │              shared.d.ts
│  │              shared.esm-bundler.js
│  │
│  ├─asynckit
│  │  │  bench.js
│  │  │  index.js
│  │  │  LICENSE
│  │  │  package.json
│  │  │  parallel.js
│  │  │  README.md
│  │  │  serial.js
│  │  │  serialOrdered.js
│  │  │  stream.js
│  │  │
│  │  └─lib
│  │          abort.js
│  │          async.js
│  │          defer.js
│  │          iterate.js
│  │          readable_asynckit.js
│  │          readable_parallel.js
│  │          readable_serial.js
│  │          readable_serial_ordered.js
│  │          state.js
│  │          streamify.js
│  │          terminator.js
│  │
│  ├─axios
│  │  │  CHANGELOG.md
│  │  │  index.d.cts
│  │  │  index.d.ts
│  │  │  index.js
│  │  │  LICENSE
│  │  │  MIGRATION_GUIDE.md
│  │  │  package.json
│  │  │  README.md
│  │  │  SECURITY.md
│  │  │
│  │  ├─dist
│  │  │  │  axios.js
│  │  │  │  axios.js.map
│  │  │  │  axios.min.js
│  │  │  │  axios.min.js.map
│  │  │  │
│  │  │  ├─browser
│  │  │  │      axios.cjs
│  │  │  │      axios.cjs.map
│  │  │  │
│  │  │  ├─esm
│  │  │  │      axios.js
│  │  │  │      axios.js.map
│  │  │  │      axios.min.js
│  │  │  │      axios.min.js.map
│  │  │  │
│  │  │  └─node
│  │  │          axios.cjs
│  │  │          axios.cjs.map
│  │  │
│  │  └─lib
│  │      │  axios.js
│  │      │  utils.js
│  │      │
│  │      ├─adapters
│  │      │      adapters.js
│  │      │      http.js
│  │      │      README.md
│  │      │      xhr.js
│  │      │
│  │      ├─cancel
│  │      │      CanceledError.js
│  │      │      CancelToken.js
│  │      │      isCancel.js
│  │      │
│  │      ├─core
│  │      │      Axios.js
│  │      │      AxiosError.js
│  │      │      AxiosHeaders.js
│  │      │      buildFullPath.js
│  │      │      dispatchRequest.js
│  │      │      InterceptorManager.js
│  │      │      mergeConfig.js
│  │      │      README.md
│  │      │      settle.js
│  │      │      transformData.js
│  │      │
│  │      ├─defaults
│  │      │      index.js
│  │      │      transitional.js
│  │      │
│  │      ├─env
│  │      │  │  data.js
│  │      │  │  README.md
│  │      │  │
│  │      │  └─classes
│  │      │          FormData.js
│  │      │
│  │      ├─helpers
│  │      │      AxiosTransformStream.js
│  │      │      AxiosURLSearchParams.js
│  │      │      bind.js
│  │      │      buildURL.js
│  │      │      callbackify.js
│  │      │      combineURLs.js
│  │      │      cookies.js
│  │      │      deprecatedMethod.js
│  │      │      formDataToJSON.js
│  │      │      formDataToStream.js
│  │      │      fromDataURI.js
│  │      │      HttpStatusCode.js
│  │      │      isAbsoluteURL.js
│  │      │      isAxiosError.js
│  │      │      isURLSameOrigin.js
│  │      │      null.js
│  │      │      parseHeaders.js
│  │      │      parseProtocol.js
│  │      │      readBlob.js
│  │      │      README.md
│  │      │      speedometer.js
│  │      │      spread.js
│  │      │      throttle.js
│  │      │      toFormData.js
│  │      │      toURLEncodedForm.js
│  │      │      validator.js
│  │      │      ZlibHeaderTransformStream.js
│  │      │
│  │      └─platform
│  │          │  index.js
│  │          │
│  │          ├─browser
│  │          │  │  index.js
│  │          │  │
│  │          │  └─classes
│  │          │          Blob.js
│  │          │          FormData.js
│  │          │          URLSearchParams.js
│  │          │
│  │          ├─common
│  │          │      utils.js
│  │          │
│  │          └─node
│  │              │  index.js
│  │              │
│  │              └─classes
│  │                      FormData.js
│  │                      URLSearchParams.js
│  │
│  ├─bootstrap
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─dist
│  │  │  ├─css
│  │  │  │      bootstrap-grid.css
│  │  │  │      bootstrap-grid.css.map
│  │  │  │      bootstrap-grid.min.css
│  │  │  │      bootstrap-grid.min.css.map
│  │  │  │      bootstrap-grid.rtl.css
│  │  │  │      bootstrap-grid.rtl.css.map
│  │  │  │      bootstrap-grid.rtl.min.css
│  │  │  │      bootstrap-grid.rtl.min.css.map
│  │  │  │      bootstrap-reboot.css
│  │  │  │      bootstrap-reboot.css.map
│  │  │  │      bootstrap-reboot.min.css
│  │  │  │      bootstrap-reboot.min.css.map
│  │  │  │      bootstrap-reboot.rtl.css
│  │  │  │      bootstrap-reboot.rtl.css.map
│  │  │  │      bootstrap-reboot.rtl.min.css
│  │  │  │      bootstrap-reboot.rtl.min.css.map
│  │  │  │      bootstrap-utilities.css
│  │  │  │      bootstrap-utilities.css.map
│  │  │  │      bootstrap-utilities.min.css
│  │  │  │      bootstrap-utilities.min.css.map
│  │  │  │      bootstrap-utilities.rtl.css
│  │  │  │      bootstrap-utilities.rtl.css.map
│  │  │  │      bootstrap-utilities.rtl.min.css
│  │  │  │      bootstrap-utilities.rtl.min.css.map
│  │  │  │      bootstrap.css
│  │  │  │      bootstrap.css.map
│  │  │  │      bootstrap.min.css
│  │  │  │      bootstrap.min.css.map
│  │  │  │      bootstrap.rtl.css
│  │  │  │      bootstrap.rtl.css.map
│  │  │  │      bootstrap.rtl.min.css
│  │  │  │      bootstrap.rtl.min.css.map
│  │  │  │
│  │  │  └─js
│  │  │          bootstrap.bundle.js
│  │  │          bootstrap.bundle.js.map
│  │  │          bootstrap.bundle.min.js
│  │  │          bootstrap.bundle.min.js.map
│  │  │          bootstrap.esm.js
│  │  │          bootstrap.esm.js.map
│  │  │          bootstrap.esm.min.js
│  │  │          bootstrap.esm.min.js.map
│  │  │          bootstrap.js
│  │  │          bootstrap.js.map
│  │  │          bootstrap.min.js
│  │  │          bootstrap.min.js.map
│  │  │
│  │  ├─js
│  │  │  │  index.esm.js
│  │  │  │  index.umd.js
│  │  │  │
│  │  │  ├─dist
│  │  │  │  │  alert.js
│  │  │  │  │  alert.js.map
│  │  │  │  │  base-component.js
│  │  │  │  │  base-component.js.map
│  │  │  │  │  button.js
│  │  │  │  │  button.js.map
│  │  │  │  │  carousel.js
│  │  │  │  │  carousel.js.map
│  │  │  │  │  collapse.js
│  │  │  │  │  collapse.js.map
│  │  │  │  │  dropdown.js
│  │  │  │  │  dropdown.js.map
│  │  │  │  │  modal.js
│  │  │  │  │  modal.js.map
│  │  │  │  │  offcanvas.js
│  │  │  │  │  offcanvas.js.map
│  │  │  │  │  popover.js
│  │  │  │  │  popover.js.map
│  │  │  │  │  scrollspy.js
│  │  │  │  │  scrollspy.js.map
│  │  │  │  │  tab.js
│  │  │  │  │  tab.js.map
│  │  │  │  │  toast.js
│  │  │  │  │  toast.js.map
│  │  │  │  │  tooltip.js
│  │  │  │  │  tooltip.js.map
│  │  │  │  │
│  │  │  │  ├─dom
│  │  │  │  │      data.js
│  │  │  │  │      data.js.map
│  │  │  │  │      event-handler.js
│  │  │  │  │      event-handler.js.map
│  │  │  │  │      manipulator.js
│  │  │  │  │      manipulator.js.map
│  │  │  │  │      selector-engine.js
│  │  │  │  │      selector-engine.js.map
│  │  │  │  │
│  │  │  │  └─util
│  │  │  │          backdrop.js
│  │  │  │          backdrop.js.map
│  │  │  │          component-functions.js
│  │  │  │          component-functions.js.map
│  │  │  │          config.js
│  │  │  │          config.js.map
│  │  │  │          focustrap.js
│  │  │  │          focustrap.js.map
│  │  │  │          index.js
│  │  │  │          index.js.map
│  │  │  │          sanitizer.js
│  │  │  │          sanitizer.js.map
│  │  │  │          scrollbar.js
│  │  │  │          scrollbar.js.map
│  │  │  │          swipe.js
│  │  │  │          swipe.js.map
│  │  │  │          template-factory.js
│  │  │  │          template-factory.js.map
│  │  │  │
│  │  │  └─src
│  │  │      │  alert.js
│  │  │      │  base-component.js
│  │  │      │  button.js
│  │  │      │  carousel.js
│  │  │      │  collapse.js
│  │  │      │  dropdown.js
│  │  │      │  modal.js
│  │  │      │  offcanvas.js
│  │  │      │  popover.js
│  │  │      │  scrollspy.js
│  │  │      │  tab.js
│  │  │      │  toast.js
│  │  │      │  tooltip.js
│  │  │      │
│  │  │      ├─dom
│  │  │      │      data.js
│  │  │      │      event-handler.js
│  │  │      │      manipulator.js
│  │  │      │      selector-engine.js
│  │  │      │
│  │  │      └─util
│  │  │              backdrop.js
│  │  │              component-functions.js
│  │  │              config.js
│  │  │              focustrap.js
│  │  │              index.js
│  │  │              sanitizer.js
│  │  │              scrollbar.js
│  │  │              swipe.js
│  │  │              template-factory.js
│  │  │
│  │  └─scss
│  │      │  bootstrap-grid.scss
│  │      │  bootstrap-reboot.scss
│  │      │  bootstrap-utilities.scss
│  │      │  bootstrap.scss
│  │      │  _accordion.scss
│  │      │  _alert.scss
│  │      │  _badge.scss
│  │      │  _breadcrumb.scss
│  │      │  _button-group.scss
│  │      │  _buttons.scss
│  │      │  _card.scss
│  │      │  _carousel.scss
│  │      │  _close.scss
│  │      │  _containers.scss
│  │      │  _dropdown.scss
│  │      │  _forms.scss
│  │      │  _functions.scss
│  │      │  _grid.scss
│  │      │  _helpers.scss
│  │      │  _images.scss
│  │      │  _list-group.scss
│  │      │  _maps.scss
│  │      │  _mixins.scss
│  │      │  _modal.scss
│  │      │  _nav.scss
│  │      │  _navbar.scss
│  │      │  _offcanvas.scss
│  │      │  _pagination.scss
│  │      │  _placeholders.scss
│  │      │  _popover.scss
│  │      │  _progress.scss
│  │      │  _reboot.scss
│  │      │  _root.scss
│  │      │  _spinners.scss
│  │      │  _tables.scss
│  │      │  _toasts.scss
│  │      │  _tooltip.scss
│  │      │  _transitions.scss
│  │      │  _type.scss
│  │      │  _utilities.scss
│  │      │  _variables-dark.scss
│  │      │  _variables.scss
│  │      │
│  │      ├─forms
│  │      │      _floating-labels.scss
│  │      │      _form-check.scss
│  │      │      _form-control.scss
│  │      │      _form-range.scss
│  │      │      _form-select.scss
│  │      │      _form-text.scss
│  │      │      _input-group.scss
│  │      │      _labels.scss
│  │      │      _validation.scss
│  │      │
│  │      ├─helpers
│  │      │      _clearfix.scss
│  │      │      _color-bg.scss
│  │      │      _colored-links.scss
│  │      │      _focus-ring.scss
│  │      │      _icon-link.scss
│  │      │      _position.scss
│  │      │      _ratio.scss
│  │      │      _stacks.scss
│  │      │      _stretched-link.scss
│  │      │      _text-truncation.scss
│  │      │      _visually-hidden.scss
│  │      │      _vr.scss
│  │      │
│  │      ├─mixins
│  │      │      _alert.scss
│  │      │      _backdrop.scss
│  │      │      _banner.scss
│  │      │      _border-radius.scss
│  │      │      _box-shadow.scss
│  │      │      _breakpoints.scss
│  │      │      _buttons.scss
│  │      │      _caret.scss
│  │      │      _clearfix.scss
│  │      │      _color-mode.scss
│  │      │      _color-scheme.scss
│  │      │      _container.scss
│  │      │      _deprecate.scss
│  │      │      _forms.scss
│  │      │      _gradients.scss
│  │      │      _grid.scss
│  │      │      _image.scss
│  │      │      _list-group.scss
│  │      │      _lists.scss
│  │      │      _pagination.scss
│  │      │      _reset-text.scss
│  │      │      _resize.scss
│  │      │      _table-variants.scss
│  │      │      _text-truncate.scss
│  │      │      _transition.scss
│  │      │      _utilities.scss
│  │      │      _visually-hidden.scss
│  │      │
│  │      ├─utilities
│  │      │      _api.scss
│  │      │
│  │      └─vendor
│  │              _rfs.scss
│  │
│  ├─bootstrap-icons
│  │  │  bootstrap-icons.svg
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─font
│  │  │  │  bootstrap-icons.css
│  │  │  │  bootstrap-icons.json
│  │  │  │  bootstrap-icons.min.css
│  │  │  │  bootstrap-icons.scss
│  │  │  │
│  │  │  └─fonts
│  │  │          bootstrap-icons.woff
│  │  │          bootstrap-icons.woff2
│  │  │
│  │  └─icons
│  │          0-circle-fill.svg
│  │          0-circle.svg
│  │          0-square-fill.svg
│  │          0-square.svg
│  │          1-circle-fill.svg
│  │          1-circle.svg
│  │          1-square-fill.svg
│  │          1-square.svg
│  │          123.svg
│  │          2-circle-fill.svg
│  │          2-circle.svg
│  │          2-square-fill.svg
│  │          2-square.svg
│  │          3-circle-fill.svg
│  │          3-circle.svg
│  │          3-square-fill.svg
│  │          3-square.svg
│  │          4-circle-fill.svg
│  │          4-circle.svg
│  │          4-square-fill.svg
│  │          4-square.svg
│  │          5-circle-fill.svg
│  │          5-circle.svg
│  │          5-square-fill.svg
│  │          5-square.svg
│  │          6-circle-fill.svg
│  │          6-circle.svg
│  │          6-square-fill.svg
│  │          6-square.svg
│  │          7-circle-fill.svg
│  │          7-circle.svg
│  │          7-square-fill.svg
│  │          7-square.svg
│  │          8-circle-fill.svg
│  │          8-circle.svg
│  │          8-square-fill.svg
│  │          8-square.svg
│  │          9-circle-fill.svg
│  │          9-circle.svg
│  │          9-square-fill.svg
│  │          9-square.svg
│  │          activity.svg
│  │          airplane-engines-fill.svg
│  │          airplane-engines.svg
│  │          airplane-fill.svg
│  │          airplane.svg
│  │          alarm-fill.svg
│  │          alarm.svg
│  │          alexa.svg
│  │          align-bottom.svg
│  │          align-center.svg
│  │          align-end.svg
│  │          align-middle.svg
│  │          align-start.svg
│  │          align-top.svg
│  │          alipay.svg
│  │          alphabet-uppercase.svg
│  │          alphabet.svg
│  │          alt.svg
│  │          amazon.svg
│  │          amd.svg
│  │          android.svg
│  │          android2.svg
│  │          app-indicator.svg
│  │          app.svg
│  │          apple.svg
│  │          archive-fill.svg
│  │          archive.svg
│  │          arrow-90deg-down.svg
│  │          arrow-90deg-left.svg
│  │          arrow-90deg-right.svg
│  │          arrow-90deg-up.svg
│  │          arrow-bar-down.svg
│  │          arrow-bar-left.svg
│  │          arrow-bar-right.svg
│  │          arrow-bar-up.svg
│  │          arrow-clockwise.svg
│  │          arrow-counterclockwise.svg
│  │          arrow-down-circle-fill.svg
│  │          arrow-down-circle.svg
│  │          arrow-down-left-circle-fill.svg
│  │          arrow-down-left-circle.svg
│  │          arrow-down-left-square-fill.svg
│  │          arrow-down-left-square.svg
│  │          arrow-down-left.svg
│  │          arrow-down-right-circle-fill.svg
│  │          arrow-down-right-circle.svg
│  │          arrow-down-right-square-fill.svg
│  │          arrow-down-right-square.svg
│  │          arrow-down-right.svg
│  │          arrow-down-short.svg
│  │          arrow-down-square-fill.svg
│  │          arrow-down-square.svg
│  │          arrow-down-up.svg
│  │          arrow-down.svg
│  │          arrow-left-circle-fill.svg
│  │          arrow-left-circle.svg
│  │          arrow-left-right.svg
│  │          arrow-left-short.svg
│  │          arrow-left-square-fill.svg
│  │          arrow-left-square.svg
│  │          arrow-left.svg
│  │          arrow-repeat.svg
│  │          arrow-return-left.svg
│  │          arrow-return-right.svg
│  │          arrow-right-circle-fill.svg
│  │          arrow-right-circle.svg
│  │          arrow-right-short.svg
│  │          arrow-right-square-fill.svg
│  │          arrow-right-square.svg
│  │          arrow-right.svg
│  │          arrow-through-heart-fill.svg
│  │          arrow-through-heart.svg
│  │          arrow-up-circle-fill.svg
│  │          arrow-up-circle.svg
│  │          arrow-up-left-circle-fill.svg
│  │          arrow-up-left-circle.svg
│  │          arrow-up-left-square-fill.svg
│  │          arrow-up-left-square.svg
│  │          arrow-up-left.svg
│  │          arrow-up-right-circle-fill.svg
│  │          arrow-up-right-circle.svg
│  │          arrow-up-right-square-fill.svg
│  │          arrow-up-right-square.svg
│  │          arrow-up-right.svg
│  │          arrow-up-short.svg
│  │          arrow-up-square-fill.svg
│  │          arrow-up-square.svg
│  │          arrow-up.svg
│  │          arrows-angle-contract.svg
│  │          arrows-angle-expand.svg
│  │          arrows-collapse-vertical.svg
│  │          arrows-collapse.svg
│  │          arrows-expand-vertical.svg
│  │          arrows-expand.svg
│  │          arrows-fullscreen.svg
│  │          arrows-move.svg
│  │          arrows-vertical.svg
│  │          arrows.svg
│  │          aspect-ratio-fill.svg
│  │          aspect-ratio.svg
│  │          asterisk.svg
│  │          at.svg
│  │          award-fill.svg
│  │          award.svg
│  │          back.svg
│  │          backpack-fill.svg
│  │          backpack.svg
│  │          backpack2-fill.svg
│  │          backpack2.svg
│  │          backpack3-fill.svg
│  │          backpack3.svg
│  │          backpack4-fill.svg
│  │          backpack4.svg
│  │          backspace-fill.svg
│  │          backspace-reverse-fill.svg
│  │          backspace-reverse.svg
│  │          backspace.svg
│  │          badge-3d-fill.svg
│  │          badge-3d.svg
│  │          badge-4k-fill.svg
│  │          badge-4k.svg
│  │          badge-8k-fill.svg
│  │          badge-8k.svg
│  │          badge-ad-fill.svg
│  │          badge-ad.svg
│  │          badge-ar-fill.svg
│  │          badge-ar.svg
│  │          badge-cc-fill.svg
│  │          badge-cc.svg
│  │          badge-hd-fill.svg
│  │          badge-hd.svg
│  │          badge-sd-fill.svg
│  │          badge-sd.svg
│  │          badge-tm-fill.svg
│  │          badge-tm.svg
│  │          badge-vo-fill.svg
│  │          badge-vo.svg
│  │          badge-vr-fill.svg
│  │          badge-vr.svg
│  │          badge-wc-fill.svg
│  │          badge-wc.svg
│  │          bag-check-fill.svg
│  │          bag-check.svg
│  │          bag-dash-fill.svg
│  │          bag-dash.svg
│  │          bag-fill.svg
│  │          bag-heart-fill.svg
│  │          bag-heart.svg
│  │          bag-plus-fill.svg
│  │          bag-plus.svg
│  │          bag-x-fill.svg
│  │          bag-x.svg
│  │          bag.svg
│  │          balloon-fill.svg
│  │          balloon-heart-fill.svg
│  │          balloon-heart.svg
│  │          balloon.svg
│  │          ban-fill.svg
│  │          ban.svg
│  │          bandaid-fill.svg
│  │          bandaid.svg
│  │          bank.svg
│  │          bank2.svg
│  │          bar-chart-fill.svg
│  │          bar-chart-line-fill.svg
│  │          bar-chart-line.svg
│  │          bar-chart-steps.svg
│  │          bar-chart.svg
│  │          basket-fill.svg
│  │          basket.svg
│  │          basket2-fill.svg
│  │          basket2.svg
│  │          basket3-fill.svg
│  │          basket3.svg
│  │          battery-charging.svg
│  │          battery-full.svg
│  │          battery-half.svg
│  │          battery.svg
│  │          behance.svg
│  │          bell-fill.svg
│  │          bell-slash-fill.svg
│  │          bell-slash.svg
│  │          bell.svg
│  │          bezier.svg
│  │          bezier2.svg
│  │          bicycle.svg
│  │          bing.svg
│  │          binoculars-fill.svg
│  │          binoculars.svg
│  │          blockquote-left.svg
│  │          blockquote-right.svg
│  │          bluetooth.svg
│  │          body-text.svg
│  │          book-fill.svg
│  │          book-half.svg
│  │          book.svg
│  │          bookmark-check-fill.svg
│  │          bookmark-check.svg
│  │          bookmark-dash-fill.svg
│  │          bookmark-dash.svg
│  │          bookmark-fill.svg
│  │          bookmark-heart-fill.svg
│  │          bookmark-heart.svg
│  │          bookmark-plus-fill.svg
│  │          bookmark-plus.svg
│  │          bookmark-star-fill.svg
│  │          bookmark-star.svg
│  │          bookmark-x-fill.svg
│  │          bookmark-x.svg
│  │          bookmark.svg
│  │          bookmarks-fill.svg
│  │          bookmarks.svg
│  │          bookshelf.svg
│  │          boombox-fill.svg
│  │          boombox.svg
│  │          bootstrap-fill.svg
│  │          bootstrap-reboot.svg
│  │          bootstrap.svg
│  │          border-all.svg
│  │          border-bottom.svg
│  │          border-center.svg
│  │          border-inner.svg
│  │          border-left.svg
│  │          border-middle.svg
│  │          border-outer.svg
│  │          border-right.svg
│  │          border-style.svg
│  │          border-top.svg
│  │          border-width.svg
│  │          border.svg
│  │          bounding-box-circles.svg
│  │          bounding-box.svg
│  │          box-arrow-down-left.svg
│  │          box-arrow-down-right.svg
│  │          box-arrow-down.svg
│  │          box-arrow-in-down-left.svg
│  │          box-arrow-in-down-right.svg
│  │          box-arrow-in-down.svg
│  │          box-arrow-in-left.svg
│  │          box-arrow-in-right.svg
│  │          box-arrow-in-up-left.svg
│  │          box-arrow-in-up-right.svg
│  │          box-arrow-in-up.svg
│  │          box-arrow-left.svg
│  │          box-arrow-right.svg
│  │          box-arrow-up-left.svg
│  │          box-arrow-up-right.svg
│  │          box-arrow-up.svg
│  │          box-fill.svg
│  │          box-seam-fill.svg
│  │          box-seam.svg
│  │          box.svg
│  │          box2-fill.svg
│  │          box2-heart-fill.svg
│  │          box2-heart.svg
│  │          box2.svg
│  │          boxes.svg
│  │          braces-asterisk.svg
│  │          braces.svg
│  │          bricks.svg
│  │          briefcase-fill.svg
│  │          briefcase.svg
│  │          brightness-alt-high-fill.svg
│  │          brightness-alt-high.svg
│  │          brightness-alt-low-fill.svg
│  │          brightness-alt-low.svg
│  │          brightness-high-fill.svg
│  │          brightness-high.svg
│  │          brightness-low-fill.svg
│  │          brightness-low.svg
│  │          brilliance.svg
│  │          broadcast-pin.svg
│  │          broadcast.svg
│  │          browser-chrome.svg
│  │          browser-edge.svg
│  │          browser-firefox.svg
│  │          browser-safari.svg
│  │          brush-fill.svg
│  │          brush.svg
│  │          bucket-fill.svg
│  │          bucket.svg
│  │          bug-fill.svg
│  │          bug.svg
│  │          building-add.svg
│  │          building-check.svg
│  │          building-dash.svg
│  │          building-down.svg
│  │          building-exclamation.svg
│  │          building-fill-add.svg
│  │          building-fill-check.svg
│  │          building-fill-dash.svg
│  │          building-fill-down.svg
│  │          building-fill-exclamation.svg
│  │          building-fill-gear.svg
│  │          building-fill-lock.svg
│  │          building-fill-slash.svg
│  │          building-fill-up.svg
│  │          building-fill-x.svg
│  │          building-fill.svg
│  │          building-gear.svg
│  │          building-lock.svg
│  │          building-slash.svg
│  │          building-up.svg
│  │          building-x.svg
│  │          building.svg
│  │          buildings-fill.svg
│  │          buildings.svg
│  │          bullseye.svg
│  │          bus-front-fill.svg
│  │          bus-front.svg
│  │          c-circle-fill.svg
│  │          c-circle.svg
│  │          c-square-fill.svg
│  │          c-square.svg
│  │          cake-fill.svg
│  │          cake.svg
│  │          cake2-fill.svg
│  │          cake2.svg
│  │          calculator-fill.svg
│  │          calculator.svg
│  │          calendar-check-fill.svg
│  │          calendar-check.svg
│  │          calendar-date-fill.svg
│  │          calendar-date.svg
│  │          calendar-day-fill.svg
│  │          calendar-day.svg
│  │          calendar-event-fill.svg
│  │          calendar-event.svg
│  │          calendar-fill.svg
│  │          calendar-heart-fill.svg
│  │          calendar-heart.svg
│  │          calendar-minus-fill.svg
│  │          calendar-minus.svg
│  │          calendar-month-fill.svg
│  │          calendar-month.svg
│  │          calendar-plus-fill.svg
│  │          calendar-plus.svg
│  │          calendar-range-fill.svg
│  │          calendar-range.svg
│  │          calendar-week-fill.svg
│  │          calendar-week.svg
│  │          calendar-x-fill.svg
│  │          calendar-x.svg
│  │          calendar.svg
│  │          calendar2-check-fill.svg
│  │          calendar2-check.svg
│  │          calendar2-date-fill.svg
│  │          calendar2-date.svg
│  │          calendar2-day-fill.svg
│  │          calendar2-day.svg
│  │          calendar2-event-fill.svg
│  │          calendar2-event.svg
│  │          calendar2-fill.svg
│  │          calendar2-heart-fill.svg
│  │          calendar2-heart.svg
│  │          calendar2-minus-fill.svg
│  │          calendar2-minus.svg
│  │          calendar2-month-fill.svg
│  │          calendar2-month.svg
│  │          calendar2-plus-fill.svg
│  │          calendar2-plus.svg
│  │          calendar2-range-fill.svg
│  │          calendar2-range.svg
│  │          calendar2-week-fill.svg
│  │          calendar2-week.svg
│  │          calendar2-x-fill.svg
│  │          calendar2-x.svg
│  │          calendar2.svg
│  │          calendar3-event-fill.svg
│  │          calendar3-event.svg
│  │          calendar3-fill.svg
│  │          calendar3-range-fill.svg
│  │          calendar3-range.svg
│  │          calendar3-week-fill.svg
│  │          calendar3-week.svg
│  │          calendar3.svg
│  │          calendar4-event.svg
│  │          calendar4-range.svg
│  │          calendar4-week.svg
│  │          calendar4.svg
│  │          camera-fill.svg
│  │          camera-reels-fill.svg
│  │          camera-reels.svg
│  │          camera-video-fill.svg
│  │          camera-video-off-fill.svg
│  │          camera-video-off.svg
│  │          camera-video.svg
│  │          camera.svg
│  │          camera2.svg
│  │          capslock-fill.svg
│  │          capslock.svg
│  │          capsule-pill.svg
│  │          capsule.svg
│  │          car-front-fill.svg
│  │          car-front.svg
│  │          card-checklist.svg
│  │          card-heading.svg
│  │          card-image.svg
│  │          card-list.svg
│  │          card-text.svg
│  │          caret-down-fill.svg
│  │          caret-down-square-fill.svg
│  │          caret-down-square.svg
│  │          caret-down.svg
│  │          caret-left-fill.svg
│  │          caret-left-square-fill.svg
│  │          caret-left-square.svg
│  │          caret-left.svg
│  │          caret-right-fill.svg
│  │          caret-right-square-fill.svg
│  │          caret-right-square.svg
│  │          caret-right.svg
│  │          caret-up-fill.svg
│  │          caret-up-square-fill.svg
│  │          caret-up-square.svg
│  │          caret-up.svg
│  │          cart-check-fill.svg
│  │          cart-check.svg
│  │          cart-dash-fill.svg
│  │          cart-dash.svg
│  │          cart-fill.svg
│  │          cart-plus-fill.svg
│  │          cart-plus.svg
│  │          cart-x-fill.svg
│  │          cart-x.svg
│  │          cart.svg
│  │          cart2.svg
│  │          cart3.svg
│  │          cart4.svg
│  │          cash-coin.svg
│  │          cash-stack.svg
│  │          cash.svg
│  │          cassette-fill.svg
│  │          cassette.svg
│  │          cast.svg
│  │          cc-circle-fill.svg
│  │          cc-circle.svg
│  │          cc-square-fill.svg
│  │          cc-square.svg
│  │          chat-dots-fill.svg
│  │          chat-dots.svg
│  │          chat-fill.svg
│  │          chat-heart-fill.svg
│  │          chat-heart.svg
│  │          chat-left-dots-fill.svg
│  │          chat-left-dots.svg
│  │          chat-left-fill.svg
│  │          chat-left-heart-fill.svg
│  │          chat-left-heart.svg
│  │          chat-left-quote-fill.svg
│  │          chat-left-quote.svg
│  │          chat-left-text-fill.svg
│  │          chat-left-text.svg
│  │          chat-left.svg
│  │          chat-quote-fill.svg
│  │          chat-quote.svg
│  │          chat-right-dots-fill.svg
│  │          chat-right-dots.svg
│  │          chat-right-fill.svg
│  │          chat-right-heart-fill.svg
│  │          chat-right-heart.svg
│  │          chat-right-quote-fill.svg
│  │          chat-right-quote.svg
│  │          chat-right-text-fill.svg
│  │          chat-right-text.svg
│  │          chat-right.svg
│  │          chat-square-dots-fill.svg
│  │          chat-square-dots.svg
│  │          chat-square-fill.svg
│  │          chat-square-heart-fill.svg
│  │          chat-square-heart.svg
│  │          chat-square-quote-fill.svg
│  │          chat-square-quote.svg
│  │          chat-square-text-fill.svg
│  │          chat-square-text.svg
│  │          chat-square.svg
│  │          chat-text-fill.svg
│  │          chat-text.svg
│  │          chat.svg
│  │          check-all.svg
│  │          check-circle-fill.svg
│  │          check-circle.svg
│  │          check-lg.svg
│  │          check-square-fill.svg
│  │          check-square.svg
│  │          check.svg
│  │          check2-all.svg
│  │          check2-circle.svg
│  │          check2-square.svg
│  │          check2.svg
│  │          chevron-bar-contract.svg
│  │          chevron-bar-down.svg
│  │          chevron-bar-expand.svg
│  │          chevron-bar-left.svg
│  │          chevron-bar-right.svg
│  │          chevron-bar-up.svg
│  │          chevron-compact-down.svg
│  │          chevron-compact-left.svg
│  │          chevron-compact-right.svg
│  │          chevron-compact-up.svg
│  │          chevron-contract.svg
│  │          chevron-double-down.svg
│  │          chevron-double-left.svg
│  │          chevron-double-right.svg
│  │          chevron-double-up.svg
│  │          chevron-down.svg
│  │          chevron-expand.svg
│  │          chevron-left.svg
│  │          chevron-right.svg
│  │          chevron-up.svg
│  │          circle-fill.svg
│  │          circle-half.svg
│  │          circle-square.svg
│  │          circle.svg
│  │          clipboard-check-fill.svg
│  │          clipboard-check.svg
│  │          clipboard-data-fill.svg
│  │          clipboard-data.svg
│  │          clipboard-fill.svg
│  │          clipboard-heart-fill.svg
│  │          clipboard-heart.svg
│  │          clipboard-minus-fill.svg
│  │          clipboard-minus.svg
│  │          clipboard-plus-fill.svg
│  │          clipboard-plus.svg
│  │          clipboard-pulse.svg
│  │          clipboard-x-fill.svg
│  │          clipboard-x.svg
│  │          clipboard.svg
│  │          clipboard2-check-fill.svg
│  │          clipboard2-check.svg
│  │          clipboard2-data-fill.svg
│  │          clipboard2-data.svg
│  │          clipboard2-fill.svg
│  │          clipboard2-heart-fill.svg
│  │          clipboard2-heart.svg
│  │          clipboard2-minus-fill.svg
│  │          clipboard2-minus.svg
│  │          clipboard2-plus-fill.svg
│  │          clipboard2-plus.svg
│  │          clipboard2-pulse-fill.svg
│  │          clipboard2-pulse.svg
│  │          clipboard2-x-fill.svg
│  │          clipboard2-x.svg
│  │          clipboard2.svg
│  │          clock-fill.svg
│  │          clock-history.svg
│  │          clock.svg
│  │          cloud-arrow-down-fill.svg
│  │          cloud-arrow-down.svg
│  │          cloud-arrow-up-fill.svg
│  │          cloud-arrow-up.svg
│  │          cloud-check-fill.svg
│  │          cloud-check.svg
│  │          cloud-download-fill.svg
│  │          cloud-download.svg
│  │          cloud-drizzle-fill.svg
│  │          cloud-drizzle.svg
│  │          cloud-fill.svg
│  │          cloud-fog-fill.svg
│  │          cloud-fog.svg
│  │          cloud-fog2-fill.svg
│  │          cloud-fog2.svg
│  │          cloud-hail-fill.svg
│  │          cloud-hail.svg
│  │          cloud-haze-fill.svg
│  │          cloud-haze.svg
│  │          cloud-haze2-fill.svg
│  │          cloud-haze2.svg
│  │          cloud-lightning-fill.svg
│  │          cloud-lightning-rain-fill.svg
│  │          cloud-lightning-rain.svg
│  │          cloud-lightning.svg
│  │          cloud-minus-fill.svg
│  │          cloud-minus.svg
│  │          cloud-moon-fill.svg
│  │          cloud-moon.svg
│  │          cloud-plus-fill.svg
│  │          cloud-plus.svg
│  │          cloud-rain-fill.svg
│  │          cloud-rain-heavy-fill.svg
│  │          cloud-rain-heavy.svg
│  │          cloud-rain.svg
│  │          cloud-slash-fill.svg
│  │          cloud-slash.svg
│  │          cloud-sleet-fill.svg
│  │          cloud-sleet.svg
│  │          cloud-snow-fill.svg
│  │          cloud-snow.svg
│  │          cloud-sun-fill.svg
│  │          cloud-sun.svg
│  │          cloud-upload-fill.svg
│  │          cloud-upload.svg
│  │          cloud.svg
│  │          clouds-fill.svg
│  │          clouds.svg
│  │          cloudy-fill.svg
│  │          cloudy.svg
│  │          code-slash.svg
│  │          code-square.svg
│  │          code.svg
│  │          coin.svg
│  │          collection-fill.svg
│  │          collection-play-fill.svg
│  │          collection-play.svg
│  │          collection.svg
│  │          columns-gap.svg
│  │          columns.svg
│  │          command.svg
│  │          compass-fill.svg
│  │          compass.svg
│  │          cone-striped.svg
│  │          cone.svg
│  │          controller.svg
│  │          cookie.svg
│  │          copy.svg
│  │          cpu-fill.svg
│  │          cpu.svg
│  │          credit-card-2-back-fill.svg
│  │          credit-card-2-back.svg
│  │          credit-card-2-front-fill.svg
│  │          credit-card-2-front.svg
│  │          credit-card-fill.svg
│  │          credit-card.svg
│  │          crop.svg
│  │          crosshair.svg
│  │          crosshair2.svg
│  │          cup-fill.svg
│  │          cup-hot-fill.svg
│  │          cup-hot.svg
│  │          cup-straw.svg
│  │          cup.svg
│  │          currency-bitcoin.svg
│  │          currency-dollar.svg
│  │          currency-euro.svg
│  │          currency-exchange.svg
│  │          currency-pound.svg
│  │          currency-rupee.svg
│  │          currency-yen.svg
│  │          cursor-fill.svg
│  │          cursor-text.svg
│  │          cursor.svg
│  │          dash-circle-dotted.svg
│  │          dash-circle-fill.svg
│  │          dash-circle.svg
│  │          dash-lg.svg
│  │          dash-square-dotted.svg
│  │          dash-square-fill.svg
│  │          dash-square.svg
│  │          dash.svg
│  │          database-add.svg
│  │          database-check.svg
│  │          database-dash.svg
│  │          database-down.svg
│  │          database-exclamation.svg
│  │          database-fill-add.svg
│  │          database-fill-check.svg
│  │          database-fill-dash.svg
│  │          database-fill-down.svg
│  │          database-fill-exclamation.svg
│  │          database-fill-gear.svg
│  │          database-fill-lock.svg
│  │          database-fill-slash.svg
│  │          database-fill-up.svg
│  │          database-fill-x.svg
│  │          database-fill.svg
│  │          database-gear.svg
│  │          database-lock.svg
│  │          database-slash.svg
│  │          database-up.svg
│  │          database-x.svg
│  │          database.svg
│  │          device-hdd-fill.svg
│  │          device-hdd.svg
│  │          device-ssd-fill.svg
│  │          device-ssd.svg
│  │          diagram-2-fill.svg
│  │          diagram-2.svg
│  │          diagram-3-fill.svg
│  │          diagram-3.svg
│  │          diamond-fill.svg
│  │          diamond-half.svg
│  │          diamond.svg
│  │          dice-1-fill.svg
│  │          dice-1.svg
│  │          dice-2-fill.svg
│  │          dice-2.svg
│  │          dice-3-fill.svg
│  │          dice-3.svg
│  │          dice-4-fill.svg
│  │          dice-4.svg
│  │          dice-5-fill.svg
│  │          dice-5.svg
│  │          dice-6-fill.svg
│  │          dice-6.svg
│  │          disc-fill.svg
│  │          disc.svg
│  │          discord.svg
│  │          display-fill.svg
│  │          display.svg
│  │          displayport-fill.svg
│  │          displayport.svg
│  │          distribute-horizontal.svg
│  │          distribute-vertical.svg
│  │          door-closed-fill.svg
│  │          door-closed.svg
│  │          door-open-fill.svg
│  │          door-open.svg
│  │          dot.svg
│  │          download.svg
│  │          dpad-fill.svg
│  │          dpad.svg
│  │          dribbble.svg
│  │          dropbox.svg
│  │          droplet-fill.svg
│  │          droplet-half.svg
│  │          droplet.svg
│  │          duffle-fill.svg
│  │          duffle.svg
│  │          ear-fill.svg
│  │          ear.svg
│  │          earbuds.svg
│  │          easel-fill.svg
│  │          easel.svg
│  │          easel2-fill.svg
│  │          easel2.svg
│  │          easel3-fill.svg
│  │          easel3.svg
│  │          egg-fill.svg
│  │          egg-fried.svg
│  │          egg.svg
│  │          eject-fill.svg
│  │          eject.svg
│  │          emoji-angry-fill.svg
│  │          emoji-angry.svg
│  │          emoji-astonished-fill.svg
│  │          emoji-astonished.svg
│  │          emoji-dizzy-fill.svg
│  │          emoji-dizzy.svg
│  │          emoji-expressionless-fill.svg
│  │          emoji-expressionless.svg
│  │          emoji-frown-fill.svg
│  │          emoji-frown.svg
│  │          emoji-grimace-fill.svg
│  │          emoji-grimace.svg
│  │          emoji-grin-fill.svg
│  │          emoji-grin.svg
│  │          emoji-heart-eyes-fill.svg
│  │          emoji-heart-eyes.svg
│  │          emoji-kiss-fill.svg
│  │          emoji-kiss.svg
│  │          emoji-laughing-fill.svg
│  │          emoji-laughing.svg
│  │          emoji-neutral-fill.svg
│  │          emoji-neutral.svg
│  │          emoji-smile-fill.svg
│  │          emoji-smile-upside-down-fill.svg
│  │          emoji-smile-upside-down.svg
│  │          emoji-smile.svg
│  │          emoji-sunglasses-fill.svg
│  │          emoji-sunglasses.svg
│  │          emoji-surprise-fill.svg
│  │          emoji-surprise.svg
│  │          emoji-tear-fill.svg
│  │          emoji-tear.svg
│  │          emoji-wink-fill.svg
│  │          emoji-wink.svg
│  │          envelope-arrow-down-fill.svg
│  │          envelope-arrow-down.svg
│  │          envelope-arrow-up-fill.svg
│  │          envelope-arrow-up.svg
│  │          envelope-at-fill.svg
│  │          envelope-at.svg
│  │          envelope-check-fill.svg
│  │          envelope-check.svg
│  │          envelope-dash-fill.svg
│  │          envelope-dash.svg
│  │          envelope-exclamation-fill.svg
│  │          envelope-exclamation.svg
│  │          envelope-fill.svg
│  │          envelope-heart-fill.svg
│  │          envelope-heart.svg
│  │          envelope-open-fill.svg
│  │          envelope-open-heart-fill.svg
│  │          envelope-open-heart.svg
│  │          envelope-open.svg
│  │          envelope-paper-fill.svg
│  │          envelope-paper-heart-fill.svg
│  │          envelope-paper-heart.svg
│  │          envelope-paper.svg
│  │          envelope-plus-fill.svg
│  │          envelope-plus.svg
│  │          envelope-slash-fill.svg
│  │          envelope-slash.svg
│  │          envelope-x-fill.svg
│  │          envelope-x.svg
│  │          envelope.svg
│  │          eraser-fill.svg
│  │          eraser.svg
│  │          escape.svg
│  │          ethernet.svg
│  │          ev-front-fill.svg
│  │          ev-front.svg
│  │          ev-station-fill.svg
│  │          ev-station.svg
│  │          exclamation-circle-fill.svg
│  │          exclamation-circle.svg
│  │          exclamation-diamond-fill.svg
│  │          exclamation-diamond.svg
│  │          exclamation-lg.svg
│  │          exclamation-octagon-fill.svg
│  │          exclamation-octagon.svg
│  │          exclamation-square-fill.svg
│  │          exclamation-square.svg
│  │          exclamation-triangle-fill.svg
│  │          exclamation-triangle.svg
│  │          exclamation.svg
│  │          exclude.svg
│  │          explicit-fill.svg
│  │          explicit.svg
│  │          exposure.svg
│  │          eye-fill.svg
│  │          eye-slash-fill.svg
│  │          eye-slash.svg
│  │          eye.svg
│  │          eyedropper.svg
│  │          eyeglasses.svg
│  │          facebook.svg
│  │          fan.svg
│  │          fast-forward-btn-fill.svg
│  │          fast-forward-btn.svg
│  │          fast-forward-circle-fill.svg
│  │          fast-forward-circle.svg
│  │          fast-forward-fill.svg
│  │          fast-forward.svg
│  │          feather.svg
│  │          feather2.svg
│  │          file-arrow-down-fill.svg
│  │          file-arrow-down.svg
│  │          file-arrow-up-fill.svg
│  │          file-arrow-up.svg
│  │          file-bar-graph-fill.svg
│  │          file-bar-graph.svg
│  │          file-binary-fill.svg
│  │          file-binary.svg
│  │          file-break-fill.svg
│  │          file-break.svg
│  │          file-check-fill.svg
│  │          file-check.svg
│  │          file-code-fill.svg
│  │          file-code.svg
│  │          file-diff-fill.svg
│  │          file-diff.svg
│  │          file-earmark-arrow-down-fill.svg
│  │          file-earmark-arrow-down.svg
│  │          file-earmark-arrow-up-fill.svg
│  │          file-earmark-arrow-up.svg
│  │          file-earmark-bar-graph-fill.svg
│  │          file-earmark-bar-graph.svg
│  │          file-earmark-binary-fill.svg
│  │          file-earmark-binary.svg
│  │          file-earmark-break-fill.svg
│  │          file-earmark-break.svg
│  │          file-earmark-check-fill.svg
│  │          file-earmark-check.svg
│  │          file-earmark-code-fill.svg
│  │          file-earmark-code.svg
│  │          file-earmark-diff-fill.svg
│  │          file-earmark-diff.svg
│  │          file-earmark-easel-fill.svg
│  │          file-earmark-easel.svg
│  │          file-earmark-excel-fill.svg
│  │          file-earmark-excel.svg
│  │          file-earmark-fill.svg
│  │          file-earmark-font-fill.svg
│  │          file-earmark-font.svg
│  │          file-earmark-image-fill.svg
│  │          file-earmark-image.svg
│  │          file-earmark-lock-fill.svg
│  │          file-earmark-lock.svg
│  │          file-earmark-lock2-fill.svg
│  │          file-earmark-lock2.svg
│  │          file-earmark-medical-fill.svg
│  │          file-earmark-medical.svg
│  │          file-earmark-minus-fill.svg
│  │          file-earmark-minus.svg
│  │          file-earmark-music-fill.svg
│  │          file-earmark-music.svg
│  │          file-earmark-pdf-fill.svg
│  │          file-earmark-pdf.svg
│  │          file-earmark-person-fill.svg
│  │          file-earmark-person.svg
│  │          file-earmark-play-fill.svg
│  │          file-earmark-play.svg
│  │          file-earmark-plus-fill.svg
│  │          file-earmark-plus.svg
│  │          file-earmark-post-fill.svg
│  │          file-earmark-post.svg
│  │          file-earmark-ppt-fill.svg
│  │          file-earmark-ppt.svg
│  │          file-earmark-richtext-fill.svg
│  │          file-earmark-richtext.svg
│  │          file-earmark-ruled-fill.svg
│  │          file-earmark-ruled.svg
│  │          file-earmark-slides-fill.svg
│  │          file-earmark-slides.svg
│  │          file-earmark-spreadsheet-fill.svg
│  │          file-earmark-spreadsheet.svg
│  │          file-earmark-text-fill.svg
│  │          file-earmark-text.svg
│  │          file-earmark-word-fill.svg
│  │          file-earmark-word.svg
│  │          file-earmark-x-fill.svg
│  │          file-earmark-x.svg
│  │          file-earmark-zip-fill.svg
│  │          file-earmark-zip.svg
│  │          file-earmark.svg
│  │          file-easel-fill.svg
│  │          file-easel.svg
│  │          file-excel-fill.svg
│  │          file-excel.svg
│  │          file-fill.svg
│  │          file-font-fill.svg
│  │          file-font.svg
│  │          file-image-fill.svg
│  │          file-image.svg
│  │          file-lock-fill.svg
│  │          file-lock.svg
│  │          file-lock2-fill.svg
│  │          file-lock2.svg
│  │          file-medical-fill.svg
│  │          file-medical.svg
│  │          file-minus-fill.svg
│  │          file-minus.svg
│  │          file-music-fill.svg
│  │          file-music.svg
│  │          file-pdf-fill.svg
│  │          file-pdf.svg
│  │          file-person-fill.svg
│  │          file-person.svg
│  │          file-play-fill.svg
│  │          file-play.svg
│  │          file-plus-fill.svg
│  │          file-plus.svg
│  │          file-post-fill.svg
│  │          file-post.svg
│  │          file-ppt-fill.svg
│  │          file-ppt.svg
│  │          file-richtext-fill.svg
│  │          file-richtext.svg
│  │          file-ruled-fill.svg
│  │          file-ruled.svg
│  │          file-slides-fill.svg
│  │          file-slides.svg
│  │          file-spreadsheet-fill.svg
│  │          file-spreadsheet.svg
│  │          file-text-fill.svg
│  │          file-text.svg
│  │          file-word-fill.svg
│  │          file-word.svg
│  │          file-x-fill.svg
│  │          file-x.svg
│  │          file-zip-fill.svg
│  │          file-zip.svg
│  │          file.svg
│  │          files-alt.svg
│  │          files.svg
│  │          filetype-aac.svg
│  │          filetype-ai.svg
│  │          filetype-bmp.svg
│  │          filetype-cs.svg
│  │          filetype-css.svg
│  │          filetype-csv.svg
│  │          filetype-doc.svg
│  │          filetype-docx.svg
│  │          filetype-exe.svg
│  │          filetype-gif.svg
│  │          filetype-heic.svg
│  │          filetype-html.svg
│  │          filetype-java.svg
│  │          filetype-jpg.svg
│  │          filetype-js.svg
│  │          filetype-json.svg
│  │          filetype-jsx.svg
│  │          filetype-key.svg
│  │          filetype-m4p.svg
│  │          filetype-md.svg
│  │          filetype-mdx.svg
│  │          filetype-mov.svg
│  │          filetype-mp3.svg
│  │          filetype-mp4.svg
│  │          filetype-otf.svg
│  │          filetype-pdf.svg
│  │          filetype-php.svg
│  │          filetype-png.svg
│  │          filetype-ppt.svg
│  │          filetype-pptx.svg
│  │          filetype-psd.svg
│  │          filetype-py.svg
│  │          filetype-raw.svg
│  │          filetype-rb.svg
│  │          filetype-sass.svg
│  │          filetype-scss.svg
│  │          filetype-sh.svg
│  │          filetype-sql.svg
│  │          filetype-svg.svg
│  │          filetype-tiff.svg
│  │          filetype-tsx.svg
│  │          filetype-ttf.svg
│  │          filetype-txt.svg
│  │          filetype-wav.svg
│  │          filetype-woff.svg
│  │          filetype-xls.svg
│  │          filetype-xlsx.svg
│  │          filetype-xml.svg
│  │          filetype-yml.svg
│  │          film.svg
│  │          filter-circle-fill.svg
│  │          filter-circle.svg
│  │          filter-left.svg
│  │          filter-right.svg
│  │          filter-square-fill.svg
│  │          filter-square.svg
│  │          filter.svg
│  │          fingerprint.svg
│  │          fire.svg
│  │          flag-fill.svg
│  │          flag.svg
│  │          floppy-fill.svg
│  │          floppy.svg
│  │          floppy2-fill.svg
│  │          floppy2.svg
│  │          flower1.svg
│  │          flower2.svg
│  │          flower3.svg
│  │          folder-check.svg
│  │          folder-fill.svg
│  │          folder-minus.svg
│  │          folder-plus.svg
│  │          folder-symlink-fill.svg
│  │          folder-symlink.svg
│  │          folder-x.svg
│  │          folder.svg
│  │          folder2-open.svg
│  │          folder2.svg
│  │          fonts.svg
│  │          forward-fill.svg
│  │          forward.svg
│  │          front.svg
│  │          fuel-pump-diesel-fill.svg
│  │          fuel-pump-diesel.svg
│  │          fuel-pump-fill.svg
│  │          fuel-pump.svg
│  │          fullscreen-exit.svg
│  │          fullscreen.svg
│  │          funnel-fill.svg
│  │          funnel.svg
│  │          gear-fill.svg
│  │          gear-wide-connected.svg
│  │          gear-wide.svg
│  │          gear.svg
│  │          gem.svg
│  │          gender-ambiguous.svg
│  │          gender-female.svg
│  │          gender-male.svg
│  │          gender-neuter.svg
│  │          gender-trans.svg
│  │          geo-alt-fill.svg
│  │          geo-alt.svg
│  │          geo-fill.svg
│  │          geo.svg
│  │          gift-fill.svg
│  │          gift.svg
│  │          git.svg
│  │          github.svg
│  │          gitlab.svg
│  │          globe-americas.svg
│  │          globe-asia-australia.svg
│  │          globe-central-south-asia.svg
│  │          globe-europe-africa.svg
│  │          globe.svg
│  │          globe2.svg
│  │          google-play.svg
│  │          google.svg
│  │          gpu-card.svg
│  │          graph-down-arrow.svg
│  │          graph-down.svg
│  │          graph-up-arrow.svg
│  │          graph-up.svg
│  │          grid-1x2-fill.svg
│  │          grid-1x2.svg
│  │          grid-3x2-gap-fill.svg
│  │          grid-3x2-gap.svg
│  │          grid-3x2.svg
│  │          grid-3x3-gap-fill.svg
│  │          grid-3x3-gap.svg
│  │          grid-3x3.svg
│  │          grid-fill.svg
│  │          grid.svg
│  │          grip-horizontal.svg
│  │          grip-vertical.svg
│  │          h-circle-fill.svg
│  │          h-circle.svg
│  │          h-square-fill.svg
│  │          h-square.svg
│  │          hammer.svg
│  │          hand-index-fill.svg
│  │          hand-index-thumb-fill.svg
│  │          hand-index-thumb.svg
│  │          hand-index.svg
│  │          hand-thumbs-down-fill.svg
│  │          hand-thumbs-down.svg
│  │          hand-thumbs-up-fill.svg
│  │          hand-thumbs-up.svg
│  │          handbag-fill.svg
│  │          handbag.svg
│  │          hash.svg
│  │          hdd-fill.svg
│  │          hdd-network-fill.svg
│  │          hdd-network.svg
│  │          hdd-rack-fill.svg
│  │          hdd-rack.svg
│  │          hdd-stack-fill.svg
│  │          hdd-stack.svg
│  │          hdd.svg
│  │          hdmi-fill.svg
│  │          hdmi.svg
│  │          headphones.svg
│  │          headset-vr.svg
│  │          headset.svg
│  │          heart-arrow.svg
│  │          heart-fill.svg
│  │          heart-half.svg
│  │          heart-pulse-fill.svg
│  │          heart-pulse.svg
│  │          heart.svg
│  │          heartbreak-fill.svg
│  │          heartbreak.svg
│  │          hearts.svg
│  │          heptagon-fill.svg
│  │          heptagon-half.svg
│  │          heptagon.svg
│  │          hexagon-fill.svg
│  │          hexagon-half.svg
│  │          hexagon.svg
│  │          highlighter.svg
│  │          highlights.svg
│  │          hospital-fill.svg
│  │          hospital.svg
│  │          hourglass-bottom.svg
│  │          hourglass-split.svg
│  │          hourglass-top.svg
│  │          hourglass.svg
│  │          house-add-fill.svg
│  │          house-add.svg
│  │          house-check-fill.svg
│  │          house-check.svg
│  │          house-dash-fill.svg
│  │          house-dash.svg
│  │          house-door-fill.svg
│  │          house-door.svg
│  │          house-down-fill.svg
│  │          house-down.svg
│  │          house-exclamation-fill.svg
│  │          house-exclamation.svg
│  │          house-fill.svg
│  │          house-gear-fill.svg
│  │          house-gear.svg
│  │          house-heart-fill.svg
│  │          house-heart.svg
│  │          house-lock-fill.svg
│  │          house-lock.svg
│  │          house-slash-fill.svg
│  │          house-slash.svg
│  │          house-up-fill.svg
│  │          house-up.svg
│  │          house-x-fill.svg
│  │          house-x.svg
│  │          house.svg
│  │          houses-fill.svg
│  │          houses.svg
│  │          hr.svg
│  │          hurricane.svg
│  │          hypnotize.svg
│  │          image-alt.svg
│  │          image-fill.svg
│  │          image.svg
│  │          images.svg
│  │          inbox-fill.svg
│  │          inbox.svg
│  │          inboxes-fill.svg
│  │          inboxes.svg
│  │          incognito.svg
│  │          indent.svg
│  │          infinity.svg
│  │          info-circle-fill.svg
│  │          info-circle.svg
│  │          info-lg.svg
│  │          info-square-fill.svg
│  │          info-square.svg
│  │          info.svg
│  │          input-cursor-text.svg
│  │          input-cursor.svg
│  │          instagram.svg
│  │          intersect.svg
│  │          journal-album.svg
│  │          journal-arrow-down.svg
│  │          journal-arrow-up.svg
│  │          journal-bookmark-fill.svg
│  │          journal-bookmark.svg
│  │          journal-check.svg
│  │          journal-code.svg
│  │          journal-medical.svg
│  │          journal-minus.svg
│  │          journal-plus.svg
│  │          journal-richtext.svg
│  │          journal-text.svg
│  │          journal-x.svg
│  │          journal.svg
│  │          journals.svg
│  │          joystick.svg
│  │          justify-left.svg
│  │          justify-right.svg
│  │          justify.svg
│  │          kanban-fill.svg
│  │          kanban.svg
│  │          key-fill.svg
│  │          key.svg
│  │          keyboard-fill.svg
│  │          keyboard.svg
│  │          ladder.svg
│  │          lamp-fill.svg
│  │          lamp.svg
│  │          laptop-fill.svg
│  │          laptop.svg
│  │          layer-backward.svg
│  │          layer-forward.svg
│  │          layers-fill.svg
│  │          layers-half.svg
│  │          layers.svg
│  │          layout-sidebar-inset-reverse.svg
│  │          layout-sidebar-inset.svg
│  │          layout-sidebar-reverse.svg
│  │          layout-sidebar.svg
│  │          layout-split.svg
│  │          layout-text-sidebar-reverse.svg
│  │          layout-text-sidebar.svg
│  │          layout-text-window-reverse.svg
│  │          layout-text-window.svg
│  │          layout-three-columns.svg
│  │          layout-wtf.svg
│  │          life-preserver.svg
│  │          lightbulb-fill.svg
│  │          lightbulb-off-fill.svg
│  │          lightbulb-off.svg
│  │          lightbulb.svg
│  │          lightning-charge-fill.svg
│  │          lightning-charge.svg
│  │          lightning-fill.svg
│  │          lightning.svg
│  │          line.svg
│  │          link-45deg.svg
│  │          link.svg
│  │          linkedin.svg
│  │          list-check.svg
│  │          list-columns-reverse.svg
│  │          list-columns.svg
│  │          list-nested.svg
│  │          list-ol.svg
│  │          list-stars.svg
│  │          list-task.svg
│  │          list-ul.svg
│  │          list.svg
│  │          lock-fill.svg
│  │          lock.svg
│  │          luggage-fill.svg
│  │          luggage.svg
│  │          lungs-fill.svg
│  │          lungs.svg
│  │          magic.svg
│  │          magnet-fill.svg
│  │          magnet.svg
│  │          mailbox-flag.svg
│  │          mailbox.svg
│  │          mailbox2-flag.svg
│  │          mailbox2.svg
│  │          map-fill.svg
│  │          map.svg
│  │          markdown-fill.svg
│  │          markdown.svg
│  │          marker-tip.svg
│  │          mask.svg
│  │          mastodon.svg
│  │          medium.svg
│  │          megaphone-fill.svg
│  │          megaphone.svg
│  │          memory.svg
│  │          menu-app-fill.svg
│  │          menu-app.svg
│  │          menu-button-fill.svg
│  │          menu-button-wide-fill.svg
│  │          menu-button-wide.svg
│  │          menu-button.svg
│  │          menu-down.svg
│  │          menu-up.svg
│  │          messenger.svg
│  │          meta.svg
│  │          mic-fill.svg
│  │          mic-mute-fill.svg
│  │          mic-mute.svg
│  │          mic.svg
│  │          microsoft-teams.svg
│  │          microsoft.svg
│  │          minecart-loaded.svg
│  │          minecart.svg
│  │          modem-fill.svg
│  │          modem.svg
│  │          moisture.svg
│  │          moon-fill.svg
│  │          moon-stars-fill.svg
│  │          moon-stars.svg
│  │          moon.svg
│  │          mortarboard-fill.svg
│  │          mortarboard.svg
│  │          motherboard-fill.svg
│  │          motherboard.svg
│  │          mouse-fill.svg
│  │          mouse.svg
│  │          mouse2-fill.svg
│  │          mouse2.svg
│  │          mouse3-fill.svg
│  │          mouse3.svg
│  │          music-note-beamed.svg
│  │          music-note-list.svg
│  │          music-note.svg
│  │          music-player-fill.svg
│  │          music-player.svg
│  │          newspaper.svg
│  │          nintendo-switch.svg
│  │          node-minus-fill.svg
│  │          node-minus.svg
│  │          node-plus-fill.svg
│  │          node-plus.svg
│  │          noise-reduction.svg
│  │          nut-fill.svg
│  │          nut.svg
│  │          nvidia.svg
│  │          nvme-fill.svg
│  │          nvme.svg
│  │          octagon-fill.svg
│  │          octagon-half.svg
│  │          octagon.svg
│  │          opencollective.svg
│  │          optical-audio-fill.svg
│  │          optical-audio.svg
│  │          option.svg
│  │          outlet.svg
│  │          p-circle-fill.svg
│  │          p-circle.svg
│  │          p-square-fill.svg
│  │          p-square.svg
│  │          paint-bucket.svg
│  │          palette-fill.svg
│  │          palette.svg
│  │          palette2.svg
│  │          paperclip.svg
│  │          paragraph.svg
│  │          pass-fill.svg
│  │          pass.svg
│  │          passport-fill.svg
│  │          passport.svg
│  │          patch-check-fill.svg
│  │          patch-check.svg
│  │          patch-exclamation-fill.svg
│  │          patch-exclamation.svg
│  │          patch-minus-fill.svg
│  │          patch-minus.svg
│  │          patch-plus-fill.svg
│  │          patch-plus.svg
│  │          patch-question-fill.svg
│  │          patch-question.svg
│  │          pause-btn-fill.svg
│  │          pause-btn.svg
│  │          pause-circle-fill.svg
│  │          pause-circle.svg
│  │          pause-fill.svg
│  │          pause.svg
│  │          paypal.svg
│  │          pc-display-horizontal.svg
│  │          pc-display.svg
│  │          pc-horizontal.svg
│  │          pc.svg
│  │          pci-card-network.svg
│  │          pci-card-sound.svg
│  │          pci-card.svg
│  │          peace-fill.svg
│  │          peace.svg
│  │          pen-fill.svg
│  │          pen.svg
│  │          pencil-fill.svg
│  │          pencil-square.svg
│  │          pencil.svg
│  │          pentagon-fill.svg
│  │          pentagon-half.svg
│  │          pentagon.svg
│  │          people-fill.svg
│  │          people.svg
│  │          percent.svg
│  │          person-add.svg
│  │          person-arms-up.svg
│  │          person-badge-fill.svg
│  │          person-badge.svg
│  │          person-bounding-box.svg
│  │          person-check-fill.svg
│  │          person-check.svg
│  │          person-circle.svg
│  │          person-dash-fill.svg
│  │          person-dash.svg
│  │          person-down.svg
│  │          person-exclamation.svg
│  │          person-fill-add.svg
│  │          person-fill-check.svg
│  │          person-fill-dash.svg
│  │          person-fill-down.svg
│  │          person-fill-exclamation.svg
│  │          person-fill-gear.svg
│  │          person-fill-lock.svg
│  │          person-fill-slash.svg
│  │          person-fill-up.svg
│  │          person-fill-x.svg
│  │          person-fill.svg
│  │          person-gear.svg
│  │          person-heart.svg
│  │          person-hearts.svg
│  │          person-lines-fill.svg
│  │          person-lock.svg
│  │          person-plus-fill.svg
│  │          person-plus.svg
│  │          person-raised-hand.svg
│  │          person-rolodex.svg
│  │          person-slash.svg
│  │          person-square.svg
│  │          person-standing-dress.svg
│  │          person-standing.svg
│  │          person-up.svg
│  │          person-vcard-fill.svg
│  │          person-vcard.svg
│  │          person-video.svg
│  │          person-video2.svg
│  │          person-video3.svg
│  │          person-walking.svg
│  │          person-wheelchair.svg
│  │          person-workspace.svg
│  │          person-x-fill.svg
│  │          person-x.svg
│  │          person.svg
│  │          phone-fill.svg
│  │          phone-flip.svg
│  │          phone-landscape-fill.svg
│  │          phone-landscape.svg
│  │          phone-vibrate-fill.svg
│  │          phone-vibrate.svg
│  │          phone.svg
│  │          pie-chart-fill.svg
│  │          pie-chart.svg
│  │          piggy-bank-fill.svg
│  │          piggy-bank.svg
│  │          pin-angle-fill.svg
│  │          pin-angle.svg
│  │          pin-fill.svg
│  │          pin-map-fill.svg
│  │          pin-map.svg
│  │          pin.svg
│  │          pinterest.svg
│  │          pip-fill.svg
│  │          pip.svg
│  │          play-btn-fill.svg
│  │          play-btn.svg
│  │          play-circle-fill.svg
│  │          play-circle.svg
│  │          play-fill.svg
│  │          play.svg
│  │          playstation.svg
│  │          plug-fill.svg
│  │          plug.svg
│  │          plugin.svg
│  │          plus-circle-dotted.svg
│  │          plus-circle-fill.svg
│  │          plus-circle.svg
│  │          plus-lg.svg
│  │          plus-slash-minus.svg
│  │          plus-square-dotted.svg
│  │          plus-square-fill.svg
│  │          plus-square.svg
│  │          plus.svg
│  │          postage-fill.svg
│  │          postage-heart-fill.svg
│  │          postage-heart.svg
│  │          postage.svg
│  │          postcard-fill.svg
│  │          postcard-heart-fill.svg
│  │          postcard-heart.svg
│  │          postcard.svg
│  │          power.svg
│  │          prescription.svg
│  │          prescription2.svg
│  │          printer-fill.svg
│  │          printer.svg
│  │          projector-fill.svg
│  │          projector.svg
│  │          puzzle-fill.svg
│  │          puzzle.svg
│  │          qr-code-scan.svg
│  │          qr-code.svg
│  │          question-circle-fill.svg
│  │          question-circle.svg
│  │          question-diamond-fill.svg
│  │          question-diamond.svg
│  │          question-lg.svg
│  │          question-octagon-fill.svg
│  │          question-octagon.svg
│  │          question-square-fill.svg
│  │          question-square.svg
│  │          question.svg
│  │          quora.svg
│  │          quote.svg
│  │          r-circle-fill.svg
│  │          r-circle.svg
│  │          r-square-fill.svg
│  │          r-square.svg
│  │          radar.svg
│  │          radioactive.svg
│  │          rainbow.svg
│  │          receipt-cutoff.svg
│  │          receipt.svg
│  │          reception-0.svg
│  │          reception-1.svg
│  │          reception-2.svg
│  │          reception-3.svg
│  │          reception-4.svg
│  │          record-btn-fill.svg
│  │          record-btn.svg
│  │          record-circle-fill.svg
│  │          record-circle.svg
│  │          record-fill.svg
│  │          record.svg
│  │          record2-fill.svg
│  │          record2.svg
│  │          recycle.svg
│  │          reddit.svg
│  │          regex.svg
│  │          repeat-1.svg
│  │          repeat.svg
│  │          reply-all-fill.svg
│  │          reply-all.svg
│  │          reply-fill.svg
│  │          reply.svg
│  │          rewind-btn-fill.svg
│  │          rewind-btn.svg
│  │          rewind-circle-fill.svg
│  │          rewind-circle.svg
│  │          rewind-fill.svg
│  │          rewind.svg
│  │          robot.svg
│  │          rocket-fill.svg
│  │          rocket-takeoff-fill.svg
│  │          rocket-takeoff.svg
│  │          rocket.svg
│  │          router-fill.svg
│  │          router.svg
│  │          rss-fill.svg
│  │          rss.svg
│  │          rulers.svg
│  │          safe-fill.svg
│  │          safe.svg
│  │          safe2-fill.svg
│  │          safe2.svg
│  │          save-fill.svg
│  │          save.svg
│  │          save2-fill.svg
│  │          save2.svg
│  │          scissors.svg
│  │          scooter.svg
│  │          screwdriver.svg
│  │          sd-card-fill.svg
│  │          sd-card.svg
│  │          search-heart-fill.svg
│  │          search-heart.svg
│  │          search.svg
│  │          segmented-nav.svg
│  │          send-arrow-down-fill.svg
│  │          send-arrow-down.svg
│  │          send-arrow-up-fill.svg
│  │          send-arrow-up.svg
│  │          send-check-fill.svg
│  │          send-check.svg
│  │          send-dash-fill.svg
│  │          send-dash.svg
│  │          send-exclamation-fill.svg
│  │          send-exclamation.svg
│  │          send-fill.svg
│  │          send-plus-fill.svg
│  │          send-plus.svg
│  │          send-slash-fill.svg
│  │          send-slash.svg
│  │          send-x-fill.svg
│  │          send-x.svg
│  │          send.svg
│  │          server.svg
│  │          shadows.svg
│  │          share-fill.svg
│  │          share.svg
│  │          shield-check.svg
│  │          shield-exclamation.svg
│  │          shield-fill-check.svg
│  │          shield-fill-exclamation.svg
│  │          shield-fill-minus.svg
│  │          shield-fill-plus.svg
│  │          shield-fill-x.svg
│  │          shield-fill.svg
│  │          shield-lock-fill.svg
│  │          shield-lock.svg
│  │          shield-minus.svg
│  │          shield-plus.svg
│  │          shield-shaded.svg
│  │          shield-slash-fill.svg
│  │          shield-slash.svg
│  │          shield-x.svg
│  │          shield.svg
│  │          shift-fill.svg
│  │          shift.svg
│  │          shop-window.svg
│  │          shop.svg
│  │          shuffle.svg
│  │          sign-dead-end-fill.svg
│  │          sign-dead-end.svg
│  │          sign-do-not-enter-fill.svg
│  │          sign-do-not-enter.svg
│  │          sign-intersection-fill.svg
│  │          sign-intersection-side-fill.svg
│  │          sign-intersection-side.svg
│  │          sign-intersection-t-fill.svg
│  │          sign-intersection-t.svg
│  │          sign-intersection-y-fill.svg
│  │          sign-intersection-y.svg
│  │          sign-intersection.svg
│  │          sign-merge-left-fill.svg
│  │          sign-merge-left.svg
│  │          sign-merge-right-fill.svg
│  │          sign-merge-right.svg
│  │          sign-no-left-turn-fill.svg
│  │          sign-no-left-turn.svg
│  │          sign-no-parking-fill.svg
│  │          sign-no-parking.svg
│  │          sign-no-right-turn-fill.svg
│  │          sign-no-right-turn.svg
│  │          sign-railroad-fill.svg
│  │          sign-railroad.svg
│  │          sign-stop-fill.svg
│  │          sign-stop-lights-fill.svg
│  │          sign-stop-lights.svg
│  │          sign-stop.svg
│  │          sign-turn-left-fill.svg
│  │          sign-turn-left.svg
│  │          sign-turn-right-fill.svg
│  │          sign-turn-right.svg
│  │          sign-turn-slight-left-fill.svg
│  │          sign-turn-slight-left.svg
│  │          sign-turn-slight-right-fill.svg
│  │          sign-turn-slight-right.svg
│  │          sign-yield-fill.svg
│  │          sign-yield.svg
│  │          signal.svg
│  │          signpost-2-fill.svg
│  │          signpost-2.svg
│  │          signpost-fill.svg
│  │          signpost-split-fill.svg
│  │          signpost-split.svg
│  │          signpost.svg
│  │          sim-fill.svg
│  │          sim-slash-fill.svg
│  │          sim-slash.svg
│  │          sim.svg
│  │          sina-weibo.svg
│  │          skip-backward-btn-fill.svg
│  │          skip-backward-btn.svg
│  │          skip-backward-circle-fill.svg
│  │          skip-backward-circle.svg
│  │          skip-backward-fill.svg
│  │          skip-backward.svg
│  │          skip-end-btn-fill.svg
│  │          skip-end-btn.svg
│  │          skip-end-circle-fill.svg
│  │          skip-end-circle.svg
│  │          skip-end-fill.svg
│  │          skip-end.svg
│  │          skip-forward-btn-fill.svg
│  │          skip-forward-btn.svg
│  │          skip-forward-circle-fill.svg
│  │          skip-forward-circle.svg
│  │          skip-forward-fill.svg
│  │          skip-forward.svg
│  │          skip-start-btn-fill.svg
│  │          skip-start-btn.svg
│  │          skip-start-circle-fill.svg
│  │          skip-start-circle.svg
│  │          skip-start-fill.svg
│  │          skip-start.svg
│  │          skype.svg
│  │          slack.svg
│  │          slash-circle-fill.svg
│  │          slash-circle.svg
│  │          slash-lg.svg
│  │          slash-square-fill.svg
│  │          slash-square.svg
│  │          slash.svg
│  │          sliders.svg
│  │          sliders2-vertical.svg
│  │          sliders2.svg
│  │          smartwatch.svg
│  │          snapchat.svg
│  │          snow.svg
│  │          snow2.svg
│  │          snow3.svg
│  │          sort-alpha-down-alt.svg
│  │          sort-alpha-down.svg
│  │          sort-alpha-up-alt.svg
│  │          sort-alpha-up.svg
│  │          sort-down-alt.svg
│  │          sort-down.svg
│  │          sort-numeric-down-alt.svg
│  │          sort-numeric-down.svg
│  │          sort-numeric-up-alt.svg
│  │          sort-numeric-up.svg
│  │          sort-up-alt.svg
│  │          sort-up.svg
│  │          soundwave.svg
│  │          sourceforge.svg
│  │          speaker-fill.svg
│  │          speaker.svg
│  │          speedometer.svg
│  │          speedometer2.svg
│  │          spellcheck.svg
│  │          spotify.svg
│  │          square-fill.svg
│  │          square-half.svg
│  │          square.svg
│  │          stack-overflow.svg
│  │          stack.svg
│  │          star-fill.svg
│  │          star-half.svg
│  │          star.svg
│  │          stars.svg
│  │          steam.svg
│  │          stickies-fill.svg
│  │          stickies.svg
│  │          sticky-fill.svg
│  │          sticky.svg
│  │          stop-btn-fill.svg
│  │          stop-btn.svg
│  │          stop-circle-fill.svg
│  │          stop-circle.svg
│  │          stop-fill.svg
│  │          stop.svg
│  │          stoplights-fill.svg
│  │          stoplights.svg
│  │          stopwatch-fill.svg
│  │          stopwatch.svg
│  │          strava.svg
│  │          stripe.svg
│  │          subscript.svg
│  │          substack.svg
│  │          subtract.svg
│  │          suit-club-fill.svg
│  │          suit-club.svg
│  │          suit-diamond-fill.svg
│  │          suit-diamond.svg
│  │          suit-heart-fill.svg
│  │          suit-heart.svg
│  │          suit-spade-fill.svg
│  │          suit-spade.svg
│  │          suitcase-fill.svg
│  │          suitcase-lg-fill.svg
│  │          suitcase-lg.svg
│  │          suitcase.svg
│  │          suitcase2-fill.svg
│  │          suitcase2.svg
│  │          sun-fill.svg
│  │          sun.svg
│  │          sunglasses.svg
│  │          sunrise-fill.svg
│  │          sunrise.svg
│  │          sunset-fill.svg
│  │          sunset.svg
│  │          superscript.svg
│  │          symmetry-horizontal.svg
│  │          symmetry-vertical.svg
│  │          table.svg
│  │          tablet-fill.svg
│  │          tablet-landscape-fill.svg
│  │          tablet-landscape.svg
│  │          tablet.svg
│  │          tag-fill.svg
│  │          tag.svg
│  │          tags-fill.svg
│  │          tags.svg
│  │          taxi-front-fill.svg
│  │          taxi-front.svg
│  │          telegram.svg
│  │          telephone-fill.svg
│  │          telephone-forward-fill.svg
│  │          telephone-forward.svg
│  │          telephone-inbound-fill.svg
│  │          telephone-inbound.svg
│  │          telephone-minus-fill.svg
│  │          telephone-minus.svg
│  │          telephone-outbound-fill.svg
│  │          telephone-outbound.svg
│  │          telephone-plus-fill.svg
│  │          telephone-plus.svg
│  │          telephone-x-fill.svg
│  │          telephone-x.svg
│  │          telephone.svg
│  │          tencent-qq.svg
│  │          terminal-dash.svg
│  │          terminal-fill.svg
│  │          terminal-plus.svg
│  │          terminal-split.svg
│  │          terminal-x.svg
│  │          terminal.svg
│  │          text-center.svg
│  │          text-indent-left.svg
│  │          text-indent-right.svg
│  │          text-left.svg
│  │          text-paragraph.svg
│  │          text-right.svg
│  │          text-wrap.svg
│  │          textarea-resize.svg
│  │          textarea-t.svg
│  │          textarea.svg
│  │          thermometer-half.svg
│  │          thermometer-high.svg
│  │          thermometer-low.svg
│  │          thermometer-snow.svg
│  │          thermometer-sun.svg
│  │          thermometer.svg
│  │          threads-fill.svg
│  │          threads.svg
│  │          three-dots-vertical.svg
│  │          three-dots.svg
│  │          thunderbolt-fill.svg
│  │          thunderbolt.svg
│  │          ticket-detailed-fill.svg
│  │          ticket-detailed.svg
│  │          ticket-fill.svg
│  │          ticket-perforated-fill.svg
│  │          ticket-perforated.svg
│  │          ticket.svg
│  │          tiktok.svg
│  │          toggle-off.svg
│  │          toggle-on.svg
│  │          toggle2-off.svg
│  │          toggle2-on.svg
│  │          toggles.svg
│  │          toggles2.svg
│  │          tools.svg
│  │          tornado.svg
│  │          train-freight-front-fill.svg
│  │          train-freight-front.svg
│  │          train-front-fill.svg
│  │          train-front.svg
│  │          train-lightrail-front-fill.svg
│  │          train-lightrail-front.svg
│  │          translate.svg
│  │          transparency.svg
│  │          trash-fill.svg
│  │          trash.svg
│  │          trash2-fill.svg
│  │          trash2.svg
│  │          trash3-fill.svg
│  │          trash3.svg
│  │          tree-fill.svg
│  │          tree.svg
│  │          trello.svg
│  │          triangle-fill.svg
│  │          triangle-half.svg
│  │          triangle.svg
│  │          trophy-fill.svg
│  │          trophy.svg
│  │          tropical-storm.svg
│  │          truck-flatbed.svg
│  │          truck-front-fill.svg
│  │          truck-front.svg
│  │          truck.svg
│  │          tsunami.svg
│  │          tv-fill.svg
│  │          tv.svg
│  │          twitch.svg
│  │          twitter-x.svg
│  │          twitter.svg
│  │          type-bold.svg
│  │          type-h1.svg
│  │          type-h2.svg
│  │          type-h3.svg
│  │          type-h4.svg
│  │          type-h5.svg
│  │          type-h6.svg
│  │          type-italic.svg
│  │          type-strikethrough.svg
│  │          type-underline.svg
│  │          type.svg
│  │          ubuntu.svg
│  │          ui-checks-grid.svg
│  │          ui-checks.svg
│  │          ui-radios-grid.svg
│  │          ui-radios.svg
│  │          umbrella-fill.svg
│  │          umbrella.svg
│  │          unindent.svg
│  │          union.svg
│  │          unity.svg
│  │          universal-access-circle.svg
│  │          universal-access.svg
│  │          unlock-fill.svg
│  │          unlock.svg
│  │          upc-scan.svg
│  │          upc.svg
│  │          upload.svg
│  │          usb-c-fill.svg
│  │          usb-c.svg
│  │          usb-drive-fill.svg
│  │          usb-drive.svg
│  │          usb-fill.svg
│  │          usb-micro-fill.svg
│  │          usb-micro.svg
│  │          usb-mini-fill.svg
│  │          usb-mini.svg
│  │          usb-plug-fill.svg
│  │          usb-plug.svg
│  │          usb-symbol.svg
│  │          usb.svg
│  │          valentine.svg
│  │          valentine2.svg
│  │          vector-pen.svg
│  │          view-list.svg
│  │          view-stacked.svg
│  │          vignette.svg
│  │          vimeo.svg
│  │          vinyl-fill.svg
│  │          vinyl.svg
│  │          virus.svg
│  │          virus2.svg
│  │          voicemail.svg
│  │          volume-down-fill.svg
│  │          volume-down.svg
│  │          volume-mute-fill.svg
│  │          volume-mute.svg
│  │          volume-off-fill.svg
│  │          volume-off.svg
│  │          volume-up-fill.svg
│  │          volume-up.svg
│  │          vr.svg
│  │          wallet-fill.svg
│  │          wallet.svg
│  │          wallet2.svg
│  │          watch.svg
│  │          water.svg
│  │          webcam-fill.svg
│  │          webcam.svg
│  │          wechat.svg
│  │          whatsapp.svg
│  │          wifi-1.svg
│  │          wifi-2.svg
│  │          wifi-off.svg
│  │          wifi.svg
│  │          wikipedia.svg
│  │          wind.svg
│  │          window-dash.svg
│  │          window-desktop.svg
│  │          window-dock.svg
│  │          window-fullscreen.svg
│  │          window-plus.svg
│  │          window-sidebar.svg
│  │          window-split.svg
│  │          window-stack.svg
│  │          window-x.svg
│  │          window.svg
│  │          windows.svg
│  │          wordpress.svg
│  │          wrench-adjustable-circle-fill.svg
│  │          wrench-adjustable-circle.svg
│  │          wrench-adjustable.svg
│  │          wrench.svg
│  │          x-circle-fill.svg
│  │          x-circle.svg
│  │          x-diamond-fill.svg
│  │          x-diamond.svg
│  │          x-lg.svg
│  │          x-octagon-fill.svg
│  │          x-octagon.svg
│  │          x-square-fill.svg
│  │          x-square.svg
│  │          x.svg
│  │          xbox.svg
│  │          yelp.svg
│  │          yin-yang.svg
│  │          youtube.svg
│  │          zoom-in.svg
│  │          zoom-out.svg
│  │
│  ├─combined-stream
│  │  │  License
│  │  │  package.json
│  │  │  Readme.md
│  │  │  yarn.lock
│  │  │
│  │  └─lib
│  │          combined_stream.js
│  │
│  ├─crypto-js
│  │  │  aes.js
│  │  │  blowfish.js
│  │  │  bower.json
│  │  │  cipher-core.js
│  │  │  CONTRIBUTING.md
│  │  │  core.js
│  │  │  crypto-js.js
│  │  │  enc-base64.js
│  │  │  enc-base64url.js
│  │  │  enc-hex.js
│  │  │  enc-latin1.js
│  │  │  enc-utf16.js
│  │  │  enc-utf8.js
│  │  │  evpkdf.js
│  │  │  format-hex.js
│  │  │  format-openssl.js
│  │  │  hmac-md5.js
│  │  │  hmac-ripemd160.js
│  │  │  hmac-sha1.js
│  │  │  hmac-sha224.js
│  │  │  hmac-sha256.js
│  │  │  hmac-sha3.js
│  │  │  hmac-sha384.js
│  │  │  hmac-sha512.js
│  │  │  hmac.js
│  │  │  index.js
│  │  │  lib-typedarrays.js
│  │  │  LICENSE
│  │  │  md5.js
│  │  │  mode-cfb.js
│  │  │  mode-ctr-gladman.js
│  │  │  mode-ctr.js
│  │  │  mode-ecb.js
│  │  │  mode-ofb.js
│  │  │  package.json
│  │  │  pad-ansix923.js
│  │  │  pad-iso10126.js
│  │  │  pad-iso97971.js
│  │  │  pad-nopadding.js
│  │  │  pad-pkcs7.js
│  │  │  pad-zeropadding.js
│  │  │  pbkdf2.js
│  │  │  rabbit-legacy.js
│  │  │  rabbit.js
│  │  │  rc4.js
│  │  │  README.md
│  │  │  ripemd160.js
│  │  │  sha1.js
│  │  │  sha224.js
│  │  │  sha256.js
│  │  │  sha3.js
│  │  │  sha384.js
│  │  │  sha512.js
│  │  │  tripledes.js
│  │  │  x64-core.js
│  │  │
│  │  └─docs
│  │          QuickStartGuide.wiki
│  │
│  ├─csstype
│  │      index.d.ts
│  │      index.js.flow
│  │      LICENSE
│  │      package.json
│  │      README.md
│  │
│  ├─debug
│  │  │  CHANGELOG.md
│  │  │  LICENSE
│  │  │  node.js
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  └─src
│  │          browser.js
│  │          common.js
│  │          index.js
│  │          node.js
│  │
│  ├─delayed-stream
│  │  │  .npmignore
│  │  │  License
│  │  │  Makefile
│  │  │  package.json
│  │  │  Readme.md
│  │  │
│  │  └─lib
│  │          delayed_stream.js
│  │
│  ├─entities
│  │  │  LICENSE
│  │  │  package.json
│  │  │  readme.md
│  │  │
│  │  └─lib
│  │      │  decode.d.ts
│  │      │  decode.d.ts.map
│  │      │  decode.js
│  │      │  decode.js.map
│  │      │  decode_codepoint.d.ts
│  │      │  decode_codepoint.d.ts.map
│  │      │  decode_codepoint.js
│  │      │  decode_codepoint.js.map
│  │      │  encode.d.ts
│  │      │  encode.d.ts.map
│  │      │  encode.js
│  │      │  encode.js.map
│  │      │  escape.d.ts
│  │      │  escape.d.ts.map
│  │      │  escape.js
│  │      │  escape.js.map
│  │      │  index.d.ts
│  │      │  index.d.ts.map
│  │      │  index.js
│  │      │  index.js.map
│  │      │
│  │      ├─esm
│  │      │  │  decode.d.ts
│  │      │  │  decode.d.ts.map
│  │      │  │  decode.js
│  │      │  │  decode.js.map
│  │      │  │  decode_codepoint.d.ts
│  │      │  │  decode_codepoint.d.ts.map
│  │      │  │  decode_codepoint.js
│  │      │  │  decode_codepoint.js.map
│  │      │  │  encode.d.ts
│  │      │  │  encode.d.ts.map
│  │      │  │  encode.js
│  │      │  │  encode.js.map
│  │      │  │  escape.d.ts
│  │      │  │  escape.d.ts.map
│  │      │  │  escape.js
│  │      │  │  escape.js.map
│  │      │  │  index.d.ts
│  │      │  │  index.d.ts.map
│  │      │  │  index.js
│  │      │  │  index.js.map
│  │      │  │  package.json
│  │      │  │
│  │      │  └─generated
│  │      │          decode-data-html.d.ts
│  │      │          decode-data-html.d.ts.map
│  │      │          decode-data-html.js
│  │      │          decode-data-html.js.map
│  │      │          decode-data-xml.d.ts
│  │      │          decode-data-xml.d.ts.map
│  │      │          decode-data-xml.js
│  │      │          decode-data-xml.js.map
│  │      │          encode-html.d.ts
│  │      │          encode-html.d.ts.map
│  │      │          encode-html.js
│  │      │          encode-html.js.map
│  │      │
│  │      └─generated
│  │              decode-data-html.d.ts
│  │              decode-data-html.d.ts.map
│  │              decode-data-html.js
│  │              decode-data-html.js.map
│  │              decode-data-xml.d.ts
│  │              decode-data-xml.d.ts.map
│  │              decode-data-xml.js
│  │              decode-data-xml.js.map
│  │              encode-html.d.ts
│  │              encode-html.d.ts.map
│  │              encode-html.js
│  │              encode-html.js.map
│  │
│  ├─esbuild
│  │  │  install.js
│  │  │  LICENSE.md
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─bin
│  │  │      esbuild
│  │  │
│  │  └─lib
│  │          main.d.ts
│  │          main.js
│  │
│  ├─estree-walker
│  │  │  CHANGELOG.md
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─dist
│  │  │  ├─esm
│  │  │  │      estree-walker.js
│  │  │  │      package.json
│  │  │  │
│  │  │  └─umd
│  │  │          estree-walker.js
│  │  │
│  │  ├─src
│  │  │      async.js
│  │  │      index.js
│  │  │      package.json
│  │  │      sync.js
│  │  │      walker.js
│  │  │
│  │  └─types
│  │          async.d.ts
│  │          index.d.ts
│  │          sync.d.ts
│  │          tsconfig.tsbuildinfo
│  │          walker.d.ts
│  │
│  ├─eventsource
│  │  │  .editorconfig
│  │  │  CONTRIBUTING.md
│  │  │  HISTORY.md
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─example
│  │  │      eventsource-polyfill.js
│  │  │      index.html
│  │  │      sse-client.js
│  │  │      sse-server.js
│  │  │
│  │  └─lib
│  │          eventsource-polyfill.js
│  │          eventsource.js
│  │
│  ├─faye-websocket
│  │  │  CHANGELOG.md
│  │  │  LICENSE.md
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  └─lib
│  │      └─faye
│  │          │  eventsource.js
│  │          │  websocket.js
│  │          │
│  │          └─websocket
│  │              │  api.js
│  │              │  client.js
│  │              │
│  │              └─api
│  │                      event.js
│  │                      event_target.js
│  │
│  ├─follow-redirects
│  │      debug.js
│  │      http.js
│  │      https.js
│  │      index.js
│  │      LICENSE
│  │      package.json
│  │      README.md
│  │
│  ├─form-data
│  │  │  index.d.ts
│  │  │  License
│  │  │  package.json
│  │  │  Readme.md
│  │  │  README.md.bak
│  │  │
│  │  └─lib
│  │          browser.js
│  │          form_data.js
│  │          populate.js
│  │
│  ├─http-parser-js
│  │      http-parser.d.ts
│  │      http-parser.js
│  │      LICENSE.md
│  │      package.json
│  │      README.md
│  │
│  ├─inherits
│  │      inherits.js
│  │      inherits_browser.js
│  │      LICENSE
│  │      package.json
│  │      README.md
│  │
│  ├─magic-string
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  └─dist
│  │          magic-string.cjs.d.ts
│  │          magic-string.cjs.js
│  │          magic-string.cjs.js.map
│  │          magic-string.es.d.mts
│  │          magic-string.es.mjs
│  │          magic-string.es.mjs.map
│  │          magic-string.umd.js
│  │          magic-string.umd.js.map
│  │
│  ├─mime-db
│  │      db.json
│  │      HISTORY.md
│  │      index.js
│  │      LICENSE
│  │      package.json
│  │      README.md
│  │
│  ├─mime-types
│  │      HISTORY.md
│  │      index.js
│  │      LICENSE
│  │      package.json
│  │      README.md
│  │
│  ├─ms
│  │      index.js
│  │      license.md
│  │      package.json
│  │      readme.md
│  │
│  ├─nanoid
│  │  │  index.browser.cjs
│  │  │  index.browser.js
│  │  │  index.cjs
│  │  │  index.d.cts
│  │  │  index.d.ts
│  │  │  index.js
│  │  │  LICENSE
│  │  │  nanoid.js
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─async
│  │  │      index.browser.cjs
│  │  │      index.browser.js
│  │  │      index.cjs
│  │  │      index.d.ts
│  │  │      index.js
│  │  │      index.native.js
│  │  │      package.json
│  │  │
│  │  ├─bin
│  │  │      nanoid.cjs
│  │  │
│  │  ├─non-secure
│  │  │      index.cjs
│  │  │      index.d.ts
│  │  │      index.js
│  │  │      package.json
│  │  │
│  │  └─url-alphabet
│  │          index.cjs
│  │          index.js
│  │          package.json
│  │
│  ├─picocolors
│  │      LICENSE
│  │      package.json
│  │      picocolors.browser.js
│  │      picocolors.d.ts
│  │      picocolors.js
│  │      README.md
│  │      types.ts
│  │
│  ├─pinia
│  │  │  index.cjs
│  │  │  index.js
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─dist
│  │  │      pinia.cjs
│  │  │      pinia.d.ts
│  │  │      pinia.esm-browser.js
│  │  │      pinia.iife.js
│  │  │      pinia.iife.prod.js
│  │  │      pinia.mjs
│  │  │      pinia.prod.cjs
│  │  │
│  │  └─node_modules
│  │      ├─.bin
│  │      │      vue-demi-fix
│  │      │      vue-demi-fix.cmd
│  │      │      vue-demi-fix.ps1
│  │      │      vue-demi-switch
│  │      │      vue-demi-switch.cmd
│  │      │      vue-demi-switch.ps1
│  │      │
│  │      └─vue-demi
│  │          │  LICENSE
│  │          │  package.json
│  │          │  README.md
│  │          │
│  │          ├─bin
│  │          │      vue-demi-fix.js
│  │          │      vue-demi-switch.js
│  │          │
│  │          ├─lib
│  │          │  │  index.cjs
│  │          │  │  index.d.ts
│  │          │  │  index.iife.js
│  │          │  │  index.mjs
│  │          │  │
│  │          │  ├─v2
│  │          │  │      index.cjs
│  │          │  │      index.d.ts
│  │          │  │      index.mjs
│  │          │  │
│  │          │  ├─v2.7
│  │          │  │      index.cjs
│  │          │  │      index.d.ts
│  │          │  │      index.mjs
│  │          │  │
│  │          │  └─v3
│  │          │          index.cjs
│  │          │          index.d.ts
│  │          │          index.mjs
│  │          │
│  │          └─scripts
│  │                  postinstall.js
│  │                  switch-cli.js
│  │                  utils.js
│  │
│  ├─postcss
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  └─lib
│  │          at-rule.d.ts
│  │          at-rule.js
│  │          comment.d.ts
│  │          comment.js
│  │          container.d.ts
│  │          container.js
│  │          css-syntax-error.d.ts
│  │          css-syntax-error.js
│  │          declaration.d.ts
│  │          declaration.js
│  │          document.d.ts
│  │          document.js
│  │          fromJSON.d.ts
│  │          fromJSON.js
│  │          input.d.ts
│  │          input.js
│  │          lazy-result.d.ts
│  │          lazy-result.js
│  │          list.d.ts
│  │          list.js
│  │          map-generator.js
│  │          no-work-result.d.ts
│  │          no-work-result.js
│  │          node.d.ts
│  │          node.js
│  │          parse.d.ts
│  │          parse.js
│  │          parser.js
│  │          postcss.d.mts
│  │          postcss.d.ts
│  │          postcss.js
│  │          postcss.mjs
│  │          previous-map.d.ts
│  │          previous-map.js
│  │          processor.d.ts
│  │          processor.js
│  │          result.d.ts
│  │          result.js
│  │          root.d.ts
│  │          root.js
│  │          rule.d.ts
│  │          rule.js
│  │          stringifier.d.ts
│  │          stringifier.js
│  │          stringify.d.ts
│  │          stringify.js
│  │          symbols.js
│  │          terminal-highlight.js
│  │          tokenize.js
│  │          warn-once.js
│  │          warning.d.ts
│  │          warning.js
│  │
│  ├─proxy-from-env
│  │      .eslintrc
│  │      .travis.yml
│  │      index.js
│  │      LICENSE
│  │      package.json
│  │      README.md
│  │      test.js
│  │
│  ├─querystringify
│  │      index.js
│  │      LICENSE
│  │      package.json
│  │      README.md
│  │
│  ├─requires-port
│  │      .npmignore
│  │      .travis.yml
│  │      index.js
│  │      LICENSE
│  │      package.json
│  │      README.md
│  │      test.js
│  │
│  ├─rollup
│  │  │  LICENSE.md
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  └─dist
│  │      │  getLogFilter.d.ts
│  │      │  getLogFilter.js
│  │      │  loadConfigFile.d.ts
│  │      │  loadConfigFile.js
│  │      │  native.js
│  │      │  parseAst.d.ts
│  │      │  parseAst.js
│  │      │  rollup.d.ts
│  │      │  rollup.js
│  │      │
│  │      ├─bin
│  │      │      rollup
│  │      │
│  │      ├─es
│  │      │  │  getLogFilter.js
│  │      │  │  package.json
│  │      │  │  parseAst.js
│  │      │  │  rollup.js
│  │      │  │
│  │      │  └─shared
│  │      │          node-entry.js
│  │      │          parseAst.js
│  │      │          watch.js
│  │      │
│  │      └─shared
│  │              fsevents-importer.js
│  │              index.js
│  │              loadConfigFile.js
│  │              parseAst.js
│  │              rollup.js
│  │              watch-cli.js
│  │              watch.js
│  │
│  ├─safe-buffer
│  │      index.d.ts
│  │      index.js
│  │      LICENSE
│  │      package.json
│  │      README.md
│  │
│  ├─sockjs-client
│  │  │  AUTHORS
│  │  │  Changelog.md
│  │  │  CODE_OF_CONDUCT.md
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─dist
│  │  │      sockjs.js
│  │  │      sockjs.js.map
│  │  │      sockjs.min.js
│  │  │      sockjs.min.js.map
│  │  │
│  │  └─lib
│  │      │  entry.js
│  │      │  facade.js
│  │      │  iframe-bootstrap.js
│  │      │  info-ajax.js
│  │      │  info-iframe-receiver.js
│  │      │  info-iframe.js
│  │      │  info-receiver.js
│  │      │  location.js
│  │      │  main.js
│  │      │  shims.js
│  │      │  transport-list.js
│  │      │  version.js
│  │      │
│  │      ├─event
│  │      │      close.js
│  │      │      emitter.js
│  │      │      event.js
│  │      │      eventtarget.js
│  │      │      trans-message.js
│  │      │
│  │      ├─transport
│  │      │  │  eventsource.js
│  │      │  │  htmlfile.js
│  │      │  │  iframe.js
│  │      │  │  jsonp-polling.js
│  │      │  │  websocket.js
│  │      │  │  xdr-polling.js
│  │      │  │  xdr-streaming.js
│  │      │  │  xhr-polling.js
│  │      │  │  xhr-streaming.js
│  │      │  │
│  │      │  ├─browser
│  │      │  │      abstract-xhr.js
│  │      │  │      eventsource.js
│  │      │  │      websocket.js
│  │      │  │
│  │      │  ├─driver
│  │      │  │      eventsource.js
│  │      │  │      websocket.js
│  │      │  │      xhr.js
│  │      │  │
│  │      │  ├─lib
│  │      │  │      ajax-based.js
│  │      │  │      buffered-sender.js
│  │      │  │      iframe-wrap.js
│  │      │  │      polling.js
│  │      │  │      sender-receiver.js
│  │      │  │
│  │      │  ├─receiver
│  │      │  │      eventsource.js
│  │      │  │      htmlfile.js
│  │      │  │      jsonp.js
│  │      │  │      xhr.js
│  │      │  │
│  │      │  └─sender
│  │      │          jsonp.js
│  │      │          xdr.js
│  │      │          xhr-cors.js
│  │      │          xhr-fake.js
│  │      │          xhr-local.js
│  │      │
│  │      └─utils
│  │              browser-crypto.js
│  │              browser.js
│  │              escape.js
│  │              event.js
│  │              iframe.js
│  │              log.js
│  │              object.js
│  │              random.js
│  │              transport.js
│  │              url.js
│  │
│  ├─source-map-js
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │  source-map.d.ts
│  │  │  source-map.js
│  │  │
│  │  └─lib
│  │          array-set.js
│  │          base64-vlq.js
│  │          base64.js
│  │          binary-search.js
│  │          mapping-list.js
│  │          quick-sort.js
│  │          source-map-consumer.js
│  │          source-map-generator.js
│  │          source-node.js
│  │          util.js
│  │
│  ├─url-parse
│  │  │  index.js
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  └─dist
│  │          url-parse.js
│  │          url-parse.min.js
│  │          url-parse.min.js.map
│  │
│  ├─uuid
│  │  │  CHANGELOG.md
│  │  │  CONTRIBUTING.md
│  │  │  LICENSE.md
│  │  │  package.json
│  │  │  README.md
│  │  │  wrapper.mjs
│  │  │
│  │  └─dist
│  │      │  index.js
│  │      │  md5-browser.js
│  │      │  md5.js
│  │      │  native-browser.js
│  │      │  native.js
│  │      │  nil.js
│  │      │  parse.js
│  │      │  regex.js
│  │      │  rng-browser.js
│  │      │  rng.js
│  │      │  sha1-browser.js
│  │      │  sha1.js
│  │      │  stringify.js
│  │      │  uuid-bin.js
│  │      │  v1.js
│  │      │  v3.js
│  │      │  v35.js
│  │      │  v4.js
│  │      │  v5.js
│  │      │  validate.js
│  │      │  version.js
│  │      │
│  │      ├─bin
│  │      │      uuid
│  │      │
│  │      ├─commonjs-browser
│  │      │      index.js
│  │      │      md5.js
│  │      │      native.js
│  │      │      nil.js
│  │      │      parse.js
│  │      │      regex.js
│  │      │      rng.js
│  │      │      sha1.js
│  │      │      stringify.js
│  │      │      v1.js
│  │      │      v3.js
│  │      │      v35.js
│  │      │      v4.js
│  │      │      v5.js
│  │      │      validate.js
│  │      │      version.js
│  │      │
│  │      ├─esm-browser
│  │      │      index.js
│  │      │      md5.js
│  │      │      native.js
│  │      │      nil.js
│  │      │      parse.js
│  │      │      regex.js
│  │      │      rng.js
│  │      │      sha1.js
│  │      │      stringify.js
│  │      │      v1.js
│  │      │      v3.js
│  │      │      v35.js
│  │      │      v4.js
│  │      │      v5.js
│  │      │      validate.js
│  │      │      version.js
│  │      │
│  │      └─esm-node
│  │              index.js
│  │              md5.js
│  │              native.js
│  │              nil.js
│  │              parse.js
│  │              regex.js
│  │              rng.js
│  │              sha1.js
│  │              stringify.js
│  │              v1.js
│  │              v3.js
│  │              v35.js
│  │              v4.js
│  │              v5.js
│  │              validate.js
│  │              version.js
│  │
│  ├─vite
│  │  │  client.d.ts
│  │  │  index.cjs
│  │  │  index.d.cts
│  │  │  LICENSE.md
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─bin
│  │  │      openChrome.applescript
│  │  │      vite.js
│  │  │
│  │  ├─dist
│  │  │  ├─client
│  │  │  │      client.mjs
│  │  │  │      client.mjs.map
│  │  │  │      env.mjs
│  │  │  │      env.mjs.map
│  │  │  │
│  │  │  ├─node
│  │  │  │  │  cli.js
│  │  │  │  │  constants.js
│  │  │  │  │  index.d.ts
│  │  │  │  │  index.js
│  │  │  │  │  runtime.d.ts
│  │  │  │  │  runtime.js
│  │  │  │  │  types.d-aGj9QkWt.d.ts
│  │  │  │  │
│  │  │  │  └─chunks
│  │  │  │          dep-BBHrJRja.js
│  │  │  │          dep-Cb3N489L.js
│  │  │  │          dep-CrWVpuYf.js
│  │  │  │          dep-IQS-Za7F.js
│  │  │  │          dep-yOtoFZYM.js
│  │  │  │
│  │  │  └─node-cjs
│  │  │          publicUtils.cjs
│  │  │
│  │  └─types
│  │          customEvent.d.ts
│  │          hmrPayload.d.ts
│  │          hot.d.ts
│  │          import-meta.d.ts
│  │          importGlob.d.ts
│  │          importMeta.d.ts
│  │          metadata.d.ts
│  │          package.json
│  │
│  ├─vue
│  │  │  index.js
│  │  │  index.mjs
│  │  │  jsx.d.ts
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─compiler-sfc
│  │  │      index.browser.js
│  │  │      index.browser.mjs
│  │  │      index.d.mts
│  │  │      index.d.ts
│  │  │      index.js
│  │  │      index.mjs
│  │  │      package.json
│  │  │      register-ts.js
│  │  │
│  │  ├─dist
│  │  │      vue.cjs.js
│  │  │      vue.cjs.prod.js
│  │  │      vue.d.mts
│  │  │      vue.d.ts
│  │  │      vue.esm-browser.js
│  │  │      vue.esm-browser.prod.js
│  │  │      vue.esm-bundler.js
│  │  │      vue.global.js
│  │  │      vue.global.prod.js
│  │  │      vue.runtime.esm-browser.js
│  │  │      vue.runtime.esm-browser.prod.js
│  │  │      vue.runtime.esm-bundler.js
│  │  │      vue.runtime.global.js
│  │  │      vue.runtime.global.prod.js
│  │  │
│  │  ├─jsx-runtime
│  │  │      index.d.ts
│  │  │      index.js
│  │  │      index.mjs
│  │  │      package.json
│  │  │
│  │  └─server-renderer
│  │          index.d.mts
│  │          index.d.ts
│  │          index.js
│  │          index.mjs
│  │          package.json
│  │
│  ├─vue-router
│  │  │  index.js
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │  vue-router-auto-routes.d.ts
│  │  │  vue-router-auto.d.ts
│  │  │
│  │  ├─dist
│  │  │      vue-router.cjs
│  │  │      vue-router.cjs.js
│  │  │      vue-router.cjs.prod.js
│  │  │      vue-router.d.ts
│  │  │      vue-router.esm-browser.js
│  │  │      vue-router.esm-bundler.js
│  │  │      vue-router.global.js
│  │  │      vue-router.global.prod.js
│  │  │      vue-router.mjs
│  │  │      vue-router.node.mjs
│  │  │      vue-router.prod.cjs
│  │  │
│  │  └─vetur
│  │          attributes.json
│  │          tags.json
│  │
│  ├─vue-stomp
│  │  │  .babelrc
│  │  │  .eslintrc.js
│  │  │  .travis.yml
│  │  │  LICENSE
│  │  │  package.json
│  │  │  README.md
│  │  │  webpack.build.config.js
│  │  │  webpack.dev.config.js
│  │  │
│  │  ├─dist
│  │  │      vue-stomp.js
│  │  │      vue-stomp.js.map
│  │  │
│  │  └─src
│  │          index.js
│  │
│  ├─websocket-driver
│  │  │  CHANGELOG.md
│  │  │  LICENSE.md
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  └─lib
│  │      └─websocket
│  │          │  driver.js
│  │          │  http_parser.js
│  │          │  streams.js
│  │          │
│  │          └─driver
│  │              │  base.js
│  │              │  client.js
│  │              │  draft75.js
│  │              │  draft76.js
│  │              │  headers.js
│  │              │  hybi.js
│  │              │  proxy.js
│  │              │  server.js
│  │              │  stream_reader.js
│  │              │
│  │              └─hybi
│  │                      frame.js
│  │                      message.js
│  │
│  ├─websocket-extensions
│  │  │  CHANGELOG.md
│  │  │  LICENSE.md
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  └─lib
│  │      │  parser.js
│  │      │  websocket_extensions.js
│  │      │
│  │      └─pipeline
│  │              cell.js
│  │              functor.js
│  │              index.js
│  │              pledge.js
│  │              README.md
│  │              ring_buffer.js
│  │
│  ├─webstomp-client
│  │  │  CHANGELOG.md
│  │  │  index.d.ts
│  │  │  LICENSE.txt
│  │  │  package.json
│  │  │  README.md
│  │  │
│  │  ├─dist
│  │  │      webstomp.js
│  │  │      webstomp.min.js
│  │  │
│  │  └─src
│  │          .babelrc
│  │          client.js
│  │          frame.js
│  │          utils.js
│  │          webstomp.js
│  │
│  └─ws
│      │  browser.js
│      │  index.js
│      │  LICENSE
│      │  package.json
│      │  README.md
│      │  wrapper.mjs
│      │
│      └─lib
│              buffer-util.js
│              constants.js
│              event-target.js
│              extension.js
│              limiter.js
│              permessage-deflate.js
│              receiver.js
│              sender.js
│              stream.js
│              subprotocol.js
│              validation.js
│              websocket-server.js
│              websocket.js
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
    │  │  background-video-m-01.mp4
    │  │  cardback.jpg
    │  │  garen-background.gif
    │  │  garen.avif
    │  │  ghost.jpg
    │  │  goback.png
    │  │  help.png
    │  │  icon.png
    │  │  logo.PNG
    │  │  logo2.png
    │  │  ornn-loading.gif
    │  │  Position_Bronze-Bot.png
    │  │  Position_Bronze-Jungle.png
    │  │  Position_Bronze-Mid.png
    │  │  Position_Bronze-Support.png
    │  │  Position_Bronze-Top.png
    │  │  Position_Challenger-Bot.png
    │  │  Position_Challenger-Jungle.png
    │  │  Position_Challenger-Mid.png
    │  │  Position_Challenger-Support.png
    │  │  Position_Challenger-Top.png
    │  │  Position_Diamond-Bot.png
    │  │  Position_Diamond-Jungle.png
    │  │  Position_Diamond-Mid.png
    │  │  Position_Diamond-Support.png
    │  │  Position_Diamond-Top.png
    │  │  Position_Emerald-Bot.png
    │  │  Position_Emerald-Jungle.png
    │  │  Position_Emerald-Mid.png
    │  │  Position_Emerald-Support.png
    │  │  Position_Emerald-Top.png
    │  │  Position_Gold-Bot.png
    │  │  Position_Gold-Jungle.png
    │  │  Position_Gold-Mid.png
    │  │  Position_Gold-Support.png
    │  │  Position_Gold-Top.png
    │  │  Position_Grandmaster-Bot.png
    │  │  Position_Grandmaster-Jungle.png
    │  │  Position_Grandmaster-Mid.png
    │  │  Position_Grandmaster-Support.png
    │  │  Position_Grandmaster-Top.png
    │  │  Position_Iron-Bot.png
    │  │  Position_Iron-Jungle.png
    │  │  Position_Iron-Mid.png
    │  │  Position_Iron-Support.png
    │  │  Position_Iron-Top.png
    │  │  Position_Master-Bot.png
    │  │  Position_Master-Jungle.png
    │  │  Position_Master-Mid.png
    │  │  Position_Master-Support.png
    │  │  Position_Master-Top.png
    │  │  Position_Platinum-Bot.png
    │  │  Position_Platinum-Jungle.png
    │  │  Position_Platinum-Mid.png
    │  │  Position_Platinum-Support.png
    │  │  Position_Platinum-Top.png
    │  │  Position_Silver-Bot.png
    │  │  Position_Silver-Jungle.png
    │  │  Position_Silver-Mid.png
    │  │  Position_Silver-Support.png
    │  │  Position_Silver-Top.png
    │  │  riotlogo.png
    │  │
    │  ├─emblem
    │  │      Rank=Bronze.png
    │  │      Rank=Challenger.png
    │  │      Rank=Diamond.png
    │  │      Rank=Emerald.png
    │  │      Rank=Gold.png
    │  │      Rank=Grandmaster.png
    │  │      Rank=Iron.png
    │  │      Rank=Master.png
    │  │      Rank=Platinum.png
    │  │      Rank=Silver.png
    │  │
    │  ├─garenimage
    │  │      Garen_0.jpg
    │  │      Garen_1.jpg
    │  │      Garen_10.jpg
    │  │      Garen_11.jpg
    │  │      Garen_13.jpg
    │  │      Garen_14.jpg
    │  │      Garen_2.jpg
    │  │      Garen_22.jpg
    │  │      Garen_23.jpg
    │  │      Garen_24.jpg
    │  │      Garen_3.jpg
    │  │      Garen_33.jpg
    │  │      Garen_4.jpg
    │  │      Garen_5.jpg
    │  │      Garen_6.jpg
    │  │
    │  ├─pick-voice
    │  │      1.ogg
    │  │      10.ogg
    │  │      101.ogg
    │  │      102.ogg
    │  │      103.ogg
    │  │      104.ogg
    │  │      105.ogg
    │  │      106.ogg
    │  │      107.ogg
    │  │      11.ogg
    │  │      110.ogg
    │  │      111.ogg
    │  │      112.ogg
    │  │      113.ogg
    │  │      114.ogg
    │  │      115.ogg
    │  │      117.ogg
    │  │      119.ogg
    │  │      12.ogg
    │  │      120.ogg
    │  │      121.ogg
    │  │      122.ogg
    │  │      126.ogg
    │  │      127.ogg
    │  │      13.ogg
    │  │      131.ogg
    │  │      133.ogg
    │  │      134.ogg
    │  │      136.ogg
    │  │      14.ogg
    │  │      141.ogg
    │  │      142.ogg
    │  │      143.ogg
    │  │      145.ogg
    │  │      147.ogg
    │  │      15.ogg
    │  │      150.ogg
    │  │      154.ogg
    │  │      157.ogg
    │  │      16.ogg
    │  │      161.ogg
    │  │      163.ogg
    │  │      164.ogg
    │  │      166.ogg
    │  │      17.ogg
    │  │      18.ogg
    │  │      19.ogg
    │  │      2.ogg
    │  │      20.ogg
    │  │      200.ogg
    │  │      201.ogg
    │  │      202.ogg
    │  │      203.ogg
    │  │      21.ogg
    │  │      22.ogg
    │  │      221.ogg
    │  │      222.ogg
    │  │      223.ogg
    │  │      23.ogg
    │  │      233.ogg
    │  │      234.ogg
    │  │      235.ogg
    │  │      236.ogg
    │  │      238.ogg
    │  │      24.ogg
    │  │      240.ogg
    │  │      245.ogg
    │  │      246.ogg
    │  │      25.ogg
    │  │      254.ogg
    │  │      26.ogg
    │  │      266.ogg
    │  │      267.ogg
    │  │      268.ogg
    │  │      27.ogg
    │  │      28.ogg
    │  │      29.ogg
    │  │      3.ogg
    │  │      30.ogg
    │  │      31.ogg
    │  │      32.ogg
    │  │      33.ogg
    │  │      34.ogg
    │  │      35.ogg
    │  │      350.ogg
    │  │      36.ogg
    │  │      360.ogg
    │  │      37.ogg
    │  │      38.ogg
    │  │      39.ogg
    │  │      4.ogg
    │  │      40.ogg
    │  │      41.ogg
    │  │      412.ogg
    │  │      42.ogg
    │  │      420.ogg
    │  │      421.ogg
    │  │      427.ogg
    │  │      429.ogg
    │  │      43.ogg
    │  │      432.ogg
    │  │      44.ogg
    │  │      45.ogg
    │  │      48.ogg
    │  │      497.ogg
    │  │      498.ogg
    │  │      5.ogg
    │  │      50.ogg
    │  │      51.ogg
    │  │      516.ogg
    │  │      517.ogg
    │  │      518.ogg
    │  │      523.ogg
    │  │      526.ogg
    │  │      53.ogg
    │  │      54.ogg
    │  │      55.ogg
    │  │      555.ogg
    │  │      56.ogg
    │  │      57.ogg
    │  │      58.ogg
    │  │      59.ogg
    │  │      6.ogg
    │  │      60.ogg
    │  │      61.ogg
    │  │      62.ogg
    │  │      63.ogg
    │  │      64.ogg
    │  │      67.ogg
    │  │      68.ogg
    │  │      69.ogg
    │  │      7.ogg
    │  │      711.ogg
    │  │      72.ogg
    │  │      74.ogg
    │  │      75.ogg
    │  │      76.ogg
    │  │      77.ogg
    │  │      777.ogg
    │  │      78.ogg
    │  │      79.ogg
    │  │      8.ogg
    │  │      80.ogg
    │  │      81.ogg
    │  │      82.ogg
    │  │      83.ogg
    │  │      84.ogg
    │  │      85.ogg
    │  │      86.ogg
    │  │      875.ogg
    │  │      876.ogg
    │  │      887.ogg
    │  │      888.ogg
    │  │      89.ogg
    │  │      895.ogg
    │  │      897.ogg
    │  │      9.ogg
    │  │      90.ogg
    │  │      901.ogg
    │  │      902.ogg
    │  │      91.ogg
    │  │      910.ogg
    │  │      92.ogg
    │  │      950.ogg
    │  │      96.ogg
    │  │      98.ogg
    │  │      99.ogg
    │  │
    │  └─sounds
    │          correct.mp3
    │          wrong.mp3
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
    │      BeaufortforLOL-Bold.otf
    │      BeaufortforLOL-Bold.ttf
    │      BeaufortforLOL-BoldItalic.otf
    │      BeaufortforLOL-Heavy.otf
    │      BeaufortforLOL-HeavyItalic.otf
    │      BeaufortforLOL-Italic(1).otf
    │      BeaufortforLOL-Italic.otf
    │      BeaufortforLOL-Light.otf
    │      BeaufortforLOL-LightItalic(1).otf
    │      BeaufortforLOL-LightItalic.otf
    │      BeaufortforLOL-Medium.otf
    │      BeaufortforLOL-MediumItalic.otf
    │      BeaufortforLOL-Regular.otf
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
