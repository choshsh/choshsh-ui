<template>
  <CCard>
    <CCardBody>
      <CTabs variant="pills">
        <CTab class="mb-3">
          <template slot="title">Prometheus</template>
          <CRow>
            <CCol>
              <CCard>
                <CCardBody>
                  <CRow class="mb-3">
                    <CCol col="11" class="mb-3 mb-xl-0"></CCol>
                    <CCol col="1" class="mb-3 mb-xl-0">
                      <CButton block color="primary" @click="openFormProm()">
                        New
                      </CButton>
                    </CCol>
                  </CRow>
                  <CModal
                    title="Prometheus Form"
                    color="primary"
                    :show.sync="formModal"
                    :size="'xl'"
                  >
                    <CRow>
                      <CCol col="12">
                        <CCard>
                          <CCardBody>
                            <form id="prometheusForm">
                              <table border="0" class="col-md-12">
                                <tbody>
                                  <tr>
                                    <th>서버 환경</th>
                                    <th>서버 선택</th>
                                    <th>라벨</th>
                                    <th></th>
                                  </tr>
                                  <tr>
                                    <td style="width:15%">
                                      <CSelect
                                        :options="['선택', 'Physical', 'VM']"
                                        :value.sync="promItem.promServerEnv"
                                        @change="
                                          updateEnv(promItem.promServerEnv)
                                        "
                                      />
                                    </td>
                                    <td class="align-top">
                                      <model-select
                                        :options="promOptions"
                                        v-model="promItem.promServerNo"
                                        placeholder="검색어 입력  (업무명 or 호스트명 or IP)"
                                      />
                                    </td>
                                    <td style="width:20%">
                                      <CSelect
                                        :options="labels"
                                        :value.sync="promItem.promLabel"
                                        :disabled="
                                          Boolean(promItem.promServerNo === '')
                                        "
                                      />
                                    </td>
                                    <td style="width:10%" class="align-top">
                                      <button
                                        id="saveBtn"
                                        type="button"
                                        class="btn btn-primary"
                                        @click="updatePromVal()"
                                      >
                                        적용
                                      </button>
                                    </td>
                                  </tr>
                                  <tr>
                                    <th colspan="2">Prometheus IP</th>
                                    <th>Prometheus Port</th>
                                    <th></th>
                                  </tr>
                                  <tr>
                                    <td colspan="2">
                                      <CInput v-model="promItem.promIp" />
                                    </td>
                                    <td>
                                      <CInput v-model="promItem.promPort" />
                                    </td>
                                    <td class="align-top">
                                      <button
                                        id="testBtn"
                                        type="button"
                                        :class="testBtnClass"
                                        @click="httpTest()"
                                      >
                                        TEST
                                      </button>
                                    </td>
                                  </tr>
                                </tbody>
                              </table>
                              <CButton
                                @click="collapse = !collapse"
                                color="success"
                                class="mb-2"
                              >
                                Agent 설치 방법
                              </CButton>
                              <CCollapse :show="collapse">
                                <CCard>
                                  <CCardHeader>
                                    <strong>Windows</strong>
                                  </CCardHeader>
                                  <CCardBody>
                                    <p>
                                      Powershell(관리자) 실행 -> 아래 스크립트
                                      실행
                                    </p>
                                    <p>
                                      [scriptblock]::Create((Invoke-RestMethod
                                      -Uri
                                      "http://51.2.30.83/script/install_windows-exporter.ps1"
                                      -Method Get)).invoke()
                                    </p>
                                    <!-- <hr />
                                    <p>서비스 이름: windows-exporter</p>
                                    <p>서비스 포트: 9182</p>
                                    <p>에이전트 설치 경로: C:\Users\설치계정</p>
                                    <p>
                                      삭제: (Get-WmiObject win32_service -Filter
                                      "name='windows-exporter'").delete()
                                    </p> -->
                                  </CCardBody>
                                </CCard>
                                <CCard>
                                  <CCardHeader>
                                    <strong>Linux</strong>
                                  </CCardHeader>
                                  <CCardBody>
                                    <p>
                                      curl
                                      http://51.2.30.83/script/install_node-exporter.sh
                                      | sudo sh
                                    </p>
                                  </CCardBody>
                                </CCard>
                              </CCollapse>
                              <div class="text-center row-fluid mb-1">
                                <button
                                  id="saveBtn"
                                  type="button"
                                  class="btn btn-primary"
                                  @click="saveProm()"
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
                    <div slot="footer" />
                  </CModal>
                  <CDataTable
                    :items="promList"
                    :fields="fields"
                    :items-per-page="10"
                    :table-filter="tableFilter"
                    :loading="loading"
                    :pagination="pagination"
                    :itemsPerPageSelect="{ values: [10, 50, 100, 500] }"
                    hover
                    column-filter
                    sorter
                  >
                    <template #edit="{ item }">
                      <td class="py-2">
                        <CButton
                          color="primary"
                          variant="outline"
                          square
                          size="sm"
                          @click="editProm(item)"
                          >Edit</CButton
                        >
                      </td>
                    </template>
                    <template #delete="{ item }">
                      <td class="py-2">
                        <CButton
                          color="danger"
                          variant="outline"
                          square
                          size="sm"
                          @click="deleteProm(item.promSeq)"
                          >Del</CButton
                        >
                      </td>
                    </template>
                  </CDataTable>
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
        </CTab>
        <CTab class="mb-3" v-if="Boolean(userId === 'choshsh')">
          <template slot="title">System</template>
          <CRow>
            <CCol sm="6">
              <CCard>
                <CCardHeader><strong>Header Menu</strong></CCardHeader>
                <CCardBody>
                  <table border="0" class="col-md-12">
                    <tbody>
                      <tr>
                        <th>Name</th>
                        <th>URL</th>
                        <th style="width:5%;"></th>
                      </tr>
                      <tr v-for="(item, index) in header" v-bind:key="index">
                        <td>
                          <CInput v-model="item.name" />
                        </td>
                        <td>
                          <CInput
                            v-model="item.url"
                            placeholder="http(s)://www.choshsh.com"
                          />
                        </td>
                        <td
                          class="align-top text-center"
                          v-if="Boolean(index > 0 || header.length > 1)"
                        >
                          <button
                            class="btn btn-danger"
                            @click="remove(header, index)"
                          >
                            <CIcon name="cil-minus" size="sm" />
                          </button>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="5" class="align-top text-center">
                          <button
                            class="btn btn-success mb-2"
                            @click="add(header)"
                          >
                            <CIcon name="cil-plus" size="sm" />
                          </button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                  <div class="text-center row-fluid mb-1 mt-4">
                    <button
                      id="saveBtn"
                      type="button"
                      class="btn btn-primary"
                      @click="save(header, 'header')"
                    >
                      저장
                    </button>
                  </div>
                </CCardBody>
              </CCard>
            </CCol>
            <CCol sm="6">
              <CCard>
                <CCardHeader><strong>Monitoring Menu</strong></CCardHeader>
                <CCardBody>
                  <table border="0" class="col-md-12">
                    <tbody>
                      <tr>
                        <th>Name</th>
                        <th>URL</th>
                        <th>Height</th>
                        <th style="width:5%;"></th>
                      </tr>
                      <tr
                        v-for="(item, index) in monitoring"
                        v-bind:key="index"
                      >
                        <td>
                          <CInput v-model="item.name" />
                        </td>
                        <td>
                          <CInput
                            v-model="item.to"
                            placeholder="http(s)://www.choshsh.com"
                          />
                        </td>
                        <td>
                          <CInput v-model="item.height" placeholder="px" />
                        </td>
                        <td
                          class="align-top text-center"
                          v-if="Boolean(index > 0 || header.length > 1)"
                        >
                          <button
                            class="btn btn-danger"
                            @click="remove(monitoring, index)"
                          >
                            <CIcon name="cil-minus" size="sm" />
                          </button>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="5" class="align-top text-center">
                          <button
                            class="btn btn-success mb-2"
                            @click="add(monitoring)"
                          >
                            <CIcon name="cil-plus" size="sm" />
                          </button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                  <div class="text-center row-fluid mb-1 mt-4">
                    <button
                      id="saveBtn"
                      type="button"
                      class="btn btn-primary"
                      @click="save(monitoring, 'monitoring')"
                    >
                      저장
                    </button>
                  </div>
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
        </CTab>
      </CTabs>
    </CCardBody>
  </CCard>
</template>

<script>
import axios from "axios";
import { ModelSelect } from "vue-search-select";
import "vue-search-select/dist/VueSearchSelect.css";

const fields = [
  { key: "promLabel", label: "라벨", _style: "min-width:100px" },
  { key: "serverNm", label: "업무명", _style: "min-width:150px" },
  { key: "promIp", label: "IP", _style: "min-width:120px" },
  { key: "promServerEnv", label: "서버환경", _style: "min-width:80px" },
  { key: "serverLocation", label: "사업장", _style: "min-width:80px" },
  {
    key: "edit",
    label: "",
    _style: "width:1%",
    filter: false,
    sorter: false,
  },
  {
    key: "delete",
    label: "",
    _style: "width:1%",
    filter: false,
    sorter: false,
  },
];

export default {
  name: "Settings",
  components: { ModelSelect },
  data() {
    return {
      nav: [],
      header: [],
      monitoring: [],
      prometheus: [],
      loading: true,
      fields: fields,
      promList: [],
      promItem: {
        promSeq: 0,
        promIp: "",
        promPort: "",
        promServerEnv: "",
        promServerNo: "",
        promLabel: "",
        isTest: false,
      },
      promItemSub: {},
      formModal: false,
      promOptions: [{ value: "", text: "" }],
      serverOptions: [],
      vmOptions: [],
      servers: [],
      vms: [],
      labels: [],
      collapse: false,
      testBtnClass: "btn btn-primary",
      tableFilter: { label: "검색", placeholder: "검색어 입력..." },
      pagination: { align: "end" },
      userId: sessionStorage.getItem("userId"),
    };
  },
  methods: {
    getMenu() {
      axios
        .get("/api/menu")
        .then((res) => {
          this.header = res.data.filter((el) => el.category === "header");
          this.monitoring = res.data.filter((el) => el.category === "iframe");
        })
        .catch((e) => console.log(e));
    },
    add(array) {
      var keys = Object.keys(array[array.length - 1]);
      var newItem = new Object();

      keys.forEach((el) => {
        newItem[el] = "";
      });

      array.push(newItem);
    },
    remove(array, index) {
      array.splice(index, 1);
    },
    save(array, entity) {
      var url;
      var param;

      if (entity == "nav") {
        url = "/api/navs";
        param = array.map((el) => {
          el._name = "CSidebarNavItem";
          return el;
        });
      } else if (entity == "header") {
        url = "/api/headers";
        param = array;
      } else if (entity == "monitoring") {
        url = "/api/monitorings";
        param = array;
      }

      axios
        .post(url, param)
        .then((res) => {
          res.status == 200 ? this.onSuccess(entity) : alert("Fail");
        })
        .catch((e) => console.log(e));
    },
    openFormProm(promItem) {
      this.promClear();
      if (promItem) this.promItem = promItem;
      this.formModal = true;
    },
    onSuccess(entity) {
      alert("Success");
    },
    setPromList() {
      axios
        .get("/api/prometheus")
        .then((res) => {
          this.promList = res.data;
          this.loading = false;
        })
        .catch((e) => console.log(e));
    },
    cancel() {
      this.formModal = false;
    },
    setServerOptions() {
      axios
        .get("/api/servers")
        .then((res) => {
          this.servers = res.data;
          this.serverOptions = res.data.map((o) => {
            return {
              value: o.ciNo,
              text:
                o.nickname +
                " / " +
                o.astNm +
                " / " +
                (o.serverIp ? o.serverIp : "-") +
                " / " +
                o.ciNo,
            };
          });
        })
        .catch((e) => console.log(e));
    },
    setVmOptions() {
      axios
        .get("/api/vms")
        .then((res) => {
          this.vms = res.data;
          this.vmOptions = res.data.map((o) => {
            return {
              value: o.vmNo,
              text:
                o.vmNickname + " / " + o.vmNm + " / " + (o.vmIp ? o.vmIp : "-"),
            };
          });
        })
        .catch((e) => console.log(e));
    },
    updateEnv(env) {
      this.promOptions =
        env === "Physical" ? this.serverOptions : this.vmOptions;
      this.setLabels(env);
    },
    updatePromVal() {
      let ip =
        this.promItem.promServerEnv === "Physical"
          ? this.servers.find((o) => o.ciNo === this.promItem.promServerNo)
              .serverIp
          : this.vms.find((o) => o.vmNo === this.promItem.promServerNo).vmIp;
      this.promItem.promIp = ip.trim();
      this.promItem.promPort =
        this.promItem.promLabel.indexOf("Windows") > 0 ? "9182" : "9100";
    },
    promClear() {
      this.promItem = {
        promSeq: 0,
        promIp: "",
        promPort: "",
        promServerEnv: "",
        promServerNo: "",
        promLabel: "",
        isTest: false,
      };
      this.collapse = false;
    },
    httpTest() {
      if (
        this.promItem.promIp.length > 15 ||
        this.promItem.promIp < 8 ||
        this.promItem.promIp.indexOf(",") > 0
      ) {
        alert("IP를 정확하게 1개만 입력해주세요");
        return;
      }

      const url =
        "http://" + this.promItem.promIp + ":" + this.promItem.promPort;

      axios
        .get("/api/prometheus/test/?url=" + url)
        .then((res) => {
          if (res.data === 200) {
            this.testBtnClass = "btn btn-success";
            this.promItem.isTest = true;
            alert("성공");
          } else {
            this.testBtnClass = "btn btn-danger";
            this.promItem.isTest = false;
            alert("실패");
          }
        })
        .catch((e) => console.log(e));
    },
    setLabels(env) {
      var df = [
        "선택",
        "S-Windows-Physical",
        "S-Windows-VM",
        "S-Linux-Physical",
        "S-Linux-VM",
        "K-Windows-Physical",
        "K-Windows-VM",
        "K-Linux-Physical",
        "K-Linux-VM",
      ];
      this.labels = df.filter((o) => o.indexOf(env) > 0 || o === "선택");
      this.testBtnClass = "btn btn-primary";
    },
    saveProm() {
      if (!this.promItem.isTest) {
        alert("TEST를 먼저 진행해주세요");
        return;
      }
      // update
      if (this.promItem.promSeq > 0) {
        axios
          .put("/api/prometheus", this.promItem)
          .then((res) => {
            if (res.data > 0) {
              alert("성공");
              this.formModal = false;
              this.setPromList();
            } else {
              alert("실패");
            }
          })
          .catch((e) => console.log(e));
      }
      // insert
      else {
        axios
          .post("/api/prometheus", this.promItem)
          .then((res) => {
            if (res.data > 0) {
              alert("성공");
              this.formModal = false;
              this.setPromList();
            } else if (res.data === 0) {
              alert("이미 등록되어 있습니다");
            } else {
              alert("실패");
            }
          })
          .catch((e) => console.log(e));
      }
    },
    editProm(promItem) {
      this.openFormProm(promItem);
      this.updateEnv(promItem.promServerEnv);
    },
    deleteProm(promSeq) {
      if (confirm("정말 삭제하겠습니까?")) {
        axios
          .delete("/api/prometheus", { data: { promSeq: promSeq } })
          .then((res) => {
            if (res.data > 0) {
              alert("성공");
              this.setPromList();
            } else {
              alert("실패");
            }
          })
          .catch((e) => console.log(e));
      }
    },
  },
  created() {
    alert("준비 중인 페이지입니다.");
    this.$router.push("/");
    this.getMenu();
    this.setPromList();
    this.setServerOptions();
    this.setVmOptions();
  },
};
</script>
