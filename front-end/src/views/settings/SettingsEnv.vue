<template>
  <CCard>
    <CCardHeader><strong>환경변수</strong></CCardHeader>
    <CCardBody>
      <table border="0" class="col-md-12">
        <tbody>
          <tr>
            <th style="width: 30%">키</th>
            <th>값</th>
            <th style="width: 10%"></th>
          </tr>
          <tr v-for="(item, index) in envs" v-bind:key="index">
            <td>
              <CInput v-model="item.key" readonly />
            </td>
            <td>
              <CInput v-model="item.value" />
            </td>
            <td class="align-top text-center">
              <button
                id="saveBtn"
                type="button"
                class="btn btn-primary"
                @click="saveEnv(item)"
              >
                저장
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </CCardBody>
  </CCard>
</template>

<script>
import * as adminService from "@/api/admin";
import * as util from "@/assets/js/util";

export default {
  name: "Settings",
  data() {
    return {
      envs: {},
    };
  },
  methods: {
    async getEnv() {
      this.envs = await adminService.getEnv();
    },
    async saveEnv(env) {
      if (util.roleCheck()) {
        if (env.id > 0) {
          let data = await adminService.putEnv(env.id, env);
          if (data.id > 0) {
            alert("성공");
            this.getEnv;
            this.$store.dispatch("env/load");
          } else {
            alert("실패");
          }
        }
      }
    },
  },
  created() {
    this.getEnv();
  },
};
</script>
