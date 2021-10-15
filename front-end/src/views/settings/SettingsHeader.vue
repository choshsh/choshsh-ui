<template>
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
              <button class="btn btn-danger" @click="remove(headers, index)">
                <CIcon name="cil-minus" size="sm" />
              </button>
            </td>
          </tr>
          <tr>
            <td colspan="5" class="align-top text-center">
              <button class="btn btn-success mb-2" @click="add(headers)">
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
          @click="save"
        >
          저장
        </button>
      </div>
    </CCardBody>
  </CCard>
</template>

<script>
import * as adminService from "@/api/admin";
import * as elHandler from "@/assets/js/elHandler";
import * as util from "@/assets/js/util";

export default {
  name: "SettingsHeader",
  data() {
    return {
      headers: [],
    };
  },
  methods: {
    add(array, event) {
      elHandler.add(array, event);
    },
    remove(array, index, event) {
      elHandler.remove(array, index, event);
    },
    async getHeader() {
      this.headers = await adminService.getHeader();
    },
    async save() {
      if (util.roleCheck()) {
        let param;
        param = this.headers;
        param.forEach((o) => (o.isDel = "N"));

        let data = await adminService.postHeader(param);
        alert(data > 0 ? "성공" : "실패");
        this.getHeader();
        this.$store.commit("component/reload", ["header"]);
      }
    },
  },
  created() {
    this.getHeader();
  },
};
</script>
