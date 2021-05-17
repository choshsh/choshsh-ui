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

INSERT INTO server (server_nm, hostname, mng_empl_nm, ip_addr, maker_nm, maker_model_nm, warranty, buy_date, price, spec, os, location, server_usage, opr) VALUES
     ('Server Room #1', 'server-01-op', 'Cho Sanghyun', '123.123.123.1', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter', 1, 0, 0, 0),
     ('Sample Server #2', 'server-02-op', 'Cho Sanghyun', '123.123.123.2', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter', 1, 0, 0, 0),
     ('Sample Server #3', 'server-03-op', 'Cho Sanghyun', '123.123.123.3', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter', 1, 0, 1, 1),
     ('Sample Server #4', 'server-04-op', 'Cho Sanghyun', '123.123.123.4', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'Intel Xeon Gold 6328H / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter', 1, 1, 2, 2);

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
