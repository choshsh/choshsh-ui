<template>
  <CModal
    color="primary"
    :show.sync="show"
    :closeOnBackdrop="false"
    :size="'xl'"
  >
    <CAlert color="danger" :show.sync="currentAlertShow" closeButton>
      {{ currentAlertMsg }}
    </CAlert>
    <div slot="header" />
    <CCard>
      <CCardHeader>
        <h2>{{ checkDate }}</h2>
        <div class="small text-muted mt-1" v-if="Boolean(checkIdc.id > 0)">
          등록자: {{ checkIdc.regUser.userName }}ㅤㅤㅤ등록 일시:
          {{ checkIdc.regDate }}
        </div>
        <div class="small text-muted" v-if="Boolean(checkIdc.id > 0)">
          수정자: {{ checkIdc.modUser.userName }}ㅤㅤㅤ수정 일시:
          {{ checkIdc.modDate }}
        </div>
      </CCardHeader>
      <CCardBody>
        <form id="checkIdcForm">
          <CRow>
            <CCol sm="1">
              <CInput label="구분" value="오전" />
            </CCol>
            <CCol sm="2">
              <CInput
                type="time"
                label="점검 시간"
                v-model="checkIdc.checkTimeFirst"
                :required="
                  Boolean(
                    !(checkIdc.checkTimeFirst || checkIdc.checkTimeSecond)
                  )
                "
              />
            </CCol>
            <CCol sm="2">
              <CInput
                label="점검자"
                v-model="checkIdc.checkUserFirst"
                :required="Boolean(checkIdc.checkTimeFirst)"
              />
            </CCol>

            <CCol sm="7">
              <CInput label="작업 내역" v-model="checkIdc.commentFirst" />
            </CCol>
          </CRow>
          <CRow>
            <CCol sm="1">
              <CInput value="오후" />
            </CCol>
            <CCol sm="2">
              <CInput
                type="time"
                v-model="checkIdc.checkTimeSecond"
                :required="
                  !(checkIdc.checkTimeFirst || checkIdc.checkTimeSecond)
                "
              />
            </CCol>
            <CCol sm="2">
              <CInput
                v-model="checkIdc.checkUserSecond"
                :required="Boolean(checkIdc.checkTimeSecond)"
              />
            </CCol>
            <CCol sm="7">
              <CInput v-model="checkIdc.commentSecond" />
            </CCol>
          </CRow>
          <hr />
          <CCard v-if="serverIssue.items.length > 0">
            <CCardHeader>
              <h5 class="font-weight-bold">장애 내역</h5>
            </CCardHeader>
            <CCardBody>
              <CDataTable
                :items="serverIssue.items"
                :fields="serverIssue.fields"
                :items-per-page="10"
                :pagination="{ align: 'center' }"
                :loading="serverIssue.loading"
                :size="'sm'"
                hover
                sorter
                v-bind:key="serverIssue.tableKey"
              >
                <template #tranGbn="{ item }">
                  <td>
                    {{ item.tranGbn === "REAL" ? "실제" : "훈련" }}
                  </td>
                </template>
                <template #incSubject="{ item }">
                  <td>
                    <a
                      href="javascript:;"
                      class="link-primary"
                      @click="modalHandlerIssue(item.incNo)"
                    >
                      {{ item.incSubject }}
                    </a>
                  </td>
                </template>
                <template #incStatusNm="{ item }">
                  <td>
                    <h5>
                      <CBadge :color="getBadge(item.incStatus)">
                        {{ item.incStatusNm }}
                      </CBadge>
                    </h5>
                  </td>
                </template>
              </CDataTable>
            </CCardBody>
          </CCard>
        </form>

        <div class="text-center row-fluid mb-1 mt-4">
          <button id="saveBtn" class="btn btn-primary" @click="save()">
            저장
          </button>
          <button
            id="cancelBtn"
            class="btn btn-secondary ml-4"
            @click="cancel()"
          >
            닫기
          </button>
        </div>
      </CCardBody>
    </CCard>
    <div slot="footer" />

    <!-- 장애 상세 모달 -->
    <div>
      <ServerIssueInfo ref="serverIssueInfo" />
    </div>
  </CModal>
</template>
<script>
import { ModelSelect } from "vue-search-select";
import "vue-search-select/dist/VueSearchSelect.css";
import * as axios from "@/assets/js/axios";
import * as elHandler from "@/assets/js/elHandler";
import urls from "@/assets/js/urls";
import * as util from "@/assets/js/util";
import * as validate from "@/assets/js/validate";
import ServerIssueInfo from "../server/ServerIssueInfo";

export default {
  components: {
    ModelSelect,
    ServerIssueInfo,
  },
  data() {
    return {
      currentAlertShow: false,
      currentAlertMsg: "",
      serverIssue: {
        items: [],
        fields: [
          { key: "svcItemNm", label: "서비스", _style: "min-width:130px" },
          { key: "incSubject", label: "제목", _style: "min-width:160px" },
          { key: "tranGbn", label: "장애 Type", _style: "width:100px" },
          { key: "incLevel", label: "장애 등급", _style: "width:100px" },
          {
            key: "incStatusNm",
            label: "진행상태",
            _style: "min-width:100px",
          },
          { key: "recovDate", label: "복구일", _style: "min-width:100px" },
          { key: "deptNm", label: "관리부서", _style: "min-width:100px" },
          { key: "emplNm", label: "운영담당자", _style: "min-width:100px" },
        ],
        loading: true,
        tableKey: 0,
      },
    };
  },
  props: {
    checkIdc: Object,
    editable: { default: false, type: Boolean },
    show: { default: false, type: Boolean },
    checkDate: String,
  },
  methods: {
    modalHandlerIssue(id) {
      this.$refs.serverIssueInfo.modalHandler(id);
    },

    add(array, event) {
      elHandler.add(array, event);
    },
    remove(array, index, event) {
      elHandler.remove(array, index, event);
    },
    getBadge(incStatus) {
      if (incStatus === "A") return "success";
      if (incStatus === "B") return "warning";
      if (incStatus === "N") return "primary";
      if (incStatus === "E") return "secondary";
    },
    cancel() {
      this.currentAlertShow = false;
      this.serverIssue.items = [];
      this.$emit("closeCheckIdcModal");
    },
    async setServerIssue(checkDate) {
      if (checkDate) {
        try {
          this.serverIssue.loading = true;

          let list = await axios.post(urls.server.issue, {
            startDate: checkDate,
            endDate: checkDate,
          });
          this.serverIssue.items = list.map((o) => {
            o.recovDate = util.dateFormat(o.recovDate);
            return o;
          });
          this.serverIssue.loading = false;
        } catch (error) {
          console.log(error);
        }
      }
    },
    async save() {
      this.checkIdc.regUser.id = sessionStorage.getItem("id");
      this.checkIdc.modUser.id = sessionStorage.getItem("id");
      this.checkIdc.checkDate = this.checkDate;

      let form = document.getElementById("checkIdcForm");

      let checkValidation = validate.check(form);
      if (!checkValidation) {
        this.currentAlertShow = true;
        this.currentAlertMsg = "필수 값을 입력해주세요.";
        return;
      }

      let addCheckIdc =
        this.checkIdc.id > 0
          ? await axios.put(
              urls.calendar.checkIdc + "/" + this.checkIdc.id,
              this.checkIdc
            )
          : await axios.post(urls.calendar.checkIdc, this.checkIdc);

      if (addCheckIdc.id > 0) {
        this.$emit("setData");
        this.$emit(
          "alertHandler",
          "[ " + this.checkDate + " ] is successfully updated."
        );
        this.cancel();
      } else {
        this.currentAlertShow = true;
        this.currentAlertMsg = "Error.";
        return;
      }
    },
  },
  created() {},
};
</script>
