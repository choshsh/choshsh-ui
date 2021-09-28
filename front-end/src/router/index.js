import Vue from "vue";
import Router from "vue-router";

// Containers
const TheContainer = () => import("@/containers/TheContainer");

// Views
const Dashboard = () => import("@/views/Dashboard");

const License = () => import("@/views/license/License");

const Charts = () => import("@/views/charts/Charts");

// 모니터링
const Monitor = () => import("@/views/monitoring/Metric");
const Log = () => import("@/views/monitoring/Log");

// 부하 테스트
const LoadTest = () => import("@/views/loadtest/LoadTest");

const Settings = () => import("@/views/settings/Settings");
const Script = () => import("@/views/script/Script");

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
      redirect: "/dashboard",
      name: "Home",
      component: TheContainer,
      children: [
        {
          path: "/dashboard",
          name: "Dashboard",
          component: Dashboard,
        },
        {
          path: "monitor",
          name: "리소스 Monitor",
          component: Monitor,
        },
        {
          path: "log",
          name: "로그 분석",
          component: Log,
        },
        {
          path: "loadtest",
          name: "부하 테스트",
          component: LoadTest,
        },
        {
          path: "charts",
          name: "Charts",
          component: Charts,
        },
        {
          path: "settings",
          name: "Settings",
          component: Settings,
        },
        {
          path: "script",
          name: "Script",
          component: Script,
        },
        // {
        //   path: "users",
        //   meta: {
        //     label: "Users",
        //   },
        //   component: {
        //     render(c) {
        //       return c("router-view");
        //     },
        //   },
        //   children: [
        //     {
        //       path: "",
        //       name: "Users",
        //       component: Users,
        //     },
        //     {
        //       path: ":id",
        //       meta: {
        //         label: "User Details",
        //       },
        //       name: "User",
        //       component: User,
        //     },
        //   ],
        // },
        // {
        //   path: "base",
        //   redirect: "/base/cards",
        //   name: "Base",
        //   component: {
        //     render(c) {
        //       return c("router-view");
        //     },
        //   },
        //   children: [
        //     {
        //       path: "cards",
        //       name: "Cards",
        //       component: Cards,
        //     },
        //     {
        //       path: "forms",
        //       name: "Forms",
        //       component: Forms,
        //     },
        //     {
        //       path: "switches",
        //       name: "Switches",
        //       component: Switches,
        //     },
        //     {
        //       path: "tables",
        //       name: "Tables",
        //       component: Tables,
        //     },
        //     {
        //       path: "tabs",
        //       name: "Tabs",
        //       component: Tabs,
        //     },
        //     {
        //       path: "breadcrumbs",
        //       name: "Breadcrumbs",
        //       component: Breadcrumbs,
        //     },
        //     {
        //       path: "carousels",
        //       name: "Carousels",
        //       component: Carousels,
        //     },
        //     {
        //       path: "collapses",
        //       name: "Collapses",
        //       component: Collapses,
        //     },
        //     {
        //       path: "jumbotrons",
        //       name: "Jumbotrons",
        //       component: Jumbotrons,
        //     },
        //     {
        //       path: "list-groups",
        //       name: "List Groups",
        //       component: ListGroups,
        //     },
        //     {
        //       path: "navs",
        //       name: "Navs",
        //       component: Navs,
        //     },
        //     {
        //       path: "navbars",
        //       name: "Navbars",
        //       component: Navbars,
        //     },
        //     {
        //       path: "paginations",
        //       name: "Paginations",
        //       component: Paginations,
        //     },
        //     {
        //       path: "popovers",
        //       name: "Popovers",
        //       component: Popovers,
        //     },
        //     {
        //       path: "progress-bars",
        //       name: "Progress Bars",
        //       component: ProgressBars,
        //     },
        //     {
        //       path: "tooltips",
        //       name: "Tooltips",
        //       component: Tooltips,
        //     },
        //   ],
        // },
        // {
        //   path: "buttons",
        //   redirect: "/buttons/standard-buttons",
        //   name: "Buttons",
        //   component: {
        //     render(c) {
        //       return c("router-view");
        //     },
        //   },
        //   children: [
        //     {
        //       path: "standard-buttons",
        //       name: "Standard Buttons",
        //       component: StandardButtons,
        //     },
        //     {
        //       path: "button-groups",
        //       name: "Button Groups",
        //       component: ButtonGroups,
        //     },
        //     {
        //       path: "dropdowns",
        //       name: "Dropdowns",
        //       component: Dropdowns,
        //     },
        //     {
        //       path: "brand-buttons",
        //       name: "Brand Buttons",
        //       component: BrandButtons,
        //     },
        //   ],
        // },
        // {
        //   path: "icons",
        //   redirect: "/icons/coreui-icons",
        //   name: "CoreUI Icons",
        //   component: {
        //     render(c) {
        //       return c("router-view");
        //     },
        //   },
        //   children: [
        //     {
        //       path: "coreui-icons",
        //       name: "Icons library",
        //       component: CoreUIIcons,
        //     },
        //     {
        //       path: "brands",
        //       name: "Brands",
        //       component: Brands,
        //     },
        //     {
        //       path: "flags",
        //       name: "Flags",
        //       component: Flags,
        //     },
        //   ],
        // },
        // {
        //   path: "notifications",
        //   redirect: "/notifications/alerts",
        //   name: "Notifications",
        //   component: {
        //     render(c) {
        //       return c("router-view");
        //     },
        //   },
        //   children: [
        //     {
        //       path: "alerts",
        //       name: "Alerts",
        //       component: Alerts,
        //     },
        //     {
        //       path: "badges",
        //       name: "Badges",
        //       component: Badges,
        //     },
        //     {
        //       path: "modals",
        //       name: "Modals",
        //       component: Modals,
        //     },
        //   ],
        // },
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
        // {
        //   path: "register",
        //   name: "Register",
        //   component: Register,
        // },
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
