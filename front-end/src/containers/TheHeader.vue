<template>
  <CHeader fixed with-subheader :colorScheme="'light'">
    <!-- 사이드바 접기/펴기 -->
    <CToggler
      in-header
      class="ml-3 d-lg-none"
      @click="$store.commit('sidebar/toggleSidebarMobile')"
    />
    <CToggler
      in-header
      class="ml-3 d-md-down-none"
      @click="$store.commit('sidebar/toggleSidebarDesktop')"
    />
    <CIcon
      in-header
      class="ml-3 d-md-down-none"
      @click="$store.commit('sidebar/toggleSidebarDesktop')"
    />
    <CHeaderBrand class="mx-auto d-lg-none" to="/"></CHeaderBrand>
    <CHeaderNav class="d-md-down-none mr-auto">
      <!-- 홈 버튼 -->
      <CHeaderNavItem class="px-3">
        <CHeaderNavLink to="/">
          <CIcon name="cil-home" />
        </CHeaderNavLink>
      </CHeaderNavItem>
      <!-- 새로고침 버튼 -->
      <CHeaderNavItem class="px-3">
        <a @click="$store.commit('component/reload', ['container'])">
          <CHeaderNavLink to>
            <CIcon name="cil-reload" />
          </CHeaderNavLink>
        </a>
      </CHeaderNavItem>
      <!-- 커스텀 헤더들 -->
      <CHeaderNavItem
        class="px-3"
        v-for="(item, index) in headers"
        v-bind:key="index"
      >
        <CHeaderNavLink>
          <div @click="openTab(item)" class="font-weight-bold">
            {{ item.name }}
          </div>
        </CHeaderNavLink>
      </CHeaderNavItem>
    </CHeaderNav>
    <!-- 로그인 로그아웃 버튼 -->
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
import * as adminService from "@/api/admin";

export default {
  name: "TheHeader",
  data() {
    return {
      headers: [],
      isLogin: sessionStorage.getItem("userId") ? true : false,
    };
  },
  methods: {
    async setData() {
      let data = await adminService.getHeader();
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
