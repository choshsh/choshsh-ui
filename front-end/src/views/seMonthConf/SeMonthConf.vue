<template>
  <CCard>
    <CCardBody>
      <CRow>
        <CCol col="11" class="mb-3 mb-xl-0"></CCol>
        <CCol col="1" class="mb-3 mb-xl-0">
          <router-link
            :to="{
              name: 'seMonthConfForm',
              params: { bdCd: bdCd },
            }"
          >
            <CButton block color="primary">New</CButton>
          </router-link>
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
        sorter
        :key="semcKey"
      >
        >
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
                    <CCol lg="12">
                      <CCard>
                        <CCardBody>
                          <CKeditor
                            ref="ClassicEditor"
                            :dataParam="item.content"
                            :disable="true"
                            :hideToolbar="true"
                          />
                        </CCardBody>
                        <div class="text-center row-fluid mb-1">
                          <router-link
                            :to="{
                              name: 'seMonthConfForm',
                              params: { bdSeq: item.bdSeq, bdCd: bdCd },
                            }"
                          >
                            <CButton class="btn btn-success">수정</CButton>
                          </router-link>
                          <CButton
                            @click="delData(item.bdSeq)"
                            class="btn btn-danger ml-3"
                            >삭제</CButton
                          >
                        </div>
                      </CCard>
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
import CKeditor from "../base/CKEditor.vue";

const fields = [
  { key: "title", label: "제목", _style: "width:30%" },
  { key: "keyword", label: "키워드", _style: "width:*" },
  { key: "regDt", label: "작성일", _style: "width:15%" },
  { key: "userName", label: "작성자", _style: "width:10%" },
  {
    key: "show_details",
    label: "",
    _style: "width:1%",
    filter: false,
    sorter: false,
  },
];

export default {
  components: {
    CKeditor,
  },
  data() {
    return {
      fields: fields,
      items: [],
      pagination: { align: "end" },
      tableFilter: { label: "검색", placeholder: "검색어 입력..." },
      collapseDuration: 0,
      tabs: ["내용"],
      vertical: { vertical: true, navs: "col-md-1", content: "col-md-11" },
      bdCd: "sl01",
      loading: true,
      semcKey: 0,
      userRoleCd: sessionStorage.getItem("userRoleCd"),
    };
  },
  methods: {
    setData() {
      axios
        .post("/api/board/getList", { bdCd: this.bdCd })
        .then((res) => {
          this.items = res.data.map((item, id) => {
            for (const key in item)
              if (item.hasOwnProperty(key))
                [(item[key] = !item[key] ? "-" : item[key])];
            return { ...item, id };
          });
          this.loading = false;
          this.semcKey++;
        })
        .catch((e) => console.log(e));
    },
    delData(bdSeq) {
      axios
        .post("/api/boardDel", { bdSeq: bdSeq })
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
    toggleDetails(item) {
      this.$set(this.items[item.id], "_toggled", !item._toggled);
      this.collapseDuration = 300;
      this.$nextTick(() => {
        this.collapseDuration = 0;
      });
    },
  },
  created: function () {
    this.setData();
  },
};
</script>