<template>
  <CCard>
    <CCardBody>
      <CTabs variant="pills">
        <CTab active class="mb-2">
          <template slot="title">{{ tabs[0] }}</template>
          <CRow>
            <CCol col="4" sm="4" md="4" xl="4" class="mb-3 mb-xl-0">
              <CCard>
                <CCardHeader class="font-weight-bold">
                  정보팀 외출 현황
                </CCardHeader>
                <CCardBody>
                  <CChartBarAdv
                    :color="'#007bff'"
                    :labels="labels"
                    :datas="datasParam"
                    :maxValParam="10"
                  />
                </CCardBody>
              </CCard>
            </CCol>
            <CCol
              col="2"
              sm="2"
              md="2"
              xl="2"
              class="mb-3 mb-xl-0"
              v-for="i in tableNm"
              v-bind:key="i"
            >
              <CCard>
                <CCardHeader class="font-weight-bold">
                  <slot name="header">{{ i }}</slot>
                </CCardHeader>
                <CCardBody>
                  <table border="0" class="col-md-12">
                    <tr v-for="j in datasParamList.today" v-bind:key="j.index">
                      <!-- 반차 추가 -->
                      <td
                        v-if="
                          Boolean(
                            j.egObject === i || j.egObject.indexOf(i) > -1
                          )
                        "
                      >
                        {{ j.mbrNm }} - {{ j.teamNm }}
                      </td>
                    </tr>
                  </table>
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
          <CCardGroup>
            <CTableWrapper
              :fields="fieldsSummary"
              :items="datasParamList.today"
              :itemsPerPage="15"
              :size="'xs'"
            />
          </CCardGroup>
        </CTab>
        <CTab>
          <template slot="title">{{ tabs[1] }}</template>
          <template>
            <form
              action="/interface/egression/excel"
              method="POST"
              id="excelForm"
            >
              <input type="hidden" name="sDate" />
              <input type="hidden" name="eDate" />
            </form>
            <CRow class="mt-3 mb-3 align-items-center">
              <CCol col="10" class="mb-3 mb-xl-0 text-right">
                From
                <input
                  type="date"
                  :value="searchParam['sdate']"
                  id="sdate"
                  name="sDate"
                />
                ~ To
                <input
                  type="date"
                  :value="searchParam['edate']"
                  id="edate"
                  name="eDate"
                />
              </CCol>
              <CCol col="1" class="mb-3 mb-xl-0">
                <CButton
                  @click="search()"
                  block
                  color="primary"
                  class="float-left"
                  >검색</CButton
                >
              </CCol>
              <CCol col="1" class="mb-3 mb-xl-0">
                <CButton @click="excel()" block color="primary">Excel</CButton>
              </CCol>
            </CRow>

            <CTableWrapper
              :fields="fields"
              :items="datasParamList.list"
              :itemsPerPage="15"
              :tableFilter="tableFilter"
              :loading="loading"
              :size="'sm'"
            />
          </template>
        </CTab>
      </CTabs>
    </CCardBody>
  </CCard>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import CTableWrapper from "../base/TableBasic.vue";
import CChartBarAdv from "../base/CChartBarAdv.vue";

const fields = [
  { key: "teamNm", label: "부서", _style: "width:120px" },
  { key: "mbrNm", label: "성명", _style: "width:100px" },
  { key: "egDate", label: "기간", _style: "width:200px" },
  { key: "egLocDtl", label: "고객사" },
  { key: "egObjectDtl", label: "직출 사유" },
  { key: "siteWork", label: "직출 구분", _style: "width:100px" },
  { key: "egStatusNm", label: "현재 상태", _style: "width:100px" },
];
const fieldsSummary = [
  { key: "teamNm", label: "부서", _style: "width:120px" },
  { key: "mbrNm", label: "성명", _style: "width:100px" },
  { key: "egDate", label: "기간", _style: "width:200px" },
  { key: "egLocDtl", label: "행선지" },
  { key: "egObjectDtl", label: "용무" },
];

export default {
  name: "Servers",
  components: { CTableWrapper, CChartBarAdv },
  data() {
    return {
      fields: fields,
      fieldsSummary: fieldsSummary,
      tabs: ["Dashboard", "검색"],
      pagination: { align: "end" },
      labels: [],
      datasParam: [],
      datasParamList: {},
      tableNm: ["휴가", "출장", "교육", "재택근무"],
      searchParam: {},
      tableFilter: { label: "검색", placeholder: "검색어 입력..." },
      loading: true,
    };
  },
  methods: {
    async setChartData() {
      // 외출 현황 > chart
      let data = await axios.get(urls.egression.chart);
      this.labels = data.getEgressionObject.map((o) => {
        this.datasParam.push(0);
        return o.egObject;
      });
      data.getEgressionChartThisDay.map((o) => {
        this.datasParam[this.labels.indexOf(o.egObject)] = o.cnt;
      });

      const now = new Date().toISOString().substr(0, 10);
      const today = this.convertDate(now);

      // 외출 현황 > data

      let param = {
        sdate: today,
        edate: today,
        egStatus: "Y",
        scope: "A",
      };
      let data2 = await axios.post(urls.egression.list, param);
      this.$set(this.datasParamList, "today", data2.list);
    },
    // 데이터 > data
    async setListData(sdateParam, edateParam) {
      const param = {
        orderBy: "ELO01",
      };
      if (sdateParam) param["sdate"] = sdateParam;
      if (edateParam) param["edate"] = edateParam;

      let data = await axios.post(urls.egression.list, param);
      this.searchParam["sdate"] =
        data.egressionSearch.sdate.substr(6, 4) +
        "-" +
        data.egressionSearch.sdate.substr(0, 2) +
        "-" +
        data.egressionSearch.sdate.substr(3, 2);
      this.searchParam["edate"] =
        data.egressionSearch.edate.substr(6, 4) +
        "-" +
        data.egressionSearch.edate.substr(0, 2) +
        "-" +
        data.egressionSearch.edate.substr(3, 2);
      this.datasParamList["list"] = data.list;
      this.loading = false;
    },
    search() {
      this.loading = true;
      this.setListData(
        this.convertDate(document.getElementById("sdate").value),
        this.convertDate(document.getElementById("edate").value)
      );
    },
    excel() {
      let $form = document.getElementById("excelForm");
      $form.sDate.value = this.convertDate(
        document.getElementById("sdate").value
      );
      $form.eDate.value = this.convertDate(
        document.getElementById("edate").value
      );
      $form.submit();
    },
    convertDate(date) {
      return (
        date.substr(5, 2) + "/" + date.substr(8, 2) + "/" + date.substr(0, 4)
      );
    },
  },
  created: function() {
    this.setChartData();
    this.setListData();
  },
};
</script>
