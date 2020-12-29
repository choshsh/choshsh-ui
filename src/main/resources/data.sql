/* 
Navigation sidebar
 */
INSERT INTO nav (id, _name, name, to, icon) VALUES
     ('1', 'CSidebarNavItem', 'Dashboard', '/dashboard', 'cil-speedometer'),
     ('2', 'CSidebarNavItem', 'Monitoring', '/monitoring', 'cil-monitor'),
     ('3', 'CSidebarNavItem', 'Physical server', '/servers', 'cil-laptop'),
     ('4', 'CSidebarNavItem', 'Virtual server', '/vms', 'cil-cloud'),
     ('5', 'CSidebarNavItem', 'Backup', '/backups', 'cil-data-transfer-down'),
     ('6', 'CSidebarNavItem', 'Chart', '/vmsChart', 'cil-chart-pie'),
     ('7', 'CSidebarNavItem', 'SE Calendar', '/dailyInspection', 'cil-pencil'),
     ('8', 'CSidebarNavItem', 'Meeting', '/seMonthConf', 'cil-people'),
     ('9', 'CSidebarNavItem', 'Settings', '/settings', 'cil-settings');
     

/* 
Header link url
 */
INSERT INTO header (id, url, name) VALUES
     ('1', 'https://github.com/SHsteak', 'SH-github'),
     ('2', 'https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d', 'SH-notion');     
