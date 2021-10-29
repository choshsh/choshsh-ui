<template>
  <CCard>
    <!-- 바디 -->
    <CCardBody>
      <CRow>
        <CCol col="4" v-for="(item, index) in items" v-bind:key="index">
          <CCard>
            <!-- 그루비룸 -->
            <CCardHeader class="font-weight-bold">
              {{ item.name }}
            </CCardHeader>
            <CListGroup>
              <div v-for="(rule, index2) in item.rules" v-bind:key="index2">
                <CListGroupItem>
                  <span>
                    <p>
                      <!-- 규칙 이름 / 상태 -->
                      <span class="font-weight-bold">{{ rule.name }}</span>
                      <CBadge :color="colorByState(rule.state)" class="ml-1">
                        ({{ rule.alerts.length + " " + rule.state }})
                      </CBadge>
                      <CIcon
                        class="ml-1"
                        name="cil-info"
                        v-c-tooltip="{
                          content: rule.query,
                        }"
                      />
                    </p>
                    <div>
                      <!-- 규칙 내용 -->
                      <ul style="-webkit-padding-start:6%">
                        <li
                          v-for="(alert, index3) in rule.alerts"
                          v-bind:key="index3"
                          v-html="alertDesc(alert)"
                          class="mb-3"
                        />
                      </ul>
                    </div>
                  </span>
                </CListGroupItem>
              </div>
            </CListGroup>
          </CCard>
        </CCol>
      </CRow>
    </CCardBody>
  </CCard>
</template>

<script>
import * as prometheusService from "@/api/prometheus";
import * as util from "@/assets/js/util";
export default {
  data() {
    return {
      items: [],
    };
  },
  computed: {
    //   상태 뱃지 설정
    colorByState() {
      return (state) =>
        state === "inactive"
          ? "success"
          : state === "firing"
          ? "danger"
          : state === "pending"
          ? "warning"
          : "";
    },
    // 그룹 내 알림 html 설정
    alertDesc() {
      return (alert) => {
        let annotations = "";
        Object.keys(alert.annotations)
          .reverse()
          .forEach((k) => {
            if (k != "dashboard") {
              annotations += "<br>" + k + " : " + alert.annotations[k];
            }
          });

        return (
          "알림 수준 : " +
          alert.labels.severity +
          "<br>발생 시간 : " +
          util.dateFormat(alert.activeAt) +
          " (" +
          ((new Date() - Date.parse(alert.activeAt)) / (1000 * 60)).toFixed(0) +
          "분 전" +
          ")" +
          annotations
        );
      };
    },
  },
  async created() {
    let data = await prometheusService.getRules();
    this.items = data.data.groups;
  },
};
</script>
