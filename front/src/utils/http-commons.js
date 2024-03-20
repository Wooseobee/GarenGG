import axios from "axios";

const baseURL = "http://localhost:8080/api";


const localAxios = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
//   withCredentials: true, //쿠키사용시 필요
});


export { localAxios };
