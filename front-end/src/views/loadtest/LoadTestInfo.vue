<template>
  <div>
    <!-- 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />
    <CCard>
      <CCardBody>
        <CRow>
          <CCol col="9">
            <!-- 상단 빌드 정보 -->
            <CCard>
              <CCardHeader>빌드 정보</CCardHeader>
              <CCardBody>
                <p class="font-weight-bold">
                  {{ jenkinsEntity.title }} - [{{
                    jenkinsEntity.jobName + " #" + jenkinsEntity.buildNumber
                  }}]
                </p>
                <CListGroup horizontal>
                  <CListGroup class="list-group-item">
                    <h5 v-if="jenkinsEntity.result" class="ml-3">
                      <CBadge :color="getBadge(jenkinsEntity.result)">
                        {{ jenkinsEntity.result }}
                      </CBadge>
                    </h5>
                    <CSpinner v-else color="info" size="sm" class="ml-3" />
                  </CListGroup>
                  <CListGroup
                    v-for="(s, index) in formatParams"
                    v-bind:key="index"
                    class="list-group-item"
                  >
                    <span class="badge badge-light ml-3"> {{ s }}</span>
                  </CListGroup>
                </CListGroup>
              </CCardBody>
            </CCard>
          </CCol>
          <CCol col="3">
            <CCard>
              <CCardHeader>부하테스트 결과 CSV 파일</CCardHeader>
              <CCardBody>
                <ul
                  style="list-style: none; -webkit-padding-start:0px"
                  v-bind:key="resultKey"
                >
                  <li
                    v-for="(s, index) in jenkinsEntity.artifacts"
                    v-bind:key="index"
                  >
                    <a :href="resultLink(s)">
                      <span class="badge badge-light badge-pill text-primary">
                        {{ s }}
                      </span>
                    </a>
                  </li>
                </ul>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
        <!-- grafana embed iframe -->
        <CRow>
          <CCol :col="jenkinsEntity.locustEnv === 'I' ? 6 : 12">
            <CCard>
              <CCardHeader>
                <h5 class="text-center">From (Jenkins namespace)</h5>
              </CCardHeader>
              <CCardBody>
                <iframe
                  :src="iframe.from.url"
                  class="embed-responsive-item"
                  :height="iframe.from.height"
                  width="100%"
                  allowfullscreen
                  frameborder="0"
                  scrolling="no"
                />
              </CCardBody>
            </CCard>
          </CCol>
          <CCol v-if="jenkinsEntity.locustEnv === 'I'" col="6">
            <CCard>
              <CCardHeader>
                <h5 class="text-center">To</h5>
              </CCardHeader>
              <CCardBody>
                <iframe
                  :src="iframe.to.url"
                  class="embed-responsive-item"
                  :height="iframe.to.height"
                  width="100%"
                  allowfullscreen
                  frameborder="0"
                  scrolling="no"
                />
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CCardBody>
    </CCard>
  </div>
</template>

<script>
import * as jenkinsService from "@/api/jenkins";
import * as adminService from "@/api/admin";
import TopButton from "@/views/base/TopButton";
import ToasterCustom from "../base/ToasterCustom";

export default {
  name: "Vms",

  components: { TopButton, ToasterCustom },

  data() {
    return {
      toaster: {
        number: 0,
        msg: "",
      },
      jenkinsEntity: {},
      iframe: {
        from: {},
        to: {},
        log: {},
      },
      formattedParams: [],
      resultKey: 0,
    };
  },

  methods: {
    // 결과 뱃지
    getBadge(result) {
      if (result === "SUCCESS") return "success";
      if (result === "FAILURE") return "danger";
      if (result === "ABORTED") return "secondary";
    },
    // 토스트 컨트롤
    toastHandler(msg) {
      this.toaster.msg = msg;
      this.toaster.number++;
    },
    // 데이터 설정
    async setData(id) {
      let data = await jenkinsService.getBuild(id);
      this.jenkinsEntity = data;
      // grafana 대시보드 로드
      this.setGrafanaIframe();
      // result가 없을 때
      if (!this.jenkinsEntity.result) {
        this.toastHandler("빌드가 진행 중이에요.");
        const wait = (timeToDelay) =>
          new Promise((resolve) => setTimeout(resolve, timeToDelay));
        while (!this.jenkinsEntity.result) {
          data = await jenkinsService.getBuild(id);
          this.jenkinsEntity = data;
          await wait(2000);
        }
        await wait(1000);
        this.jenkinsEntity = data;
        this.toastHandler("빌드가 완료됐어요.");
        this.resultKey++;
      }
    },

    // iframe 설정
    async setGrafanaIframe() {
      let fromTime = this.jenkinsEntity.timestamp;
      let toTime = this.jenkinsEntity.duration
        ? this.jenkinsEntity.timestamp + this.jenkinsEntity.duration
        : "";

      let iframeData = await adminService.getIframe("loadtest");
      this.setGrafanaFrom(iframeData, fromTime, toTime);
      // 클러스터 내부 테스트 시에만
      if (this.jenkinsEntity.locustEnv === "I") {
        this.setGrafanaTo(iframeData, fromTime, toTime);
      }
    },
    // From iframe 설정
    setGrafanaFrom(iframeData, fromTime, toTime) {
      let from = iframeData.find((obj) => obj.name === "from");
      this.iframe.from = from;
      this.iframe.from.url =
        from.url +
        "&refresh=10s&var-namespace=jenkins" +
        "&from=" +
        fromTime +
        "&to=" +
        toTime;
    },
    // To iframe 설정
    setGrafanaTo(iframeData, fromTime, toTime) {
      let to = iframeData.find((obj) => obj.name === "to");
      this.iframe.to = to;
      let host = this.jenkinsEntity.paramValues[
        this.jenkinsEntity.paramKeys.indexOf("host")
      ];

      if (host.indexOf("https://") > -1) host = host.replace("https://", "");
      if (host.indexOf("http://") > -1) host = host.replace("http://", "");
      let podName = host.split(".")[0];
      let namespace = host.split(".")[1];
      this.iframe.to.url =
        to.url +
        "&refresh=10s&var-namespace=" +
        namespace +
        "&var-pod=" +
        podName +
        "&from=" +
        fromTime +
        "&to=" +
        toTime;
    },
  },

  computed: {
    // 파라미터 formatter
    formatParams() {
      if (typeof this.jenkinsEntity.paramKeys === "object") {
        return this.jenkinsEntity.paramKeys.map(
          (v, i) => v + " = " + this.jenkinsEntity.paramValues[i]
        );
      }
    },
    resultLink() {
      return (s) =>
        this.$store.getters["env/getEnv"]("LOADTEST_JENKINS_URL") +
        "/job/" +
        this.$store.getters["env/getEnv"]("LOADTEST_JOB") +
        "/" +
        this.jenkinsEntity.buildNumber +
        "/artifact/" +
        s;
    },
  },

  created() {
    if (this.$route.query.id > 0) {
      this.setData(this.$route.query.id);
    }
  },
};
</script>
