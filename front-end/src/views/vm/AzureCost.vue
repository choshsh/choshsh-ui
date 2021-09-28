<template>
  <div>
    <!-- 상단 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />
    <!-- 본문 -->
    <CCard>
      <CCardBody>
        <!-- 탭 -->
        <CTabs variant="pills">
          <CTab active class="mb-2">
            <template slot="title">요약</template>
            <CCard>
              <CCardBody>
                <!-- 리스트 -->
                <CRow>
                  <CCol col="6">
                    <CCard>
                      <CCardBody>
                        <CChartLineAdv
                          :datas="chartLine.datas"
                          :labels="chartLine.labels"
                          :yMax="1500000"
                          :legends="['비용']"
                        />
                      </CCardBody>
                    </CCard>
                  </CCol>
                  <CCol col="1"></CCol>
                  <CCol col="3">
                    <CCard>
                      <CCardBody>
                        <CDataTable
                          :items="azureCostsSummary"
                          :fields="fields"
                          :items-per-page="10"
                          :pagination="{ align: 'center' }"
                          :loading="loading"
                          :size="'sm'"
                          hover
                          sorter
                        >
                          <template #azureDate="{ item }">
                            <td>
                              <a
                                href="javascript:;"
                                class="link-primary"
                                @click="infoModalHandler(item.azureDate)"
                              >
                                {{ item.azureDate }}
                              </a>
                            </td>
                          </template>
                          <template #sum="{ item }">
                            <td class="text-right">
                              {{ numberWithCommas(item.sum) }}
                            </td>
                          </template>
                          <template #margin="">
                            <td></td>
                          </template>
                        </CDataTable>
                      </CCardBody>
                    </CCard>
                  </CCol>
                </CRow>
              </CCardBody>
            </CCard>
          </CTab>
          <CTab class="mb-2">
            <template slot="title">상세</template>
            <CRow>
              <CCol col="6">
                <CCard>
                  <CCardBody>
                    <!-- 상단 버튼 -->
                    <CRow class="mb-3" v-if="Boolean(role === 'A')">
                      <CCol col="6" class="mb-3 mb-xl-0"></CCol>
                      <CCol col="3" class="mb-3 mb-xl-0">
                        <CButton
                          block
                          color="primary"
                          @click="infoModalHandler()"
                        >
                          New
                        </CButton>
                      </CCol>
                      <CCol col="3" class="mb-3 mb-xl-0">
                        <form id="vmExcelForm" method="POST">
                          <CButton @click="excel()" block color="primary"
                            >Excel</CButton
                          >
                        </form>
                      </CCol>
                    </CRow>

                    <!-- 리스트 -->
                    <CDataTable
                      :items="azureCosts"
                      :fields="fieldsInfo"
                      :items-per-page="15"
                      :itemsPerPageSelect="{ values: [15, 50, 100, 500] }"
                      :pagination="{ align: 'center' }"
                      :loading="loading"
                      :size="'sm'"
                      :columnFilterValue="dashboard.columnFilter"
                      hover
                      column-filter
                      sorter
                    >
                      <template #nickname="{ item }">
                        <td>
                          <a
                            href="javascript:;"
                            class="link-primary"
                            @click="infoModalHandler(item.id)"
                          >
                            {{ item.nickname }}
                          </a>
                        </td>
                      </template>
                      <template #cost="{ item }">
                        <td class="text-right">
                          {{ numberWithCommas(item.cost) }}
                        </td>
                      </template>
                      <template #margin="">
                        <td></td>
                      </template>
                    </CDataTable>
                  </CCardBody>
                </CCard>
              </CCol>
            </CRow>
          </CTab>
        </CTabs>

        <!-- Info 모달 -->
        <AzureCostInfo
          ref="azureCostInfo"
          @alertHandler="alertHandler"
          @setData="setData"
        />
      </CCardBody>
    </CCard>
  </div>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as util from "@/assets/js/util";
import AzureCostInfo from "./AzureCostInfo";
import CChartLineAdv from "../base/CChartLineAdv";
import ToasterCustom from "../base/ToasterCustom";

const fields = [
  { key: "azureDate", label: "사용월", _style: "width:130px" },
  { key: "sum", label: "비용(원)", _style: "width:160px" },
];
const fieldsInfo = [
  { key: "azureDate", label: "사용월", _style: "width:130px" },
  { key: "resource", label: "리소스", _style: "width:160px" },
  { key: "cost", label: "비용(원)", _style: "width:160px" },
];

export default {
  name: "Azure",
  components: { AzureCostInfo, CChartLineAdv, ToasterCustom },
  data() {
    return {
      azureCosts: [],
      azureCostsSummary: [],
      fields: fields,
      fieldsInfo: fieldsInfo,
      loading: true,
      info: {
        show: false,
      },
      dashboard: {
        data: [],
        columnFilter: {},
        tableKey: 0,
      },
      toaster: {
        number: 0,
        msg: "",
      },
      role: sessionStorage.getItem("role"),
      chartLine: {
        labels: [],
        datas: [],
      },
    };
  },
  methods: {
    async setData() {
      this.loading = true;
      // 목록 설정
      this.azureCostsSummary = await axios.get(urls.azure.cost + "/summary");
      // 차트 설정
      this.setChart(this.azureCostsSummary.slice(0, 5));
      this.azureCosts = await axios.get(urls.azure.cost);
      this.loading = false;
    },
    setChart(arr) {
      const reversed = arr.reverse();
      this.chartLine.labels = reversed.map((o) => [o.azureDate]);
      this.chartLine.datas = [reversed.map((o) => o.sum)];
    },
    alertHandler(msg) {
      this.toaster.msg = msg;
      this.toaster.number++;
    },
    infoModalHandler(azureDate) {
      this.$refs.azureCostInfo.modalHandler(azureDate ? azureDate : "");
    },
    excel() {
      alert("준비 중입니다.");
    },
    numberWithCommas(x) {
      return util.numberWithCommas(x);
    },
  },
  created() {
    this.setData();
  },
};
</script>
