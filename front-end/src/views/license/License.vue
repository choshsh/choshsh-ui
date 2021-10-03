<template>
  <CCard>
    <!-- 상태 알람 -->
    <CAlert color="success" :show.sync="alert.counter" closeButton>
      {{ alert.msg }}
      <CProgress
        :max="5"
        :value="alert.counter"
        height="3px"
        color="success"
        animate
      />
    </CAlert>

    <!-- 카테고리 모달 -->
    <LicenseCategory
      :show="category.show"
      :editable="true"
      ref="licenseCategory"
      @categoryModalHandler="categoryModalHandler"
    />
    <!-- info 모달 -->
    <LicenseInfo
      :editable="true"
      ref="licenseInfo"
      @infoModalHandler="infoModalHandler"
      @setData="setData"
      @alertHandler="alertHandler"
    />

    <CCardBody>
      <CRow class="mb-3">
        <CCol col="1" class="mb-3 mb-xl-0">
          <CButton block color="primary" @click="categoryModalHandler()">
            Category
          </CButton>
        </CCol>
        <CCol col="9" class="mb-3 mb-xl-0"></CCol>
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
      <CDataTable
        :items="licenses"
        :fields="fields"
        :items-per-page="15"
        :itemsPerPageSelect="{ values: [15, 50, 100, 500] }"
        :pagination="{ align: 'center' }"
        :table-filter="{ label: '검색', placeholder: '검색어 입력...' }"
        :loading="loading"
        :size="'sm'"
        hover
        column-filter
        sorter
      >
        <template #name="{ item }">
          <td>
            <a
              href="javascript:;"
              class="link-primary"
              @click="infoModalHandler(item.id)"
            >
              {{ item.name }}
            </a>
          </td>
        </template>
        <template #category="{ item }">
          <td>
            {{ item.licenseCategory.name }}
          </td>
        </template>
        <template #countNotUsed="{ item }">
          <td>
            {{ item.count - item.countUsed }}
          </td>
        </template>
      </CDataTable>
    </CCardBody>
  </CCard>
</template>

<script>
// 테이블 컬럼
const fields = [
  { key: "category", label: "카테고리", _style: "width:120px" },
  { key: "name", label: "라이선스명", _style: "min-width:160px" },
  { key: "vendor", label: "벤더", _style: "width:120px" },
  { key: "client", label: "거래처", _style: "width:120px" },
  { key: "count", label: "전체 개수", _style: "width:100px" },
  { key: "countUsed", label: "사용 중", _style: "width:100px" },
  { key: "countNotUsed", label: "미사용", _style: "width:100px" },
];

import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as util from "@/assets/js/util";
import LicenseCategory from "./LicenseCategory";
import LicenseInfo from "./LicenseInfo";

export default {
  name: "Licenses",
  components: { LicenseCategory, LicenseInfo },
  data() {
    return {
      licenses: [],
      fields: fields,
      loading: false,
      use: {},
      category: {
        show: false,
      },
      alert: {
        counter: 0,
        msg: "",
      },
    };
  },
  methods: {
    async setData() {
      this.loading = true;
      this.licenses = await axios.get(urls.license.list);
      this.loading = false;
    },
    // 상세 모달 컨트롤
    infoModalHandler(id) {
      this.$refs.licenseInfo.modalHandler(id > 0 ? id : -1);
    },
    // 카테고리 모달 컨트롤
    categoryModalHandler() {
      this.$refs.licenseCategory.setData();
      this.category.show = !this.category.show;
    },
    alertHandler(msg) {
      this.alert.msg = msg;
      this.alert.counter = 5;
    },
    excel() {
      alert("준비 중입니다");
    },
  },
  created() {
    this.setData();
  },
};
</script>
