<template>
  <CFooter :fixed="false">
    <div class="ml-auto">
      <a href="#">CHO SANG HYUN</a>
      <span class="ml-1">&copy; {{new Date().getFullYear()}}</span>
    </div>
  </CFooter>
</template>

<script>
export default {
  name: 'TheFooter'
}
</script>
