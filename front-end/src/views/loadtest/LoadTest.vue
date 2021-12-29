<template>
  <div>
    <!-- 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />

    <CCard>
      <CCardBody>
        <!-- 상단 버튼 -->
        <CRow class="mb-3">
          <CCol col="9" class="align-self-center">
            <a href="#" @click="flowHandler"
              >부하 테스트 기능은 이렇게 동작해요
            </a>
          </CCol>
          <CCol col="2" class="align-self-center d-flex flex-row-reverse">
            실행해보세요 >>
          </CCol>
          <CCol col="1">
            <CButton block color="primary" @click="modalHandler">
              New
            </CButton>
          </CCol>
        </CRow>

        <!-- 요약 -->
        <CRow>
          <CCol
            col="3"
            v-for="(item, index) in dashboard.data"
            v-bind:key="index"
          >
            <CCard>
              <CCardHeader class="font-weight-bold">{{
                item.label
              }}</CCardHeader>
              <CCardBody>
                <table class="table table-sm table-borderless">
                  <tr v-for="(value, key) in item.list" v-bind:key="key">
                    <td>{{ key }}</td>
                    <td class="text-right">
                      <a
                        href="javascript:;"
                        class="link-primary"
                        @click="changeColumnFilter(item.column, key, dashboard)"
                      >
                        {{ value }}
                      </a>
                    </td>
                  </tr>
                </table>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>

        <!-- 테이블 -->
        <CCard>
          <CCardBody>
            <CDataTable
              :items="jobs"
              :fields="fields"
              :items-per-page="5"
              :itemsPerPageSelect="{ values: [5, 10, 50, 100] }"
              :pagination="{ align: 'center' }"
              :table-filter="{ label: '검색', placeholder: '검색어 입력...' }"
              :loading="loading"
              :size="'sm'"
              sorter
            >
              <!-- 제목 -->
              <template #title="{ item }">
                <td class="font-weight-bold">
                  <a
                    href="javascript:;"
                    @click="routeToInfo(item.id)"
                    class="text-primary"
                  >
                    {{ item.title }}
                  </a>
                </td>
              </template>
              <!-- 파라미터 formatter -->
              <template #params="{ item }">
                <td>
                  <ul style="list-style: none; -webkit-padding-start:0px">
                    <li
                      v-for="(s, index) in formatParams(
                        item.paramKeys,
                        item.paramValues
                      )"
                      v-bind:key="index"
                    >
                      <span class="badge badge-light"> {{ s }}</span>
                    </li>
                  </ul>
                </td>
              </template>
              <!-- 소요시간 formatter -->
              <template #duration="{ item }">
                <td>
                  {{
                    item.duration > 0
                      ? new Date(item.duration).toISOString().substr(11, 8)
                      : "-"
                  }}
                </td>
              </template>
              <!-- result 뱃지 -->
              <template #result="{ item }">
                <td>
                  <h5 v-if="item.result">
                    <CBadge :color="getBadge(item.result)">
                      {{ item.result }}
                    </CBadge>
                  </h5>
                  <CSpinner v-else color="info" size="sm" />
                </td>
              </template>
              <!-- 아티팩트 formatter -->
              <template #artifacts="{ item }">
                <td>
                  <ul style="list-style: none; -webkit-padding-start:0px">
                    <li v-for="(s, index) in item.artifacts" v-bind:key="index">
                      <a :href="resultLink(s, item.buildNumber)">
                        <span class="badge badge-light badge-pill text-primary">
                          {{ s }}
                        </span>
                      </a>
                    </li>
                  </ul>
                </td>
              </template>
            </CDataTable>
          </CCardBody>
        </CCard>
      </CCardBody>
    </CCard>

    <!-- 상세 모달 -->
    <LoadTestForm ref="loadTestForm" />
    <CModal
      color="primary"
      :show.sync="modal.flow.show"
      :closeOnBackdrop="true"
      :size="'lg'"
    >
      <CCard>
        <CCardHeader class="font-weight-bold">GitHub 바로가기</CCardHeader>
        <CCardBody>
          <CListGroup>
            <CListGroupItem>
              <a
                href="https://github.com/choshsh/jenkins-api-springboot"
                target="_black"
              >
                Jenkins-Rest
              </a>
              : Jenkins API 애플리케이션
            </CListGroupItem>
            <CListGroupItem>
              <a
                href="https://github.com/choshsh/devops-study/blob/master/jenkins/pipelines/loadTest"
                target="_black"
              >
                Jenkins Pipeline
              </a>
              : 부하테스트 Pod를 실행하는 jenkins 스크립트
            </CListGroupItem>
            <CListGroupItem>
              <a
                href="https://github.com/choshsh/devops-study/blob/master/jenkins/podTemplates/loadTest.yaml"
                target="_black"
              >
                부하테스트 Pod
              </a>
              : 부하테스트 Pod manifest
            </CListGroupItem>
          </CListGroup>
        </CCardBody>
      </CCard>
      <CCard>
        <CCardHeader class="font-weight-bold">부하테스트 흐름도</CCardHeader>
        <CCardBody>
          <img src="@/assets/images/loadtest-flow.jpg" class="img-fluid" />
        </CCardBody>
      </CCard>
      <div class="text-center row-fluid mb-1 mt-3">
        <button
          id="cancelBtn"
          type="button"
          class="btn btn-secondary"
          @click="flowHandler()"
        >
          닫기
        </button>
      </div>
      <div slot="footer" />
    </CModal>
  </div>
</template>

<script>
import * as jenkinsService from "@/api/jenkins";
import TopButton from "@/views/base/TopButton";
import ToasterCustom from "@/views/base/ToasterCustom";
import LoadTestForm from "./LoadTestForm";

const fields = [
  { key: "result", label: "결과", _style: "width:7%" },
  { key: "title", label: "제목", _style: "width:15%" },
  { key: "jobName", label: "Job", _style: "width:15%" },
  { key: "buildNumber", label: "빌드번호", _style: "width:10%" },
  { key: "locustEnvName", label: "대상환경", _style: "width:10%" },
  { key: "params", label: "파라미터", _style: "width:20%" },
  { key: "regDate", label: "시작시간", _style: "width:12%" },
  { key: "duration", label: "소요시간(HH:mm:ss)", _style: "width:10%" },
  { key: "artifacts", label: "아티팩트(Click)", _style: "width:20%" },
];

export default {
  name: "Load-Test",

  components: { TopButton, ToasterCustom, LoadTestForm },

  data() {
    return {
      fields: fields,
      loading: true,
      alert: {
        color: "",
        msg: "",
        counter: 0,
      },
      jobs: [],
      dashboard: {
        data: [],
        columnFilter: {},
        tableKey: 0,
      },
      toaster: {
        number: 0,
        msg: "",
      },
      modal: {
        flow: {
          show: false,
        },
      },
    };
  },

  methods: {
    // 결과 뱃지
    getBadge(result) {
      if (result === "SUCCESS") return "success";
      if (result === "FAILURE") return "danger";
      if (result === "ABORTED") return "secondary";
    },
    // 데이터 설정
    async setData() {
      let data = await jenkinsService.getBuild();
      this.jobs = data;
      this.loading = false;
    },
    formatParams() {},
    // 상세 모달
    modalHandler() {
      this.$refs.loadTestForm.modalHandler();
    },
    // 알람 컨트롤
    alertHandler(msg) {
      this.toaster.msg = msg;
      this.toaster.number++;
    },
    // 파라미터 formatter
    formatParams(ks, vs) {
      return ks.map((v, i) => v + " = " + vs[i]);
    },
    // 상세 페이지로 이동
    routeToInfo(id) {
      this.$router.push({
        path: "/loadTestInfo",
        query: { id: id },
      });
    },
    // 부하테스트 기능 정보
    flowHandler() {
      this.modal.flow.show = !this.modal.flow.show;
    },
  },

  computed: {
    resultLink() {
      return (resultName, buildNumber) =>
        this.$store.getters["env/getEnv"]("LOADTEST_JENKINS_URL") +
        "/job/" +
        this.$store.getters["env/getEnv"]("LOADTEST_JOB") +
        "/" +
        buildNumber +
        "/artifact/" +
        resultName;
    },
  },

  created() {
    this.setData();
  },
};
</script>
