<template>
  <CSidebar
    fixed
    :minimize="minimize"
    :show="show"
    @update:show="(value) => $store.commit('set', ['sidebarShow', value])"
  >
    <CSidebarBrand class="d-md-down-none" to="/">
      <h3>ITSMV</h3>
      <!--       <CIcon
        class="c-sidebar-brand-full"
        name="logo"
        size="custom-size"
        :height="35"
        viewBox="0 0 556 134"
      />
      <CIcon
        class="c-sidebar-brand-minimized"
        name="logo"
        size="custom-size"
        :height="35"
        viewBox="0 0 110 134"
      /> -->
    </CSidebarBrand>

    <CRenderFunction flat :content-to-render="nav" />
    <CSidebarMinimizer
      class="d-md-down-none"
      @click.native="$store.commit('set', ['sidebarMinimize', !minimize])"
    />
  </CSidebar>
</template>

<script>
import axios from "axios";

export default {
  name: "TheSidebar",
  data() {
    return { nav: [{ _name: "CSidebarNav", _children: [] }] };
  },
  computed: {
    show() {
      return this.$store.state.sidebarShow;
    },
    minimize() {
      return this.$store.state.sidebarMinimize;
    },
  },
  methods: {
    setBoardList() {
      axios
        .get("/api/nav")
        .then((res) => {
          this.nav[0]["_children"] = res.data;
        })
        .catch((e) => console.log(e));
    },
  },
  created() {
    this.setBoardList();
  },
};
</script>
