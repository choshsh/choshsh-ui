<template>
  <div>
    <!-- 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />
    <!-- 본문 -->
    <CCard>
      <CCardBody>
        <FullCalendar :options="calendarOptions" class="myCalendar" />
      </CCardBody>
      <div>
        <CheckIdcInfo
          :show="checkIdcInfoShow"
          :editable="true"
          :checkIdc="checkIdc"
          :checkDate="Boolean(checkDate) ? checkDate : null"
          ref="checkIdcInfo"
          @closeCheckIdcModal="closeCheckIdcModal"
          @setData="setData"
          @alertHandler="alertHandler"
        />
      </div>
    </CCard>
  </div>
</template>
<script>
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import { formatDate } from "@fullcalendar/vue";
import "vue-search-select/dist/VueSearchSelect.css";
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as util from "@/assets/js/util";
import CheckIdcInfo from "./CheckIdcInfo";
import ToasterCustom from "../base/ToasterCustom";

export default {
  components: {
    FullCalendar,
    CheckIdcInfo,
    ToasterCustom,
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
      checkDate: "",
      checkIdcInfoShow: false,
      items: [],
      checkIdc: {},
      checkIdcEntity: {
        id: -1,
        checkDate: "",
        checkTimeFirst: "",
        checkUserFirst: "",
        commentFirst: "",
        checkTimeSecond: "",
        checkUserSecond: "",
        commentSecond: "",
        isError: "N",
        regUser: { id: "" },
        modUser: { id: "" },
      },
      serverIssue: [],
      toaster: {
        number: 0,
        msg: "",
      },
    };
  },
  methods: {
    // 날짜 클릭
    handleDateClick(arg) {
      this.openCheckIdcModal(arg.dateStr);
    },
    // 이벤트 클릭
    handleEventClick(info) {
      this.openCheckIdcModal(info.event.startStr);
    },
    // 일일점검 리스트
    async setData() {
      this.calendarOptions["events"] = [];
      this.items = await axios.get(urls.calendar.checkIdc);

      // 장애 내역
      this.serverIssue = await axios.post(urls.server.issueCountDay, {
        startDate: this.items[0].checkDate,
        endDate: this.items[this.items.length - 1].checkDate,
      });
      try {
        this.items.forEach((o) => {
          let serverIssueCnt = this.serverIssue.filter((s) => {
            return s.incDate === o.checkDate;
          });
          if (serverIssueCnt.length > 0) {
            o.serverIssueCnt = serverIssueCnt[0].cnt;
            o.isError = "Y";
          } else {
            o.serverIssueCnt = 0;
            o.isError = "N";
          }
        });
      } catch (error) {
        console.log(error);
      }

      // 캘린더 뷰
      this.items.forEach((o) => this.setCalendarView(o));
    },
    // 상세 모달
    openCheckIdcModal(checkDate) {
      this.alertCounter = 0;
      let data = this.items.find((o) => o.checkDate === checkDate);
      this.checkIdc = util.cloneObject(data ? data : this.checkIdcEntity);
      this.checkDate = checkDate;
      this.$refs.checkIdcInfo.setServerIssue(checkDate);
      this.checkIdcInfoShow = true;
    },
    closeCheckIdcModal() {
      try {
        document
          .getElementById("checkIdcForm")
          .classList.remove("was-validated");
      } catch (e) {
        console.log(e.name);
      }
      this.checkIdcInfoShow = false;
      this.checkDate = "";
    },
    alertHandler(msg) {
      this.toaster.msg = msg;
      this.toaster.number++;
    },
    // 달력 요약 데이터 설정
    setCalendarView(obj) {
      this.calendarOptions["events"].push({
        date: obj.checkDate,
        title:
          "1. 점검 여부: 오전(" +
          (obj.checkTimeFirst ? "O" : "X") +
          ") 오후(" +
          (obj.checkTimeSecond ? "O" : "X") +
          ")",
        backgroundColor: "#FFFFFF",
        borderColor: "#FFFFFF",
        textColor: "#000000",
      });

      let errorCount = obj.serverIssueCnt;
      this.calendarOptions["events"].push({
        date: obj.checkDate,
        title: "2. 장애: " + errorCount + "건",
        backgroundColor: "#FFFFFF",
        borderColor: "#FFFFFF",
        textColor: errorCount > 0 ? "#e55353" : "#000000",
      });

      let commentCount =
        (obj.commentFirst ? 1 : 0) + (obj.commentSecond ? 1 : 0);
      this.calendarOptions["events"].push({
        date: obj.checkDate,
        title: "3. 작업: " + commentCount + "건",
        backgroundColor: "#FFFFFF",
        borderColor: "#FFFFFF",
        textColor: commentCount > 0 ? "#321fdb" : "#000000",
      });
    },
  },
  async created() {
    this.setData();
  },
};
</script>

<style scoped>
.myCalendar {
  cursor: pointer;
}
</style>
