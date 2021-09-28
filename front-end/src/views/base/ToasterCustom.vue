<template>
  <CToaster :autohide="3000">
    <template v-for="toast in fixedToasts">
      <CToast :key="'toast' + toast" :show="true" :color="'info'" header="알림">
        {{ msg }}
      </CToast>
    </template>
  </CToaster>
</template>
<script>
export default {
  props: {
    fixedToasts: 0,
    msg: "",
  },
};
</script>
