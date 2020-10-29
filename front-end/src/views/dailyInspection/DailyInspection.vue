<template>
  <CCard>
    <CCollapse :show="collapse">
      <CCard body-wrapper>
        <CRow>
          <CCol col="12">
            <CCard>
              <CCardHeader>
                <h4>{{selectedDate}} 점검일지 작성</h4>
                <div
                  class="small text-muted mt-1"
                  v-if="Boolean(regUser)"
                >등록자: {{regUser}}ㅤㅤㅤ등록 일시: {{regDate}}</div>
                <div
                  class="small text-muted"
                  v-if="Boolean(modUser)"
                >수정자: {{modUser}}ㅤㅤㅤ수정 일시: {{modDate}}</div>
              </CCardHeader>
              <CCardBody>
                <CRow>
                  <CCol sm="1">
                    <CInput label="구분" value="오전" />
                  </CCol>
                  <CCol sm="2">
                    <CInput type="time" label="시작 시간" v-model="diStartTime" />
                  </CCol>
                  <CCol sm="2">
                    <CInput type="time" label="종료 시간" v-model="diEndTime" />
                  </CCol>
                  <CCol sm="2">
                    <CInput label="점검자" v-model="diInspector" />
                  </CCol>
                  <CCol sm="1">
                    <CSelect
                      label="장애 유무"
                      :options="['Y','N']"
                      :value.sync="isError"
                      @change="clear2()"
                    />
                  </CCol>
                  <CCol sm="4">
                    <CInput label="작업 내역" v-model="diComment" />
                  </CCol>
                </CRow>
                <CRow>
                  <CCol sm="1">
                    <CInput value="오후" />
                  </CCol>
                  <CCol sm="2">
                    <CInput type="time" v-model="diStartTime2" />
                  </CCol>
                  <CCol sm="2">
                    <CInput type="time" v-model="diEndTime2" />
                  </CCol>
                  <CCol sm="2">
                    <CInput v-model="diInspector2" />
                  </CCol>
                  <CCol sm="1">
                    <CSelect :options="['Y','N']" :value.sync="isError2" @change="clear2()" />
                  </CCol>
                  <CCol sm="4">
                    <CInput v-model="diComment2" />
                  </CCol>
                </CRow>
                <CCollapse :show="Boolean(isError === 'Y' || isError2 === 'Y')">
                  <hr />
                  <CRow v-if="Boolean(isError === 'Y' || isError2 === 'Y')">
                    <CCol sm="12">
                      <table border="0" class="col-md-12">
                        <tbody>
                          <tr>
                            <th style="width:45%;">
                              장애 대상
                              <div class="small text-muted">(업무명, 호스트명, IP, 관리번호)</div>
                            </th>
                            <th>장애 내용</th>
                            <th style="width:5%;"></th>
                          </tr>
                          <tr v-for="(item, index) in diErrorList" v-bind:key="index">
                            <td class="align-top">
                              <model-select
                                :options="options"
                                v-model="item.ciNo"
                                placeholder="검색어 입력  (업무명 or 호스트명 or IP or 관리번호)"
                              />
                            </td>
                            <td>
                              <CInput v-model="item.dieComment" :readonly="Boolean(!item.ciNo)" />
                            </td>
                            <td
                              class="align-top text-center"
                              v-if="Boolean(index > 0 || diErrorList.length > 1)"
                            >
                              <button class="btn btn-danger" @click="removeDiErrorList(index)">
                                <CIcon name="cil-minus" size="sm" />
                              </button>
                            </td>
                          </tr>
                          <tr>
                            <td colspan="5" class="align-top text-center">
                              <button class="btn btn-success mb-2" @click="addDiErrorList()">
                                <CIcon name="cil-plus" size="sm" />
                              </button>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </CCol>
                  </CRow>
                </CCollapse>
                <div class="text-center row-fluid mb-1 mt-4">
                  <button id="saveBtn" type="button" class="btn btn-primary" @click="save()">저장</button>
                  <button
                    id="cancelBtn"
                    type="button"
                    class="btn btn-secondary ml-4"
                    @click="cancel()"
                  >닫기</button>
                </div>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CCard>
    </CCollapse>
    <CCardBody>
      <FullCalendar :options="calendarOptions" />
    </CCardBody>
  </CCard>
</template>
<script>
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import { formatDate } from "@fullcalendar/vue";
import { ModelSelect } from "vue-search-select";
import "vue-search-select/dist/VueSearchSelect.css";
import axios from "axios";

export default {
  components: {
    FullCalendar,
    ModelSelect,
  },
  data() {
    return {
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin],
        locale: "ko",
        initialView: "dayGridMonth",
        titleFormat: { year: "numeric", month: "2-digit" },
        height: 730,
        weekends: false,
        dateClick: this.handleDateClick,
        eventClick: this.handleEventClick,
        events: [],
      },
      collapse: false,
      selectedDate: "",
      isError: "N",
      diStartTime: "",
      diEndTime: "",
      diInspector: "",
      diComment: "",
      isError2: "N",
      diStartTime2: "",
      diEndTime2: "",
      diInspector2: "",
      diComment2: "",
      diErrorList: [
        {
          ciNo: "",
          dieComment: "",
        },
      ],
      options: [],
      servers: [],
      thisMonth: formatDate(new Date(), {
        month: "numeric",
      }),
      diList: [],
      dieList: [],
      regUser: "",
      regDate: "",
      modUser: "",
      modDate: "",
    };
  },
  methods: {
    // 날짜 클릭
    handleDateClick(arg) {
      this.setDetail(arg.dateStr);
    },
    // 이벤트 클릭
    handleEventClick(info) {
      this.setDetail(info.event.startStr);
    },
    // data 설정
    setDetail(dateParam) {
      window.scrollTo(0, 0);
      this.clear();
      this.selectedDate = dateParam;
      // 상세 data 있을 경우 bind
      this.diList.forEach((o) => {
        if (o.diDate === this.selectedDate) {
          this.isError = o.diIsError;
          this.diStartTime = o.diStartTime;
          this.diEndTime = o.diEndTime;
          this.diInspector = o.diInspector;
          this.diComment = o.diComment;
          this.isError2 = o.diIsError2;
          this.diStartTime2 = o.diStartTime2;
          this.diEndTime2 = o.diEndTime2;
          this.diInspector2 = o.diInspector2;
          this.diComment2 = o.diComment2;
          this.regUser = o.regUser;
          this.regDate = o.regDate;
          this.modUser = o.modUser;
          this.modDate = o.modDate;
          if (o.diIsError === "Y" || o.diIsError2 === "Y") {
            this.diErrorList = [];
            this.dieList.map((oc) => {
              if (oc.diDate === this.selectedDate) {
                this.diErrorList.push({
                  ciNo: oc.ciNo,
                  dieComment: oc.dieComment,
                });
              }
            });
          }
        }
      });
      this.collapse = true;
    },
    cancel() {
      this.collapse = false;
      this.clear();
    },
    // 장애 대상 추가
    addDiErrorList() {
      this.diErrorList.push({
        ciNo: "",
        dieComment: "",
      });
    },
    // 장애 대상 삭제
    removeDiErrorList(idx) {
      this.diErrorList.splice(idx, 1);
    },
    // 초기화
    clear() {
      this.selectedDate = "";
      this.isError = "N";
      this.diStartTime = "";
      this.diEndTime = "";
      this.diInspector = "";
      this.diComment = "";
      this.isError2 = "N";
      this.diStartTime2 = "";
      this.diEndTime2 = "";
      this.diInspector2 = "";
      this.diComment2 = "";
      this.diErrorList = [
        {
          ciNo: "",
          dieComment: "",
        },
      ];
      this.regUser = "";
      this.regDate = "";
      this.modUser = "";
      this.modDate = "";
    },
    clear2() {
      if (this.isError === "N" && this.isError2 === "N") {
        this.diErrorList = [
          {
            ciNo: "",
            dieComment: "",
          },
        ];
      }
    },
    // 일일점검 리스트
    setDis() {
      this.calendarOptions["events"] = [];
      axios
        .post("/api/dis", { default: 1 })
        .then((res) => {
          this.diList = res.data;
          this.diList.forEach((o) => {
            this.calendarOptions["events"].push({
              date: o.diDate,
              title:
                "1. 점검 여부: 오전(" +
                (o.diStartTime ? "O" : "X") +
                ") 오후(" +
                (o.diStartTime2 ? "O" : "X") +
                ")",
              backgroundColor: "#FFFFFF",
              borderColor: "#FFFFFF",
              textColor: "#000000",
            });
            /*      this.calendarOptions["events"].push({
              date: o.diDate,
              title: "2. 점검자: " + o.diInspector,
              backgroundColor: "#FFFFFF",
              borderColor: "#FFFFFF",
              textColor: "#000000",
            }); */
            this.calendarOptions["events"].push({
              date: o.diDate,
              title: "2. 장애: " + o.dieCnt + "건",
              backgroundColor: "#FFFFFF",
              borderColor: "#FFFFFF",
              textColor: o.dieCnt > 0 ? "#e55353" : "#000000",
            });
            this.calendarOptions["events"].push({
              date: o.diDate,
              title:
                "3. 작업: " +
                ((o.diComment ? 1 : 0) + (o.diComment2 ? 1 : 0)) +
                "건",
              backgroundColor: "#FFFFFF",
              borderColor: "#FFFFFF",
              textColor:
                (o.diComment ? 1 : 0) + (o.diComment2 ? 1 : 0) > 0
                  ? "#321fdb"
                  : "#000000",
            });
          });
        })
        .catch((e) => console.log(e));
    },
    // 장애 대상 리스트
    setDies() {
      this.calendarOptions["events"] = [];
      axios
        .post("/api/dies", { default: 1 })
        .then((res) => {
          this.dieList = res.data;
        })
        .catch((e) => console.log(e));
    },
    // 서버 select list 데이터
    setServers() {
      axios
        .get("/api/servers")
        .then((res) => {
          this.options = res.data.map((o) => {
            return {
              value: o.ciNo,
              text:
                o.nickname +
                " / " +
                o.astNm +
                " / " +
                (o.serverIp ? o.serverIp : "-") +
                " / " +
                o.ciNo,
            };
          });
        })
        .catch((e) => console.log(e));
    },
    save() {
      const param = {
        diDate: this.selectedDate,
        diStartTime: this.diStartTime,
        diEndTime: this.diEndTime,
        diInspector: this.diInspector,
        diIsError: this.isError,
        diComment: this.diComment,
        diStartTime2: this.diStartTime2,
        diEndTime2: this.diEndTime2,
        diInspector2: this.diInspector2,
        diIsError2: this.isError2,
        diComment2: this.diComment2,
        regUser: sessionStorage.getItem("userName"),
      };
      let check = true;

      if (this.isError === "Y" || this.isError2 === "Y") {
        this.diErrorList.forEach((o) => {
          o.diDate = this.selectedDate;
          o.dieRegUser = sessionStorage.getItem("userName");
          if (!o.ciNo || !o.dieComment) {
            check = false;
          }
        });
        param["dieList"] = this.diErrorList;
      }
      if (!check) {
        alert("내용을 입력해주세요.");
      } else {
        // 저장
        axios
          .post("/api/diSave", param)
          .then((res) => {
            alert("저장됐습니다.");
            this.setDis();
            this.setDies();
            this.clear();
            this.collapse = false;
          })
          .catch((e) => console.log(e));
      }
    },
  },
  created() {
    this.setServers();
    this.setDis();
    this.setDies();
  },
};
</script>