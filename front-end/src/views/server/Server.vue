<template>
  <CCard>
    <CCardBody>
      <CRow class="mb-3">
        <CCol col="10" sm="10" md="10" xl="11" class="mb-3 mb-xl-0"></CCol>
        <CCol col="1" class="mb-3 mb-xl-0">
          <form action="/api/server/excel" method="POST">
            <CButton :type="'submit'" block color="primary">Excel</CButton>
          </form>
        </CCol>
      </CRow>
      <CRow class="">
        <CCol col="4">
          <CCard>
            <CCardHeader>서버 개수</CCardHeader>
            <CCardBody>
              <CChartDoughnutAdv
                :labels="[
                  '서울 :' + chartDataCnt['location']['s'],
                  '구미: ' + chartDataCnt['location']['g'],
                ]"
                :datas="[
                  chartDataCnt['location']['s'],
                  chartDataCnt['location']['g'],
                ]"
              />
            </CCardBody>
          </CCard>
        </CCol>
        <CCol col="4">
          <CCard>
            <CCardHeader>운영 상태</CCardHeader>
            <CCardBody>
              <CChartDoughnutAdv
                :labels="[
                  '지급사용 :' + chartDataCnt['opr']['op'],
                  '폐기대상: ' + chartDataCnt['opr']['dis'],
                  '보관: ' + chartDataCnt['opr']['keep'],
                ]"
                :datas="[
                  chartDataCnt['opr']['op'],
                  chartDataCnt['opr']['dis'],
                  chartDataCnt['opr']['keep'],
                ]"
              />
            </CCardBody>
          </CCard>
        </CCol>
        <CCol col="4">
          <CCard>
            <CCardHeader>OS 및 스토리지</CCardHeader>
            <CCardBody>
              <CChartDoughnutAdv
                :labels="[
                  '윈도우 :' + chartDataCnt['os']['nt'],
                  '리눅스: ' + chartDataCnt['os']['linux'],
                  '스토리지: ' + chartDataCnt['os']['storage'],
                  '기타: ' +
                    (items.length -
                      chartDataCnt['os']['nt'] -
                      chartDataCnt['os']['linux'] -
                      chartDataCnt['os']['storage']),
                ]"
                :datas="[
                  chartDataCnt['os']['nt'],
                  chartDataCnt['os']['linux'],
                  chartDataCnt['os']['storage'],
                  items.length -
                    chartDataCnt['os']['nt'] -
                    chartDataCnt['os']['linux'] -
                    chartDataCnt['os']['storage'],
                ]"
              />
            </CCardBody>
          </CCard>
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
      >
        <template #opr="{ item }">
          <td>
            <h5>
              <CBadge :color="getBadge(item.opr)">{{ item.opr }}</CBadge>
            </h5>
          </td>
        </template>
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
                  <!-- 서버상세 -->
                  <CCol lg="12">
                    <ServerInfo :item="item" />
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
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/url";
import CTableWrapper from "../base/TableBasicServerInfo.vue";
import ServerInfo from "./ServerInfo.vue";
import CChartDoughnutAdv from "../base/CChartDoughnutAdv";

const fields = [
  { key: "location", label: "사업장", _style: "width:110px" },
  { key: "serverNm", label: "업무명", _style: "min-width:180px" },
  { key: "hostname", label: "호스트명", _style: "min-width:180px" },
  { key: "opr", label: "운영상태", _style: "width:100px" },
  { key: "ipAddr", label: "IP", _style: "width:140px" },
  { key: "makerNm", label: "제조사", _style: "width:100px" },
  { key: "osNm", label: "OS계열", _style: "width:85px" },
  { key: "serverUsage", label: "용도", _style: "width:80px" },
  { key: "mngEmplNm", label: "담당자", _style: "width:100px" },
  { key: "id", label: "관리번호", _style: "width:160px" },
  {
    key: "show_details",
    label: "",
    _style: "width:1%",
    filter: false,
    sorter: false,
  },
];

export default {
  name: "Server",
  components: { CTableWrapper, ServerInfo, CChartDoughnutAdv },
  data() {
    return {
      fields: fields,
      items: [],
      tableFilter: { label: "검색", placeholder: "검색어 입력..." },
      collapseDuration: 0,
      tabs: ["정보"],
      vertical: { vertical: true, navs: "col-md-1", content: "col-md-11" },
      pagination: { align: "end" },
      loading: true,
      chartDataCnt: { location: {}, os: {}, opr: {} },
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
    getBadge(opr) {
      return opr === "지급사용"
        ? "success"
        : opr === "보관"
        ? "warning"
        : opr === "폐기대상"
        ? "danger"
        : "";
    },
    async setData() {
      this.items = await axios.get(urls.server.list);
      this.loading = false;
      this.setChartData();
    },
    setChartData() {
      if (this.items.length > 0) {
        this.chartDataCnt["location"] = {
          s: this.items.filter((o) => o.location.indexOf("서울") > -1).length,
          g: this.items.filter((o) => o.location === "구미").length,
        };
        this.chartDataCnt["opr"] = {
          op: this.items.filter((o) => o.opr === "지급사용").length,
          keep: this.items.filter((o) => o.opr === "보관").length,
          dis: this.items.filter((o) => o.opr === "폐기대상").length,
        };
        this.chartDataCnt["os"] = {
          nt: this.items.filter((o) => o.osNm === "Windows").length,
          linux: this.items.filter((o) => o.osNm === "Linux").length,
          storage: this.items.filter((o) => o.osNm === "Storage").length,
        };
      }
    },
  },
  created: function() {
    this.setData();
  },
};
</script>
