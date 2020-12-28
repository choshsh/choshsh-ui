/* 
Navigation sidebar
 */
INSERT INTO nav (id, _name, name, to, icon) VALUES
     ('1', 'CSidebarNavItem', 'Dashboard', '/dashboard', 'cil-speedometer'),
     ('2', 'CSidebarNavItem', 'Physical server', '/servers', 'cil-laptop'),
     ('3', 'CSidebarNavItem', 'Virtual server', '/vms', 'cil-cloud'),
     ('4', 'CSidebarNavItem', 'Backup', '/backups', 'cil-data-transfer-down'),
     ('5', 'CSidebarNavItem', 'Chart', '/vmsChart', 'cil-chart-pie'),
     ('6', 'CSidebarNavItem', 'SE Calendar', '/dailyInspection', 'cil-pencil'),
     ('7', 'CSidebarNavItem', 'Meeting', '/seMonthConf', 'cil-people'),
     ('8', 'CSidebarNavItem', 'Settings', '/settings', 'cil-settings');
     

/* 
Header link url
 */
INSERT INTO header (id, url, name) VALUES
     ('1', 'https://github.com/SHsteak', 'SH-github'),
     ('2', 'https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d', 'SH-notion');     