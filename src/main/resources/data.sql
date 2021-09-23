INSERT INTO header (url, name, sort, is_blank) VALUES
     ('https://github.com/choshsh', 'SH-GitHub', 1, 'Y'),
     ('https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d', 'SH-Notion', 2, 'Y'),
     ('https://jenkins.choshsh.com', 'Jenkins', 3, 'Y'),
     ('/settings', '설정', 99, 'N');

INSERT INTO iframe (category, url, name, height, sort) VALUES
     ('log', 'http://loki-grafana.choshsh.com/d/XeS0yvN7z/loki-dashboard?orgId=1&refresh=5s&from=now-30m&to=now', 'Loki', '1500', 1),
     ('metric', 'http://loki-grafana.choshsh.com/d/U8-JyvH7k/choshsh-pod-metric?orgId=1', 'Pod', '1000', 2),
     ('metric', 'http://loki-grafana.choshsh.com/d/1XRIUDN7k/linux?orgId=1&from=now-30m&to=now', 'Node', '2200', 1);

INSERT INTO editor (title, keyword, content, reg_date) VALUES
     ('Sample', 'Basic, Intro', '<h3>Hi, this is Cho Sanghyun.</h3><p>&nbsp;</p><p>This is a sample of editor.</p><p>&nbsp;</p><p>My blog url below.</p><figure class="table"><table><tbody><tr><td>Github</td><td>https://github.com/SHsteak</td></tr><tr><td>Notion</td><td>https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d</td></tr></tbody></table></figure><p>&nbsp;</p><p>Thank you.</p>', now());

INSERT INTO user  (id, user_id, user_name, user_pw, role) VALUES
     ('1', 'choshsh', '조상현', 'choshsh', 'ADMIN');
