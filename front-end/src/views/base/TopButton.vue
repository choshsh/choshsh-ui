<template>
  <CRow class="mb-3 d-flex justify-content-between">
    <CCol col="11"></CCol>
    <CCol v-if="isNew" col="1">
      <CButton block color="primary" @click="newItem">
        New
      </CButton>
    </CCol>
  </CRow>
</template>

<script>
export default {
  props: {
    isNew: { default: false, type: Boolean },
  },
  methods: {
    newItem() {
      if (this.isNew) this.$emit("modalHandler");
    },
  },
};
</script>
