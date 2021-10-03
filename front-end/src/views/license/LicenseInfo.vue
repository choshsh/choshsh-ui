<template>
  <CModal
    color="primary"
    :show.sync="show"
    :closeOnBackdrop="false"
    :size="'xl'"
  >
    <!-- 헤더 -->
    <div slot="header"><strong>Lincense Info</strong></div>

    <!-- 라이선스 정보 -->
    <CCard>
      <CCardHeader class="font-weight-bold">라이선스 정보</CCardHeader>
      <CCardBody>
        <!-- 알람 -->
        <CAlert
          :color="license.alert.color"
          :show.sync="license.alert.show"
          closeButton
          >{{ license.alert.msg }}</CAlert
        >
        <!-- 바디 -->
        <form id="licenseForm">
          <CRow>
            <CCol col="2">
              <CSelect
                label="카테고리"
                :options="options['category']"
                :value.sync="item.licenseCategory.id"
                name="category"
                placeholder="..."
                required
              />
            </CCol>
            <CCol col="5">
              <CInput
                label="라이선스"
                name="name"
                v-model="item.name"
                required
              />
            </CCol>
            <CCol col="1">
              <CInput
                label="개수"
                type="number"
                name="count"
                v-model="item.count"
                required
              />
            </CCol>
            <CCol col="2">
              <CInput
                label="벤더"
                name="vendor"
                v-model="item.vendor"
                required
              />
            </CCol>
            <CCol col="2">
              <CInput
                label="거래처"
                name="vendor"
                v-model="item.client"
                required
              />
            </CCol>
          </CRow>
        </form>

        <!-- 하단 버튼 -->
        <div class="text-center row-fluid mb-1">
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
            class="btn btn-secondary ml-3"
            @click="cancel()"
          >
            닫기
          </button>
        </div>
      </CCardBody>
    </CCard>
    <!-- 라이선스 사용 내역 -->
    <CCard v-if="Boolean(item.id > 0)">
      <CCardHeader class="font-weight-bold">
        사용 내역 (미사용: {{ notUseCount }})
      </CCardHeader>
      <CCardBody>
        <!-- 라이선스 사용 내역 알림 -->
        <CAlert
          :color="licenseUse.alert.color"
          :show.sync="licenseUse.alert.counter"
          closeButton
        >
          {{ licenseUse.alert.msg }}
          <CProgress
            :max="5"
            :value="licenseUse.alert.counter"
            height="3px"
            :color="licenseUse.alert.color"
            animate
          />
        </CAlert>
        <!-- 라이선스 사용 내역 입력란 -->
        <table
          class="col-md-12 table table-sm table-borderless"
          v-if="licenseUse.showForm"
        >
          <tbody>
            <!-- 테이블 헤더 -->
            <tr>
              <th style="width: 10%">서버 환경</th>
              <th>서버</th>
              <th style="width:10%">사용 개수</th>
              <th style="width:7%;"></th>
              <th style="width:7%;"></th>
              <th style="width:7%;"></th>
            </tr>
            <tr>
              <td>
                <CSelect
                  :options="options['serverEnv']"
                  :value.sync="licenseUse.item.serverEnv"
                  placeholder="..."
                  @change="updateEnv(licenseUse.item.serverEnv)"
                />
              </td>
              <td>
                <model-select
                  :options="options['serverList']"
                  v-model="licenseUse.item.serverId"
                  placeholder="검색  (사업장 or 업무명 or 서버명 or IP)"
                />
              </td>
              <td>
                <CInput type="number" v-model="licenseUse.item.count" />
              </td>
              <td class="align-top text-right">
                <button class="btn btn-primary mb-2" @click="saveLicenseUse()">
                  저장
                </button>
              </td>
              <td class="align-top text-right">
                <button
                  class="btn btn-secondary mb-2"
                  @click="licenseUseFormHandler(false)"
                >
                  취소
                </button>
              </td>
              <td class="align-top text-right">
                <button
                  class="btn btn-danger mb-2"
                  @click="delLicenseUse(licenseUse.item.id)"
                >
                  삭제
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <CRow class="mb-3">
          <CCol col="10" class="mb-3 mb-xl-0"></CCol>
          <CCol col="2" class="mb-3 mb-xl-0">
            <CButton block color="primary" @click="licenseUseFormHandler(true)"
              >서버 매핑</CButton
            >
          </CCol>
        </CRow>
        <CDataTable
          :items="licenseUse.items"
          :fields="licenseUse.fields"
          :items-per-page="10"
          :itemsPerPageSelect="{ values: [15, 50, 100, 500] }"
          :pagination="{ align: 'center' }"
          :table-filter="{ label: '검색', placeholder: '검색어 입력...' }"
          :loading="licenseUse.loading"
          :size="'sm'"
          hover
          sorter
        >
          <template #nickname="{ item }">
            <td>
              <a
                href="javascript:;"
                class="link-primary"
                @click="licenseUseFormHandler(true, item.id)"
              >
                {{ item.nickname }}
              </a>
            </td>
          </template>
        </CDataTable>
      </CCardBody>
    </CCard>

    <div slot="footer" />
  </CModal>
</template>
<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as util from "@/assets/js/util";
import * as validate from "@/assets/js/validate";
import { ModelSelect } from "vue-search-select";
import "vue-search-select/dist/VueSearchSelect.css";

// 라이선스 사용 내역
const fields = [
  { key: "location", label: "사업장", _style: "width:8%" },
  { key: "serverEnvName", label: "서버 환경", _style: "width:10%" },
  { key: "nickname", label: "업무명", _style: "min-width:15%" },
  { key: "name", label: "서버명", _style: "min-width:15%" },
  { key: "usage", label: "용도", _style: "width:7%" },
  { key: "ip", label: "IP", _style: "width:10%" },
  { key: "user", label: "담당자", _style: "width:7%" },
  { key: "count", label: "사용 개수", _style: "width:10%" },
];

export default {
  name: "Licenses",
  components: { ModelSelect },
  data() {
    return {
      item: {},
      notUseCount: 0,
      itemEntity: {
        id: -1,
        name: "",
        vendor: "",
        client: "",
        licenseCategory: {
          id: -1,
        },
        regUser: {},
        modUser: {},
      },
      alert: {
        show: false,
        msg: "",
        color: "",
      },
      options: {
        category: [],
        serverEnv: [],
        serverList: [],
        vms: [],
        servers: [],
      },
      license: {
        alert: {
          show: 0,
          msg: "",
          color: "",
        },
      },
      licenseUse: {
        items: [],
        item: {},
        itemEntity: {
          id: -1,
          serverEnv: "",
          serverId: "",
          count: 0,
          licenseEntity: {
            id: -1,
          },
        },
        fields: fields,
        showForm: false,
        alert: {
          counter: 0,
          msg: "",
          color: "",
        },
        loading: false,
      },
      vms: [],
      servers: [],
      show: false,
    };
  },
  props: {
    editable: { default: false, type: Boolean },
  },
  methods: {
    modalHandler(id) {
      if (document.getElementById("licenseForm"))
        document
          .getElementById("licenseForm")
          .classList.remove("was-validated");
      this.licenseAlertHandler();
      this.licenseUseAlertHandler();
      this.setOptions();
      this.setData(id);
      this.show = !this.show;
    },
    // 라이선스 정보 데이터
    async setData(id) {
      // 데이터 초기 설정
      if (id > 0) {
        this.item = await axios.get(urls.license.info + "/" + id);
        this.setLicenseUseData(id);
      } else {
        this.item = util.cloneObject(this.itemEntity);
        this.setLicenseUseData(-1);
      }
    },
    // 라이선스 사용 내역 데이터
    async setLicenseUseData(id) {
      this.licenseUse.loading = true;
      if (id > 0) {
        let data = await axios.get(urls.license.use + "/bylicense/" + id);
        data.forEach((o) => {
          // 물리
          if (o.serverEnv === "P") {
            let item = this.servers.find((s) => s.ciNo === o.serverId);
            o.nickname = item.nickname;
            o.name = item.astNm;
            o.usage = item.serverUsage;
            o.ip = item.serverIp;
            o.location = item.locationNm;
            o.user = item.mngEmplNm;
          }
          // 가상
          else if (o.serverEnv === "V") {
            let item = this.vms.find((v) => v.id === parseInt(o.serverId));
            o.nickname = item.nickname;
            o.name = item.name;
            o.usage = item.vmUsageName;
            o.ip = item.ip;
            o.location = item.locationName;
            o.user = item.user;
          }
        });
        this.notUseCount =
          this.item.count -
          data.reduce(function(accumulator, currentValue) {
            return accumulator + currentValue.count;
          }, 0);
        this.licenseUse.items = data;
      }
      this.licenseUse.loading = false;
    },
    // select 데이터 설정
    async setOptions() {
      this.options["category"] = [];
      let data = await axios.get(urls.license.category);
      data.forEach((o) =>
        this.options["category"].push({ value: o.id, label: o.name })
      );
      let serverEnv = await axios.get(urls.code.serverEnv);
      this.$set(this.options, "serverEnv", serverEnv);
    },
    // 저장
    async save() {
      this.item.regUser = {};
      this.item.modUser = {};
      this.item.regUser.id = sessionStorage.getItem("id");
      this.item.modUser.id = sessionStorage.getItem("id");

      let form = document.getElementById("licenseForm");
      let checkValidation = validate.check(form);
      if (!checkValidation) {
        this.licenseAlertHandler("danger", "필수 값을 입력하세요", true);
        return;
      }

      let add =
        this.item.id > 0
          ? await axios.put(urls.license.update + "/" + this.item.id, this.item)
          : await axios.post(urls.license.update, this.item);

      if (add.id > 0) {
        this.setData(add.id);
        this.licenseAlertHandler("success", "successfully updated", true);
      } else {
        this.licenseAlertHandler("danger", "failed", true);
      }
    },
    // 모달 닫기
    cancel() {
      this.alert.show = false;
      this.licenseUseFormHandler(false);
      this.$emit("setData");
      this.$emit("infoModalHandler");
    },
    // vm, server Select 옵션 데이터 설정
    async setServerAndVm() {
      this.vms = await axios.get(urls.vm.list);

      this.options["vms"] = this.vms.map((o) => {
        return {
          value: o.id,
          text:
            o.locationName +
            ", " +
            o.nickname +
            ", " +
            o.name +
            ", " +
            (o.ip ? o.ip : "-"),
        };
      });
      this.servers = await axios.get(urls.server.list);
      this.options["servers"] = this.servers.map((o) => {
        return {
          value: o.ciNo,
          text:
            o.locationNm +
            ", " +
            o.nickname +
            ", " +
            o.astNm +
            ", " +
            (o.serverIp ? o.serverIp : "-") +
            ", " +
            o.ciNo,
        };
      });
    },
    // 서버 환경에 따라 리스트 옵션 변경
    updateEnv(serverEnv) {
      let data = serverEnv === "P" ? this.options.servers : this.options.vms;
      this.$set(this.options, "serverList", data);
    },
    // 라이선스 사용 등록
    async saveLicenseUse() {
      // 이미 등록된 서버인지 검사
      let checkServer =
        this.licenseUse.item.serverEnv === "P"
          ? this.licenseUse.items.filter(
              (o) => o.serverId === this.licenseUse.item.serverId
            ).length
          : this.licenseUse.items.filter(
              (o) => o.serverId === this.licenseUse.item.serverId
            ).length;
      if (checkServer > 0 && this.licenseUse.item.id < 1) {
        this.licenseUseAlertHandler("danger", "이미 등록된 서버입니다", 5);
        return;
      }
      // 개수 유효성 검사
      if (this.licenseUse.item.id > 0) {
        // 기존
        if (
          this.licenseUse.item.count >
          this.notUseCount +
            this.licenseUse.items.find((o) => o.id === this.licenseUse.item.id)
              .count
        ) {
          this.licenseUseAlertHandler("danger", "최대 사용 개수 초과", 5);
          return;
        }
      } else {
        //  신규
        if (this.licenseUse.item.count > this.notUseCount) {
          this.licenseUseAlertHandler("danger", "최대 사용 개수 초과", 5);
          return;
        }
      }
      // FK 설정
      this.licenseUse.item.licenseEntity.id = this.item.id;
      let add =
        this.licenseUse.item.id > 0
          ? await axios.put(
              urls.license.use + "/" + this.licenseUse.item.id,
              this.licenseUse.item
            )
          : await axios.post(urls.license.use, this.licenseUse.item);

      if (add.id > 0) {
        this.setLicenseUseData(this.item.id);
        this.licenseUseFormHandler(false);
        this.$emit("setData");
        this.licenseUseAlertHandler("success", "successfully updated", 5);
      } else {
        this.licenseUseAlertHandler("danger", "failed", 5);
      }
    },
    // 라이선스 사용 삭제
    async delLicenseUse(id) {
      let result = await axios.del(urls.license.use + "/" + id);
      if (result > 0) {
        this.licenseUseAlertHandler("success", "successfully deleted", 5);
      } else {
        this.licenseUseAlertHandler("danger", "failed", 5);
      }
      this.setData(this.item.id);
    },
    // 라이선스 사용 입력란 컨트롤
    async licenseUseFormHandler(bool, id) {
      // 알람 초기화
      this.licenseUseAlertHandler();
      let data;
      if (id > 0) {
        data = util.cloneObject(await axios.get(urls.license.use + "/" + id));
        this.updateEnv(data.serverEnv);
      } else {
        data = util.cloneObject(this.licenseUse.itemEntity);
      }
      if (data.serverEnv === "V") data.serverId = parseInt(data.serverId);
      this.licenseUse.item = data;
      this.licenseUse.showForm = bool;
    },
    // 라이선스 사용 알람 컨트롤
    licenseAlertHandler(color, msg, bool) {
      this.license.alert.color = color;
      this.license.alert.msg = msg;
      this.license.alert.show = bool;
      if (!color) this.license.alert.show = false;
    },
    licenseUseAlertHandler(color, msg, counter) {
      this.licenseUse.alert.color = color;
      this.licenseUse.alert.msg = msg;
      this.licenseUse.alert.counter = counter;
      if (!color) this.licenseUse.alert.counter = 0;
    },
  },
  created() {
    this.setServerAndVm();
  },
};
</script>
