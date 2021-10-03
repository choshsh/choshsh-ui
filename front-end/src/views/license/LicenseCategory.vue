<template>
  <CModal
    color="primary"
    :show.sync="show"
    :closeOnBackdrop="false"
    :size="'lg'"
  >
    <div slot="header"><h5>License Category</h5></div>

    <!-- 알람 -->
    <CAlert :color="alert.color" :show.sync="alert.show" closeButton>
      {{ alert.msg }}
    </CAlert>

    <CCard>
      <CCardBody>
        <table class="col-md-12 table table-sm table-borderless">
          <tbody>
            <!-- 테이블 헤더 -->
            <tr>
              <th style="width: 5%">순서</th>
              <th class="w-25">카테고리 이름</th>
              <th style="width:4%;"></th>
              <th style="width:4%;"></th>
            </tr>
            <!-- 테이블 바디 -->
            <tr v-for="(item, index) in items" v-bind:key="index">
              <td>
                <CInput type="number" v-model="item.sort" required />
              </td>
              <td>
                <CInput v-model="item.name" required />
              </td>
              <td class="align-top text-right">
                <button class="btn btn-primary mb-2" @click="save(item)">
                  저장
                </button>
              </td>
              <td class="align-top text-left">
                <!-- <button
                  class="btn btn-danger mb-2"
                  @click="del(item)"
                  v-show="Boolean(item.id > 0)"
                >
                  삭제
                </button> -->
                <button
                  class="btn btn-secondary mb-2"
                  @click="remove(items, index)"
                  v-show="
                    Boolean(item.id < 1 && (index > 0 || items.length > 1))
                  "
                >
                  취소
                </button>
              </td>
            </tr>
            <!-- 테이블 + 버튼 -->
            <tr>
              <td colspan="5" class="align-top text-center">
                <button class="btn btn-success mb-2" @click="add(items)">
                  <CIcon name="cil-plus" size="sm" />
                </button>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- 하단 버튼 -->
        <div class="text-center row-fluid mt-3">
          <button
            id="cancelBtn"
            type="button"
            class="btn btn-secondary"
            @click="cancel()"
          >
            닫기
          </button>
        </div>
      </CCardBody>
    </CCard>
    <div slot="footer" />
  </CModal>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import * as util from "@/assets/js/util";
import * as elHandler from "@/assets/js/elHandler";

export default {
  name: "Licenses",
  components: {},
  data() {
    return {
      items: [],
      itemEntity: {
        id: -1,
        name: "",
        sort: 0,
        regUser: {},
        modUser: {},
      },
      alert: {
        show: false,
        msg: "",
        color: "",
      },
    };
  },
  props: {
    editable: { default: false, type: Boolean },
    show: { default: false, type: Boolean },
  },
  methods: {
    async setData() {
      let data = await axios.get(urls.license.category);

      if (data.length > 0) {
        this.items = data;
      } else {
        this.$set(this.items, 0, util.cloneObject(this.itemEntity));
      }
    },
    // 모달 닫기
    cancel() {
      this.alert.show = false;
      this.$emit("categoryModalHandler");
    },
    // 데이터 저장
    async save(item) {
      item.regUser = {};
      item.modUser = {};
      item.regUser.id = sessionStorage.getItem("id");
      item.modUser.id = sessionStorage.getItem("id");

      item.name = item.name.toUpperCase();

      item.id = item.id ? item.id : -1;

      let add =
        item.id > 0
          ? await axios.put(urls.license.category + "/" + item.id, item)
          : await axios.post(urls.license.category, item);

      if (add.id > 0) {
        item.id = add.id;
        this.alert.msg = "successfully updated";
        this.alert.color = "success";
      } else {
        this.alert.msg = "failed";
        this.alert.color = "danger";
      }
      this.alert.show = true;
    },
    // 데이터 삭제
    async del() {},
    // 한 줄 추가
    add(array, event) {
      elHandler.add(array, event);
    },
    // 한 줄 삭제
    remove(array, index, event) {
      elHandler.remove(array, index, event);
    },
  },
  created() {
    this.setData();
  },
};
</script>
