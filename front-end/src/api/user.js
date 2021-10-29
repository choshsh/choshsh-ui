import * as axios from "./axios";

async function login(param) {
  return await axios.post("/api/user/login", param);
}

export { login };
