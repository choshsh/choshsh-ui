import Vue from "vue";
import Vuex from "vuex";

import sidebar from "./modules/sidebar";
import env from "./modules/env";
import component from "./modules/component";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    sidebar,
    env,
    component,
  },
});
