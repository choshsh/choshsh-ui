INSERT INTO nav (_name, name, url, icon) VALUES
     ('CSidebarNavItem', 'Server', '/server', 'cil-laptop'),
     ('CSidebarNavItem', 'Monitoring', '/monitoring', 'cil-monitor'),
     ('CSidebarNavItem', 'Calendar', '/calendar', 'cil-calendar'),
     ('CSidebarNavItem', 'Editor', '/editor', 'cil-pencil'),
     ('CSidebarNavItem', 'Settings', '/settings', 'cil-settings');


INSERT INTO header (url, name) VALUES
     ('https://github.com/SHsteak', 'SH-github'),
     ('https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d', 'SH-notion');

INSERT INTO monitoring (url, name, height) VALUES
     ('http://choshsh.koreacentral.cloudapp.azure.com:3000/d/hb7fSE0Zz/choshsh-monitoring-sample?orgId=1&refresh=1m', 'Grafana', '2200'),
     ('http://choshsh.koreacentral.cloudapp.azure.com:3000/d/Vw_UfM-Gk/alert?orgId=1&refresh=1m', 'Grafana-Alert', '1000'),
     ('http://choshsh.koreacentral.cloudapp.azure.com:5602/app/dashboards#/view/a9dbe9c0-50b0-11eb-b4f7-fdd31496e7d4?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3Anow-15m%2Cto%3Anow))&show-query-input=true', 'Kibana', '1500');

INSERT INTO dept (dept_cd, dept_nm) VALUES
     ('D001', 'Team-01'),
     ('D002', 'Team-02');

INSERT INTO location (location_cd, location_nm) VALUES
     ('L001', 'Seoul'),
     ('L002', 'Gumi');

INSERT INTO opr (opr_cd, opr_nm) VALUES
     ('O001', 'Using'),
     ('O002', 'Keep'),
     ('O003', 'Disposal');

INSERT INTO usage (usage_cd, usage_nm) VALUES
     ('U001', 'OP'),
     ('U002', 'DEV'),
     ('U003', 'OLD');

INSERT INTO os (os_cd, os_nm, os_detail) VALUES
     ('OS001', 'Windows', 'Windows Server 2016 Standard'),
     ('OS002', 'Windows', 'Windows Server 2019 Standard'),
     ('OS003', 'Linux', 'CentOS-8.3.2011'),
     ('OS004', 'Linux', 'SUSE Linux Enterprise Server 12 SP5'),
     ('OS005', 'Storage', '-');

INSERT INTO server (server_nm, hostname, dept_cd, mng_empl_nm, location_cd, location_detail, opr_cd, usage_cd, ip_addr, os_cd, maker_nm, maker_model_nm, warranty, buy_date, price, serial, spec) VALUES
     ('2F Server Room #1', 'server-01-op', 'D001', 'Cho Sanghyun', 'L001', '7F Server Room', 'O001', 'U001', '123.123.123.1', 'OS002', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN01', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('Sample Server #2', 'server-02-op', 'D001', 'Cho Sanghyun', 'L001', '7F Server Room', 'O001', 'U002', '123.123.123.2', 'OS002', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN02', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('Sample Server #3', 'server-03-op', 'D001', 'Cho Sanghyun', 'L001', '7F Server Room', 'O002', 'U003', '123.123.123.3', 'OS003', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN03', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('Sample Server #4', 'server-04-op', 'D001', 'Cho Sanghyun', 'L001', '7F Server Room', 'O001', 'U001', '123.123.123.4', 'OS004', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN04', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('Sample Server #5', 'server-05-op', 'D001', 'Cho Sanghyun', 'L001', '7F Server Room', 'O002', 'U002', '123.123.123.5', 'OS001', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN05', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('Sample Server #6', 'server-06-op', 'D001', 'Cho Sanghyun', 'L001', '7F Server Room', 'O001', 'U001', '123.123.123.6', 'OS002', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN06', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('Sample Server #7', 'server-07-op', 'D002', 'Cho Sanghyun', 'L002', '2F Server Room', 'O001', 'U001', '124.123.123.7', 'OS004', 'DELL', 'PowerEdge T630', '3', '2020-01-03', '50000', 'SN07', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB * 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('Sample Server #8', 'server-08-op', 'D002', 'Cho Sanghyun', 'L002', '2F Server Room', 'O003', 'U002', '124.123.123.8', 'OS002', 'DELL', 'PowerEdge T630', '3', '2020-01-03', '50000', 'SN08', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB * 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('Sample Server #9', 'server-09-op', 'D002', 'Cho Sanghyun', 'L002', '2F Server Room', 'O001', 'U001', '124.123.123.9', 'OS002', 'DELL', 'PowerEdge T630', '3', '2020-01-03', '50000', 'SN09', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB * 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('Sample Storage #1', 'storage-01', 'D001', 'Cho Sanghyun', 'L001', '7F Server Room', 'O001', 'U001', '125.123.123.10', 'OS005', 'HP', 'HPE Nimble Storage', '3', '2020-01-03', '50000', 'SN10', 'SSD 1TB * 12EA / SAN port * 4EA');

INSERT INTO editor (title, keyword, content, reg_date) VALUES
     ('Sample', 'Basic, Intro', '<h3>Hi, this is Cho Sanghyun.</h3><p>&nbsp;</p><p>This is a sample of editor.</p><p>&nbsp;</p><p>My blog url below.</p><figure class="table"><table><tbody><tr><td>Github</td><td>https://github.com/SHsteak</td></tr><tr><td>Notion</td><td>https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d</td></tr></tbody></table></figure><p>&nbsp;</p><p>Thank you.</p>', now());

INSERT INTO calendar (calendar_date, start_time, end_time, is_error, reg_user, comment) VALUES
     ('2021-03-01', '16:37', '16:40', 'Y', 'Cho Sanghyun', ''),
     ('2021-03-02', '19:00', '19:30', 'Y', 'Cho Sanghyun', 'Study');

INSERT INTO calendar_child  (calendar_date, server_id, comment) VALUES
     ('2021-03-01', '1', 'Disk Fault 1EA'),
     ('2021-03-02', '3', 'Memory Warning 1EA'),
     ('2021-03-02', '4', 'Critical error');

INSERT INTO user  (user_no, user_id, user_pw) VALUES
     ('1', 'choshsh', 'choshsh');
