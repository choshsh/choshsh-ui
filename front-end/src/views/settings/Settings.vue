<template>
  <CCard>
    <CCardBody>
      <CTabs variant="pills">
        <CTab class="mb-3">
          <template slot="title">Iframe</template>
          <SettingsIframe />
        </CTab>
        <CTab class="mb-3">
          <template slot="title">Header</template>
          <CCol col="8">
            <SettingsHeader />
          </CCol>
        </CTab>
        <CTab class="mb-3">
          <template slot="title">환경변수</template>
          <CCol col="8">
            <SettingsEnv />
          </CCol>
        </CTab>
      </CTabs>
    </CCardBody>
  </CCard>
</template>

<script>
import SettingsHeader from "./SettingsHeader";
import SettingsIframe from "./SettingsIframe";
import SettingsEnv from "./SettingsEnv";

export default {
  name: "Settings",
  components: { SettingsHeader, SettingsIframe, SettingsEnv },
};
</script>
