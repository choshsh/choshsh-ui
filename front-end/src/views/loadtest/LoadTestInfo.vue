<template>
  <div>
    <!-- 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />

    <CCard>
      <CCardBody>
        <TopButton :isNew="true" @modalHandler="modalHandler" />
        <!-- 상단 버튼 -->

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

        <!-- 데이터 목록 -->
        <CCard>
          <CCardBody>
            <CDataTable
              :items="jobs"
              :fields="fields"
              :items-per-page="10"
              :itemsPerPageSelect="{ values: [10, 20, 50, 100] }"
              :pagination="{ align: 'center' }"
              :table-filter="{ label: '검색', placeholder: '검색어 입력...' }"
              :loading="loading"
              :size="'sm'"
              hover
              sorter
            >
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
                      <span class="badge badge-light badge-pill"> {{ s }}</span>
                    </li>
                  </ul>
                </td>
              </template>
              <template #nickname="{ item }">
                <td>
                  <a
                    href="javascript:;"
                    class="link-primary"
                    @click="modalHandler(item.id)"
                  >
                    {{ item.nickname }}
                  </a>
                </td>
              </template>

              <template #vmPower="{ item }">
                <td>
                  <h5>
                    <CBadge :color="getBadge(item.vmPower)">{{
                      item.vmPower
                    }}</CBadge>
                  </h5>
                </td>
              </template>

              <!-- <template #licenseUseCount="{ item }">
          <td>
            <a
              href="javascript:;"
              class="link-primary"
              @click="openLicenseModal('vm', item.id)"
            >
              {{ item.licenseUseCount ? item.licenseUseCount : 0 }}
            </a>
          </td>
        </template> -->
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
  { key: "title", label: "제목", _style: "width:15%" },
  { key: "buildNumber", label: "빌드번호", _style: "width:10%" },
  { key: "jobName", label: "Job", _style: "width:15%" },
  { key: "params", label: "파라미터", _style: "width:20%" },
  { key: "regDate", label: "시작일자", _style: "width:70px" },
  { key: "duration", label: "소요시간", _style: "width:70px" },
  { key: "result", label: "결과", _style: "width:80px" },
];

export default {
  name: "Vms",
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
    };
  },
  methods: {
    getBadge(vmPower) {
      return vmPower === "ON"
        ? "success"
        : vmPower === "OFF"
        ? "danger"
        : "primary";
    },
    // 데이터 설정
    async setData() {
      let data = await axios.get("/jenkins/job");
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
    formatParams(ks, vs) {
      return ks.map((v, i) => v + " = " + vs[i]);
    },
  },

  created() {
    this.setData();
    alert(this.$route.query.buildNumber);
  },
};
</script>
