import axios from "axios";

const baseURL = "https://j10a605.p.ssafy.io:8084/api";

const localAxios = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
  //   withCredentials: true, //쿠키사용시 필요
});

export { localAxios };
