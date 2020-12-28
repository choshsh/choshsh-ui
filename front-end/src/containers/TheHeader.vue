<template>
  <CHeader fixed with-subheader :colorScheme="'light'">
    <CToggler
      in-header
      class="ml-3 d-lg-none"
      @click="$store.commit('toggleSidebarMobile')"
    />
    <CToggler
      in-header
      class="ml-3 d-md-down-none"
      @click="$store.commit('toggleSidebarDesktop')"
    />
    <CIcon
      in-header
      class="ml-3 d-md-down-none"
      @click="$store.commit('toggleSidebarDesktop')"
    />
    <CHeaderBrand class="mx-auto d-lg-none" to="/"></CHeaderBrand>
    <CHeaderNav class="d-md-down-none mr-auto">
      <CHeaderNavItem class="px-3">
        <CHeaderNavLink to="/dashboard">
          <CIcon name="cil-home" />
        </CHeaderNavLink>
      </CHeaderNavItem>
      <CHeaderNavItem class="px-3">
        <a @click="functions">
          <CHeaderNavLink to>
            <CIcon name="cil-reload" />
          </CHeaderNavLink>
        </a>
      </CHeaderNavItem>
      <CHeaderNavItem
        class="px-3"
        v-for="(item, index) in data"
        v-bind:key="index"
      >
        <CHeaderNavLink>
          <div @click="openTab(item.url)">{{ item.name }}</div>
        </CHeaderNavLink>
      </CHeaderNavItem>
    </CHeaderNav>
  </CHeader>
</template>

<script>
import TheHeaderDropdownAccnt from "./TheHeaderDropdownAccnt";
import axios from "axios";

export default {
  name: "TheHeader",
  components: {
    TheHeaderDropdownAccnt,
  },
  data() {
    return {
      data: Array,
    };
  },
  props: {
    functions: { type: Function },
  },
  methods: {
    getData() {
      axios
        .get("/api/header")
        .then((res) => {
          this.data = res.data;
        })
        .catch((e) => console.log(e));
    },
    openTab(url) {
      window.open(url, "_blank");
    },
  },
  created() {
    this.getData();
  },
};
</script>
