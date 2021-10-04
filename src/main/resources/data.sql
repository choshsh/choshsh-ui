INSERT INTO header (url, name, sort, is_blank) VALUES
     ('https://github.com/choshsh', 'SH-GitHub', 1, 'Y'),
     ('https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d', 'SH-Notion', 2, 'Y'),
     ('http://jenkins.choshsh.com', 'Jenkins', 3, 'Y'),
     ('/settings', '설정', 99, 'N');

INSERT INTO iframe (category, url, name, height, sort) VALUES
     ('log', 'http://grafana.choshsh.com/d/XeS0yvN7z/kube-all-resource-log?orgId=1', 'ALL', '1100', 1),
     ('log', 'http://grafana.choshsh.com/d/JqglEFN7z/kube-control-plane-log?orgId=1', 'Control Plane', '1000', 2),
     ('metric', 'http://grafana.choshsh.com/d/dn42GQH7z/linux?orgId=1', 'Node', '2200', 1),
     ('metric', 'http://grafana.choshsh.com/d/U8-JyvH7k/choshsh-pod-metric?orgId=1', 'Pod', '1000', 2),
     ('loadtest', 'http://grafana.choshsh.com/d/U8-JyvH7k/choshsh-pod-metric?orgId=1', 'from', '1200', 1),
     ('loadtest', 'http://grafana.choshsh.com/d/U8-JyvH7k/choshsh-pod-metric?orgId=1', 'to', '1200', 1);

INSERT INTO user (id, user_id, user_name, user_pw, role) VALUES
     ('1', 'choshsh', '조상현', 'choshsh', 'ADMIN'),
     ('2', 'viewer', 'viewer', 'viewer', 'GUEST');

INSERT INTO envvar (id, key, value) VALUES
     ('1', 'LOADTEST_JENKINS_URL', 'http://jenkins.choshsh.com'),
     ('2', 'LOADTEST_GIT_URL', 'https://github.com/choshsh/devops-study/tree/master/script/loadtest'),
     ('3', 'LOADTEST_JOB', 'load-test');


