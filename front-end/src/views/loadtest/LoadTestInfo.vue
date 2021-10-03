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
              <CCardBody>
                <p class="font-weight-bold">
                  {{ jenkinsEntity.title }} - [{{
                    jenkinsEntity.jobName + " #" + jenkinsEntity.buildNumber
                  }}]
                </p>
                <ul
                  style="list-style: none; -webkit-padding-start:0px"
                  class="list-group list-group-horizontal"
                >
                  <li class="list-group-item">
                    <h5 v-if="jenkinsEntity.result">
                      <CBadge :color="getBadge(jenkinsEntity.result)">
                        {{ jenkinsEntity.result }}
                      </CBadge>
                    </h5>
                    <CSpinner v-else color="info" size="sm" />
                  </li>
                  <li
                    v-for="(s, index) in formatParams"
                    v-bind:key="index"
                    class="list-group-item"
                  >
                    <span class="badge badge-light"> {{ s }}</span>
                  </li>
                </ul>
              </CCardBody>
            </CCard>
          </CCol>
          <CCol col="3">
            <CCard>
              <CCardBody>
                <ul style="list-style: none; -webkit-padding-start:0px">
                  <li
                    v-for="(s, index) in jenkinsEntity.artifacts"
                    v-bind:key="index"
                  >
                    <a
                      :href="
                        jenkinsURL +
                          '/job/' +
                          jenkinsJob +
                          '/' +
                          jenkinsEntity.buildNumber +
                          '/artifact/' +
                          s
                      "
                    >
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
          <CCol col="4">
            <CCard>
              <CCardHeader>
                <h5 class="text-center">From (jenkins namespace)</h5>
              </CCardHeader>
              <CCardBody>
                <iframe
                  :src="iframe.from.url + '&refresh=5s&var-namespace=jenkins'"
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
          <CCol col="4">
            <CCard>
              <CCardHeader>
                <h5 class="text-center">From (jenkins namespace)</h5>
              </CCardHeader>
              <CCardBody>
                <iframe
                  :src="iframe.from.url + '&refresh=5s&var-namespace=jenkins'"
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
          <CCol col="4">
            <CCard>
              <CCardHeader>
                <h5 class="text-center">From (jenkins namespace)</h5>
              </CCardHeader>
              <CCardBody>
                <iframe
                  :src="iframe.from.url + '&refresh=5s&var-namespace=jenkins'"
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
        </CRow>
      </CCardBody>
    </CCard>
  </div>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
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
      jenkinsURL: "",
      jenkinsJob: "",
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
      let data = await axios.get("/jenkins/build/" + id);
      this.jenkinsEntity = data;
      // result가 없을 때
      if (!this.jenkinsEntity.result) {
        this.toastHandler("빌드가 진행 중이에요.");
        const wait = (timeToDelay) =>
          new Promise((resolve) => setTimeout(resolve, timeToDelay));
        while (!this.jenkinsEntity.result) {
          data = await axios.get("/jenkins/build/" + id);
          this.jenkinsEntity = data;
          if (this.jenkinsEntity.result) {
            this.toastHandler("빌드가 완료됐어요.");
          }
          await wait(2000);
        }
      }
    },

    // 환경변수 값 가져오기
    async setEnv() {
      let data = await axios.get(urls.admin.env + "/LOADTEST_JENKINS_URL");
      this.jenkinsURL = data.value;
      data = await axios.get(urls.admin.env + "/LOADTEST_JOB");
      this.jenkinsJob = data.value;
    },
    // iframe 설정
    async setGrafanaIframe() {
      this.setEnv();
      // time range
      let fromTime = this.jenkinsEntity.timestamp;
      let toTime = this.jenkinsEntity.duration
        ? this.jenkinsEntity.timestamp + this.jenkinsEntity.duration
        : "";

      let data = await axios.get(urls.admin.iframe + "/loadtest");

      // from
      let from = data.find((obj) => obj.name === "from");
      this.iframe.from = from;
      this.iframe.from.url =
        from.url +
        "&refresh=5s&var-namespace=jenkins&from=" +
        fromTime +
        "&to=" +
        toTime;

      // to
      let to = data.find((obj) => obj.name === "to");
      this.iframe.to = to;
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
  },

  created() {
    if (this.$route.query.id > 0) {
      this.setData(this.$route.query.id);
      // grafana 대시보드 로드
      this.setGrafanaIframe();
    }
  },
};
</script>
