## 목표

- 모니터링 UI 통합 (Grafana, Loki)
- Jenkins CI 통합

## 개발환경

- Spring Boot
- Vue.js

## Build

- 기본

  1. npm build

  ```bash
  cd <project_home>/front-end
  npm i
  ```

  2. gradle build

  ```bash
  chmod +x gradlew
  ./gradlew bootJar
  ```

- 컨테이너
```bash
./gradlew jib
```