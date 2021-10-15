import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex);

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
