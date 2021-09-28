<template>
  <CCard>
    <CCardBody>
      <CRow>
        <CCol sm="6">
          <CCard>
            <CCardHeader><strong>헤더 설정</strong></CCardHeader>
            <CCardBody>
              <table border="0" class="col-md-12">
                <tbody>
                  <tr>
                    <th style="width: 8%">순서</th>
                    <th class="w-25">이름</th>
                    <th>URL</th>
                    <th style="width: 10%">새 탭</th>
                    <th style="width:5%;"></th>
                  </tr>
                  <tr v-for="(item, index) in headers" v-bind:key="index">
                    <td>
                      <CInput v-model="item.sort" />
                    </td>
                    <td>
                      <CInput v-model="item.name" />
                    </td>
                    <td>
                      <CInput v-model="item.url" />
                    </td>
                    <td>
                      <CInput v-model="item.isBlank" />
                    </td>
                    <td
                      class="align-top text-center"
                      v-if="Boolean(index > 0 || headers.length > 1)"
                    >
                      <button
                        class="btn btn-danger"
                        @click="remove(headers, index)"
                      >
                        <CIcon name="cil-minus" size="sm" />
                      </button>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="5" class="align-top text-center">
                      <button
                        class="btn btn-success mb-2"
                        @click="add(headers)"
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
                  @click="save('header')"
                >
                  저장
                </button>
              </div>
            </CCardBody>
          </CCard>
        </CCol>
        <CCol sm="6">
          <CCard>
            <CCardHeader><strong>iframe 설정</strong></CCardHeader>
            <CCardBody>
              <table border="0" class="col-md-12">
                <tbody>
                  <tr>
                    <th style="width: 8%">순서</th>
                    <th style="width: 10%">분류</th>
                    <th style="width: 15%">이름</th>
                    <th>URL</th>
                    <th style="width: 15%">Height</th>
                    <th style="width:5%;"></th>
                  </tr>
                  <tr v-for="(item, index) in iframes" v-bind:key="index">
                    <td>
                      <CInput v-model="item.sort" />
                    </td>
                    <td>
                      <CInput v-model="item.category" />
                    </td>
                    <td>
                      <CInput v-model="item.name" />
                    </td>
                    <td>
                      <CInput v-model="item.url" />
                    </td>
                    <td>
                      <CInput v-model="item.height" placeholder="px" />
                    </td>
                    <td
                      class="align-top text-center"
                      v-if="Boolean(index > 0 || headers.length > 1)"
                    >
                      <button
                        class="btn btn-danger"
                        @click="remove(iframes, index)"
                      >
                        <CIcon name="cil-minus" size="sm" />
                      </button>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="5" class="align-top text-center">
                      <button
                        class="btn btn-success mb-2"
                        @click="add(iframes)"
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
                  @click="save('iframe')"
                >
                  저장
                </button>
              </div>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </CCardBody>
  </CCard>
</template>

<script>
import * as axios from "@/assets/js/axios";
import * as elHandler from "@/assets/js/elHandler";
import urls from "@/assets/js/urls";

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
  components: {},
  data() {
    return {
      headers: [],
      iframes: [],
      role: sessionStorage.getItem("role"),
    };
  },
  methods: {
    add(array, event) {
      elHandler.add(array, event);
    },
    remove(array, index, event) {
      elHandler.remove(array, index, event);
    },
    setData() {
      this.getMenu();
      this.getIframe();
    },
    async getMenu() {
      this.headers = await axios.get(urls.admin.header);
    },
    async getIframe() {
      this.iframes = await axios.get(urls.admin.iframe);
    },
    async save(category) {
      if (this.role === "ADMIN") {
        let url;
        let param;

        if (category === "header") {
          url = urls.admin.header;
          param = this.headers;
        } else if (category === "iframe") {
          url = urls.admin.iframe;
          param = this.iframes;
        }
        param.forEach((o) => (o.isDel = "N"));
        let data = await axios.post(url, param);
        alert(data > 0 ? "성공" : "실패");
        this.setData();
      } else {
        alert("권한이 부족합니다.");
      }
    },
  },
  created() {
    this.setData();
  },
};
</script>
