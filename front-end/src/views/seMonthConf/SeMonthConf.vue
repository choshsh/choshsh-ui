<template>
  <div>
    <!-- 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />
    <!-- 상세 모달 -->
    <CModal
      color="primary"
      :show.sync="show"
      :closeOnBackdrop="false"
      :size="'xl'"
    >
      <CAlert color="danger" :show.sync="showValidationAlert" closeButton>
        필수 값을 입력해주세요.
      </CAlert>
      <div slot="header" />
      <!-- 본문 -->
      <CCard>
        <CCardHeader class="font-weight-bold">SE 월간회의</CCardHeader>
        <CCardBody>
          <form id="meetingForm">
            <CRow>
              <CCol sm="12">
                <CInput
                  label="제목"
                  v-model="meeting.title"
                  placeholder="Enter 제목"
                  id="title"
                  required
                />
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="12">
                <CInput
                  label="키워드"
                  v-model="meeting.keyword"
                  placeholder="Enter 키워드"
                  required
                />
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="12">
                <CKeditor
                  ref="ClassicEditor"
                  :dataParam="meeting.content"
                  v-bind:key="ckKey"
                />
              </CCol>
            </CRow>
            <div class="text-center row-fluid mb-1 mt-3">
              <button
                id="saveBtn"
                type="button"
                class="btn btn-primary"
                @click="save()"
              >
                저장
              </button>
              <button
                id="cancelBtn"
                type="button"
                class="btn btn-secondary ml-4"
                @click="cancel()"
              >
                취소
              </button>
            </div>
          </form>
        </CCardBody>
      </CCard>
      <div slot="footer" />
    </CModal>
    <!-- 본문 -->
    <CCard>
      <CCardBody>
        <CRow>
          <CCol col="11" class="mb-3 mb-xl-0"></CCol>
          <CCol col="1" class="mb-3 mb-xl-0">
            <CButton @click="openForm()" block color="primary">New</CButton>
          </CCol>
        </CRow>
        <CDataTable
          :items="items"
          :fields="fields"
          :items-per-page="15"
          :table-filter="{ label: '검색', placeholder: '검색어 입력...' }"
          :loading="loading"
          :pagination="{ align: 'center' }"
          :itemsPerPageSelect="{ values: [15, 50, 100, 500] }"
          :size="'sm'"
          hover
          sorter
        >
          <template #title="{ item }">
            <td>
              <a
                href="javascript:;"
                class="link-primary"
                @click="openForm(item)"
              >
                {{ item.title }}
              </a>
            </td>
          </template>
          <template #regDate="{ item }">
            <td>
              {{ item.regDate.substring(0, 10) }}
            </td>
          </template>
          <template #regUser="{ item }">
            <td>
              {{ item.regUser.userName }}
            </td>
          </template>
        </CDataTable>
      </CCardBody>
    </CCard>
  </div>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as validate from "@/assets/js/validate";
import * as util from "@/assets/js/util";
import CKeditor from "../base/CKEditor.vue";
import ToasterCustom from "../base/ToasterCustom";

const fields = [
  { key: "title", label: "제목", _style: "width:30%" },
  { key: "keyword", label: "키워드", _style: "width:20%" },
  { key: "regDate", label: "작성일", _style: "width:15%" },
  { key: "regUser", label: "작성자", _style: "width:10%" },
];
export default {
  components: {
    CKeditor,
    ToasterCustom,
  },
  data() {
    return {
      fields: fields,
      items: [],
      meeting: {},
      meetingEntity: {
        id: -1,
        title: "",
        keyword: "",
        content: "",
        regUser: { id: "" },
        modUser: { id: "" },
      },
      loading: true,
      ckKey: 0,
      show: false,

      alertCounter: 0,
      showValidationAlert: false,
      toaster: {
        number: 0,
        msg: "",
      },
    };
  },
  methods: {
    async setData() {
      this.items = await axios.get("/api/meeting");
      this.loading = false;
    },
    alertHandler(msg) {
      this.toaster.msg = msg;
      this.toaster.number++;
    },
    async save() {
      this.meeting.content = this.$refs.ClassicEditor.getData();

      let form = document.getElementById("meetingForm");

      let checkValidation = validate.check(form);
      if (!checkValidation) {
        this.showValidationAlert = true;
        return;
      }

      this.meeting.regUser.id = sessionStorage.getItem("id");
      this.meeting.modUser.id = sessionStorage.getItem("id");

      let addMeeting =
        this.meeting.id > 0
          ? await axios.put("/api/meeting/" + this.meeting.id, this.meeting)
          : await axios.post("/api/meeting", this.meeting);

      if (addMeeting.id > 0) {
        this.meeting = addMeeting;
        this.setData();
        this.alertHandler("Content is successfully updated.");
        this.cancel();
      } else {
        alert("실패");
        return;
      }
    },

    openForm(meeting) {
      this.meeting = util.cloneObject(meeting ? meeting : this.meetingEntity);
      this.ckKey++;
      this.show = true;
    },
    cancel() {
      this.show = false;
    },
  },
  created: function() {
    this.setData();
  },
};
</script>
