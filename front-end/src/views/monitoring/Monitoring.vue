<template>
  <CCard>
    <CCardBody>
      <CTabs variant="pills" :active-tab="0">
        <CTab class="mb-3" v-for="(item, index) in slot" v-bind:key="item">
          <template slot="title">{{ item }}</template>
          <CRow>
            <CCol>
              <CCard>
                <CCardBody>{{ content[index] }}</CCardBody>
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

const slot = ["menu01", "menu02", "menu03"];

const content = ["content01", "content02", "content03"];

export default {
  name: "Monitoring",
  data() {
    return {
      slot: slot,
      content: content,
    };
  },
  methods: {},
};
</script>
