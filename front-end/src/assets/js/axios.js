import axios from "axios";

const DOMAIN = "";

const req = (method, url, param, func) => {
  return axios({
    method,
    url: DOMAIN + url,
    param,
  })
    .then((res) => res.data)
    .catch((e) => console.log(e));
};

export async function get(url, param) {
  try {
    return await req("get", url, param);
  } catch (e) {
    return new Error(e.status);
  }
}

export async function post(url, param) {
  try {
    return req("post", url, param);
  } catch (e) {
    return new Error(e.status);
  }
}

export async function put(url, param) {
  try {
    return req("put", url, param);
  } catch (e) {
    return new Error(e.status);
  }
}

export async function del(url, param) {
  try {
    return req("delete", url, param);
  } catch (e) {
    return new Error(e.status);
  }
}
