<template>
  <div class="c-app">
    <TheSidebar ref="sidebar" />
    <CWrapper>
      <TheHeader 
        ref="header"
        :functions="reload" 
      />
      <div class="c-body">
        <main class="c-main">
          <CContainer fluid :key="mainKey">
            <transition name="fade">
              <router-view @on-save-setting="refresh"></router-view>
            </transition>
          </CContainer>
        </main>
      </div>
      <TheFooter />
    </CWrapper>
  </div>
</template>

<script>
import TheSidebar from "./TheSidebar";
import TheHeader from "./TheHeader";
import TheFooter from "./TheFooter";

export default {
  name: "TheContainer",
  components: {
    TheSidebar,
    TheHeader,
    TheFooter
  },
  data() {
    return {
      mainKey: 0
    };
  },
  methods: {
    reload() {
      this.mainKey++;
    },
    refresh(entity) {
      if (entity === "nav")
        this.$refs.sidebar.setBoardList();
      else if (entity === "header")
        this.$refs.header.getData();
    }
  }
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
