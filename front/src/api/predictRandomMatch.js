import { localAxios } from "@/utils/http-commons";

const local = localAxios;

function randomMatch() {
  return local.get(`/randomMatch`);
}

export { randomMatch };
