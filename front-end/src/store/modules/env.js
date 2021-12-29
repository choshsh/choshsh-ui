import * as adminService from "@/api/admin";

const state = {
  envs: [],
};

const mutations = {
  set(state, [variable, value]) {
    state[variable] = value;
  },
};

const actions = {
  async load({ commit }) {
    let data = await adminService.getEnv();
    commit("set", ["envs", data]);
  },
};

const getters = {
  getEnv: (state) => (variable) => {
    return state.envs.find((env) => env.key === variable).value;
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
