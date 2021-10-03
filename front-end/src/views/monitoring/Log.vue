<template>
  <CCard>
    <CCardBody>
      <CTabs variant="pills" :active-tab="0" @update:activeTab="setUrl">
        <CTab class="mb-3" v-for="(item, index) in data" v-bind:key="index">
          <template slot="title">{{ item.name }}</template>
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
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";

export default {
  name: "VM-Chart",
  data() {
    return {
      data: [],
      urls: [],
    };
  },
  methods: {
    async getData() {
      this.data = await axios.get(urls.admin.iframe + "/log");
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
