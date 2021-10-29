<template>
  <CCard>
    <CCardHeader><strong>iframe 설정</strong></CCardHeader>
    <CCardBody>
      <table border="0" class="col-md-12">
        <tbody>
          <tr>
            <th style="width: 5%">순서</th>
            <th style="width: 10%">분류</th>
            <th style="width: 10%">이름</th>
            <th style="width: 25%">URL</th>
            <th style="width: 5%">Height</th>
            <th>설명</th>
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
            <td>
              <CInput v-model="item.tooltip" />
            </td>
            <td
              class="align-top text-center"
              v-if="Boolean(index > 0 || iframes.length > 1)"
            >
              <button class="btn btn-danger" @click="remove(iframes, index)">
                <CIcon name="cil-minus" size="sm" />
              </button>
            </td>
          </tr>
          <tr>
            <td colspan="7" class="align-top text-center">
              <button class="btn btn-success mb-2" @click="add(iframes)">
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
  name: "SettingsIframe",
  data() {
    return {
      iframes: [],
    };
  },
  methods: {
    add(array, event) {
      elHandler.add(array, event);
    },
    remove(array, index, event) {
      elHandler.remove(array, index, event);
    },
    async getIframe() {
      this.iframes = await adminService.getIframe();
    },
    async save() {
      if (util.roleCheck()) {
        let param;
        param = this.iframes;
        param.forEach((o) => (o.isDel = "N"));

        let data = await adminService.postIframe(param);
        alert(data > 0 ? "성공" : "실패");
        this.getIframe();
      }
    },
  },
  created() {
    this.getIframe();
  },
};
</script>
