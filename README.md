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

<img alt="JavaScript" src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white">
<img alt="vue.js" src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">
<img alt="bootstrap" src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">

#### Back-end

<img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/>
<img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=Spring Boot&logoColor=white"/>

#### Database

<img alt="MySQL" src ="https://img.shields.io/badge/mysql-4479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white"/>

#### 버전 및 이슈관리

<img alt="gitlab" src ="https://img.shields.io/badge/gitlab-FC6D26.svg?&style=for-the-badge&logo=gitlab&logoColor=white"/>

#### 협업 툴

<img alt="discord" src ="https://img.shields.io/badge/discord-5865F2.svg?&style=for-the-badge&logo=discord&logoColor=white"/>
<img alt="jira" src ="https://img.shields.io/badge/jira-0052CC.svg?&style=for-the-badge&logo=jira&logoColor=white"/>
<img alt="notion" src ="https://img.shields.io/badge/notion-000000.svg?&style=for-the-badge&logo=notion&logoColor=white"/>
<img alt="mattermost" src ="https://img.shields.io/badge/mattermost-0058CC.svg?&style=for-the-badge&logo=mattermost&logoColor=white"/>

#### 서비스 배포 환경

<img alt="AWS Lightsail" src ="https://img.shields.io/badge/AWS Lightsail-FF9900.svg?&style=for-the-badge&logo=amazonaws&logoColor=white"/>
<img alt="docker" src ="https://img.shields.io/badge/docker-2496ED.svg?&style=for-the-badge&logo=docker&logoColor=white"/>
<img alt="nginx" src ="https://img.shields.io/badge/nginx-009639.svg?&style=for-the-badge&logo=nginx&logoColor=white"/>

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
```

</details>
<details>
<summary>Backend</summary>

```
```

</details>
