<template>
  <CModal
    color="primary"
    :show.sync="modal.show"
    :closeOnBackdrop="false"
    :size="'xl'"
  >
    <div slot="header"><strong>VM Info</strong></div>
    <!-- 상단 알람 -->
    <CAlert color="danger" :show.sync="alert.show" closeButton>
      {{ alert.msg }}
    </CAlert>
    <!-- 본문 -->
    <CCard>
      <form id="vmForm">
        <input type="hidden" v-model="vm.id" name="id" />
        <table border="1" class="col-md-12 table-outline info-table">
          <tbody>
            <tr>
              <th style="width: 10%">업무명</th>
              <td style="width: 20%">
                <CInput
                  v-model="vm.nickname"
                  name="nickname"
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">VM명</th>
              <td style="width: 20%">
                <CInput
                  v-model="vm.name"
                  name="name"
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">IP</th>
              <td style="width: 20%">
                <CInput v-model="vm.ip" name="ip" style="margin:0" />
              </td>
            </tr>
            <tr>
              <th style="width: 10%">용도</th>
              <td style="width: 20%">
                <CSelect
                  :options="options['vmUsage']"
                  :value.sync="vm.vmUsage"
                  name="vmUsage"
                  placeholder="..."
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">전원</th>
              <td style="width: 20%">
                <CSelect
                  :options="options['vmPower']"
                  :value.sync="vm.vmPower"
                  name="vmUsage"
                  placeholder="..."
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">사업장</th>
              <td style="width: 20%">
                <CSelect
                  :options="this.options['location']"
                  :value.sync="vm.location"
                  name="location"
                  placeholder="..."
                  style="margin:0"
                  required
                />
              </td>
            </tr>
            <tr>
              <th style="width: 10%">VM 호스트</th>
              <td style="width: 20%">
                <CSelect
                  :options="options['vmHost']"
                  :value.sync="vm.vmHost"
                  name="vmHost"
                  placeholder="..."
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">운영체제</th>
              <td colspan="3" style="width: 20%">
                <CSelect
                  :options="options['vmOs']"
                  :value.sync="vm.os"
                  name="os"
                  placeholder="..."
                  style="margin:0"
                  required
                />
              </td>
            </tr>
            <tr>
              <th style="width: 10%">CPU</th>
              <td style="width: 20%">
                <CInput
                  v-model="vm.cpu"
                  name="cpu"
                  type="Number"
                  append="Cores"
                  style="margin:0"
                />
              </td>
              <th style="width: 10%">Memory</th>
              <td style="width: 20%">
                <CInput
                  v-model="vm.memory"
                  name="memory"
                  type="Number"
                  append="GB"
                  style="margin:0"
                />
              </td>
              <th style="width: 10%">Disk</th>
              <td style="width: 20%">
                <CInput
                  v-model="vm.disk"
                  name="disk"
                  type="Number"
                  append="GB"
                  style="margin:0"
                />
              </td>
            </tr>
            <tr>
              <th style="width: 10%">운영 담당자</th>
              <td style="width: 20%">
                <CInput
                  v-model="vm.user"
                  name="user"
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">관리 담당자</th>
              <td style="width: 20%">
                <CInput
                  v-model="vm.manager"
                  name="manager"
                  style="margin:0"
                  required
                />
              </td>
            </tr>
            <tr>
              <th style="width: 10%">Comment</th>
              <td colspan="5" rowspan="5" style="width: 20%">
                <CTextarea
                  rows="5"
                  v-model="vm.comment"
                  name="comment"
                  style="margin:0"
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
        >
          저장
        </button>
        <button
          id="cancelBtn"
          type="button"
          class="btn btn-danger ml-4"
          @click="remove()"
          v-if="Boolean(vm.id > 0)"
        >
          삭제
        </button>
        <button
          id="cancelBtn"
          type="button"
          class="btn btn-secondary ml-4"
          @click="modalHandler()"
        >
          취소
        </button>
      </div>
    </CCard>
    <div slot="footer" />
  </CModal>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as validate from "@/assets/js/validate";
import * as util from "@/assets/js/util";

export default {
  name: "VMForm",
  data() {
    return {
      options: {
        vmHost: [],
        os: [],
        location: [],
        vmUsage: [],
      },
      modal: {
        show: false,
      },
      alert: {
        color: "",
        show: false,
        msg: "",
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
      document.getElementById("vmForm").classList.remove("was-validated");
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
      let form = document.getElementById("vmForm");
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
