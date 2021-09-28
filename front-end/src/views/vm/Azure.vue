<template>
  <div>
    <!-- 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />
    <!-- 본문 -->
    <CCard>
      <CCardBody>
        <!-- 상단 버튼 -->
        <CRow class="mb-3">
          <CCol col="10" class="mb-3 mb-xl-0"></CCol>
          <CCol col="1" class="mb-3 mb-xl-0">
            <CButton block color="primary" @click="infoModalHandler()">
              New
            </CButton>
          </CCol>
          <CCol col="1" class="mb-3 mb-xl-0">
            <form id="vmExcelForm" method="POST">
              <CButton @click="excel()" block color="primary">Excel</CButton>
            </form>
          </CCol>
        </CRow>

        <!-- 리스트 -->
        <CCard>
          <CCardBody>
            <CDataTable
              :items="azures"
              :fields="fields"
              :items-per-page="15"
              :itemsPerPageSelect="{ values: [15, 50, 100, 500] }"
              :pagination="{ align: 'center' }"
              :table-filter="{ label: '검색', placeholder: '검색어 입력...' }"
              :loading="loading"
              :size="'sm'"
              :columnFilterValue="dashboard.columnFilter"
              hover
              column-filter
              sorter
            >
              <template #vmPower="{ item }">
                <td>
                  <h5>
                    <CBadge :color="getBadge(item.vmPower)">{{
                      item.vmPower
                    }}</CBadge>
                  </h5>
                </td>
              </template>
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
            </CDataTable>
          </CCardBody>
        </CCard>
      </CCardBody>

      <!-- Info 모달 -->
      <AzureInfo
        ref="azureInfo"
        @alertHandler="alertHandler"
        @setData="setData"
      />
    </CCard>
  </div>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as util from "@/assets/js/util";
import AzureInfo from "./AzureInfo";
import ToasterCustom from "../base/ToasterCustom";

const fields = [
  { key: "location", label: "위치", _style: "width:130px" },
  { key: "nickname", label: "업무명", _style: "min-width:160px" },
  { key: "name", label: "VM명", _style: "min-width:160px" },
  { key: "vmPower", label: "전원", _style: "width:70px" },
  { key: "vmUsageName", label: "용도", _style: "width:80px" },
  { key: "ip", label: "IP", _style: "width:140px" },
  { key: "osLine", label: "OS계열", _style: "width:85px" },
  { key: "osName", label: "OS상세", _style: "min-width:120px" },
  { key: "user", label: "담당자", _style: "width:100px" },
];

export default {
  name: "Azure",
  components: { AzureInfo, ToasterCustom },
  data() {
    return {
      azures: [],
      fields: fields,
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
    async setData() {
      this.loading = true;
      let data = await axios.get(urls.azure.list);
      data.forEach(
        (o) => (o.osLine = o.os.substring(0, 1) === "W" ? "Windows " : "Linux")
      );
      this.azures = data;
      this.loading = false;
    },
    alertHandler(msg) {
      this.toaster.msg = msg;
      this.toaster.number++;
    },
    infoModalHandler(azureId) {
      this.$refs.azureInfo.modalHandler(azureId ? azureId : -1);
    },
    excel() {
      alert("준비 중입니다.");
    },
  },
  created() {
    this.setData();
  },
};
</script>
