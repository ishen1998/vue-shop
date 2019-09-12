<template>
  <div>
    <div>
      <van-nav-bar
        title="售后列表"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
      <van-tabs v-model="active">
        <van-tab title="商品售后">
          <div>
            <p style="padding:20% 39%;color:#ccc">空空如也~</p>
            <van-button type="primary" size="large" @click="moveHome"
              >去购物 >></van-button
            >
          </div>
        </van-tab>
        <van-tab title="运费退款"></van-tab>
      </van-tabs>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    console.log("成功跳转");
  },
  data() {
    return {
      active: 0
    };
  },
  methods: {
    onClickLeft() {
      console.log("返回");
      this.$router.go(-1);
    },
    moveHome() {
      this.$router.push("/home");
      this.$store.commit("getActive");
      console.log(this.$store.state.active);
    }
  }
};
</script>

<style scoped>
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
</style>
