<template>
  <CCard>
    <CCardBody>
      <CTabs variant="pills" :active-tab="0" @update:activeTab="setUrl">
        <CTab class="mb-3" v-for="(item, index) in data" v-bind:key="index">
          <template slot="title">
            <span v-if="item.tooltip" v-c-tooltip="{ content: item.tooltip }">
              {{ item.name }}
            </span>
            <span v-else>
              {{ item.name }}
            </span>
          </template>
          <CRow>
            <CCol>
              <CCard>
                <iframe
                  :src="urls[index]"
                  class="embed-responsive-item"
                  :height="item.height"
                  width="100%"
                  allowfullscreen
                  frameborder="0"
                  scrolling="no"
                />
              </CCard>
            </CCol>
          </CRow>
        </CTab>
      </CTabs>
    </CCardBody>
  </CCard>
</template>

<script>
import * as adminService from "@/api/admin";

export default {
  name: "Metric",
  data() {
    return {
      data: [],
      urls: [],
    };
  },
  methods: {
    async getData() {
      this.data = await adminService.getIframe("metric");
      this.setUrl(0);
    },
    setUrl(index) {
      this.$set(this.urls, index, this.data[index].url);
    },
  },
  created() {
    this.getData();
  },
};
</script>
