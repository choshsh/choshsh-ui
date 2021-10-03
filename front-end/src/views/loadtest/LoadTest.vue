<template>
  <div>
    <!-- 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />

    <CCard>
      <CCardBody>
        <!-- 상단 버튼 -->
        <TopButton :isNew="true" @modalHandler="modalHandler" />

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
                      <a
                        :href="
                          jenkinsURL +
                            '/job/' +
                            jenkinsJob +
                            '/' +
                            item.buildNumber +
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
                </td>
              </template>
            </CDataTable>
          </CCardBody>
        </CCard>
      </CCardBody>
    </CCard>

    <!-- 상세 모달 -->
    <div>
      <LoadTestForm ref="loadTestForm" />
    </div>
  </div>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import TopButton from "@/views/base/TopButton";
import ToasterCustom from "../base/ToasterCustom";
import LoadTestForm from "./LoadTestForm";

const fields = [
  { key: "result", label: "결과", _style: "width:7%" },
  { key: "title", label: "제목", _style: "width:15%" },
  { key: "jobName", label: "Job", _style: "width:15%" },
  { key: "buildNumber", label: "빌드번호", _style: "width:10%" },
  { key: "locustEnv", label: "대상환경", _style: "width:10%" },
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
      vms: [],
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
    // 데이터 설정
    async setData() {
      let data = await axios.get("/jenkins/build");
      console.log(data);
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
    // 환경변수 값 가져오기
    async setEnv() {
      let data = await axios.get(urls.admin.env + "/LOADTEST_JENKINS_URL");
      this.jenkinsURL = data.value;
      data = await axios.get(urls.admin.env + "/LOADTEST_JOB");
      this.jenkinsJob = data.value;
    },
    // 상세 페이지로 이동
    routeToInfo(id) {
      this.$router.push({
        path: "/loadTestInfo",
        query: { id: id },
      });
    },
  },

  created() {
    this.setEnv();
    this.setData();
  },
};
</script>
