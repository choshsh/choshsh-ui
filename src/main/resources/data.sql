INSERT INTO nav (id, _name, name, url, icon) VALUES
     ('1', 'CSidebarNavItem', 'Dashboard', '/dashboard', 'cil-speedometer'),
     ('2', 'CSidebarNavItem', 'Monitoring', '/monitoring', 'cil-monitor'),
     ('3', 'CSidebarNavItem', 'Physical server', '/servers', 'cil-laptop'),
     ('4', 'CSidebarNavItem', 'Virtual server', '/vms', 'cil-cloud'),
     ('5', 'CSidebarNavItem', 'Backup', '/backups', 'cil-data-transfer-down'),
     ('6', 'CSidebarNavItem', 'Chart', '/vmsChart', 'cil-chart-pie'),
     ('7', 'CSidebarNavItem', 'SE Calendar', '/dailyInspection', 'cil-pencil'),
     ('8', 'CSidebarNavItem', 'Meeting', '/seMonthConf', 'cil-people'),
     ('9', 'CSidebarNavItem', 'Settings', '/settings', 'cil-settings');
     

INSERT INTO header (id, url, name) VALUES
     ('1', 'https://github.com/SHsteak', 'SH-github'),
     ('2', 'https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d', 'SH-notion');

INSERT INTO monitoring (id, url, name, height) VALUES
     ('1', 'http://choshsh.koreacentral.cloudapp.azure.com:3000/d/hb7fSE0Zz/choshsh-monitoring-sample?orgId=1&refresh=1m', 'Grafana', '2200'),
     ('2', 'http://choshsh.koreacentral.cloudapp.azure.com:3000/d/Vw_UfM-Gk/alert?orgId=1&refresh=1m', 'Grafana-Alert', '1000'),  
     ('3', 'http://choshsh.koreacentral.cloudapp.azure.com:5601/app/dashboards#/view/e985b760-4d02-11eb-b6c2-270578c3d43b?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3Anow-15m%2Cto%3Anow))&show-query-input=true', 'Kibana', '1300');
