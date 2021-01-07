# 목표

- SE 자원 관리
- SE 업무 효율화

# 프로젝트 정보

### **Application**

- Spring Boot 2.4.1
- OpenJDK 15.0.1
- H2 Database
- MySQL
- Vue.js

# 사용법

## Build

### 1. 소스 빌드

VueJS를 사용하기 때문에 Node 설치가 필수입니다.

```bash
cd <project_home>

gradle bootJar

# /build/lib/ 디렉토리에 jar파일이 생성된다.
```

### 2. 도커 이미지 빌드

```bash
docker build -t <태그명> .
```

## Run

- jar

    ```bash
    java -jar itsmv-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
    ```

- docker

    ```bash
    docker run --name itsmv -p 8080:8080 <이미지명> --spring.profiles.active=dev
    ```

---

## Confirm

[http://localhost:8080](http://localhost:8080) 에 접속

![img](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/dce79e41-b884-496e-b579-a576513599d5/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210107%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210107T133740Z&X-Amz-Expires=86400&X-Amz-Signature=24bbf57079c764ade939cf6fad7df92ed3afc586b9a7003c14b8f4509261fe99&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22)
