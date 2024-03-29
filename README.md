# choshsh-ui

하나의 UI에서 여러 오픈소스의 데이터를 보기 위해 시작한 애플리케이션입니다.
Web 콘솔을 제공하는 오픈소스 S/W들이 많습니다. 하지만 사용하는 오픈소스의 수가 많아지면서 브라우저 탭을 여러개 열고 사용하는게 번거롭고 귀찮았습니다.

아래 2가지를 사용하여 구현했습니다.

- Vue.js (+ [CoreUI](https://coreui.io/docs/getting-started/introduction/) 템플릿)
- Spring Boot

## API 문서

- [https://choshsh.github.io/choshsh-ui/](https://choshsh.github.io/choshsh-ui/)

## UI 샘플

### 부하테스트 (Jenkins + Grafana)

[jenkins-api-springboot](https://github.com/choshsh/jenkins-api-springboot) 애플리케이션의 API를 사용하여
jenkins 빌드를 트리거합니다. 테스트 결과는 아티팩트로 저장합니다.

![https://user-images.githubusercontent.com/40452325/139435682-a18ef1e4-10d2-42b9-b114-1e9249d31bc0.gif](https://user-images.githubusercontent.com/40452325/139435682-a18ef1e4-10d2-42b9-b114-1e9249d31bc0.gif)

### 모니터링

**grafana 대시보드**

![https://user-images.githubusercontent.com/40452325/139429434-bd9c0018-d5b2-4680-9a9e-200f3967671e.png](https://user-images.githubusercontent.com/40452325/139429434-bd9c0018-d5b2-4680-9a9e-200f3967671e.png)

## **How to use**

1. Run grafana, prometheus, app (with docker-compose)

    ```bash
    docker-compose up
    ```

2. Open on browser (auth: choshsh/choshsh)

   http://localhost:8080


3. Check API docs on local

   http://localhost:8080/swagger-ui/index.html

## **How to run for dev**

1. Run grafana, prometheus

   ```bash
   docker-compose up -d grafana prometheus
   ```
2. Run backend - Spring Boot app

    ```bash
    ./gradlew bootRun
    ```

3. Run frontend - Vue.js

    ```bash
    cd front-end
    yarn install --frozen-lockfile
    yarn serve
    ```

4. Auth

    ```bash
    # For web and grafana
    choshsh / choshsh
    ```