import * as axios from "./axios";

const URL = "/api/prometheus";

async function getRules() {
  return await axios.get(URL + "/rules");
}

async function getAlerts() {
  return await axios.get(URL + "/alerts");
}

export { getRules, getAlerts };
