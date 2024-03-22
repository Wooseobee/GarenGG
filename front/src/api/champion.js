import { localAxios } from "@/utils/http-commons";

const local = localAxios;

async function championInfo(){
    return local.get(`/champions`);
}

export { championInfo }