<template>
  <CCard>
    <CCardBody>
      <CRow>
        <CCol col="11" class="mb-3 mb-xl-0"></CCol>
        <CCol col="1" class="mb-3 mb-xl-0">
          <CButton @click="formModal = true" color="primary">New</CButton>
          <CModal
            title="Editor Form"
            color="primary"
            :show.sync="formModal"
            :size="'xl'"
          >
            <CRow>
              <CCol col="12">
                <CCard>
                  <CCardBody>
                    <form id="boardForm">
                      <CRow>
                        <CCol sm="12">
                          <CInput
                            label="제목"
                            v-model="form.title"
                            placeholder="Enter 제목"
                          />
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol sm="12">
                          <CInput
                            label="키워드"
                            v-model="form.keyword"
                            placeholder="Enter 키워드"
                          />
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol sm="12">
                          <CKeditor
                            ref="ClassicEditorForm"
                            :dataParam="form.content"
                            :key="ckKey"
                          />
                        </CCol>
                      </CRow>
                      <div class="text-center row-fluid mb-1 mt-3">
                        <button
                          id="saveBtn"
                          type="button"
                          class="btn btn-primary"
                          @click="modalSave(form.entityId)"
                        >
                          저장
                        </button>
                        <button
                          id="cancelBtn"
                          type="button"
                          class="btn btn-secondary ml-4"
                          @click="modalClose"
                        >
                          취소
                        </button>
                      </div>
                    </form>
                  </CCardBody>
                </CCard>
              </CCol>
            </CRow>
            <div slot="footer" />
          </CModal>
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
        :sorterValue="{ column: 'regDate', asc: false }"
        hover
        sorter
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
                          <CButton
                            @click="modalEdit(item)"
                            class="btn btn-success"
                            >수정</CButton
                          >
                          <CButton
                            @click="deleteData(item.entityId)"
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
import moment from "moment";

const fields = [
  { key: "title", label: "제목", _style: "width:30%" },
  { key: "keyword", label: "키워드", _style: "width:*" },
  { key: "regDate", label: "작성일", _style: "width:15%" },
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
      loading: true,
      userRoleCd: sessionStorage.getItem("userRoleCd"),
      formModal: false,
      form: {
        title: "",
        keyword: "",
        content: "",
      },
      ckKey: 0,
    };
  },
  methods: {
    getData() {
      axios
        .get("/api/editor")
        .then((res) => {
          this.items = res.data.map((item, id) => {
            item.regDate = this.dateFormatter(item.regDate);
            return { ...item, id };
          });
          this.loading = false;
        })
        .catch((e) => console.log(e));
    },
    deleteData(entityId) {
      axios
        .delete("/api/editor/" + entityId)
        .then((res) => {
          res.status == 200 ? this.onSuccess() : alert("Fail");
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
    modalSave(entityId) {
      var url = "";
      this.form.content = this.$refs.ClassicEditorForm.getData();
      if (entityId >= 0) {
        axios
          .put("/api/editor/" + entityId, this.form)
          .then((res) => {
            res.status == 200 ? this.onSuccess() : alert("Fail");
          })
          .catch((e) => console.log(e));
      } else {
        axios
          .post("/api/editor", this.form)
          .then((res) => {
            res.status == 200 ? this.onSuccess() : alert("Fail");
          })
          .catch((e) => console.log(e));
      }
    },
    modalEdit(item) {
      console.log(item);
      this.form.title = item.title;
      this.form.keyword = item.keyword;
      this.form.content = item.content;
      this.form.entityId = item.entityId;
      this.ckKey++;
      this.formModal = true;
    },
    modalClose() {
      this.formModal = false;
    },
    dateFormatter(date) {
      return moment(date).format("YYYY-MM-DD HH:mm:ss");
    },
    onSuccess() {
      alert("Success");
      this.clear();
      this.getData();
      this.formModal = false;
    },
    clear() {
      this.ckKey++;
      this.items = [];
      this.form = {
        title: "",
        keyword: "",
        content: "",
      };
    },
  },
  created: function() {
    this.getData();
  },
};
</script>
