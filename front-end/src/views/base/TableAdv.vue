<template>
  <CCard>
    <CCardBody>
      <CDataTable
        :fields="fields"
        :items="items"
        :items-per-page="itemsPerPage"
        :table-filter="tableFilter"
        :loading="loading"
        :hover="hover"
        column-filter
        sorter
        items-per-page-select
        pagination

      >
        <template #show_details="{item, index}">
          <td class="py-2">
            <CButton
              color="primary"
              variant="outline"
              square
              size="sm"
              @click="toggleDetails(item, index)"
            >{{Boolean(item._toggled) ? 'Hide' : 'Show'}}</CButton>
          </td>
        </template>
      </CDataTable>
    </CCardBody>
  </CCard>
</template>


<script>
export default {
  name: "TableAdv",
  data() {
    return {
      tableFilter: { label: "전체 검색", placeholder: "검색어 입력..." },
      collapseDuration: 0,
      loading: true,
      hover: true
    };
  },
  props: {
    fields: Array,
    items: Array,
    itemsPerPage: Number,
    hover: Boolean
  },
  methods: {
    toggleDetails(item) {
      this.$set(this.items[item.id], "_toggled", !item._toggled);
      this.collapseDuration = 300;
      this.$nextTick(() => {
        this.collapseDuration = 0;
      });
    },
  }
};
</script>