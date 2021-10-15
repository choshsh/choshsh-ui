import * as axios from "./axios";

const JENKINS = "/jenkins";

async function getBuild(id) {
  if (id) {
    return await axios.get(JENKINS + "/build/" + id);
  } else {
    return await axios.get(JENKINS + "/build");
  }
}

async function getCode(param) {
  return await axios.get(JENKINS + "/code/" + param);
}

async function getPyscript() {
  return await axios.get(JENKINS + "/pyscript");
}

async function postBuild(param) {
  return await axios.post(JENKINS + "/build", param);
}

export { getBuild, getCode, getPyscript, postBuild };
