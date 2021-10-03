<template>
  <CCard>
    <!-- 상단 알람 -->
    <AlertCounter
      :counter="alert.counter"
      :color="alert.color"
      :msg="alert.msg"
    />

    <!-- 바디 -->
    <CCardBody>
      <TopButton
        v-if="Boolean(role === 'A')"
        :isNew="true"
        @modalHandler="modalHandler"
      />
      <CRow>
        <CCol
          col="3"
          v-for="(item, index) in scriptCategory"
          v-bind:key="index"
        >
          <CCard>
            <CCardHeader class="font-weight-bold">
              {{ item.label }}
            </CCardHeader>
            <CListGroup>
              <div v-for="(item2, index2) in items" v-bind:key="index2">
                <CListGroupItem
                  href="javascript:;"
                  v-if="item2.scriptCategory === item.label"
                  @click="modalHandler(item2.id)"
                >
                  <span>
                    <p class="font-weight-bold">{{ item2.name }}</p>
                    <div class="small text-muted">{{ item2.description }}</div>
                  </span>
                </CListGroupItem>
              </div>
            </CListGroup>
          </CCard>
        </CCol>
      </CRow>
    </CCardBody>

    <!-- 상세 모달 -->
    <div>
      <ScriptInfo
        ref="scriptInfo"
        @setData="setData"
        @alertHandler="alertHandler"
      />
    </div>
  </CCard>
</template>

<script>
import TopButton from "@/views/base/TopButton";
import ScriptInfo from "@/views/script/ScriptInfo";
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";
import AlertCounter from "@/views/base/AlertCounter";

export default {
  components: { TopButton, ScriptInfo, AlertCounter },
  data() {
    return {
      role: sessionStorage.getItem("role"),
      category: ["Agent 설치", "설정", "보안", "DBMS"],
      items: [1, 2, 3, 4, 5, 6, 6, 7, 10],
      scriptCategory: [],
      alert: {
        color: "",
        msg: "",
        counter: 0,
      },
    };
  },
  methods: {
    // 상세 모달
    modalHandler(id) {
      this.$refs.scriptInfo.modalHandler(id > 0 ? id : -1);
    },
    // 알람 컨트롤
    alertHandler(color, msg, counter) {
      this.alert.color = color;
      this.alert.msg = msg;
      this.alert.counter = counter;
      if (!color) this.alert.counter = 0;
    },
    // 데이터
    async setData() {
      this.items = await axios.get(urls.script.list);
    },
  },
  async created() {
    this.scriptCategory = await axios.get(urls.code.scriptCategory);
    this.setData();
  },
};
</script>
