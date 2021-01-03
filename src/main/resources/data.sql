INSERT INTO nav (_name, name, url, icon) VALUES
     --('CSidebarNavItem', 'Dashboard', '/dashboard', 'cil-speedometer'),
     ('CSidebarNavItem', 'Server', '/server', 'cil-laptop'),
     ('CSidebarNavItem', 'Monitoring', '/monitoring', 'cil-monitor'),
     --('CSidebarNavItem', 'Backup', '/backups', 'cil-data-transfer-down'),
     --('CSidebarNavItem', 'Chart', '/vmsChart', 'cil-chart-pie'),
     ('CSidebarNavItem', 'Calendar', '/calendar', 'cil-calendar'),
     ('CSidebarNavItem', 'Editor', '/editor', 'cil-pencil'),
     ('CSidebarNavItem', 'Settings', '/settings', 'cil-settings');


INSERT INTO header (url, name) VALUES
     ('https://github.com/SHsteak', 'SH-github'),
     ('https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d', 'SH-notion');

INSERT INTO monitoring (url, name, height) VALUES
     ('http://choshsh.koreacentral.cloudapp.azure.com:3000/d/hb7fSE0Zz/choshsh-monitoring-sample?orgId=1&refresh=1m', 'Grafana', '2200'),
     ('http://choshsh.koreacentral.cloudapp.azure.com:3000/d/Vw_UfM-Gk/alert?orgId=1&refresh=1m', 'Grafana-Alert', '1000'),
     ('http://choshsh.koreacentral.cloudapp.azure.com:5601/app/dashboards#/view/57e3d5f0-4e0e-11eb-929a-dffa52227728?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!f%2Cvalue%3A30000)%2Ctime%3A(from%3Anow-30m%2Cto%3Anow))&show-query-input=true&hide-filter-bar=true', 'Kibana', '1300');

INSERT INTO dept (dept_cd, dept_nm) VALUES
     ('D001', '정보1팀'),
     ('D002', '정보2팀');

INSERT INTO location (location_cd, location_nm) VALUES
     ('L001', '서울'),
     ('L002', '구미');

INSERT INTO opr (opr_cd, opr_nm) VALUES
     ('O001', '지급사용'),
     ('O002', '보관'),
     ('O003', '폐기대상');

INSERT INTO usage (usage_cd, usage_nm) VALUES
     ('U001', '운영'),
     ('U002', '개발'),
     ('U003', '조회');

INSERT INTO os (os_cd, os_nm, os_detail) VALUES
     ('OS001', 'Windows', 'Windows Server 2016 Standard'),
     ('OS002', 'Windows', 'Windows Server 2019 Standard'),
     ('OS003', 'Linux', 'CentOS-8.3.2011'),
     ('OS004', 'Linux', 'SUSE Linux Enterprise Server 12 SP5'),
     ('OS005', 'Storage', '-');

INSERT INTO server (server_nm, hostname, dept_cd, mng_empl_nm, location_cd, location_detail, opr_cd, usage_cd, ip_addr, os_cd, maker_nm, maker_model_nm, warranty, buy_date, price, serial, spec) VALUES
     ('샘플 서버 #1', 'server-01-op', 'D001', '조상현', 'L001', '본사 7F 서버실', 'O001', 'U001', '123.123.123.1', 'OS002', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN01', 'Intel� Xeon� Gold 6328H Processor / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('샘플 서버 #2', 'server-02-op', 'D001', '조상현', 'L001', '본사 7F 서버실', 'O001', 'U002', '123.123.123.2', 'OS002', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN02', 'Intel� Xeon� Gold 6328H Processor / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('샘플 서버 #3', 'server-03-op', 'D001', '조상현', 'L001', '본사 7F 서버실', 'O002', 'U003', '123.123.123.3', 'OS003', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN03', 'Intel� Xeon� Gold 6328H Processor / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('샘플 서버 #4', 'server-04-op', 'D001', '조상현', 'L001', '본사 7F 서버실', 'O001', 'U001', '123.123.123.4', 'OS004', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN04', 'Intel� Xeon� Gold 6328H Processor / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('샘플 서버 #5', 'server-05-op', 'D001', '조상현', 'L001', '본사 7F 서버실', 'O002', 'U002', '123.123.123.5', 'OS001', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN05', 'Intel� Xeon� Gold 6328H Processor / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('샘플 서버 #6', 'server-06-op', 'D001', '조상현', 'L001', '본사 7F 서버실', 'O001', 'U001', '123.123.123.6', 'OS002', 'HP', 'HPE ProLiant DL360 Gen9', '3', '2020-01-03', '50000', 'SN06', 'Intel� Xeon� Gold 6328H Processor / DDR4-3200 128GB / 500GB / 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('샘플 서버 #7', 'server-07-op', 'D002', '조상현', 'L002', '구미 2F 서버실', 'O001', 'U001', '124.123.123.7', 'OS004', 'DELL', 'PowerEdge T630', '3', '2020-01-03', '50000', 'SN07', 'Intel� Xeon� Gold 6328H Processor / DDR4-3200 128GB / 500GB * 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('샘플 서버 #8', 'server-08-op', 'D002', '조상현', 'L002', '구미 2F 서버실', 'O003', 'U002', '124.123.123.8', 'OS002', 'DELL', 'PowerEdge T630', '3', '2020-01-03', '50000', 'SN08', 'Intel� Xeon� Gold 6328H Processor / DDR4-3200 128GB / 500GB * 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('샘플 서버 #9', 'server-09-op', 'D002', '조상현', 'L002', '구미 2F 서버실', 'O001', 'U001', '124.123.123.9', 'OS002', 'DELL', 'PowerEdge T630', '3', '2020-01-03', '50000', 'SN09', 'Intel� Xeon� Gold 6328H Processor / DDR4-3200 128GB / 500GB * 2EA / RAID 1 / SATA 3.5inch / 5U / 10Gb Ethernet Network Adapter'),
     ('샘플 스토리지 #1', 'storage-01', 'D001', '조상현', 'L001', '본사 7F 서버실', 'O001', 'U001', '125.123.123.10', 'OS005', 'HP', 'HPE Nimble Storage', '3', '2020-01-03', '50000', 'SN10', 'SSD 1TB * 12EA / SAN port * 4EA');

INSERT INTO editor (title, keyword, content, reg_date) VALUES
     ('게시판 시작하기', '기본, 소개', '<h3>안녕하세요, 조상현입니다.</h3><p>&nbsp;</p><p>이것은 간단한 에디터 형태의 게시판입니다.</p><p>&nbsp;</p><p>아래는 제가 열심히 학습하는 내용을 확인할 수 있는 주소입니다.</p><figure class="table"><table><tbody><tr><td>Github 주소</td><td>https://github.com/SHsteak</td></tr><tr><td>포트폴리오 주소</td><td>https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d</td></tr></tbody></table></figure><p>&nbsp;</p><p>감사합니다.</p>', now());

INSERT INTO calendar (calendar_date, start_time, end_time, is_error, reg_user, comment) VALUES
     ('2021-01-01', '16:37', '16:40', 'Y', '조상현', ''),
     ('2021-01-04', '19:00', '19:30', 'Y', '조상현', '공부 중입니다');

INSERT INTO calendar_child  (calendar_date, server_id, comment) VALUES
     ('2021-01-01', '1', '디스크 Fault 1EA'),
     ('2021-01-04', '3', '메모리 Warning 1EA'),
     ('2021-01-04', '4', '크리티컬 에러 (온도)');
