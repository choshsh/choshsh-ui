<template>
  <CCard>
    <CCardBody>
      <CRow>
        <CCol col="10" sm="10" md="10" xl="11" class="mb-3 mb-xl-0"></CCol>
        <CCol col="2" sm="2" md="2" xl="1" class="mb-3 mb-xl-0">
          <CButton @click="downExcel()" block color="primary">엑셀 다운</CButton>
        </CCol>
      </CRow>
      <CDataTable
        :items="items"
        :fields="fields"
        :items-per-page="10"
        :table-filter="tableFilter"
        :loading="loading"
        :pagination="pagination"
        hover
        column-filter
        sorter
        items-per-page-select
      >
        <template #oprNm="{item}">
          <td>
            <h5>
              <CBadge :color="getBadge(item.oprNm)">{{item.oprNm}}</CBadge>
            </h5>
          </td>
        </template>
        <!--         <template #serverUsage="{item}">
          <td>
            <CBadge :color="getBadge2(item.serverUsage)">{{item.serverUsage}}</CBadge>
          </td>
        </template>-->
        <template #show_details="{item, index}">
          <td class="py-2">
            <CButton
              color="primary"
              variant="outline"
              square
              size="sm"
              @click="toggleDetails(item, index)"
            >{{Boolean(item._toggled) ? 'Hide' : 'Show'}}</CButton>
          </td>
        </template>
        <template #details="{item}">
          <CCollapse :show="Boolean(item._toggled)" :duration="collapseDuration">
            <CCardBody>
              <CTabs variant="pills" :vertical="vertical">
                <CTab active>
                  <template slot="title">{{tabs[0]}}</template>
                  <!-- 서버상세 -->
                  <template>
                    <CCol lg="12">
                      <CCard>
                        <CCardBody>
                          <table border="1" class="col-md-12 table-outline">
                            <tbody>
                              <tr>
                                <th>관리 부서</th>
                                <td>{{item.mngDeptNm}}</td>
                                <th>관리 담당자</th>
                                <td>{{item.mngDeptNm}}</td>
                                <th>위치</th>
                                <td colspan="3">{{item.locationDetail}}</td>
                              </tr>
                              <tr>
                                <th>제조사</th>
                                <td>{{item.serverMakerNm}}</td>
                                <th>모델명</th>
                                <td>{{item.ciModel}}</td>
                                <th>시리얼 번호</th>
                                <td>{{item.serialNo}}</td>
                                <th>유지보수</th>
                                <td>{{item.mngAgree}}</td>
                              </tr>
                              <tr>
                                <th>구입일자</th>
                                <td>{{item.buyDate}}</td>
                                <th>구입부서</th>
                                <td>{{item.buyDeptNm}}</td>
                                <th>구입거래선</th>
                                <td>{{item.buyPartnerNm}}</td>
                                <th>구입금액</th>
                                <td>{{item.buyAmt}}</td>
                              </tr>
                              <tr>
                                <th>스펙 요약</th>
                                <td colspan="7">{{item.spec}}</td>
                              </tr>
                              <tr>
                                <th>OS 계열</th>
                                <td>{{item.serverOsLine}}</td>
                                <th>OS 상세</th>
                                <td colspan="3">{{item.serverOs}}</td>
                                <th>DBMS</th>
                                <td>{{item.serverDbms}}</td>
                              </tr>
                            </tbody>
                          </table>
                        </CCardBody>
                      </CCard>
                    </CCol>
                  </template>
                </CTab>
                <CTab>
                  <template slot="title">{{tabs[1]}}</template>
                  <template>
                    <CCol lg="12" :key="serverChangeKey">
                      <CTableWrapper
                        :fields="fieldsServerChange"
                        :show="Boolean(item._toggled)"
                        :items="itemsServerChange"
                        :ciNo="item.ciNo"
                        small
                      />
                    </CCol>
                  </template>
                </CTab>
                <!--                 <CTab>
                  <template slot="title">
                    {{tabs[2]}}
                  </template>
                               개발중
                </CTab>-->
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
import CTableWrapper from "../base/TableBasicInfo.vue";
import usersData from "../users/UsersData";

const fields = [
  { key: "locationNm", label: "사업장", _style: "width:110px" },
  { key: "nickname", label: "업무명", _style: "min-width:180px" },
  { key: "astNm", label: "호스트명", _style: "min-width:180px" },
  { key: "oprNm", label: "운영상태", _style: "width:100px" },
  { key: "serverIp", label: "IP", _style: "width:140px" },
  { key: "serverMakerNm", label: "제조사", _style: "width:100px" },
  { key: "serverOsLine", label: "OS계열", _style: "width:85px" },
  { key: "serverUsage", label: "용도", _style: "width:80px" },
  { key: "mngEmplNm", label: "담당자", _style: "width:100px" },
  { key: "ciNo", label: "관리번호", _style: "width:160px" },
  {
    key: "show_details",
    label: "",
    _style: "width:1%",
    filter: false,
    sorter: false
  }
];

const fieldsServerChange = [
  { key: "chgGbnNm", label: "변경구분", _style: "width:120px" },
  { key: "chgDate", label: "변경일자", _style: "width:100px" },
  { key: "chgText", label: "내역" },
  { key: "chgBeforeText", label: "Before" },
  { key: "chgAfterText", label: "After" }
];

export default {
  name: "Servers",
  components: { CTableWrapper },
  data() {
    return {
      fields: fields,
      items: [],
      fieldsServerChange: fieldsServerChange,
      itemsServerChange: {},
      tableFilter: { label: "전체 검색", placeholder: "검색어 입력..." },
      collapseDuration: 0,
      tabs: ["정보", "변경이력"],
      vertical: { vertical: true, navs: "col-md-1", content: "col-md-11" },
      serverChangeKey: 0,
      pagination: { align: "end" },
      loading: true
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
        .then(res => {
          this.itemsServerChange[ciNo] = res.data.map(item => {
            for (const key in item) {
              if (item.hasOwnProperty(key))
                [(item[key] = !item[key] ? "-" : item[key])];
            }
            return item;
          });
          this.serverChangeKey += 1;
        })
        .catch(e => console.log(e));
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
    /*     getBadge2(serverUsage) {
      return serverUsage === "운영"
        ? "success"
        : serverUsage === "개발"
        ? "warning"
        : serverUsage === "유휴"
        ? "secondary"
        : "";
    }, */
    setData() {
      axios
        .get("/api/servers")
        .then(res => {
          this.items = res.data.map((item, id) => {
            for (const key in item)
              if (item.hasOwnProperty(key))
                [(item[key] = !item[key] ? "-" : item[key])];
            return { ...item, id };
          });
          this.loading = false;
        })
        .catch(e => console.log(e));
    },
    downExcel() {
      window.location.href = "http://localhost:8080/api/serverExcel";
    }
  },
  created: function() {
    this.setData();
  }
};
</script>