<template>
  <CModal
    color="primary"
    :show.sync="modal.show"
    :closeOnBackdrop="false"
    :size="'xl'"
  >
    <!-- 헤더 -->
    <div slot="header"><strong>Script Info</strong></div>
    <!-- 상단 알람 -->
    <CAlert color="danger" :show.sync="alert.show" closeButton>
      {{ alert.msg }}
    </CAlert>

    <!-- 바디 -->
    <!-- 상세 조회 -->
    <CCard>
      <CCardBody>
        <CTabs
          variant="pills"
          id="tabs"
          @update:activeTab="setCodeFull"
          :active-tab="0"
          :key="tabKey"
        >
          <CTab class="mb-3script">
            <template slot="title">사용법</template>
            <CCard v-if="Boolean(script.id > 0)">
              <CCardBody>
                <span class="font-weight-bold">{{ script.name }}</span>
                <div class="small text-muted">
                  {{ script.scriptCode }}
                </div>
                <Codemirror :code="code" :mode="getScriptCodeType" />
              </CCardBody>
            </CCard>
          </CTab>
          <CTab class="mb-3">
            <template slot="title">소스 코드</template>

            <CCard v-if="Boolean(script.id > 0)">
              <CCardBody>
                <Codemirror
                  :code="codeFull"
                  :mode="getScriptCodeType"
                  :lineNumbers="true"
                />
              </CCardBody>
            </CCard>
          </CTab>
          <CTab class="mb-3" v-if="Boolean(script.id < 0 || whoami === 'A')">
            <template slot="title">설정</template>
            <CCard>
              <form id="scriptForm">
                <input type="hidden" v-model="script.id" name="id" />
                <table border="1" class="col-md-12 table-outline info-table">
                  <tbody>
                    <tr>
                      <th style="width: 10%">제목</th>
                      <td style="">
                        <CInput
                          v-model="script.name"
                          name="name"
                          style="margin:0"
                          required
                        />
                      </td>
                    </tr>
                    <tr>
                      <th style="width: 10%">분류</th>
                      <td colspan="3" style="">
                        <CSelect
                          :options="options['scriptCategory']"
                          :value.sync="script.scriptCategory"
                          name="scriptCategory"
                          placeholder="..."
                          style="margin:0"
                          required
                        />
                      </td>
                    </tr>
                    <tr>
                      <th style="width: 10%">Code</th>
                      <td colspan="3" style="">
                        <CSelect
                          :options="options['scriptCode']"
                          :value.sync="script.scriptCode"
                          name="scriptCode"
                          placeholder="..."
                          style="margin:0"
                          required
                        />
                      </td>
                    </tr>
                    <tr>
                      <th style="width: 10%">git 주소</th>
                      <td style="">
                        <CInput
                          v-model="script.url"
                          name="url"
                          style="margin:0"
                          required
                        />
                      </td>
                    </tr>
                    <tr>
                      <th style="width: 10%">설명</th>
                      <td style="">
                        <CInput
                          v-model="script.description"
                          name="description"
                          style="margin:0"
                          required
                        />
                      </td>
                    </tr>
                  </tbody>
                </table>
              </form>
            </CCard>
          </CTab>
        </CTabs>
      </CCardBody>
    </CCard>

    <!-- 하단 버튼 -->
    <div class="text-center row-fluid mb-1 mt-3">
      <button
        v-if="Boolean(role === 'A')"
        id="saveBtn"
        type="button"
        class="btn btn-primary"
        @click="save()"
      >
        저장
      </button>
      <button
        v-if="Boolean(role === 'A' && script.id > 0)"
        id="cancelBtn"
        type="button"
        class="btn btn-danger ml-4"
        @click="remove()"
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
    <!-- 푸터 -->
    <div slot="footer" />
  </CModal>
</template>

<script>
import Codemirror from "@/views/base/Codemirror";
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as validate from "@/assets/js/validate";
import * as util from "@/assets/js/util";

export default {
  components: { Codemirror },
  data() {
    return {
      role: sessionStorage.getItem("role"),
      modal: {
        show: false,
      },
      alert: {
        color: "",
        show: false,
        msg: "",
      },
      script: {},
      scriptEntity: {
        id: -1,
        scriptCategory: "",
        scriptCode: "",
        name: "",
        url: "",
        description: "",
        regUser: { id: "" },
        modUser: { id: "" },
      },
      options: {
        scriptCategory: [],
        scriptCode: [],
      },
      code: "",
      codeFull: "",
      mode: "",
      tabKey: 0,
      whoami: sessionStorage.getItem("role"),
    };
  },
  methods: {
    modalHandler(id) {
      // document.getElementById("scriptForm").classList.remove("was-validated");
      this.alertHandler();
      this.code = "";
      this.codeFull = "";
      this.setData(id);
      this.tabKey++;
      this.modal.show = !this.modal.show;
    },
    // 알람 컨트롤
    alertHandler(color, msg, bool) {
      this.alert.color = color;
      this.alert.msg = msg;
      this.alert.show = bool;
      if (!color) this.alert.show = false;
    },
    async setData(id) {
      if (id > 0) {
        this.script = await axios.get(urls.script.info + "/" + id);
        if (this.script.scriptCode === "SHELL") {
          this.code = 'curl -fsSL "' + this.script.url + '" | sudo sh';
        } else {
          this.code =
            '[scriptblock]::Create((Invoke-RestMethod -Uri "' +
            this.script.url +
            '"  -Method Get)).invoke()';
        }
      } else {
        this.script = util.cloneObject(this.scriptEntity);
      }
    },
    async save() {
      // 필수 값 체크
      let form = document.getElementById("scriptForm");
      let checkValidation = validate.check(form);
      if (!checkValidation) {
        this.alertHandler("danger", "필수 값을 입력해주세요", true);
        return;
      }

      this.script.regUser.id = sessionStorage.getItem("id");
      this.script.modUser.id = sessionStorage.getItem("id");

      let add =
        this.script.id > 0
          ? await axios.put(
              urls.script.update + "/" + this.script.id,
              this.script
            )
          : await axios.post(urls.script.update, this.script);

      if (add.id > 0) {
        this.script = add;
        this.$emit("setData");
        this.$emit(
          "alertHandler",
          "success",
          "[ " + add.name + " ] is successfully updated",
          5
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
        await axios.del(urls.script.update + "/" + this.script.id);
        this.modalHandler();
        this.$emit("setData");
        this.$emit(
          "alertHandler",
          "success",
          "[ " + this.script.name + " ] is successfully deleted",
          5
        );
      }
    },
    async setCodeFull(index) {
      if (!this.codeFull && this.script.id > 0 && index === 1) {
        let param = {
          url: this.script.url,
        };
        this.codeFull = await axios.post(urls.script.sourcecode, param);
      }
    },
  },
  computed: {
    getScriptCodeType() {
      if (this.script.scriptCode) {
        return this.options["scriptCode"].find(
          (o) => o.value === this.script.scriptCode
        ).label;
      }
    },
  },
  async created() {
    this.options["scriptCategory"] = await axios.get(urls.code.scriptCategory);
    this.options["scriptCode"] = await axios.get(urls.code.scriptCode);
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
