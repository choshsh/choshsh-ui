<template>
  <CCard>
    <CCardBody>
      <CTabs variant="pills" :active-tab="0" @update:activeTab="setUrl">
        <CTab class="mb-3" v-for="(item, index) in data" v-bind:key="index">
          <template slot="title">{{ item.name }}</template>
          <CRow>
            <CCol>
              <CCard>
                <CCardBody>
                  <iframe
                    :src="urls[index]"
                    class="embed-responsive-item"
                    :height="item.height"
                    width="100%"
                    allowfullscreen
                    frameborder="0"
                    scrolling="no"
                  />
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
        </CTab>
      </CTabs>
    </CCardBody>
  </CCard>
</template>

<script>
import axios from "axios";

export default {
  name: "Monitoring",
  data() {
    return {
      data: [],
      urls: [],
    };
  },
  methods: {
    getData() {
      axios
        .get("/api/monitoring")
        .then((res) => {
          this.data = res.data;
          this.setUrl(0);
        })
        .catch((e) => console.log(e));
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
