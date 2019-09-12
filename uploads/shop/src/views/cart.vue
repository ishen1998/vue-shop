<template>
  <div class="container">
    <Footer></Footer>
    <div class="header">
      <van-nav-bar left-text="进货车" />
    </div>
    <!-- 王牌买手模块 -->
    <div style="margin:30px 0">
      <div class="hot" v-show="carData.length == 0 ? false : true">
        <van-icon name="shop-collect" />
        <span style="font-size:14px;">王牌买手</span>
        <van-button
          type="default"
          size="small"
          class="delbtn"
          @click="emptyShop"
          >全部删除</van-button
        >
      </div>
      <div
        style="margin-bottom:10px"
        v-for="item in carData"
        :key="item.itemId"
      >
        <car :item="item" @changeC="change" :allgoods="true" />
      </div>
      <!-- 购物车的商品组件 -->
      <div
        class="hot"
        style="padding:100px 0"
        v-show="carData.length == 0 ? true : false"
      >
        <h6 style="margin-bottom:20px;color:#999">空空如也~去逛逛吧</h6>
        <van-button type="info" @click="moveHome">点我去</van-button>
      </div>
    </div>
    <!-- 热门推荐模块 -->
    <div style="margin:10px 0;padding-bottom:80px">
      <div class="hot">
        <van-icon name="fire-o" />
        <span style="font-size:14px;margin-left:5px">热门推荐</span>
      </div>
      <productList />
    </div>
    <!-- 结算按钮 -->
    <div v-show="carData.length == 0 ? false : true">
      <submitBar />
    </div>
  </div>
</template>
<script>
import productList from "../components/product/productList";
import car from "../components/card/car";
import submitBar from "../components/card/submitBar";
import Footer from "../components/TabBar/footer.vue";
export default {
  updated() {
    var cId = this.$store.getters.getcId;
    this.$http.get(`/shop/Trolley/${cId}`).then(res => {
      // console.log(res.data.data)
      this.carData = res.data.data;
    });
  },
  data() {
    return {
      carData: [],
      checked: ""
    };
  },
  created() {
    // console.log(this.carData)
    if (this.$store.getters.getLoginState == true) {
      var cId = this.$store.getters.getcId;
      this.$http.get(`/shop/Trolley/${cId}`).then(res => {
        // console.log(res.data.data)
        this.carData = res.data.data;
        var i = 0;
        if (this.carData.length != 0) {
          this.carData.forEach(item => {
            i += parseInt(item.num * item.price);
          });
        }
        this.$store.commit("setTotalPrice", i);
      });
    }
  },
  components: { productList, car, submitBar, Footer },
  methods: {
    moveHome() {
      this.$router.push("/home");
    },
    change(i) {
      this.checked = i;
      // console.log(this.checked);
    },
    emptyShop() {
      this.$http.delete(`/shop/Trolley/${this.$store.getters.getcId}`);
    }
  }
};
</script>
<style scoped lang="less">
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
.container {
  background-color: #f6f6f6;
  .header {
    width: 100%;
    background-color: white;
    z-index: 99;
    position: fixed;
    top: 0;
  }
  .hot {
    text-align: center;
    padding: 20px 0;
    background-color: white;
  }
  .van-submit-bar {
    bottom: 48px;
  }
  .delbtn {
    position: absolute;
    right: 5%;
  }
}
</style>
