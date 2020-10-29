<template>
  <CCard>
    <CCardHeader v-if="Boolean(caption)">
      <slot name="header">{{caption}}</slot>
    </CCardHeader>
    <CCardBody>
      <CDataTable
        :fields="fields"
        :items="Boolean(show) ? items[ciNo] : null"
        :hover="hover"
        :bordered="bordered"
        :small="small"
        :items-per-page="5"
        :itemsPerPageSelect="Boolean(itemsPerPageSelect) ? {values:[5,20,50,100]} : false"
        pagination
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
    ciNo: String,
    show: Boolean,
    caption: String,
    itemsPerPageSelect: Boolean,
  },
};
</script>
