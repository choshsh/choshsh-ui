<template>
  <div>
    <CCard v-for="(item, index) in data" v-bind:key="index">
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
    </CCard>
  </div>
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
      this.data = await axios.get(urls.admin.iframe + "/vm");
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
