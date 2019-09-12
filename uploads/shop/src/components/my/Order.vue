<template>
  <div>
    <div>
      <van-nav-bar
        style="position: fixed; top:0; width:100%; z-index:999"
        title="我的订单"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <div style="padding:45px 0;">
      <van-tabs v-model="active" swipeable animated lazy-render>
        <van-tab title="全部">
          <div v-for="(item, i) in carData" :key="i">
            <div class="hot">
              <van-icon name="shop-collect" style="margin-left:100px" />
              <span style="font-size:14px">王牌买手</span>
              <span style="font-size:14px;margin-left:50px;color:red">{{
                carheader
              }}</span>
            </div>
            <car :item="item" :flag="true" :allgoods="true" />
            <div style="text-align:right; padding:10px 15px">
              <van-button
                plain
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                @click="delOrder(item.orderId)"
                >取消订单</van-button
              >
              <van-button round type="danger" size="small">立即支付</van-button>
            </div>
          </div>
        </van-tab>
        <van-tab title="待发货"></van-tab>
        <van-tab title="待收货"></van-tab>
        <van-tab title="代付款">
          <div v-for="(item, i) in behalfData" :key="i">
            <div class="hot">
              <van-icon name="shop-collect" style="margin-left:100px" />
              <span style="font-size:14px">王牌买手</span>
            </div>
            <car
              :item="item"
              :flag="true"
              @changCheckbox="p"
              :checkeds="checked"
              :NoAll="Noall"
            />
            <div style="text-align:right; padding:10px 15px">
              <van-button
                plain
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                @click="delOrder(item.orderId)"
                >取消订单</van-button
              >
              <van-button round type="danger" size="small">立即支付</van-button>
            </div>
          </div>
        </van-tab>
        <van-tab title="已完成"></van-tab>
      </van-tabs>
    </div>
    <van-submit-bar
      v-show="checkeds"
      :price="zongjia * 100"
      button-text="合并支付"
    >
      <van-checkbox v-model="checked" @click="allGoods">全选</van-checkbox>
      <van-button
        size="mini"
        type="danger"
        round
        style="margin:5px"
        @click="allNoGoods"
        >删除全选</van-button
      >
    </van-submit-bar>
  </div>
</template>

<script>
import car from "../card/car.vue";
import { setTimeout } from "timers";
export default {
  data() {
    return {
      active: 0,
      carData: [],
      item: "",
      orderId: [],
      a: true,
      checkeds: this.zongjia == 0 ? false : true,
      checked: false,
      twochecked: false,
      zongjia: 0,
      Noall: true,
      behalfId: [],
      behalfData: [],
      carheader: ""
    };
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    p(e, a) {
      console.log(e);
      console.log(a);
      if (e == false) {
        console.log(this.behalfData);
        this.zongjia += a.priceTotal;
        //判断全选
        var testZj = 0;
        this.behalfData.forEach(item => {
          testZj += item.priceTotal;
        });
        if (testZj == this.zongjia) {
          this.checked = true;
        }
      } else {
        this.zongjia = this.zongjia - a.priceTotal;
        //判断全选
        var Zj = 0;
        this.behalfData.forEach(item => {
          Zj += item.priceTotal;
          Zj -= a.priceTotal;
        });
        if (Zj != this.zongjia) {
          this.checked = false;
        }
      }
    },
    delOrder(orderId) {
      this.$dialog
        .confirm({
          title: "请确认",
          message: "取消订单后将返回到购物车"
        })
        .then(() => {
          this.$http
            .delete(`/shop/order/deleteByOrderId?orderId=${orderId}`)
            .then(res => {
              if (res.data.code == 200) {
                this.$toast("取消成功");
                setTimeout(() => {
                  this.$router.push("/cart");
                }, 500);
              }
            });
        })
        .catch(() => {
          return;
        });
    },
    allGoods() {
      if (this.checked == false) {
        var testZj = 0;
        this.behalfData.forEach(item => {
          testZj += item.priceTotal;
        });
        this.zongjia = testZj;
      }
    },
    allNoGoods() {
      this.checked = false;
      this.Noall = !this.Noall;
      this.zongjia = 0;
    }
  },
  created() {
    this.active = parseInt(this.$route.params.orderid);
    // var data = { current: 1, cId: this.$store.getters.getcId }
    this.$http
      .get(`/shop/order/queryOrderBycId?cId=${this.$store.getters.getcId}`)
      .then(res => {
        console.log(res.data.data);
        if (res.data.data != null) {
          var orderId = [];
          var behalfId = [];
          res.data.data.forEach(item => {
            orderId.push(item.orderId);
            if (item.payStatus == "1") {
              behalfId.push(item.orderId);
            }
          });
          this.orderId = orderId;
          this.behalfId = behalfId;
        }
      });
  },
  components: { car },
  watch: {
    orderId: function(a) {
      for (let i = 0; i < a.length; i++) {
        this.$http
          .get(`/shop/orderDetail/queryOrderDetail?orderId=${a[i]}`)
          .then(res => {
            this.carData.push(res.data.data[0]);
            // console.log(this.carData);
          });
      }
    },
    behalfId: function(a) {
      for (let i = 0; i < a.length; i++) {
        this.$http
          .get(`/shop/orderDetail/queryOrderDetail?orderId=${a[i]}`)
          .then(res => {
            this.behalfData.push(res.data.data[0]);
          });
      }
    }
  }
};
</script>

<style scoped lang="less">
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
.hot {
  display: flex;
  text-align: center;
  padding: 10px 10px;
  background-color: white;
  text-align: center;
}
</style>
