export default [
  {
    _name: "CSidebarNav",
    _children: [
      {
        _name: "CSidebarNavTitle",
        _children: ["모니터링"],
      },
      {
        _name: "CSidebarNavItem",
        name: "Metric",
        to: "/monitor/metric",
        icon: "cil-chart-line",
      },
      {
        _name: "CSidebarNavItem",
        name: "Log",
        to: "/monitor/log",
        icon: "cil-notes",
      },
      {
        _name: "CSidebarNavItem",
        name: "Alert",
        to: "/monitor/alert",
        icon: "cilBell",
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
    ],
  },
];
