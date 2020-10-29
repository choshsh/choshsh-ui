<template>
  <CRow>
    <CCol col="12">
      <CCard>
        <CCardHeader>
          <strong>VM</strong>
          <small>Form</small>
        </CCardHeader>
        <CCardBody>
          <form id="vmForm">
            <CRow>
              <CCol sm="6">
                <CInput label="VM명" v-model="vmNm" placeholder="Enter VM명" />
              </CCol>
              <CCol sm="6">
                <CInput
                  label="업무명"
                  v-model="vmNickname"
                  placeholder="Enter 업무명"
                />
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="6">
                <CInput
                  label="호스트명"
                  v-model="vmHostname"
                  placeholder="Enter 호스트명"
                />
              </CCol>
              <CCol sm="6">
                <CInput label="UUID" v-model="vmNo" placeholder="Enter UUID" />
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="6">
                <CInput
                  label="서비스 IP"
                  v-model="vmIp"
                  placeholder="Enter IP"
                />
              </CCol>
              <CCol sm="6">
                <CSelect
                  label="용도"
                  :options="this.options['usage']"
                  :value.sync="vmUsageCd"
                />
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="6">
                <CInput
                  label="운영 담당자"
                  v-model="vmUseEmpl"
                  placeholder="Enter 운영 담당자"
                />
              </CCol>
              <CCol sm="6">
                <CInput
                  label="관리 담당자"
                  v-model="vmMngEmpl"
                  placeholder="Enter 관리 담당자"
                />
              </CCol>
            </CRow>
            <CRow>
              <!-- <CCol sm="4">
                <CRow form class="form-group">
                  <CCol sm="3">사업장 {{vmClusterCd}}</CCol>
                  <CInputRadioGroup
                    class="col-sm-9"
                    :options="[{value:'S0120703', label:'본사'},{value:'G1510300', label:'구미'}]"
                    :custom="true"
                    :checked="`Option 4`"
                    :inline="true"
                    :name="vmClusterLocation"
                  />
                </CRow>
              </CCol>-->
              <CCol sm="4">
                <CSelect
                  label="VM 호스트"
                  :options="this.options['cluster']"
                  :value.sync="vmClusterCd"
                />
              </CCol>
              <CCol sm="4">
                <CSelect
                  label="운영체제"
                  :options="this.options['os']"
                  :value.sync="vmOsCd"
                />
              </CCol>
              <CCol sm="4">
                <CSelect
                  label="전원"
                  :options="['...', 'Y', 'N']"
                  :value.sync="vmPowerOn"
                />
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="4">
                <CInput
                  label="CPU"
                  v-model="vmCpuCore"
                  type="Number"
                  append="Cores"
                />
              </CCol>
              <CCol sm="4">
                <CInput
                  label="Memory"
                  v-model="vmMemory"
                  type="Number"
                  append="GB"
                />
              </CCol>
              <CCol sm="4">
                <CInput
                  label="Disk"
                  v-model="vmDisk"
                  type="Number"
                  append="GB"
                />
              </CCol>
            </CRow>
            <CRow>
              <CCol sm="12">
                <CTextarea
                  label="Comment"
                  placeholder="Content..."
                  rows="5"
                  v-model="vmComment"
                />
              </CCol>
            </CRow>
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
                @click="cancel()"
              >
                취소
              </button>
            </div>
          </form>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
</template>

<script>
import axios from "axios";

export default {
  name: "VmsForm",
  data() {
    return {
      vmFormKey: 0,
      options: {
        cluster: [{ value: 0, label: "..." }],
        os: [{ value: 0, label: "..." }],
        usage: [{ value: 0, label: "..." }],
        vmClusterLocation: "",
      },
      selectedOption: {
        cluster: "",
        os: "",
        usage: "",
      },
      vmNm: "",
      vmNo: "",
      vmNoParam: this.$route.params.vmNo,
      vmNickname: "",
      vmClusterCd: "",
      vmUsageCd: "",
      vmOsCd: "",
      vmIp: "",
      vmCpuCore: 0,
      vmMemory: 0,
      vmDisk: 0,
      vmUseEmpl: "",
      vmMngEmpl: "",
      vmPowerOn: "",
      vmClusterLocation: "",
      vmComment: "",
      vmHostname: "",
      validateArr: [
        "vmNm",
        "vmNo",
        "vmNickname",
        "vmClusterCd",
        "vmUsageCd",
        "vmOsCd",
      ],
    };
  },
  methods: {
    setData() {
      axios
        .post("/api/vmForm", { vmNo: "" })
        .then((res) => {
          // select options 설정

          res.data.getVmCdList["cluster"].forEach((o) => {
            this.options["cluster"].push({
              value: o.vmClusterCd,
              label: o.vmClusterNm,
            });
          });
          res.data.getVmCdList["os"].forEach((o) => {
            this.options["os"].push({ value: o.vmOsCd, label: o.vmOsDetail });
          });
          res.data.getVmCdList["usage"].forEach((o) => {
            this.options["usage"].push({
              value: o.vmUsageCd,
              label: o.vmUsageNm,
            });
          });
        })
        .catch((e) => console.log(e));
    },
    save() {
      const form = {
        vmNm: this.vmNm,
        vmNo: this.vmNo,
        vmNickname: this.vmNickname,
        vmClusterCd: this.vmClusterCd,
        vmUsageCd: this.vmUsageCd,
        vmOsCd: this.vmOsCd,
        vmIp: this.vmIp,
        vmCpuCore: this.vmCpuCore,
        vmMemory: this.vmMemory,
        vmDisk: this.vmDisk,
        vmUseEmpl: this.vmUseEmpl,
        vmMngEmpl: this.vmMngEmpl,
        vmPowerOn: this.vmPowerOn,
        vmComment: this.vmComment,
        vmHostname: this.vmHostname,
      };
      if (this.validateCheck(form)) {
        axios
          .post("/api/vmSave", form)
          .then((res) => {
            if (res.data > 0) {
              alert("저장 성공");
              this.$router.push("vms");
            } else {
              alert("저장 실패");
            }
          })
          .catch((e) => console.log(e));
      } else {
        alert("필수 값을 입력해주세요.");
      }
    },
    validateCheck(form) {
      let check = true;
      this.validateArr.forEach((s) => {
        if (!form[s]) {
          check = false;
        }
      });
      return check;
    },
    getVm() {
      if (this.vmNoParam) {
        axios
          .post("/api/getVm", { vmNo: this.vmNoParam })
          .then((res) => {
            this.vmNm = res.data.vmNm;
            this.vmNo = res.data.vmNo;
            this.vmNickname = res.data.vmNickname;
            this.vmClusterCd = res.data.vmClusterCd;
            this.vmUsageCd = res.data.vmUsageCd;
            this.vmOsCd = res.data.vmOsCd;
            this.vmIp = res.data.vmIp;
            this.vmCpuCore = res.data.vmCpuCore;
            this.vmMemory = res.data.vmMemory;
            this.vmDisk = res.data.vmDisk;
            this.vmUseEmpl = res.data.vmUseEmpl;
            this.vmMngEmpl = res.data.vmMngEmpl;
            this.vmPowerOn = res.data.vmPowerOn === "ON" ? "Y" : "N";
            this.vmComment = res.data.vmComment;
            this.vmHostname = res.data.vmHostname;
          })
          .catch((e) => console.log(e));
      }
    },
    cancel() {
      this.$router.push("vms");
    },
  },
  created: function () {
    this.setData();
    this.getVm();
  },
};
</script>