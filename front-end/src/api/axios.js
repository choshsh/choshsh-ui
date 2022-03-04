import axios from "axios";

const instance = axios.create({
  headers: { "Content-Type": "application/json" },
  timeout: 1000,
});

function setAuth() {
  if (!instance.defaults.headers.common["Authorization"]) {
    instance.defaults.headers.common["Authorization"] = sessionStorage.getItem(
      "userId"
    );
  }
}

export async function get(url, param) {
  try {
    setAuth();
    return (await instance.get(url, { params: param })).data;
  } catch (e) {
    return new Error(e);
  }
}

export async function post(url, param) {
  try {
    setAuth();
    return (await instance.post(url, param)).data;
  } catch (e) {
    return new Error(e);
  }
}

export async function put(url, param) {
  try {
    setAuth();
    return (await instance.put(url, param)).data;
  } catch (e) {
    return new Error(e);
  }
}

export async function del(url, param) {
  try {
    setAuth();
    return (await instance.delete(url, param)).data;
  } catch (e) {
    return new Error(e);
  }
}
