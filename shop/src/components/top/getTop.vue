<template>
  <van-icon
    @click="getTop"
    :class="[ishow ? 'top' : 'topShow']"
    size="50"
    name="upgrade"
  />
</template>
<script>
export default {
  props: ["top"],
  data() {
    return {
      ishow: false
    };
  },
  methods: {
    getTop() {
      window.scrollTo(0, 0);
      this.ishow = false;
    }
  },
  watch: {
    top(va) {
      if (va > 1000) {
        this.ishow = true;
        return;
      }
      this.ishow = false;
    }
  },
  created() {}
};
</script>
<style scoped>
.top {
  border-radius: 50%;
  position: fixed;
  top: 80%;
  left: 80%;
  /* background-color: rgba(135, 206, 235,.3); */
  color: rgba(158, 158, 160, 0.6);
  /* color: bisque; */
  /* background-color: rgba(240, 248, 255,.6); */
  /* opacity: 0.3; */
  /* color: darksalmon */
}
.topShow {
  border-radius: 50%;
  position: fixed;
  top: 80%;
  left: 80%;
  background-color: beige;
  color: bisque;
  /* background-color: rgba(240, 248, 255,.6); */
  /* opacity: 0.3; */
  /* color: darksalmon */
  display: none;
}
</style>
