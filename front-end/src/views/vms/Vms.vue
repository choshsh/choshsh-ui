<template>
  <CCard>
    <CCardBody>
      <CRow>
        <CCol col="10" class="mb-3 mb-xl-0"></CCol>
        <CCol col="1" class="mb-3 mb-xl-0">
          <router-link :to="'vmsForm'" v-if="Boolean(userRoleCd === 'M001')">
            <CButton block color="primary">New</CButton>
          </router-link>
        </CCol>
        <CCol col="1" class="mb-3 mb-xl-0">
          <form action="/api/vmExcel" method="POST">
            <CButton :type="'submit'" block color="primary">Excel</CButton>
          </form>
        </CCol>
      </CRow>
      <CRow class="">
        <CCol col="4">
          <CCard>
            <CCardHeader>용도</CCardHeader>
            <CCardBody>
              <CChartDoughnutAdv
                :labels="[
                  '운영: ' + chartDataCnt['usage']['op'],
                  '개발: ' + chartDataCnt['usage']['old'],
                  '조회: ' + chartDataCnt['usage']['dev'],
                ]"
                :datas="[
                  chartDataCnt['usage']['op'],
                  chartDataCnt['usage']['old'],
                  chartDataCnt['usage']['dev'],
                ]" /></CCardBody
          ></CCard>
        </CCol>
        <CCol col="4">
          <CCard>
            <CCardHeader>전원</CCardHeader>
            <CCardBody>
              <CChartDoughnutAdv
                :labels="[
                  'ON: ' + chartDataCnt['power']['on'],
                  'OFF: ' + chartDataCnt['power']['off'],
                ]"
                :datas="[
                  chartDataCnt['power']['on'],
                  chartDataCnt['power']['off'],
                ]" /></CCardBody
          ></CCard>
        </CCol>
        <CCol col="4">
          <CCard>
            <CCardHeader>OS</CCardHeader>
            <CCardBody>
              <CChartDoughnutAdv
                :labels="[
                  '윈도우: ' + chartDataCnt['os']['nt'],
                  '리눅스: ' + chartDataCnt['os']['linux'],
                ]"
                :datas="[
                  chartDataCnt['os']['nt'],
                  chartDataCnt['os']['linux'],
                ]" /></CCardBody
          ></CCard>
        </CCol>
      </CRow>
      <CDataTable
        :items="items"
        :fields="fields"
        :items-per-page="10"
        :table-filter="tableFilter"
        :loading="loading"
        :pagination="pagination"
        :itemsPerPageSelect="{ values: [10, 50, 100, 500] }"
        hover
        column-filter
        sorter
        :key="vmListKey"
      >
        <template #vmPowerOn="{ item }">
          <td>
            <h5>
              <CBadge :color="getBadge(item.vmPowerOn)">{{
                item.vmPowerOn
              }}</CBadge>
            </h5>
          </td>
        </template>
        <!--         <template #vmUsageNm="{item}">
          <td>
            <CBadge :color="getBadge2(item.vmUsageNm)">{{item.vmUsageNm}}</CBadge>
          </td>
        </template>-->
        <template #show_details="{ item, index }">
          <td class="py-2">
            <CButton
              color="primary"
              variant="outline"
              square
              size="sm"
              @click="toggleDetails(item, index)"
              >{{ Boolean(item._toggled) ? "Hide" : "Show" }}</CButton
            >
          </td>
        </template>
        <template #details="{ item }">
          <CCollapse
            :show="Boolean(item._toggled)"
            :duration="collapseDuration"
          >
            <CCardBody>
              <CTabs variant="pills" :vertical="vertical">
                <CTab active>
                  <template slot="title">{{ tabs[0] }}</template>
                  <CCol lg="12">
                    <CCard>
                      <CCardBody>
                        <VmsInfo :item="item" />
                        <div
                          class="text-center row-fluid mb-1 mt-3"
                          v-if="Boolean(userRoleCd === 'M001')"
                        >
                          <router-link
                            :to="{
                              name: 'vmsForm',
                              params: { vmNo: item.vmNo },
                            }"
                          >
                            <CButton class="btn btn-success">수정</CButton>
                          </router-link>
                          <CButton
                            @click="delData(item.vmNo)"
                            class="btn btn-danger ml-3"
                            >삭제</CButton
                          >
                        </div>
                      </CCardBody>
                    </CCard>
                  </CCol>
                </CTab>
              </CTabs>
            </CCardBody>
          </CCollapse>
        </template>
      </CDataTable>
    </CCardBody>
  </CCard>
</template>


<script>
import axios from "axios";
import VmsInfo from "./VmsInfo.vue";
import CChartDoughnutAdv from "../base/CChartDoughnutAdv";

const fields = [
  { key: "vmClusterLocationNm", label: "사업장", _style: "width:110px" },
  { key: "vmNickname", label: "업무명", _style: "min-width:180px" },
  { key: "vmNm", label: "VM명", _style: "min-width:180px" },
  { key: "vmPowerOn", label: "전원", _style: "width:70px" },
  { key: "vmIp", label: "IP", _style: "width:140px" },
  { key: "vmOsLine", label: "OS계열", _style: "width:85px" },
  { key: "vmOsDetail", label: "OS상세", _style: "min-width:150px" },
  { key: "vmClusterMaker", label: "제조사", _style: "width:80px" },
  { key: "vmUsageNm", label: "용도", _style: "width:80px" },
  { key: "vmUseEmpl", label: "담당자", _style: "width:100px" },
  { key: "vmClusterNm", label: "클러스터", _style: "width:120px" },
  {
    key: "show_details",
    label: "",
    _style: "width:1%",
    filter: false,
    sorter: false,
  },
];

export default {
  name: "Vms",
  components: { VmsInfo, CChartDoughnutAdv },
  data() {
    return {
      fields: fields,
      items: [],
      itemsServerChange: {},
      tableFilter: { label: "검색", placeholder: "검색어 입력..." },
      collapseDuration: 0,
      tabs: ["정보"],
      vertical: { vertical: true, navs: "col-md-1", content: "col-md-11" },
      serverChangeKey: 0,
      pagination: { align: "end" },
      loading: true,
      userRoleCd: sessionStorage.getItem("userRoleCd"),
      vmListKey: 0,
      chartDataCnt: { os: {}, power: {}, usage: {} },
    };
  },
  methods: {
    toggleDetails(item) {
      this.$set(this.items[item.id], "_toggled", !item._toggled);
      this.collapseDuration = 300;
      this.$nextTick(() => {
        this.collapseDuration = 0;
      });
    },
    getBadge(vmPowerOn) {
      return vmPowerOn === "ON"
        ? "success"
        : vmPowerOn === "OFF"
        ? "danger"
        : "primary";
    },
    setData() {
      axios
        .get("/api/vms")
        .then((res) => {
          this.items = res.data.map((item, id) => {
            for (const key in item)
              if (item.hasOwnProperty(key))
                [(item[key] = !item[key] ? "-" : item[key])];
            return { ...item, id };
          });
          this.setChartData();
          this.loading = false;
        })
        .catch((e) => console.log(e));
    },
    setChartData() {
      this.chartDataCnt["os"] = {
        nt: this.items.filter((o) => o.vmOsLine === "NT").length,
        linux: this.items.filter((o) => o.vmOsLine === "LINUX").length,
      };
      this.chartDataCnt["power"] = {
        on: this.items.filter((o) => o.vmPowerOn === "ON").length,
        off: this.items.filter((o) => o.vmPowerOn === "OFF").length,
      };
      this.chartDataCnt["usage"] = {
        op: this.items.filter((o) => o.vmUsageNm === "운영").length,
        old: this.items.filter((o) => o.vmUsageNm === "조회").length,
        dev: this.items.filter((o) => o.vmUsageNm === "개발").length,
      };
    },
    delData(vmNoParam) {
      axios
        .post("/api/delVm", { vmNo: vmNoParam })
        .then((res) => {
          if (res.data > 0) {
            alert("삭제 성공");
            this.setData();
          } else {
            alert("삭제 실패");
          }
        })
        .catch((e) => console.log(e));
    },
  },
  created: function () {
    this.setData();
  },
};
</script>