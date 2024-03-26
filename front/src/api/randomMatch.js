import { localAxios } from "@/utils/http-commons";

const local = localAxios;

export function randomMatch() {
  return local.get(`/randomMatch`);
}

export async function playGame(body) {
  return local.post(`/playGame`, body);
}
