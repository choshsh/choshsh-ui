<template>
  <CCard>
    <CCardHeader v-if="Boolean(caption)">
      <slot name="header">{{caption}}</slot>
    </CCardHeader>
    <CCardBody>
      <CDataTable
        :fields="fields"
        :items="Boolean(show) ? items[workflowJobId] : null"
        :hover="hover"
        :bordered="bordered"
        :small="small"
        :items-per-page="5"
        pagination
        :table-filter="tableFilter"
        :itemsPerPageSelect="Boolean(itemsPerPageSelect) ? {values:[5,20,50,100]} : false"
      ></CDataTable>
    </CCardBody>
  </CCard>
</template>

<script>
export default {
  name: "Table",
  props: {
    items: {},
    fields: Array,
    hover: Boolean,
    bordered: Boolean,
    small: Boolean,
    workflowJobId: Number,
    show: Boolean,
    caption: String,
    tableFilter: Object,
    itemsPerPageSelect: Boolean,
  },
};
</script>
