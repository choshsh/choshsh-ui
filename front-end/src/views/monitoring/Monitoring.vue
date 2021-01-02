<template>
  <CCard>
    <CCardBody>
      <CTabs variant="pills" :active-tab="0">
        <CTab class="mb-3" v-for="(item, index) in data" v-bind:key="index">
          <template slot="title">{{ item.name }}</template>
          <CRow>
            <CCol>
              <CCard>
                <CCardBody>
                  <iframe
                    :src="item.url"
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
      data: Array,
    };
  },
  methods: {
    getData() {
      axios
        .get("/api/monitoring")
        .then((res) => {
          this.data = res.data;
          console.log(this.data);
        })
        .catch((e) => console.log(e));
    },
  },
  created() {
    this.getData();
  },
};
</script>
