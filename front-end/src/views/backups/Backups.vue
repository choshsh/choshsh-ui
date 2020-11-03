<template>
  <CCard>
    <CCardBody>
      <form action="/api/backupExcel" method="POST" id="backupExcelForm">
        <!-- <form action="/api/backupExcelFull" method="POST"> -->
        <CRow class="mb-3 align-items-center">
          <CCol col="2" class="mb-3 mb-xl-0 text-right align-items-center">
            <CSelect
              horizontal
              :options="options"
              :value.sync="selectedOption"
              name="nlCd"
            />
          </CCol>
          <CCol col="7" class="mb-3 mb-xl-0 text-right">
            From
            <input
              type="date"
              :value="searchParam['workflowStartDate']"
              name="workflowStartDate"
              id="workflowStartDate"
            />
            ~ To
            <input
              type="date"
              :value="searchParam['workflowEndDate']"
              name="workflowEndDate"
              id="workflowEndDate"
            />
          </CCol>
          <CCol col="1" class="mb-3 mb-xl-0">
            <CButton @click="search()" block color="primary" class="float-left"
              >검색</CButton
            >
          </CCol>
          <CCol col="1" class="mb-3 mb-xl-0">
            <CButton :type="'submit'" block color="primary">Excel</CButton>
          </CCol>
          <CCol col="1" class="mb-3 mb-xl-0">
            <CButton @click="excelFull()" block color="primary"
              >ExcelFull</CButton
            >
          </CCol>
        </CRow>
      </form>
      <CRow class="">
        <CCol col="4">
          <CCard>
            <CCardHeader>백업 정보</CCardHeader>
            <CCardBody>
              - 기간
              <br />
              <span class="ml-3 font-weight-bold">
                {{ searchParam["workflowStartDate"] }} ~
                {{ searchParam["workflowEndDate"] }}</span
              >
              <br />
              <br />
              - 백업 용량
              <br />
              <span class="ml-3 font-weight-bold">
                {{
                  this.backupSizeTotal > 0
                    ? Math.round(backupSizeTotal / 1024, 0)
                    : 0
                }}
                TB
              </span>
              <span class=""> ({{ backupSizeTotal }} GB) </span>
              <br />
              <br />
              <span class="font-italic"
                >※ "시작일의 전일 18:00" ~ "종료일 11:00" 기준입니다.</span
              >
            </CCardBody>
          </CCard>
        </CCol>
        <CCol col="4">
          <CCard>
            <CCardHeader>백업 상태</CCardHeader>
            <CCardBody>
              <CChartDoughnutAdv
                :labels="[
                  '성공: ' + getChartData().length,
                  '실패: ' + (items.length - getChartData().length),
                ]"
                :datas="[
                  getChartData().length,
                  items.length - getChartData().length,
                ]" /></CCardBody
          ></CCard>
        </CCol>
        <CCol col="4">
          <CCard>
            <CCardHeader>성공한 백업 실행 수 (Level)</CCardHeader>
            <CCardBody>
              <CChartDoughnutAdv
                :labels="[
                  '전체: ' +
                    getChartData().filter((o) => o.backupLevel === 'full')
                      .length,
                  '증분: ' +
                    getChartData().filter((o) => o.backupLevel === 'incr')
                      .length,
                  '기타: ' +
                    getChartData().filter(
                      (o) =>
                        o.backupLevel !== 'full' && o.backupLevel !== 'incr'
                    ).length,
                ]"
                :datas="[
                  getChartData().filter((o) => o.backupLevel === 'full').length,
                  getChartData().filter((o) => o.backupLevel === 'incr').length,
                  getChartData().filter(
                    (o) => o.backupLevel !== 'full' && o.backupLevel !== 'incr'
                  ).length,
                ]"
              /> </CCardBody
          ></CCard>
        </CCol>
      </CRow>
      <CDataTable
        :items="items"
        :fields="fields"
        :items-per-page="10"
        :loading="loading"
        :pagination="pagination"
        :itemsPerPageSelect="{ values: [10, 50, 100, 500] }"
        hover
        column-filter
        sorter
      >
        <template #workflowStatus="{ item }">
          <td>
            <h5>
              <CBadge :color="getBadge(item.workflowStatus)">{{
                item.workflowStatus
              }}</CBadge>
            </h5>
          </td>
        </template>
        <!--         <template #serverUsage="{item}">
          <td>
            <CBadge :color="getBadge2(item.serverUsage)">{{item.serverUsage}}</CBadge>
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
                  <template>
                    <CCol lg="12" :key="backupTargetKey">
                      <CTableWrapper
                        :fields="fieldsBackupChange"
                        :show="Boolean(item._toggled)"
                        :items="itemsBackupChange"
                        :workflowJobId="item.workflowJobId"
                        :tableFilter="tableFilter"
                        :itemsPerPageSelect="true"
                        small
                      />
                    </CCol>
                  </template>
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
import CTableWrapper from "../base/TableBasicBackupInfo.vue";
import CChartDoughnutAdv from "../base/CChartDoughnutAdv";
import CChartBarAdvMulti from "../base/CChartBarAdvMulti.vue";

const fields = [
  { key: "clientName", label: "서버명", _style: "min-width:150px" },
  { key: "groupName", label: "그룹명", _style: "min-width:150px" },
  { key: "backupLevel", label: "레벨", _style: "width:80px" },
  { key: "workflowStatus", label: "상태", _style: "width:100px" },
  { key: "saveSetSize", label: "백업 크기(GB)", _style: "width:100px" },
  { key: "workflowStartTime", label: "시작 일시", _style: "width:140px" },
  { key: "workflowEndTime", label: "종료 일시", _style: "width:140px" },
  { key: "term", label: "소요 시간(h/m/s)", _style: "width:100px" },
  { key: "saveSetNameCnt", label: "백업 대상", _style: "width:80px" },
  {
    key: "show_details",
    label: "",
    _style: "width:1%",
    filter: false,
    sorter: false,
  },
];

const fieldsBackupChange = [
  { key: "saveSetName", label: "대상" },
  { key: "saveSetSize", label: "크기", _style: "width:20%" },
];

export default {
  name: "Servers",
  components: { CTableWrapper, CChartDoughnutAdv, CChartBarAdvMulti },
  data() {
    return {
      fields: fields,
      items: [],
      fieldsBackupChange: fieldsBackupChange,
      itemsBackupChange: {},
      tableFilter: { label: "검색", placeholder: "검색어 입력..." },
      collapseDuration: 0,
      tabs: ["백업 대상"],
      vertical: { vertical: true, navs: "col-md-1", content: "col-md-11" },
      backupTargetKey: 0,
      pagination: { align: "end" },
      loading: true,
      options: [],
      selectedOption: "S",
      searchParam: {},
      tableFilter: { label: "검색", placeholder: "검색어 입력..." },
      backupChartKey: 0,
      backupSizeTotal: 0,
    };
  },
  methods: {
    toggleDetails(item) {
      this.$set(this.items[item.id], "_toggled", !item._toggled);
      if (item._toggled) this.setBackupChange(item);
      this.collapseDuration = 300;
      this.$nextTick(() => {
        this.collapseDuration = 0;
      });
    },
    setBackupChange(item) {
      const workflowJobId = item.workflowJobId;
      axios
        .post("/api/getSaveSetList", {
          workflowJobId: workflowJobId,
          nlCd: this.selectedOption,
        })
        .then((res) => {
          this.itemsBackupChange[workflowJobId] = res.data;
          this.backupTargetKey += 1;
        })
        .catch((e) => console.log(e));
    },
    getBadge(workflowStatus) {
      return workflowStatus === "succeeded" ? "success" : "danger";
    },
    setData(workflowStartDateParam, workflowEndDateParam) {
      this.loading = true;
      const param = {
        nlCd: this.selectedOption,
      };
      if (workflowStartDateParam)
        param["workflowStartDate"] = workflowStartDateParam;
      if (workflowEndDateParam) param["workflowEndDate"] = workflowEndDateParam;

      axios
        .post("/api/backups", param)
        .then((res) => {
          /* 리스트 data */
          this.items = res.data.getBackupList.list.map((item, id) => {
            item.workflowStartTime = item.workflowStartTime.substr(0, 16);
            item.workflowEndTime = item.workflowEndTime.substr(0, 16);
            return { ...item, id };
          });
          /* 사업장 select */
          this.options = [];
          res.data.getBackupCdList.location.map((o) => {
            this.options.push({ value: o.nlCd, label: o.nlNm });
          });
          /* 검색 파라미터 설정 */
          this.searchParam["workflowStartDate"] =
            res.data.getBackupList.nmcSearch.workflowStartDate.substr(6, 4) +
            "-" +
            res.data.getBackupList.nmcSearch.workflowStartDate.substr(0, 2) +
            "-" +
            res.data.getBackupList.nmcSearch.workflowStartDate.substr(3, 2);
          this.searchParam["workflowEndDate"] =
            res.data.getBackupList.nmcSearch.workflowEndDate.substr(6, 4) +
            "-" +
            res.data.getBackupList.nmcSearch.workflowEndDate.substr(0, 2) +
            "-" +
            res.data.getBackupList.nmcSearch.workflowEndDate.substr(3, 2);
          this.searchParam["nlCd"] = res.data.getBackupList.nmcSearch.nlCd;
          this.setBackupSize();
          this.loading = false;
        })
        .catch((e) => console.log(e));
    },
    search() {
      this.setData(
        this.convertDate(document.getElementById("workflowStartDate").value),
        this.convertDate(document.getElementById("workflowEndDate").value)
      );
    },
    convertDate(date) {
      return (
        date.substr(5, 2) + "/" + date.substr(8, 2) + "/" + date.substr(0, 4)
      );
    },
    excelFull() {
      let $form = document.getElementById("backupExcelForm");
      $form.action = "/api/backupExcelFull";
      $form.submit();
    },
    getChartData() {
      return this.items.filter((o) => o.workflowStatus === "succeeded");
    },
    setBackupSize() {
      this.backupSizeTotal =
        this.items.length > 0
          ? Math.round(
              this.items
                .filter((o) => o.workflowStatus === "succeeded")
                .map((a) => a.saveSetSize)
                .reduce(function add(sum, val) {
                  return sum + val;
                }, 0)
            )
          : 0;
    },
  },
  created: function () {
    this.setData();
  },
};
</script>