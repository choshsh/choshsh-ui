# ITSM-View

## 1. 목표

- SE 자원 관리
- SE 업무 효율화

## 2. 환경

- Docker 환경
- Front-End
- Vue.js
- Back-End
- Spring 2.3.2
- openjdk 14.0.2
- 웹서버 / WAS / DB
- nginx 1.19.3 / tomcat 10 / mysql 8
- CI/CD
- gitlab-runner(shell)

## 3. 구조

docker/
├── backups
│   ├── full_backup_20201028.sql
│   ├── full_backup_20201029.sql
│   ├── full_backup_20201030.sql
│   ├── full_backup_20201031.sql
│   ├── full_backup_20201101.sql
│   ├── full_backup_20201102.sql
│   ├── full_backup_20201103.sql
│   ├── log
│   ├── start-backup.sh
│   └── start-restore.sh
├── config
│   ├── gitlab-runner
│   │   ├── auto-deploy.sh
│   │   ├── config.toml
│   │   └── deploy.sh
│   ├── nginx
│   │   ├── default.conf
│   │   ├── nginx.conf
│   │   ├── web-lb.conf
│   │   ├── web-was1.conf
│   │   └── web-was2.conf
│   └── was
│       ├── app.jar
│       └── Dockerfile
├── docker-compose.yml
└── images
    ├── gitlab-runner.tar
    ├── mysql.tar
    ├── nginx.tar
    ├── node-exporter.tar
    ├── openjdk-14.0.2.tar
    └── portainer-ce.tar
       
## 4. docker-compose

```yaml
version: '3.8'

services:
  web:
    image: nginx
    container_name: web
    restart: always
    ports:
      - 80:80
    volumes:
      - /root/docker/config/nginx/nginx.conf:/etc/nginx/nginx.conf:ro
      - /root/docker/config/nginx/default.conf:/etc/nginx/conf.d/default.conf:ro
      - /usr/share/zoneinfo/Asia/Seoul:/etc/localtime:ro

  was1:
    build:
      context: /root/docker/config/was
      dockerfile: Dockerfile
      args:
        - JAR_FILE=*.jar
    image: 'choshsh/itsmv'
    container_name : was1
    restart: always
    volumes:
      - /usr/share/zoneinfo/Asia/Seoul:/etc/localtime:ro
    command:
      - --spring.profiles.active=prod

  was2:
    image: 'choshsh/itsmv'
    container_name : was2
    restart: always
    volumes:
      - /usr/share/zoneinfo/Asia/Seoul:/etc/localtime:ro
    command:
      - --spring.profiles.active=prodSlave

  db:
    image: mysql
    container_name: mysql
    restart: always
    ports:
      - 3306:3306
    environment:
      MYSQL_ROOT_PASSWORD: P@ssw0rd!@#
      MYSQL_INITDB_SKIP_TZINFO: 1
    command:
      - --lower_case_table_names=1
    volumes:
      - /usr/share/zoneinfo/Asia/Seoul:/etc/localtime:ro
      - /Data/mysql:/var/lib/mysql:rw

  gitlab-runner:
    image: gitlab/gitlab-runner
    container_name: gitlab-runner
    restart: always
    volumes:
      - /root/docker/config/gitlab-runner:/etc/gitlab-runner
      - /var/run/docker.sock:/var/run/docker.sock
      - /root/docker/config/gitlab-runner/deploy.sh:/tmp/deploy.sh
        #      - /root/docker/config/gitlab-runner/id_rsa:/home/gitlab-runner/.ssh/id_rsa
        #- /root/docker/config/gitlab-runner/id_rsa.pub:/home/gitlab-runner/.ssh/id_rsa.pub
    environment:
      TZ: Asia/Seoul
```
