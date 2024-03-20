import { localAxios } from "@/utils/http-commons";

const local = localAxios;

function championInfo(success, fail){
    local.get(`/champions`).then(success).catch(fail);
}

export { championInfo }