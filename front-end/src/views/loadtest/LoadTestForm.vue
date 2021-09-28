<template>
  <CModal
    color="primary"
    :show.sync="modal.show"
    :closeOnBackdrop="false"
    :size="'m'"
  >
    <div slot="header"><strong>부하 테스트 등록</strong></div>
    <!-- 상단 알람 -->
    <CAlert color="danger" :show.sync="alert.show" closeButton>
      {{ alert.msg }}
    </CAlert>
    <!-- 본문 -->
    <form :id="formName">
      <input type="hidden" v-model="vm.id" name="id" />
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
                <CIcon name="cil-external-link" v- />
              </a>
            </th>
            <td style="width: 20%">
              <CInput
                v-model="entity.jobName"
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
                v-model="entity.host"
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
                v-model="entity.pyscript"
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
                :value.sync="entity.duration"
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
                :value.sync="entity.max"
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
                :value.sync="entity.increase"
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
        id="saveBtn"
        type="button"
        class="btn btn-primary"
        v-if="Boolean(editable)"
        @click="save()"
        v-c-tooltip="{
          content: '너무 과하게 사용하면 슬퍼요😂\n(AWS 비용..)',
        }"
      >
        시작
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
import * as util from "@/assets/js/util";

export default {
  name: "Form",
  data() {
    return {
      options: {
        vmHost: [],
        os: [],
        location: [],
        vmUsage: [],
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
      formName: "loadtestForm",
      jenkinsLink: "http://jenkins.choshsh.com",
      entity: {
        jobName: "load-test",
        title: "",
        host: "",
        duration: "",
        max: 0,
        increase: 0,
        pyscript: "",
      },
      vm: {},
      vmEntity: {
        id: -1,
        name: "",
        nickname: "",
        ip: "",
        cpu: 0,
        memory: 0,
        disk: 0,
        user: "",
        manager: "",
        comment: "",
        location: "",
        vmHost: "",
        os: "",
        vmPower: "",
        vmUsage: "",
        locationName: "",
        vmHostName: "",
        osName: "",
        vmUsageName: "",
        licenseUseCount: 0,
        regUser: { id: "" },
        modUser: { id: "" },
      },
    };
  },
  props: {
    editable: { default: false, type: Boolean },
  },
  methods: {
    // Info 모달 컨트롤
    modalHandler(vmId) {
      document.getElementById(this.formName).classList.remove("was-validated");
      this.alertHandler();
      if (vmId) this.setData(vmId);
      this.modal.show = !this.modal.show;
    },
    // 알람 컨트롤
    alertHandler(color, msg, bool) {
      this.alert.color = color;
      this.alert.msg = msg;
      this.alert.show = bool;
      if (!color) this.alert.show = false;
    },
    async setData(vmId) {
      if (vmId > 0) {
        this.vm = await axios.get(urls.vm.info + "/" + vmId);
      } else {
        this.vm = util.cloneObject(this.vmEntity);
      }
    },
    // select 데이터 설정
    async setOption() {
      this.options.location = await axios.get(urls.code.location);
      this.options.vmHost = await axios.get(urls.code.vmHost);
      this.options.vmOs = await axios.get(urls.code.vmOs);
      this.options.vmPower = await axios.get(urls.code.vmPower);
      this.options.vmUsage = await axios.get(urls.code.vmUsage);
      this.options.yn = await axios.get(urls.code.yn);
    },
    // 저장
    async save() {
      // 필수 값 체크
      let form = document.getElementById(this.formName);
      let checkValidation = validate.check(form);
      if (!checkValidation) {
        this.alertHandler("danger", "필수 값을 입력해주세요", true);
        return;
      }

      this.vm.regUser.id = sessionStorage.getItem("id");
      this.vm.modUser.id = sessionStorage.getItem("id");

      let add =
        this.vm.id > 0
          ? await axios.put(urls.vm.update + "/" + this.vm.id, this.vm)
          : await axios.post(urls.vm.update, this.vm);

      if (add.id > 0) {
        this.vm = add;
        this.$emit("setData");
        this.$emit(
          "alertHandler",
          "[ " + add.nickname + " ] is successfully updated"
        );
        this.modalHandler();
      } else {
        alert("실패");
        return;
      }
    },
    async remove() {
      let confirm = window.confirm("정말 삭제하겠습니까?");
      if (confirm) {
        let result = await axios.del(urls.vm.delete + "/" + this.vm.id);
        if (result > 0) {
          // 삭제 성공
          this.$emit("setData");
          this.$emit(
            "alertHandler",
            "[ " + this.vm.nickname + " ] is successfully deleted"
          );
          this.modalHandler();
        } else {
          // 삭제 실패
          this.alertHandler("danger", "삭제 실패.", true);
        }
      }
    },
  },
  created() {
    this.setOption();
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
