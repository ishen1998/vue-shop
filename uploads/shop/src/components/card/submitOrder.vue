<template>
  <div class="container">
    <van-nav-bar
      title="确认订单"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <div class="address" @click="moveaddress">
      <div style="padding:10px 10px; letter-spacing:1px">
        <p style="font-size:14px">
          <span v-text="address.name"></span>
          <span style="margin-left:25px" v-text="address.tel">电话</span>
        </p>
        <p style="font-size:12px;margin-top:10px">
          <van-tag round type="danger" style="margin-right:5px">默认</van-tag
          >收货地址:
          <span v-text="address.address">地址</span>
        </p>
      </div>
      <div style="padding-right:10px;color:#999">
        <van-icon name="arrow" />
      </div>
    </div>
    <div style="margin: 10px 0">
      <van-cell-group>
        <van-cell title="优惠券" :value="coupon" />
        <van-cell title="运费券" value="暂无可用" />
      </van-cell-group>
    </div>
    <div class="settlement">
      <p>
        <span>商品金额</span>
        <span>￥39.48</span>
      </p>
      <p>
        <span>总运费</span>
        <span>+￥6</span>
      </p>
      <p>
        <span>商品优惠</span>
        <span>-￥0</span>
      </p>
      <p>
        <span>运费优惠</span>
        <span>-￥0</span>
      </p>
    </div>
    <div style="margin:10px 0; background-color:#fff">
      <div class="hot">
        <van-icon name="shop-collect" />
        <span style="font-size:14px">王牌买手</span>
      </div>
      <div v-for="item in carData" :key="item.itemId">
        <car :item="item" :flag="true" />
      </div>
      <div>
        <van-cell-group>
          <van-field
            style="font-size:12px"
            v-model="message"
            label="留言"
            type="textarea"
            placeholder="选填(50字以内)可以告诉卖家你的特殊要求"
            rows="1"
            autosize
            maxlength="50"
          />
        </van-cell-group>
      </div>
    </div>
    <div>
      <submitBar :flag="true" />
    </div>
  </div>
</template>
<script>
import car from "../../components/card/car";
import submitBar from "../../components/card/submitBar";
export default {
  data() {
    return {
      address: {},
      coupon: "暂无可用",
      carData: [],
      message: ""
    };
  },
  created() {
    if (this.$store.getters.getLoginState == false) {
      this.$router.push("/my");
    }
    this.$store.getters.getAddress.some(item => {
      if (item.isDefault == true) {
        this.address = item;
        return;
      }
    });
    //王牌买手购物车
    var cId = this.$store.getters.getcId;
    this.$http.get(`/shop/Trolley/${cId}`).then(res => {
      this.carData = res.data.data;
      var i = 0;
      if (this.carData.length != 0) {
        this.carData.forEach(item => {
          i += parseInt(item.num * item.price);
        });
      }
      this.$store.commit("setTotalPrice", i);
    });
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    moveaddress() {
      this.$router.push("/address");
    }
  },
  components: { car, submitBar }
};
</script>
<style lang="less" scoped>
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
.van-nav-bar {
  position: fixed;
  z-index: 999;
  width: 100%;
}
.container {
  background-color: #f6f6f6;
  .address {
    padding-top: 50px;
    padding-bottom: 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #fff;
  }
  .settlement {
    background-color: #fff;
    padding: 10px 10px;
    p {
      display: flex;
      justify-content: space-between;
      font-size: 12px;
      padding: 5px 5px;
    }
  }
  .hot {
    text-align: center;
    padding: 10px 0;
    background-color: white;
  }
}
</style>
