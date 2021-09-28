<template>
  <CAlert :color="color" :show.sync="counter" closeButton>
    {{ msg }}
    <CProgress :max="5" :value="counter" height="3px" :color="color" animate />
  </CAlert>
</template>
<script>
export default {
  props: {
    counter: 0,
    color: "",
    msg: "",
  },
};
</script>
