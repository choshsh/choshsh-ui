INSERT INTO header (url, name, sort, is_blank)
VALUES
     ('https://github.com/choshsh', 'SH-GitHub', 1, 'Y'),
     ('https://www.notion.so/choshsh/9019293e1d86475fb19fe0a5d1149f0d', 'SH-Notion', 2, 'Y'),
     ('http://jenkins.choshsh.com', 'Jenkins', 3, 'Y'),
     ('http://jaeger.choshsh.com', 'Jaeger', 4, 'Y'),
     ('/settings', '설정', 99, 'N');

INSERT INTO iframe (category, url, name, height, tooltip, sort)
VALUES
     ('log', 'http://localhost:3000/d/XeS0yvN7z/kube-all-resource-log?orgId=1', 'ALL', '1100', null,1),
     ('log', 'http://localhost:3000/d/JqglEFN7z/kube-control-plane-log?orgId=1', 'Control Plane', '1000', 'API서버, 컨트롤러 매니저, 스케줄러, etcd, CoreDNS, kube-proxy', 2),
     ('metric', 'http://localhost:3000/d/4b545447f/kubernetes-all-in-one-cluster-monitoring-kr?orgId=1', 'Kubernetes', '1800', null, 1),
     ('metric', 'http://localhost:3000/d/LJ_uJAvmk/istio-service-dashboard?orgId=1', 'Istio', '2300', 'Istio 네트워크 모니터링', 2),
     ('metric', 'http://localhost:3000/d/ku9BHmOnz/jvm-micrometer?orgId=1', 'JVM', '3300', 'Prometheus Kubernetes SD를 위한 Annotation이 있는 JVM 앱 모니터링', 3),
     ('loadtest', 'http://localhost:3000/d/U8-JyvH7k/choshsh-pod-metric?orgId=1', 'from', '1200', null, 1),
     ('loadtest', 'http://localhost:3000/d/U8-JyvH7k/choshsh-pod-metric?orgId=1', 'to', '1200', null, 2);

INSERT INTO users (user_id, user_name, user_pw, role)
VALUES
     ('choshsh', '조상현', 'choshsh', 'ADMIN'),
     ('viewer', 'viewer', 'viewer', 'GUEST');

INSERT INTO envvar (env_key, env_value)
VALUES
     ('LOADTEST_JENKINS_URL', 'http://jenkins.choshsh.com'),
     ('LOADTEST_GIT_URL', 'https://github.com/choshsh/devops-study/tree/master/script/loadtest'),
     ('LOADTEST_JOB', 'load-test'),
     ('PROMETHEUS_URL', 'http://127.0.0.1:9090');


