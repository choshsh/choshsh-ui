<template>
  <CCard>
    <CCardBody>
      <CRow class="mb-3">
        <CCol col="10" sm="10" md="10" xl="11" class="mb-3 mb-xl-0"></CCol>
        <CCol col="1" class="mb-3 mb-xl-0">
          <form action="/api/serverExcel" method="POST">
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
        <template #oprNm="{ item }">
          <td>
            <h5>
              <CBadge :color="getBadge(item.oprNm)">{{ item.oprNm }}</CBadge>
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
                    <ServersInfo :item="item" />
                  </CCol>
                </CTab>
                <CTab>
                  <template slot="title">{{ tabs[1] }}</template>
                  <CCol lg="12" :key="serverChangeKey">
                    <CTableWrapper
                      :fields="fieldsServerChange"
                      :show="Boolean(item._toggled)"
                      :items="itemsServerChange"
                      :ciNo="item.ciNo"
                      :itemsPerPageSelect="true"
                      small
                    />
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
import CTableWrapper from "../base/TableBasicServerInfo.vue";
import ServersInfo from "./ServersInfo.vue";
import CChartDoughnutAdv from "../base/CChartDoughnutAdv";

const fields = [
  { key: "locationNm", label: "사업장", _style: "width:110px" },
  { key: "serverNm", label: "업무명", _style: "min-width:180px" },
  { key: "hostname", label: "호스트명", _style: "min-width:180px" },
  { key: "oprNm", label: "운영상태", _style: "width:100px" },
  { key: "ipAddr", label: "IP", _style: "width:140px" },
  { key: "makerNm", label: "제조사", _style: "width:100px" },
  { key: "osNm", label: "OS계열", _style: "width:85px" },
  { key: "usageNm", label: "용도", _style: "width:80px" },
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

const fieldsServerChange = [
  { key: "chgGbnNm", label: "변경구분", _style: "width:120px" },
  { key: "chgDate", label: "변경일자", _style: "width:100px" },
  { key: "chgText", label: "내역" },
  { key: "chgBeforeText", label: "Before" },
  { key: "chgAfterText", label: "After" },
];

export default {
  name: "Servers",
  components: { CTableWrapper, ServersInfo, CChartDoughnutAdv },
  data() {
    return {
      fields: fields,
      items: [],
      fieldsServerChange: fieldsServerChange,
      itemsServerChange: {},
      tableFilter: { label: "검색", placeholder: "검색어 입력..." },
      collapseDuration: 0,
      tabs: ["정보", "변경이력"],
      vertical: { vertical: true, navs: "col-md-1", content: "col-md-11" },
      serverChangeKey: 0,
      pagination: { align: "end" },
      loading: true,
      chartDataCnt: { location: {}, os: {}, opr: {} },
    };
  },
  methods: {
    toggleDetails(item) {
      this.$set(this.items[item.id], "_toggled", !item._toggled);
      if (item._toggled) this.setServerChange(item);
      this.collapseDuration = 300;
      this.$nextTick(() => {
        this.collapseDuration = 0;
      });
    },
    setServerChange(item) {
      const ciNo = item.ciNo;
      axios
        .post("/api/serverChange", { ciNo: ciNo })
        .then((res) => {
          this.itemsServerChange[ciNo] = res.data.map((item) => {
            for (const key in item) {
              if (item.hasOwnProperty(key))
                [(item[key] = !item[key] ? "-" : item[key])];
            }
            return item;
          });
          this.serverChangeKey += 1;
        })
        .catch((e) => console.log(e));
    },
    getBadge(oprNm) {
      return oprNm === "지급사용"
        ? "success"
        : oprNm === "보관"
        ? "warning"
        : oprNm === "폐기대상"
        ? "danger"
        : "";
    },
    setData() {
      axios
        .get("/api/server")
        .then((res) => {
          this.items = res.data.map((item, id) => {
            item["locationNm"] = item.locationEntity.locationNm;
            item["oprNm"] = item.oprEntity.oprNm;
            item["usageNm"] = item.usageEntity.usageNm;
            item["osNm"] = item.osEntity.osNm;
            return { ...item, id };
          });
          this.setChartData();
          this.loading = false;
        })
        .catch((e) => console.log(e));
    },
    setChartData() {
      this.chartDataCnt["location"] = {
        s: this.items.filter((o) => o.locationNm.indexOf("서울") > -1).length,
        g: this.items.filter((o) => o.locationNm === "구미").length,
      };
      this.chartDataCnt["opr"] = {
        op: this.items.filter((o) => o.oprNm === "지급사용").length,
        keep: this.items.filter((o) => o.oprNm === "보관").length,
        dis: this.items.filter((o) => o.oprNm === "폐기대상").length,
      };
      this.chartDataCnt["os"] = {
        nt: this.items.filter((o) => o.osNm === "Windows").length,
        linux: this.items.filter((o) => o.osNm === "Linux").length,
        storage: this.items.filter((o) => o.osNm === "Storage").length,
      };
      this.chartDataCnt["os"];
    },
  },
  created: function() {
    this.setData();
  },
};
</script>
