import * as axios from "./axios";

const ADMIN = "/api/admin";

async function getHeader() {
  return await axios.get(ADMIN + "/header");
}

async function postHeader(param) {
  return await axios.post(ADMIN + "/header", param);
}

async function getIframe(param) {
  if (param) {
    return await axios.get(ADMIN + "/iframe/" + param);
  } else {
    return await axios.get(ADMIN + "/iframe");
  }
}

async function postIframe(param) {
  return await axios.post(ADMIN + "/iframe", param);
}

async function getEnv() {
  return await axios.get(ADMIN + "/env");
}

async function putEnv(id, param) {
  return await axios.put(ADMIN + "/env/" + id, param);
}

export { getHeader, postHeader, getIframe, postIframe, getEnv, putEnv };
