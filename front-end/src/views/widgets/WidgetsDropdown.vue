<template>
  <CRow>
    <!-- 물리서버 차트 -->
    <CCol sm="6" lg="3">
      <router-link :to="'/serversChart'">
        <CWidgetDropdown color="primary" header="물리서버 차트">
          <template #footer>
            <CChartBarSimple class="mt-3 mx-3" style="height:70px" label labels="months" />
          </template>
        </CWidgetDropdown>
      </router-link>
    </CCol>
    <!-- 가상서버 차트 -->
    <CCol sm="6" lg="3">
      <router-link :to="'vmsChart'">
        <CWidgetDropdown color="info" header="가상서버 차트">
          <template #default />
          <template #footer>
            <CChartBarSimple class="mt-3 mx-3" style="height:70px" label labels="months" />
          </template>
        </CWidgetDropdown>
      </router-link>
    </CCol>
    <CCol sm="6" lg="3">
      <CWidgetDropdown color="warning" header="백업 차트">
        <template #footer>
          <CChartLineSimple
            class="mt-3"
            style="height:70px"
            background-color="rgba(255,255,255,.2)"
            :data-points="[78, 81, 80, 45, 34, 12, 40]"
            :options="{ elements: { line: { borderWidth: 2.5 }}}"
            point-hover-background-color="warning"
            label="Members"
            labels="months"
          />
        </template>
      </CWidgetDropdown>
    </CCol>
    <CCol sm="6" lg="3">
    </CCol>
  </CRow>
</template>

<script>
import { CChartLineSimple, CChartBarSimple } from "../charts/index.js";

export default {
  name: "WidgetsDropdown",
  components: { CChartLineSimple, CChartBarSimple },
};
</script>
