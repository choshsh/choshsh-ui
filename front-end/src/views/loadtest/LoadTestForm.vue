<template>
  <CModal color="primary" :show.sync="modal.show" :closeOnBackdrop="false">
    <!-- 알림 -->
    <ToasterCustom :msg="toaster.msg" :fixedToasts="toaster.number" />

    <div slot="header"><strong>부하 테스트 등록</strong></div>
    <!-- 상단 알람 -->
    <CAlert color="danger" :show.sync="alert.show" closeButton>
      {{ alert.msg }}
    </CAlert>
    <!-- 본문 -->
    <form :id="formId">
      <table border="1" class="col-md-12 table-outline info-table">
        <tbody>
          <tr>
            <th style="width: 10%">
              Jenkins Job
              <a
                :href="jenkinsLink"
                target="_black"
                v-c-tooltip="{
                  content: 'GitHub에서 Jenkinsfile 보기',
                }"
              >
                <CIcon name="cil-external-link" />
              </a>
            </th>
            <td style="width: 20%">
              <CInput
                v-model="this.entity.jobName"
                name="jobName"
                style="margin:0"
                readonly
                required
              />
            </td>
          </tr>
          <tr>
            <th style="width: 10%">
              제목
            </th>
            <td style="width: 20%">
              <CInput
                v-model="entity.title"
                name="title"
                style="margin:0"
                placeholder="간단한 테스트"
                required
              />
            </td>
          </tr>

          <tr>
            <th style="width: 10%">
              호스트
              <CIcon
                name="cil-info"
                v-c-tooltip="{
                  content: 'root url을 입력합니다.',
                }"
              />
            </th>
            <td style="width: 20%">
              <CInput
                v-model="params.host"
                name="host"
                style="margin:0"
                placeholder="https://<url>"
                required
              />
            </td>
          </tr>

          <tr>
            <th style="width: 10%">
              python 스크립트
              <CIcon
                name="cil-info"
                v-c-tooltip="{
                  content:
                    'Jenkinsfile이 있는 git repo에서의 상대 경로를 입력합니다.',
                }"
              />
            </th>
            <td style="width: 20%">
              <CInput
                v-model="params.pyscript"
                name="pyscript"
                style="margin:0"
                placeholder="/path/script.py"
                required
              />
            </td>
          </tr>
          <tr>
            <th style="width: 10%">테스트 지속시간</th>
            <td style="width: 20%">
              <CSelect
                :options="options['duration']"
                :value.sync="params.duration"
                name="duration"
                placeholder="..."
                style="margin:0"
                required
              />
            </td>
          </tr>
          <tr>
            <th style="width: 10%">
              최대 사용자 생성 수
              <CIcon
                name="cil-info"
                v-c-tooltip="{
                  content:
                    '최대 사용자 수를 설정합니다. 사용자는 1부터 시작해서 설정값까지 생성됩니다.',
                }"
              />
            </th>
            <td style="width: 20%">
              <CSelect
                :value.sync="params.max"
                :options="options['max']"
                name="max"
                placeholder="..."
                style="margin:0"
                required
              />
            </td>
          </tr>
          <tr>
            <th style="width: 10%">
              초당 사용자 증가 수
              <CIcon
                name="cil-info"
                v-c-tooltip="{
                  content: '1초마다 생성할 사용자 수를 설정합니다.',
                }"
              />
            </th>
            <td style="width: 20%">
              <CSelect
                :value.sync="params.increase"
                :options="options['increase']"
                name="increase"
                placeholder="..."
                style="margin:0"
                required
              />
            </td>
          </tr>
        </tbody>
      </table>
    </form>
    <!-- 하단 버튼 -->
    <div class="text-center row-fluid mb-1 mt-3">
      <button
        v-if="!runBuild"
        id="saveBtn"
        type="button"
        class="btn btn-primary"
        @click="build()"
        v-c-tooltip="{
          content: '너무 과하게 사용하면 슬퍼요😂\n(AWS 비용..)',
        }"
      >
        시작
      </button>
      <button v-else type="button" class="btn btn-primary" @click="inBuild">
        요청 중 <CSpinner color="success" size="sm" />
      </button>
      <button
        id="cancelBtn"
        type="button"
        class="btn btn-secondary ml-4"
        @click="modalHandler()"
      >
        닫기
      </button>
    </div>
    <div slot="footer" />
  </CModal>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as validate from "@/assets/js/validate";
import ToasterCustom from "../base/ToasterCustom";

export default {
  name: "LoadTestForm",
  components: { ToasterCustom },
  data() {
    return {
      options: {
        duration: ["10s", "30s", "1m"],
        max: [1, 10, 100, 200],
        increase: [1, 5, 10, 30, 50],
      },
      modal: {
        show: false,
      },
      alert: {
        color: "",
        show: false,
        msg: "",
      },
      formId: "loadtestForm",
      entity: {
        jobName: "",
        title: "",
      },
      params: {
        host: "",
        duration: "",
        max: 0,
        increase: 0,
        pyscript: "",
      },
      jenkinsLink: "",
      jenkinsJob: "",
      toaster: {
        number: 0,
        msg: "",
      },
      runBuild: false,
    };
  },
  methods: {
    // Info 모달 컨트롤
    modalHandler() {
      if (document.getElementById(this.formId)) {
        document.getElementById(this.formId).classList.remove("was-validated");
      }
      this.alertHandler();
      this.entity.params = {};
      this.params = {};
      this.runBuild = false;
      this.modal.show = !this.modal.show;
    },
    // 알람 컨트롤
    alertHandler(color, msg, bool) {
      if (!color) this.alert.show = false;
      else {
        this.alert.color = color;
        this.alert.msg = msg;
        this.alert.show = bool;
      }
    },
    // 토스트 컨트롤
    toastHandler(msg) {
      this.toaster.msg = msg;
      this.toaster.number++;
    },
    // 저장
    async build() {
      // 유효성 검사
      let form = document.getElementById(this.formId);
      let checkValidation = validate.check(form);
      if (!checkValidation) {
        this.alertHandler("danger", "필수 값을 입력해주세요", true);
        return;
      }

      // 알람
      this.toastHandler(
        "빌드를 요청을 완료했어요.\n잠시만 기다려주시면 페이지 이동해요."
      );
      this.runBuild = true;

      // api 요청
      this.entity.params = this.params;
      let buildNumber = await axios.post("/jenkins/build", this.entity);

      // 완료 후 이동
      if (buildNumber > 0) {
        this.$router.push({
          path: "/loadTestInfo",
          query: { buildNumber: buildNumber },
        });
      } else {
        this.modalHandler();
      }
    },
    // 중복실행 방지
    async inBuild() {
      this.toastHandler("이미 실행 중이에요.\n잠시만 기다려주세요");
    },
    // 환경변수 값 가져오기
    async setEnv() {
      let data = await axios.get(urls.admin.env + "/LOADTEST_JENKINS_URL");
      this.jenkinsLink = data.value;
      data = await axios.get(urls.admin.env + "/LOADTEST_JOB");
      this.jenkinsJob = data.value;
      this.entity.jobName = data.value;
    },
  },
  created() {
    this.setEnv();
  },
};
</script>
<style scoped>
.info-table > tbody > tr > th,
td {
  height: 40px;
  padding: 1em;
}
</style>
