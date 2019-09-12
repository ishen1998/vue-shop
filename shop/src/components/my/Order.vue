<template>
  <div style="background-color:#f6f6f6">
    <div>
      <van-nav-bar
        style="position: fixed; top:0; width:100%; z-index:9999"
        title="我的订单"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <div style="padding-top:45px; z-index:1">
      <van-tabs v-model="active" swipeable animated lazy-render>
        <!-- 全部模块 -->
        <van-tab title="全部">
          <!-- 全部部分——判断为空订单时显示图片 -->
          <div v-show="orderData.length > 0 ? false : true">
            <img src="../../assets/kk.jpg" width="100%" height="300px" />
          </div>
          <div v-for="(item, i) in orderData" :key="i" class="bg">
            <!-- 订单的头部 -->
            <div class="hot">
              <van-icon name="shop-collect" style="margin-left:100px" />
              <span style="font-size:14px">王牌买手</span>
              <span
                style="font-size:14px;margin-left:50px;color:red"
                v-text="
                  item.orderStatus == '0'
                    ? '进行中'
                    : item.orderStatus === '2'
                    ? '已取消'
                    : item.payStatus === '1'
                    ? item.orderStatus === '1'
                      ? '已完成'
                      : item.orderStatus === '3'
                      ? '退货中'
                      : '待发货'
                    : '代付款'
                "
              ></span>
            </div>
            <!-- 订单商品 -->
            <div v-for="(elem, i) in item.orderDetailInfos" :key="i">
              <car :item="elem" :flag="true" :allgoods="true" />
            </div>
            <!--可选按钮-->
            <div style="text-align:right; padding:10px 15px">
              <span style="position: absolute;right:60%">
                订单总价:
                <span style="color:red">￥{{ item.payAmount }}</span>
              </span>
              <van-button
                plain
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                @click="delOrder(item.orderId, item.orderStatus)"
                v-show="
                  !item.receiptStatus
                    ? item.orderStatus === '0'
                      ? item.payStatus === '0'
                        ? true
                        : false
                      : false
                    : false
                "
              >
                取消订单
              </van-button>
              <van-button
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                @click="payment(item)"
                v-show="
                  !item.receiptStatus
                    ? item.orderStatus === '0'
                      ? item.payStatus === '0'
                        ? true
                        : false
                      : false
                    : false
                "
              >
                立即支付
              </van-button>
              <van-button
                plain
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                @click="sale(item.orderId)"
                v-show="
                  item.receiptStatus === '1'
                    ? item.orderStatus === '1'
                      ? item.payStatus === '1'
                        ? true
                        : false
                      : false
                    : false
                "
              >
                申请售后
              </van-button>
              <van-button
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                @click="determineOrder(item.orderId)"
                v-show="
                  item.receiptStatus === '0'
                    ? item.orderStatus === '0'
                      ? item.payStatus === '1'
                        ? true
                        : false
                      : false
                    : false
                "
              >
                确认收货
              </van-button>
              <van-button
                plain
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                v-show="
                  item.receiptStatus === '1'
                    ? item.orderStatus === '3'
                      ? item.payStatus === '1'
                        ? true
                        : false
                      : false
                    : false
                "
              >
                取消退货
              </van-button>
              <van-button
                plain
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                @click="delOrder(item.orderId, '2')"
                v-show="
                  !item.receiptStatus
                    ? item.orderStatus === '2'
                      ? item.payStatus === '0'
                        ? true
                        : false
                      : false
                    : false
                "
              >
                删除订单
              </van-button>
            </div>
          </div>
        </van-tab>
        <!-- 代付款模块 -->
        <van-tab title="代付款">
          <div v-show="behalfData.length > 0 ? false : true">
            <img src="../../assets/kk.jpg" width="100%" height="300px" />
          </div>
          <van-checkbox-group v-model="result">
            <div v-for="(item, i) in behalfData" :key="i" class="bg">
              <!-- 订单的头部 -->
              <div class="hot">
                <van-checkbox
                  :key="item.orderId"
                  :name="item.orderId"
                ></van-checkbox>
                <van-icon name="shop-collect" style="margin-left:100px" />
                <span style="font-size:14px">王牌买手</span>
              </div>
              <!-- 订单的商品 -->
              <div v-for="(elem, i) in item.orderDetailInfos" :key="i">
                <car :item="elem" :flag="true" />
              </div>
              <div
                style="text-align:right; padding:10px 15px;position: relative"
              >
                <span style="position: absolute;right:60%">
                  订单总价:
                  <span style="color:red">￥{{ item.payAmount }}</span>
                </span>
                <van-button
                  plain
                  round
                  size="small"
                  type="danger"
                  style="margin:0 10px"
                  @click="delOrder(item.orderId)"
                >
                  取消订单
                </van-button>
                <van-button
                  round
                  type="danger"
                  size="small"
                  v-show="true"
                  @click="payment(item)"
                >
                  立即支付
                </van-button>
              </div>
            </div>
          </van-checkbox-group>
        </van-tab>
        <!-- 待收货模块 -->
        <van-tab title="待收货">
          <div v-show="DeliverData.length > 0 ? false : true">
            <img src="../../assets/kk.jpg" width="100%" height="300px" />
          </div>
          <div v-for="(item, i) in DeliverData" :key="i" class="bg">
            <!-- 订单的头部 -->
            <div class="hot">
              <van-icon name="shop-collect" style="margin-left:100px" />
              <span style="font-size:14px">王牌买手</span>
            </div>
            <div v-for="(elem, i) in item.orderDetailInfos" :key="i">
              <car :item="elem" :allgoods="true" :flag="true" />
            </div>
            <div
              style="text-align:right; padding:10px 15px; position: relative"
            >
              <span style="position: absolute;right:60%">
                订单总价:
                <span style="color:red">￥{{ item.payAmount }}</span>
              </span>
              <van-button
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                @click="determineOrder(item.orderId)"
              >
                确认收货
              </van-button>
            </div>
          </div>
        </van-tab>
        <!-- 已完成模块 -->
        <van-tab title="已完成">
          <div v-show="overData.length > 0 ? false : true">
            <img src="../../assets/kk.jpg" width="100%" height="300px" />
          </div>
          <!--已完成shopcar-->
          <div v-for="(item, i) in overData" :key="i" class="bg">
            <!-- 订单的头部 -->
            <div class="hot">
              <van-icon name="shop-collect" style="margin-left:100px" />
              <span style="font-size:14px">王牌买手</span>
            </div>
            <div v-for="(elem, i) in item.orderDetailInfos" :key="i">
              <car :item="elem" :allgoods="true" :flag="true" />
            </div>
            <div
              style="text-align:right; padding:10px 15px; position: relative"
            >
              <span style="position: absolute;right:60%">
                订单总价:
                <span style="color:red">￥{{ item.payAmount }}</span>
              </span>
              <van-button
                plain
                round
                size="small"
                type="danger"
                style="margin:0 10px"
                @click="sale(item.orderId)"
              >
                申请售后
              </van-button>
            </div>
          </div>
        </van-tab>
      </van-tabs>
    </div>
    <van-submit-bar
      v-show="
        $store.getters.getLoginState == true
          ? active == 1
            ? true
            : false
          : false
      "
      :price="totalPrice * 100"
      button-text="合并支付"
      @submit="allPay()"
    >
      <van-checkbox v-model="getChecked">全选</van-checkbox>
    </van-submit-bar>
  </div>
</template>

<script>
import car from "../card/car.vue";
import { setTimeout } from "timers";
import Qs from "qs";
export default {
  data() {
    return {
      active: 0,
      carData: [],
      item: "",
      a: true,
      zongjia: 0,
      behalfData: [],
      DeliverData: [],
      orderData: [],
      overData: [],
      result: [],
      checked: false
    };
  },
  methods: {
    onClickLeft() {
      this.$store.commit("setValue", 0);
      this.$store.commit("setKaJuanId", 0);
      // this.$store.commit("setTickIndex", -1);
      this.$router.push("/my");
    },
    delOrder(orderId, status) {
      if (status === "2") {
        //删除订单
        this.$dialog
          .confirm({
            title: "请确认",
            message: "确定要删除订单吗?"
          })
          .then(() => {
            this.$http
              .delete(`/shop/order/deleteByOrderId/${orderId}`)
              .then(res => {
                if (res.data.code == 200) {
                  this.$router.push({
                    path: "/pay",
                    query: { msg: "删除成功" }
                  });
                }
              });
          })
          .catch(() => {});
      } else if (status === "1") {
        this.determineOrder(orderId);
      } else {
        //取消订单
        this.$dialog
          .confirm({
            title: "请确认",
            message: "确定要取消订单吗?"
          })
          .then(() => {
            this.$http
              .put("/shop/order/cancelOrder", Qs.stringify({ orderId }))
              .then(res => {
                if (res.data.code == 200) {
                  this.$toast("取消成功");
                  setTimeout(() => {
                    this.$router.push({
                      path: "/pay",
                      query: { msg: res.data.message }
                    });
                  }, 500);
                }
              });
          })
          .catch(() => {
            return;
          });
      }
    },
    payment(item) {
      this.$dialog
        .confirm({
          title: "提示",
          message: "确认支付"
        })
        .then(() => {
          this.$store.commit("setValue", 0);
          this.$store.commit("setKaJuanId", 0);
          var readyData = Qs.stringify({ orderId: item.orderId });
          this.$http.put("/shop/order/paySucceed", readyData).then(res => {
            if (res.data.code == 200) {
              this.$router.push({
                path: "/pay",
                query: { msg: res.data.message }
              });
            } else {
              this.$toast(res.data.message);
            }
          });
        })
        .catch(() => {
          this.$toast("已取消");
        });
    },
    determineOrder(orderId) {
      this.$dialog
        .confirm({
          title: "请确认",
          message: "确定收货了吗?"
        })
        .then(() => {
          this.$http
            .put(`/shop/order/confirmOrder?orderId=${orderId}`)
            .then(res => {
              if (res.data.code == 500) {
                this.$toast(res.data.message);
              }
              this.$router.push({
                path: "/pay",
                query: { msg: res.data.message }
              });
            });
        })
        .catch(() => {
          this.$toast("已取消");
        });
    },
    obtainOder() {
      this.$http
        .get(`/shop/order/queryOrderBycId?cId=${this.$store.getters.getcId}`)
        .then(res => {
          if (res.data.data != null) {
            res.data.data.forEach(item => {
              this.orderData.push(item);
              if (item.payStatus === "0" && item.orderStatus == "0") {
                this.behalfData.push(item);
              }
              if (item.payStatus == "1" && item.orderStatus == "0") {
                this.DeliverData.push(item);
              }
              if (item.payStatus == "1" && item.orderStatus == "1") {
                this.overData.push(item);
              }
            });
          }
        });
    },
    sale(orderId) {
      this.$http.put("/shop/order/recedeOrder?orderId=" + orderId).then(res => {
        this.$router.push({ path: "/pay", query: { msg: res.data.message } });
      });
    },
    allPay() {
      if (this.result.length <= 0) {
        this.$toast("没有可支付的商品");
        return;
      }
      this.$dialog
        .confirm({
          title: "请确认",
          message: "共" + this.totalPrice + "元,确认支付吗"
        })
        .then(() => {
          this.$http.put("/shop/order/payTogether", this.result).then(() => {
            this.$router.push({ path: "/pay", query: { msg: "支付成功" } });
          });
        })
        .catch(() => {
          // on cancel
        });
    }
  },
  created() {
    this.active = parseInt(this.$route.params.orderid);
    if (this.$store.getters.getLoginState == true) {
      //获取订单并且分类
      this.obtainOder();
    } else {
      this.$dialog
        .alert({
          title: "提示",
          message: "你还未登陆,需要登陆才能访问"
        })
        .then(() => {
          this.$router.push("/my");
        });
    }
  },
  components: { car },
  computed: {
    totalPrice() {
      return this.behalfData.reduce(
        (total, item) =>
          total +
          (this.result.indexOf(item.orderId) !== -1 ? item.payAmount : 0),
        0
      );
    },
    getChecked: {
      get: function() {
        return this.result.length == this.behalfData.length ? true : false;
      },
      set: function(val) {
        if (val == false) {
          this.result = [];
        } else {
          this.behalfData.forEach(item => {
            this.result.push(item.orderId);
          });
        }
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
.bg {
  background-color: #fff;
  margin: 10px 10px;
  padding-bottom: 50px;
}
</style>
