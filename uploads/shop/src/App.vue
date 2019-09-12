<template>
  <div>
    <!-- <van-swipe @change="onChange">
      <van-swipe-item>1</van-swipe-item>
      <van-swipe-item>2</van-swipe-item>
      <van-swipe-item>3</van-swipe-item>
      <van-swipe-item>4</van-swipe-item>
    </van-swipe> -->
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  // methods: {
  //   onChange(index) {
  //     this.$toast('当前 Swipe 索引：' + index);
  //   }
  // }
};
</script>
<style lang="css">
* {
  margin: 0;
  padding: 0;
}
.van-swipe__indicator {
  background-color: rgb(136, 136, 136) !important;
}
</style>
