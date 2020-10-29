<template>
  <CCard>
    <CCardHeader v-if="Boolean(caption)">
      <slot name="header">{{caption}}</slot>
    </CCardHeader>
    <CCardBody>
      <CDataTable
        :fields="fields"
        :items="items"
        :hover="hover"
        :bordered="bordered"
        :small="small"
        :items-per-page="itemsPerPage"
        :pagination="pagination"
        :tableFilter="tableFilter"
        :loading="loading"
        sorter
      ></CDataTable>
    </CCardBody>
  </CCard>
</template>

<script>
export default {
  name: "Table",
  data() {
    return {
      pagination: {
        align: "end"
      }
    };
  },
  props: {
    items: {},
    fields: Array,
    hover: Boolean,
    bordered: Boolean,
    small: Boolean,
    caption: String,
    itemsPerPage: Number,
    tableFilter: {},
    loading: {
      type: Boolean,
      default() {
        return false;
      }
    }
  }
};
</script>
