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
        v-if="Boolean(userRoleCd === 'M001' || userRoleCd === 'T001')"
      >
        <CHeaderNavLink to="/egressions">외출 관리</CHeaderNavLink>
      </CHeaderNavItem>
      <CHeaderNavItem class="px-3">
        <CHeaderNavLink>
          <div @click="monitoringLink()">통합 모니터링</div>
        </CHeaderNavLink>
      </CHeaderNavItem>
      <CHeaderNavItem class="px-3">
        <CHeaderNavLink>
          <div @click="repoLink()">자료실</div>
        </CHeaderNavLink>
      </CHeaderNavItem>
      <CHeaderNavItem class="px-3">
        <CHeaderNavLink>
          <div @click="gitlabLink()">GitLab</div>
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
      userRoleCd: sessionStorage.getItem("userRoleCd"),
      monitoring: "",
      repo: "",
      gitlab: "",
    };
  },
  props: {
    functions: { type: Function },
  },
  methods: {
    monitoringLink() {
      const content = "모니터링 시스템 접속정보\n\nID : Guest\nPW : Guest";
      alert(content);
      window.open(this.monitoring, "_blank");
    },
    repoLink() {
      window.open(this.repo, "_blank");
    },
    gitlabLink() {
      window.open(this.gitlab, "_blank");
    },
    getLinkedURL() {
      axios
        .post("/api/linkedURL")
        .then((res) => {
          this.monitoring = res.data.monitoring;
          this.repo = res.data.repo;
          this.gitlab = res.data.gitlab;
        })
        .catch((e) => console.log(e));
    },
  },
  created() {
    this.getLinkedURL();
  },
};
</script>
