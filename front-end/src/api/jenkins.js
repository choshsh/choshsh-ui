import * as axios from "./axios";

const URL = "/jenkins";

async function getBuild(id) {
  if (id) {
    return await axios.get(URL + "/build/" + id);
  } else {
    return await axios.get(URL + "/build");
  }
}

async function getCode(param) {
  return await axios.get(URL + "/code/" + param);
}

async function getPyscript() {
  return await axios.get(URL + "/pyscript");
}

async function postBuild(param) {
  return await axios.post(URL + "/build", param);
}

export { getBuild, getCode, getPyscript, postBuild };
