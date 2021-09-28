<template>
  <CCard>
    <CCardHeader v-if="Boolean(caption)">
      <slot name="header">{{ caption }}</slot>
    </CCardHeader>
    <CCardBody>
      <CDataTable
        :fields="fields"
        :items="items"
        :hover="hover"
        :bordered="bordered"
        :small="small"
        :items-per-page="10"
        :pagination="{ align: 'center' }"
        :table-filter="tableFilter"
        :itemsPerPageSelect="
          Boolean(itemsPerPageSelect) ? { values: [10, 50, 100] } : false
        "
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
    caption: String,
    tableFilter: Object,
    itemsPerPageSelect: Boolean,
  },
};
</script>
