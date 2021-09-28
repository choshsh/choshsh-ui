export default [
  {
    _name: "CSidebarNav",
    _children: [
      {
        _name: "CSidebarNavTitle",
        _children: ["메인"],
      },
      {
        _name: "CSidebarNavItem",
        name: "쿠버네티스",
        to: "/dashboard",
        icon: "cib-kubernetes",
      },
      {
        _name: "CSidebarNavTitle",
        _children: ["모니터링"],
      },
      {
        _name: "CSidebarNavItem",
        name: "Log",
        to: "/log",
        icon: "cil-notes",
      },
      {
        _name: "CSidebarNavItem",
        name: "Metric",
        to: "/monitor",
        icon: "cil-chart-line",
      },
      {
        _name: "CSidebarNavTitle",
        _children: ["테스트"],
      },
       {
        _name: "CSidebarNavItem",
        name: "부하 테스트",
        to: "/loadtest",
        icon: "cil-history",
      },
      {
        _name: "CSidebarNavTitle",
        _children: ["ETC"],
      },
      {
        _name: "CSidebarNavItem",
        name: "스크립트 모음",
        to: "/monitor",
        icon: "cil-description",
      },
    ],
  },
];
