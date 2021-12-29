const state = {
  container: 0,
  header: 0,
};

const mutations = {
  reload(state, [variable]) {
    state[variable]++;
  },
};

export default {
  namespaced: true,
  state,
  mutations,
};
