import * as axios from "./axios";

const URL = "/api/admin";

async function getHeader() {
  return await axios.get(URL + "/header");
}

async function postHeader(param) {
  return await axios.post(URL + "/header", param);
}

async function getIframe(param) {
  if (param) {
    return await axios.get(URL + "/iframe/" + param);
  } else {
    return await axios.get(URL + "/iframe");
  }
}

async function postIframe(param) {
  return await axios.post(URL + "/iframe", param);
}

async function getEnv() {
  return await axios.get(URL + "/env");
}

async function putEnv(id, param) {
  return await axios.put(URL + "/env/" + id, param);
}

export { getHeader, postHeader, getIframe, postIframe, getEnv, putEnv };
