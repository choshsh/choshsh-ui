<template>
  <CSidebar
    fixed
    :minimize="minimize"
    :show="show"
    @update:show="(value) => $store.commit('sidebar/set', ['sidebarShow', value])"
  >
    <CSidebarBrand class="d-md-down-none" to="/">
      <h3>CHOSHSH</h3>
    </CSidebarBrand>

    <CRenderFunction flat :content-to-render="$options.nav" />
    <CSidebarMinimizer
      class="d-md-down-none"
      @click.native="$store.commit('sidebar/set', ['sidebarMinimize', !minimize])"
    />
  </CSidebar>
</template>

<script>
import nav from "./_nav";

export default {
  name: "TheSidebar",
  nav,
  computed: {
    show() {
      return this.$store.state.sidebar.sidebarShow;
    },
    minimize() {
      return this.$store.state.sidebar.sidebarMinimize;
    },
  },
};
</script>
