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

INSERT INTO dept (dept_cd, dept_nm) VALUES
     ('D001', '정보1팀'),
     ('D002', '정보2팀');

INSERT INTO location (location_cd, location_nm) VALUES
     ('L001', '서울'),
     ('L002', '구미');

INSERT INTO opr (opr_cd, opr_nm) VALUES
     ('O001', '지급사용'),
     ('O002', '보관'),
     ('O003', '폐기');

INSERT INTO usage (usage_cd, usage_nm) VALUES
     ('U001', '운영'),
     ('U002', '개발'),
     ('U003', '조회');

INSERT INTO os (os_cd, os_nm, os_detail) VALUES
     ('OS001', 'Windows', 'Windows Server 2016 Standard'),
     ('OS002', 'Windows', 'Windows Server 2019 Standard'),
     ('OS003', 'Linux', 'CentOS-8.3.2011'),
     ('OS004', 'Linux', 'SUSE Linux Enterprise Server 12 SP5');

INSERT INTO server (id, server_nm, hostname, dept_cd, mng_empl_nm, location_cd, location_detail, opr_cd, usage_cd, ip_addr, os_cd, maker_nm, buy_date, price, serial) VALUES
     ('1', '샘플서버 #1', 'sample-01-op', 'D001', '조상현', 'L001', '본사 7F 서버실', 'O001', 'U001', '123.123.123.123', 'OS002', 'HP', '2020-01-03', '50000', 'T012345'),
     ('2', '샘플서버 #2', 'sample-01-op', 'D002', '조상현', 'L002', '구미 2F 서버실', 'O001', 'U001', '124.123.123.123', 'OS003', 'DELL', '2020-01-03', '50000', 'T012346');


