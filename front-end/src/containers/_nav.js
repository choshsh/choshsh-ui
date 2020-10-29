export default [
  {
    _name: 'CSidebarNav',
    _children: [
      {
        _name: 'CSidebarNavItem',
        name: '대시보드',
        to: '/dashboard',
        icon: 'cil-speedometer',
      },
      {
        _name: 'CSidebarNavTitle',
        _children: ['업무']
      },
      {
        _name: 'CSidebarNavItem',
        name: '물리서버',
        to: '/servers',
        icon: 'cil-laptop'
      },
      {
        _name: 'CSidebarNavItem',
        name: '가상서버',
        to: '/vms',
        icon: 'cil-cloud'
      },
      {
        _name: 'CSidebarNavItem',
        name: '백업',
        to: '/backups',
        icon: 'cil-data-transfer-down'
      },
      {
        _name: 'CSidebarNavTitle',
        _children: ['차트']
      },
      {
        _name: 'CSidebarNavItem',
        name: '물리서버 차트',
        to: '/serversChart',
        icon: 'cil-bar-chart'
      },
      {
        _name: 'CSidebarNavItem',
        name: '가상서버 차트',
        to: '/vmsChart',
        icon: 'cil-chart-pie'
      },
      {
        _name: 'CSidebarNavTitle',
        _children: ['게시판']
      },
      {
        _name: 'CSidebarNavItem',
        name: '서버실 점검일지(Demo)',
        to: '/dailyInspection',
        icon: 'cil-pencil'
      },
      {
        _name: 'CSidebarNavItem',
        name: 'SE 월간회의 (Demo)',
        to: '/seMonthConf',
        icon: 'cil-people'
      },
    ]
  }
]