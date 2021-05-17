<template>
  <CCard>
    <CModal
      title="Calendar Form"
      color="primary"
      :show.sync="formModal"
      :size="'xl'"
    >
      <!-- <CCollapse :show="collapse"> -->
      <CCard>
        <CCardHeader>
          <h4>{{ selectedDate }}</h4>
        </CCardHeader>
        <CCardBody>
          <CRow>
            <CCol sm="2">
              <CInput type="time" label="시작 시간" v-model="form.startTime" />
            </CCol>
            <CCol sm="2">
              <CInput type="time" label="종료 시간" v-model="form.endTime" />
            </CCol>
            <CCol sm="2">
              <CInput label="점검자" v-model="form.regUser" />
            </CCol>
            <CCol sm="1">
              <CSelect
                label="장애 유무"
                :options="['Y', 'N']"
                :value.sync="form.isError"
                @change="clear2()"
              />
            </CCol>
            <CCol sm="5">
              <CInput label="작업 내역" v-model="form.comment" />
            </CCol>
          </CRow>
          <CCollapse :show="Boolean(form.isError === 'Y')">
            <hr />
            <CRow>
              <CCol sm="12">
                <table border="0" class="col-md-12">
                  <tbody>
                    <tr>
                      <th style="width:45%;">
                        장애 대상
                        <div class="small text-muted">
                          (업무명, 호스트명, IP, 관리번호)
                        </div>
                      </th>
                      <th>장애 내용</th>
                      <th style="width:5%;"></th>
                    </tr>
                    <tr
                      v-for="(item, index) in calendarChildEntity"
                      :key="index"
                    >
                      <td class="align-top">
                        <model-select
                          :options="options"
                          v-model="item.id"
                          placeholder="검색어 입력  (업무명 or 호스트명 or IP or 사업장)"
                        />
                      </td>
                      <td>
                        <CInput
                          v-model="item.comment"
                          :readonly="Boolean(!item.id)"
                        />
                      </td>
                      <td
                        class="align-top text-center"
                        v-if="
                          Boolean(index > 0 || calendarChildEntity.length > 1)
                        "
                      >
                        <button class="btn btn-danger" @click="remove(index)">
                          <CIcon name="cil-minus" size="sm" />
                        </button>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="5" class="align-top text-center">
                        <button class="btn btn-success mb-2" @click="add()">
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
              닫기
            </button>
          </div>
        </CCardBody>
      </CCard>
      <!-- </CCollapse> -->
      <div slot="footer" />
    </CModal>
    <CCardBody>
      <FullCalendar :options="calendarOptions" class="myCalendar" />
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
import * as axios2 from "@/assets/js/axios";
import urls from "@/assets/js/url";

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
      formModal: false,
      options: [],
      servers: [],
      thisMonth: formatDate(new Date(), {
        month: "numeric",
      }),
      items: [],

      form: {
        calendarDate: "",
        startTime: "",
        endTime: "",
        regUser: "",
        comment: "",
        isError: "N",
        calendarChildEntity: [],
      },
      calendarChildEntity: [
        {
          calendarDate: "",
          id: "",
          comment: "",
        },
      ],
      isUpdate: false,
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
      if (this.items.length > 0) {
        this.items.forEach((o) => {
          if (o.calendarDate === this.selectedDate) {
            this.form = o;
            this.isUpdate = true;
            if (o.isError === "Y") {
              this.calendarChildEntity = o.calendarChildEntity;
            }
          }
        });
      }
      this.formModal = true;
    },
    cancel() {
      this.formModal = false;
      this.clear();
    },
    // 장애 대상 추가
    add() {
      this.calendarChildEntity.push({
        ciNo: "",
        dieComment: "",
      });
    },
    // 장애 대상 삭제
    remove(idx) {
      this.calendarChildEntity.splice(idx, 1);
    },
    // 초기화
    clear() {
      this.form = {};
      this.form = {
        calendarDate: "",
        startTime: "",
        endTime: "",
        regUser: "",
        comment: "",
        isError: "N",
        calendarChildEntity: [],
      };

      this.clear2();
      this.isUpdate = false;
    },
    clear2() {
      this.calendarChildEntity = [];
      this.calendarChildEntity = [
        {
          calendarDate: "",
          id: "",
          comment: "",
        },
      ];
    },
    // 일일점검 리스트
    async getData() {
      this.calendarOptions["events"] = [];

      this.items = await axios2.get(urls.calendar.list);

      this.items.forEach((o) => {
        this.calendarOptions["events"].push({
          date: o.calendarDate,
          title: "1. 점검 여부: " + (o.startTime ? "O" : "X"),
          backgroundColor: "#FFFFFF",
          borderColor: "#FFFFFF",
          textColor: "#000000",
        });
        this.calendarOptions["events"].push({
          date: o.calendarDate,
          title: "2. 장애: " + o.calendarChildEntity.length + "건",
          backgroundColor: "#FFFFFF",
          borderColor: "#FFFFFF",
          textColor: o.calendarChildEntity.length > 0 ? "#e55353" : "#000000",
        });
        this.calendarOptions["events"].push({
          date: o.calendarDate,
          title: "3. 작업: " + (o.comment ? 1 : 0) + "건",
          backgroundColor: "#FFFFFF",
          borderColor: "#FFFFFF",
          textColor: (o.comment ? 1 : 0) > 0 ? "#321fdb" : "#000000",
        });
      });
    },
    // 서버 select list 데이터
    setServers() {
      axios
        .get("/api/server")
        .then((res) => {
          this.options = res.data.map((o) => {
            return {
              value: o.id,
              text:
                o.serverNm +
                " / " +
                o.hostname +
                " / " +
                o.ipAddr +
                " / " +
                o.location,
            };
          });
        })
        .catch((e) => console.log(e));
    },
    save() {
      this.form.calendarDate = this.selectedDate;
      this.form.calendarChildEntity = this.calendarChildEntity.map((el) => {
        if (el.id != "") {
          el.calendarDate = this.selectedDate;
          return el;
        }
      });
      console.log(this.form.calendarChildEntity);
      if (this.isUpdate) {
        axios
          .put("/api/calendar/" + this.form.calendarDate, this.form)
          .then((res) => {
            alert("Success.");
            this.getData();
            this.clear();
            this.formModal = false;
          })
          .catch((e) => console.log(e));
      } else {
        axios
          .post("/api/calendar", this.form)
          .then((res) => {
            alert("Success.");
            this.getData();
            this.clear();
            this.formModal = false;
          })
          .catch((e) => console.log(e));
      }
    },
  },
  created() {
    this.setServers();
    this.getData();
  },
};
</script>

<style scoped>
.myCalendar {
  cursor: pointer;
}</style
>>
