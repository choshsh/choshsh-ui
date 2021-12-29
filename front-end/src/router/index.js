import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  mode: "hash",
  linkActiveClass: "active",
  scrollBehavior: () => ({ y: 0 }),
  routes: configRoutes(),
});

function configRoutes() {
  return [
    {
      path: "/",
      redirect: "/monitor/metric",
      name: "Home",
      component: () => import("@/containers/TheContainer"),
      children: [
        {
          path: "monitor/metric",
          name: "Metric",
          component: () => import("@/views/monitoring/Metric"),
        },
        {
          path: "monitor/log",
          name: "Log",
          component: () => import("@/views/monitoring/Log"),
        },
        {
          path: "monitor/alert",
          name: "Alert",
          component: () => import("@/views/monitoring/Alert"),
        },
        {
          path: "loadtest",
          name: "부하 테스트",
          component: () => import("@/views/loadtest/LoadTest"),
        },
        {
          path: "loadTestInfo",
          name: "부하 테스트 상세",
          component: () => import("@/views/loadtest/LoadTestInfo"),
        },
        {
          path: "settings",
          name: "Settings",
          component: () => import("@/views/settings/Settings"),
        },
      ],
    },
    {
      path: "/pages",
      redirect: "/pages/404",
      name: "Pages",
      component: {
        render(c) {
          return c("router-view");
        },
      },
      children: [
        {
          path: "404",
          name: "Page404",
          component: () => import("@/views/pages/Page404"),
        },
        {
          path: "500",
          name: "Page500",
          component: () => import("@/views/pages/Page500"),
        },
        {
          path: "login",
          name: "Login",
          component: () => import("@/views/pages/Login"),
        },
      ],
    },
    {
      path: "*",
      redirect: "/pages/404",
      name: "Pages",
      component: () => import("@/views/pages/Page404"),
    },
  ];
}
