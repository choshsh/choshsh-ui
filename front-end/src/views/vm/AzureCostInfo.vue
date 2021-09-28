<template>
  <CModal
    color="primary"
    :show.sync="modal.show"
    :closeOnBackdrop="false"
    :size="'lg'"
  >
    <!-- 상단 알람 -->
    <CAlert :color="alert.color" :show.sync="alert.show" closeButton>
      {{ alert.msg }}
    </CAlert>
    <div slot="header" />

    <CCard>
      <CCardHeader class="font-weight-bold">Azure Cost Info</CCardHeader>
      <CCardBody>
        <form id="azureCostForm">
          <table border="1" class="col-md-12 table-outline info-table">
            <tbody>
              <tr>
                <th style="width:15%">사용 월</th>
                <td>
                  <CInput
                    name="azureDate"
                    v-model="azureDate"
                    style="margin:0;"
                    placeholder="yyyy-mm (ex. 2021-04)"
                    required
                  />
                </td>
              </tr>
            </tbody>
          </table>
          <table border="1" class="col-md-12 table-outline info-table">
            <tbody>
              <tr v-for="(item, index) in azureResources" v-bind:key="index">
                <td style="width:15%">{{ item.resource }}</td>
                <td>
                  <CInput
                    type="number"
                    v-model="item.cost"
                    name="cost"
                    style="margin:0;"
                    required
                  />
                </td>
              </tr>
            </tbody>
          </table>
        </form>
      </CCardBody>
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
      azureResources: [
        { resource: "디스크", cost: 0 },
        { resource: "가상머신", cost: 0 },
        { resource: "VPN", cost: 0 },
        { resource: "스냅샷", cost: 0 },
        { resource: "ASR(DR)", cost: 0 },
        { resource: "ETC", cost: 0 },
      ],
      azureCostEntity: {
        azureDate: "",
        resource: "",
        cost: 0,
      },
      azureDate: "",
      options: {},
    };
  },
  props: {
    azureId: Number,
  },
  methods: {
    // Info 모달 컨트롤
    modalHandler(azureDate) {
      this.alertHandler();
      this.setData(azureDate);
      this.modal.show = !this.modal.show;
    },
    // 알람 컨트롤
    alertHandler(color, msg, bool) {
      this.alert.color = color;
      this.alert.msg = msg;
      this.alert.show = bool;
      if (!color) this.alert.show = false;
    },
    // 수정 시 데이터 설정
    async setData(azureDate) {
      if (azureDate) {
        this.azureDate = azureDate;
        let data = await axios.get(urls.azure.cost + "/" + azureDate);
        this.azureResources.forEach((o) => {
          data.forEach((d) => {
            if (d.resource === o.resource) {
              o.cost = d.cost;
            }
          });
        });
      } else {
        this.azure = util.cloneObject(this.azureEntity);
      }
    },
    // 저장
    async save() {
      // 필수값 체크
      let form = document.getElementById("azureCostForm");
      let checkValidation = validate.check(form);
      if (!checkValidation) {
        this.alertHandler("danger", "필수 값을 입력하세요", true);
        return;
      }

      // 기존 정보 삭제
      await axios.del(urls.azure.cost + "/" + this.azureDate);

      let add;
      this.azureResources.forEach(async (o) => {
        o.azureDate = this.azureDate;
        add = await axios.post(urls.azure.cost, o);
      });
      // 저장
      this.$emit("setData");
      this.$emit(
        "alertHandler",
        "[ " + this.azureDate + " ] is successfully updated."
      );
      this.modalHandler();
    },
  },
  created() {},
};
</script>
<style scoped>
.info-table > tbody > tr > th,
td {
  height: 40px;
  padding: 1em;
}
</style>
