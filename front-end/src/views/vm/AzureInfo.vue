<template>
  <CModal
    color="primary"
    :show.sync="modal.show"
    :closeOnBackdrop="false"
    :size="'xl'"
  >
    <!-- 상단 알람 -->
    <CAlert :color="alert.color" :show.sync="alert.show" closeButton>
      {{ alert.msg }}
    </CAlert>
    <div slot="header"><strong>Azure Info</strong></div>

    <CCard>
      <form id="azureForm">
        <table border="1" class="col-md-12 table-outline info-table">
          <tbody>
            <tr>
              <th style="width: 10%">업무명</th>
              <td style="width: 20%">
                <CInput
                  v-model="azure.nickname"
                  name="nickname"
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">VM명</th>
              <td style="width: 20%">
                <CInput
                  v-model="azure.name"
                  name="name"
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">IP</th>
              <td style="width: 20%">
                <CInput
                  v-model="azure.ip"
                  name="ip"
                  style="margin:0"
                  required
                />
              </td>
            </tr>
            <tr>
              <th style="width: 10%">용도</th>
              <td style="width: 20%">
                <CSelect
                  :options="options['vmUsage']"
                  :value.sync="azure.vmUsage"
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
                  :value.sync="azure.vmPower"
                  name="vmUsage"
                  placeholder="..."
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">위치</th>
              <td style="width: 20%">
                <CSelect
                  :options="this.options['location']"
                  :value.sync="azure.location"
                  name="location"
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
                  v-model="azure.cpu"
                  name="cpu"
                  type="Number"
                  append="Cores"
                  style="margin:0"
                />
              </td>
              <th style="width: 10%">Memory</th>
              <td style="width: 20%">
                <CInput
                  v-model="azure.memory"
                  name="memory"
                  type="Number"
                  append="GB"
                  style="margin:0"
                />
              </td>
              <th style="width: 10%">Disk</th>
              <td style="width: 20%">
                <CInput
                  v-model="azure.disk"
                  name="disk"
                  type="Number"
                  append="GB"
                  style="margin:0"
                />
              </td>
            </tr>
            <tr>
              <th style="width: 10%">운영체제</th>
              <td colspan="5" style="width: 20%">
                <CSelect
                  :options="options['vmOs']"
                  :value.sync="azure.os"
                  name="os"
                  placeholder="..."
                  style="margin:0"
                  required
                />
              </td>
            </tr>
            <tr>
              <th style="width: 10%">운영 담당자</th>
              <td style="width: 20%">
                <CInput
                  v-model="azure.user"
                  name="user"
                  placeholder="Enter 운영 담당자"
                  style="margin:0"
                  required
                />
              </td>
              <th style="width: 10%">관리 담당자</th>
              <td style="width: 20%">
                <CInput
                  v-model="azure.manager"
                  name="manager"
                  placeholder="Enter 관리 담당자"
                  style="margin:0"
                  required
                />
              </td>
            </tr>
            <tr>
              <th style="width: 10%">Comment</th>
              <td colspan="5" rowspan="5" style="width: 20%">
                <CTextarea
                  placeholder="Content..."
                  rows="5"
                  v-model="azure.comment"
                  name="comment"
                  style="margin:0"
                />
              </td>
            </tr>
          </tbody>
        </table>
      </form>
    </CCard>

    <!-- 하단 버튼 -->
    <div class="text-center row-fluid mb-1">
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
        @click="modalHandler()"
      >
        취소
      </button>
    </div>

    <div slot="footer" />
  </CModal>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as util from "@/assets/js/util";
import * as validate from "@/assets/js/validate";

export default {
  name: "AzureInfo",
  data() {
    return {
      modal: {
        show: false,
      },
      alert: {
        color: "",
        show: false,
        msg: "",
      },
      azure: {},
      azureEntity: {
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
        os: "",
        vmPower: "",
        vmUsage: "",
        regUser: {
          id: -1,
        },
        modUser: {
          id: -1,
        },
      },
      options: {},
    };
  },
  props: {
    azureId: Number,
  },
  methods: {
    // Info 모달 컨트롤
    modalHandler(azureId) {
      this.alertHandler();
      this.setData(azureId);
      this.modal.show = !this.modal.show;
    },
    // 알람 컨트롤
    alertHandler(color, msg, bool) {
      this.alert.color = color;
      this.alert.msg = msg;
      this.alert.show = bool;
      if (!color) this.alert.show = false;
    },
    // select 옵션 설정
    async setOption() {
      this.options.location = await axios.get(urls.code.azureLocation);
      this.options.vmOs = await axios.get(urls.code.vmOs);
      this.options.vmPower = await axios.get(urls.code.vmPower);
      this.options.vmUsage = await axios.get(urls.code.vmUsage);
      this.options.yn = await axios.get(urls.code.yn);
    },
    // 수정 시 데이터 설정
    async setData(azureId) {
      if (azureId > 0) {
        this.azure = await axios.get(urls.azure.info + "/" + azureId);
      } else {
        this.azure = util.cloneObject(this.azureEntity);
      }
    },
    // 저장
    async save() {
      // 필수값 체크
      let form = document.getElementById("azureForm");
      let checkValidation = validate.check(form);
      if (!checkValidation) {
        this.alertHandler("danger", "필수 값을 입력하세요", true);
        return;
      }

      this.azure.regUser.id = sessionStorage.getItem("id");
      this.azure.modUser.id = sessionStorage.getItem("id");

      // 저장
      let add =
        this.azure.id > 0
          ? await axios.put(urls.azure.update + "/" + this.azure.id, this.azure)
          : await axios.post(urls.azure.update, this.azure);

      if (add.id > 0) {
        this.$emit("setData");
        this.$emit(
          "alertHandler",
          "[ " + add.nickname + " ] is successfully updated"
        );
        this.modalHandler();
      } else {
        // 저장 실패 시
        this.alertHandler("danger", "failed", true);
        return;
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
