<template>
  <van-submit-bar
    :price="totalPrice * 100 - value"
    :button-text="buttonText"
    @submit="submit"
  ></van-submit-bar>
</template>

<script>
import Qs from "qs";
import { mapState } from "vuex";
export default {
  data() {
    return {
      cId: "",
      shopList: [],
      checked: false,
      zhongjia: 0,
      ress: {},
      shopCar: [],
      value: this.$store.getters.getValue,
      buttonText: "提交订单",
      kaJuanId: 0
    };
  },
  created() {
    if (this.$store.getters.getLoginState == true) {
      this.TotalPrice();
    }
  },
  methods: {
    submit() {
      this.kaJuanId = this.$store.getters.getKaJuanId;
      if (this.$props.flag == true) {
        if (this.ress.consigneeName) {
          //生成订单
          var data = {
            consignee: this.ress.consigneeName,
            consigneeMobile: this.ress.consigneeMobile,
            consigneeAddress: this.ress.consigineeAddress,
            trolleyId: this.shopCar,
            cId: this.$store.getters.getcId,
            cLoginName: this.$store.getters.getLoginName,
            couponAmount: this.value / 100,
            ticketId: this.kaJuanId == 0 ? "" : this.kaJuanId
          };
          console.log(data);
          this.$http
            .post("/shop/order/insertOrder", data)
            .then(res => {
              if (res.data.code == 500) {
                this.$toast(res.data.message);
                return;
              }
              //清空购物车
              this.$http.delete(`/shop/Trolley/${this.$store.getters.getcId}`);
              //确认支付信息
              this.$dialog
                .confirm({
                  title: "支付提示",
                  message: "订单提交成功,现在支付吗?"
                })
                .then(() => {
                  let readyData = Qs.stringify({
                    orderId: res.data.data.orderInfo.orderId
                  });
                  this.$http
                    .put("/shop/order/paySucceed", readyData)
                    .then(res => {
                      this.$router.push({
                        path: "/pay",
                        query: { msg: res.data.message, flag: true }
                      });
                    })
                    .catch(err => {
                      console.log(err);
                    });
                })
                .catch(() => {
                  this.$router.push("/order/1");
                });
            })
            .catch(err => {
              console.log(err);
              this.$toast("出现意料之外的错误");
            });
        } else {
          this.$toast("请选择地址");
        }
      }
    },
    TotalPrice() {
      if (this.$props.flag == true) this.buttonText = "立即支付";
      this.zhongjia = this.$store.getters.getTotalPrice;
      this.shopCar = this.$route.query.result;
      this.$http
        .get("/shop/selectByCustomerId", { cId: this.$store.getters.getcId })
        .then(res => {
          if (res.data.code == 200) {
            res.data.data.some(item => {
              if (item.addressStatus == "Y") {
                this.ress = item;
                return;
              }
            });
          }
        });
    }
  },
  watch: {
    "$store.getters.getChecked": function() {
      this.zhongjia =
        this.$store.getters.getTotalPrice - this.$store.getters.getChecked;
    },
    "$store.getters.getValue": function() {
      this.value = this.$store.getters.getValue;
    }
  },
  props: ["flag", "item", "Allprice"],
  computed: {
    ...mapState(["totalPrice"])
  }
};
</script>

<style lang="less" scoped></style>
