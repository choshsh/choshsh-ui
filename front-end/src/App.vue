<template>
  <router-view></router-view>
</template>

<script>
export default {
  name: "App",
  methods: {
    sessionCheck() {
      if (!sessionStorage.getItem("userSeq") > 0)
        this.$router.push("/pages/login");
    },
  },
  created() {
    //this.sessionCheck();
  },
};
</script>

<style lang="scss">
// Import Main styles for this application
@import "assets/scss/style";
</style>
