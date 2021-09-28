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
        v-for="(item, index) in headers"
        v-bind:key="index"
      >
        <CHeaderNavLink>
          <div @click="openTab(item)" class="font-weight-bold">{{ item.name }}</div>
        </CHeaderNavLink>
      </CHeaderNavItem>
    </CHeaderNav>
    <CHeaderNav class="mr-3">
      <CHeaderNavItem class="d-md-down-none mx-2">
        <CHeaderNavLink>
          <a @click="login()" v-if="!isLogin">
            <CIcon name="cil-lock-locked" /> Login
          </a>
          <a @click="logout()" v-else>
            <CIcon name="cil-lock-unlocked" /> Logout
          </a>
        </CHeaderNavLink>
      </CHeaderNavItem>
    </CHeaderNav>
  </CHeader>
</template>

<script>
import * as axios from "@/assets/js/axios";
import urls from "@/assets/js/urls";

export default {
  name: "TheHeader",
  data() {
    return {
      headers: [],
      isLogin: sessionStorage.getItem("userId") ? true : false,
    };
  },
  props: {
    functions: { type: Function },
  },
  methods: {
    async setData() {
      let data = await axios.get(urls.admin.header);
      this.headers = data;
    },
    openTab(item) {
      if (item.isBlank === "Y") {
        window.open(item.url, "_blank");
      } else {
        this.$router.push(item.url);
      }
    },
    logout() {
      if (confirm("로그아웃하시겠습니까?")) {
        sessionStorage.clear();
        this.isLogin = false;
        this.$router.push("/");
      }
    },
    login() {
      this.$router.push("/pages/login");
    },
  },
  created() {
    this.setData();
  },
};
</script>
