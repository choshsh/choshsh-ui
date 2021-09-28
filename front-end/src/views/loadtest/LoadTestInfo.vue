<template>
  <div>
    <!-- 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />

    <CCard>
      <CCardBody>
        <!-- 상단 버튼 -->
        <TopButton
          :isNew="true"
          :isExcel="false"
          :excelForm="'vmExcelForm'"
          @modalHandler="modalHandler"
          @excel="excel"
        />

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
              :items="vms"
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
              v-bind:key="dashboard.tableKey"
            >
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
      <LoadTestForm
        :editable="true"
        ref="vmInfo"
        @setData="setData"
        @alertHandler="alertHandler"
      />
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
  { key: "locationName", label: "사업장", _style: "width:100px" },
  { key: "nickname", label: "업무명", _style: "min-width:160px" },
  { key: "name", label: "VM명", _style: "min-width:160px" },
  { key: "vmPower", label: "전원", _style: "width:70px" },
  { key: "vmUsageName", label: "용도", _style: "width:80px" },
  { key: "ip", label: "IP", _style: "width:140px" },
  // { key: "licenseUseCount", label: "Lincese", _style: "width:85px" },
  { key: "osLine", label: "OS계열", _style: "width:85px" },
  { key: "osName", label: "OS상세", _style: "min-width:120px" },
  { key: "user", label: "담당자", _style: "width:100px" },
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
      this.vms = await axios.get(urls.vm.list);
      this.vms.forEach(
        (o) => (o.osLine = o.os.substring(0, 1) === "W" ? "Windows " : "Linux")
      );
      this.setDashboard();
      this.loading = false;
    },
    // 상세 모달
    modalHandler(vmId) {
      this.$refs.vmInfo.modalHandler(vmId > 0 ? vmId : -1);
    },
    openLicenseModal(type, id) {},
    // 엑셀 다운
    excel() {
      let $form = document.getElementById("vmExcelForm");
      $form.action = urls.vm.excel;
      $form.submit();
    },
    // 알람 컨트롤
    alertHandler(msg) {
      this.toaster.msg = msg;
      this.toaster.number++;
    },
    // 요약 설정
    setDashboard() {
      this.dashboard.data = [];
      let data = {
        label: "사업장",
        column: "locationName",
        list: {
          본사: this.vms.filter((o) => o.location === "S").length,
          구미: this.vms.filter((o) => o.location === "K").length,
          ㅤ: "ㅤ",
        },
      };
      this.dashboard.data.push(data);
      data = {
        label: "용도",
        column: "vmUsageName",
        list: {
          운영: this.vms.filter((o) => o.vmUsage === "OP").length,
          개발: this.vms.filter((o) => o.vmUsage === "DEV").length,
          조회: this.vms.filter((o) => o.vmUsage === "OLD").length,
        },
      };
      this.dashboard.data.push(data);
      data = {
        label: "OS계열",
        column: "osLine",
        list: {
          Windows: this.vms.filter((o) => o.os.substring(0, 1) === "W").length,
          Linux: this.vms.filter((o) => o.os.substring(0, 1) !== "W").length,
          ㅤ: "ㅤ",
        },
      };
      this.dashboard.data.push(data);
      data = {
        label: "전원",
        column: "vmPower",
        list: {
          ON: this.vms.filter((o) => o.vmPower === "ON").length,
          OFF: this.vms.filter((o) => o.vmPower === "OFF").length,
          ㅤ: "ㅤ",
        },
      };
      this.dashboard.data.push(data);
    },
    changeColumnFilter(key, value, obj) {
      obj.columnFilter[key] = value;
      obj.tableKey++;
    },
  },
  created() {
    this.setData();
  },
};
</script>
