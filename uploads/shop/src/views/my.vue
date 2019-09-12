<template>
  <div class="container">
    <div class="header" style="z-index:999">
      <van-nav-bar left-text="个人中心" />
    </div>
    <div class="bg">
      <img src="../assets/bg.jpg" height="250" width="100%" class="bg-img" />
      <div class="tx-img">
        <img
          :src="img"
          height="50"
          width="50"
          style="border-radius:25px;z-index:1"
          @click="movePersonalInformation"
        />
        <span v-if="loginState">
          <span @click="login">登陆/</span>
          <span @click="register">注册</span>
        </span>
        <span v-if="!loginState">欢迎您,{{ username }}</span>
      </div>
      <div class="list">
        <div>
          <p>--</p>
          <p>我的收藏</p>
        </div>
        <div class="div">
          <p>12</p>
          <p>足迹</p>
        </div>
        <div>
          <p>--</p>
          <p>优惠券</p>
        </div>
      </div>
    </div>
    <div class="goodsState">
      <van-grid :border="false" :column-num="5">
        <van-grid-item @click="moveOrder(3)">
          <van-icon name="gold-coin-o" />
          <span>代付款</span>
        </van-grid-item>
        <van-grid-item @click="moveOrder(1)">
          <van-icon name="logistics" />
          <span>待发货</span>
        </van-grid-item>
        <van-grid-item @click="moveOrder(2)">
          <van-icon name="envelop-o" />
          <span>待收货</span>
        </van-grid-item>
        <van-grid-item @click="moveAfterSale">
          <van-icon name="after-sale" />
          <span>退款/售后</span>
        </van-grid-item>
        <van-grid-item @click="moveOrder(0)">
          <van-icon name="orders-o" />
          <span>全部订单</span>
        </van-grid-item>
      </van-grid>
    </div>
    <div class="tool">
      <p>其他工具</p>
      <div class="toolInfo">
        <van-grid :border="false" :column-num="4">
          <van-grid-item @click="moveAddress">
            <van-icon name="location-o" />
            <span>地址管理</span>
          </van-grid-item>
          <van-grid-item @click="Toast()">
            <van-icon name="credit-pay" />
            <span>关于退换货</span>
          </van-grid-item>
          <van-grid-item @click="Toast()">
            <van-icon name="service-o" />
            <span>客服</span>
          </van-grid-item>
          <van-grid-item @click="Toast()">
            <van-icon name="manager-o" />
            <span>申请入驻</span>
          </van-grid-item>
          <van-grid-item @click="Toast()">
            <van-icon name="diamond-o" />
            <span>关于亿订</span>
          </van-grid-item>
          <van-grid-item @click="Toast()">
            <van-icon name="setting-o" />
            <span>设置</span>
          </van-grid-item>
        </van-grid>
      </div>
    </div>
    <div style="margin-top:10px; background-color:white">
      <div style="margin:10px 0;padding-bottom:60px">
        <div>
          <van-icon name="fire-o" />
          <span style="font-size:14px;margin-left:5px">热门推荐</span>
        </div>
        <productList />
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>
<script>
import Footer from "../components/TabBar/footer.vue";
import productList from "../components/product/productList";

export default {
  data() {
    return {
      loginState: !this.$store.getters.getLoginState,
      mobile: this.$store.getters.getMobile,
      username: "",
      sex: "",
      cId: "",
      img: this.$store.state.img
    };
  },
  created() {
    if (this.loginState === false) {
      this.$http.get(`/shop/selectByMobile?mobile=${this.mobile}`).then(res => {
        this.username = res.data.data.cLoginName;
        this.sex = res.data.data.sex;
        this.cId = res.data.data.cId;
        this.img = res.data.data.img;
      });
    }
  },
  components: {
    Footer,
    productList
  },
  methods: {
    moveOrder(id) {
      this.$router.push(`/order/${id}`);
    },
    moveAfterSale() {
      this.$router.push("/afterSale");
    },
    Toast() {
      this.$toast("敬请期待");
    },
    register() {
      this.$router.push("/register");
    },
    login() {
      this.$router.push("/login");
    },
    movePersonalInformation() {
      if (this.$store.getters.getLoginState == true) {
        this.$router.push({
          path: "/personalInformation",
          query: {
            cLoginName: this.username,
            mobile: this.mobile,
            sex: this.sex,
            cId: this.cId
          }
        });
      } else {
        this.$notify("请先登录");
      }
    },
    moveAddress() {
      if (this.$store.getters.getLoginState == true) {
        this.$router.push("/address");
      } else {
        this.$toast("请登录");
      }
    }
  }
};
</script>
<style scoped lang="less">
.container {
  background-color: #f6f6f6;
  .header {
    width: 100%;
    background-color: white;
    z-index: 99;
    position: fixed;
    top: 0;
  }
  .bg {
    height: 200px;
    width: 100%;
    .bg-img {
      position: relative;
      z-index: 1;
    }
    .tx-img {
      z-index: 99;
      position: absolute;
      top: 60px;
      margin: 20px 20px;
      span {
        color: white;
      }
    }
  }
  .goodsState {
    margin-top: 60px;
    span {
      font-size: 12px;
      padding-top: 5px;
    }
  }
  .tool {
    margin-top: 10px;
    background: #fff;
    p {
      padding: 10px 10px;
      font-size: 10px;
    }
    .toolInfo {
      span {
        font-size: 10px;
      }
    }
  }
}
.list {
  width: 100%;
  position: absolute;
  z-index: 99;
  top: 200px;
  display: flex;
  justify-content: space-around;
  font-size: 12px;
  color: white;
  .div {
    margin: 0 50px;
  }
}
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
</style>
