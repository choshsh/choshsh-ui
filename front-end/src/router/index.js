import Vue from "vue";
import Router from "vue-router";

// Containers
const TheContainer = () => import("@/containers/TheContainer");

// 모니터링
const Monitor = () => import("@/views/monitoring/Metric");
const Log = () => import("@/views/monitoring/Log");
const Alert = () => import("@/views/monitoring/Alert");

// 부하 테스트
const LoadTest = () => import("@/views/loadtest/LoadTest");
const LoadTestInfo = () => import("@/views/loadtest/LoadTestInfo");

const Settings = () => import("@/views/settings/Settings");

const Page404 = () => import("@/views/pages/Page404");
const Page500 = () => import("@/views/pages/Page500");
const Login = () => import("@/views/pages/Login");

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
      component: TheContainer,
      children: [
        {
          path: "monitor/metric",
          name: "Metric",
          component: Monitor,
        },
        {
          path: "monitor/log",
          name: "Log",
          component: Log,
        },
        {
          path: "monitor/alert",
          name: "Alert",
          component: Alert,
        },
        {
          path: "loadtest",
          name: "부하 테스트",
          component: LoadTest,
        },
        {
          path: "loadTestInfo",
          name: "부하 테스트 상세",
          component: LoadTestInfo,
        },
        {
          path: "settings",
          name: "Settings",
          component: Settings,
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
          component: Page404,
        },
        {
          path: "500",
          name: "Page500",
          component: Page500,
        },
        {
          path: "login",
          name: "Login",
          component: Login,
        },
      ],
    },
    {
      path: "*",
      redirect: "/pages/404",
      name: "Pages",
      component: Page404,
    },
  ];
}
