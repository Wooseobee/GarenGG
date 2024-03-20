import { localAxios } from "@/utils/http-commons";

const local = localAxios;

function duoRecommendationChampion(params, success, fail){
    local.get(`/duoRecommendation/${params.champion}-${params.position}`).then(success).catch(fail);
}

function userriotApiCrawlHealthCheck(success, fail) {
    local.get(`/`).then(success).catch(fail);
}

function listAttraction(body, success, fail) {
    local.post(`/`, body).then(success).catch(fail);
}

export { duoRecommendationChampion}