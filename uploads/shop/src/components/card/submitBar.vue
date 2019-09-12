<template>
  <van-submit-bar
    :price="zhongjia * 100"
    button-text="提交订单"
    @submit="submit"
  >
    <!-- <van-checkbox v-model="checked">全选</van-checkbox> -->
  </van-submit-bar>
</template>

<script>
import { setTimeout } from "timers";
export default {
  created() {
    if (this.$store.getters.getLoginState == true) {
      this.$http
        .get(`/shop/Trolley/${this.$store.getters.getcId}`)
        .then(res => {
          //确认订单的商品详情信息参数
          var shop = [];
          res.data.data.forEach(item => {
            let a = {};
            a.itemId = item.itemId;
            a.itemName = item.itemName;
            a.itemNum = item.num;
            a.itemPrice = item.price;
            a.itemImg = item.itemImg;
            shop.push(a);
          });
          this.shopCar = shop;
          //计算总价
          var i = 0;
          res.data.data.forEach(item => {
            i += parseInt(item.num * item.price);
          });
          this.zhongjia = i;
        });
    }
  },
  data() {
    return {
      cId: "",
      shopList: [],
      checked: false,
      zhongjia: 0,
      ress: {},
      shopCar: []
    };
  },
  methods: {
    submit() {
      if (this.$props.flag == true) {
        var address = this.$store.getters.getAddress;
        address.some(item => {
          if (item.isDefault == true) {
            this.ress = item;
          }
        });

        if (this.$store.getters.getAddress.length > 0) {
          //生成订单
          var data = {
            consignee: this.ress.name,
            consigneeMobile: this.ress.tel,
            consigneeAddress: this.ress.address,
            orderDetailInfos: this.shopCar,
            cId: this.$store.getters.getcId,
            cLoginName: this.$store.getters.getLoginName
          };
          this.$http.post("/shop/order/insertOrder", data).then(() => {
            this.$toast.success("提交成功");
            setTimeout(() => {
              this.$http.delete(`/shop/Trolley/${this.$store.getters.getcId}`);
              this.$router.push("/my");
            }, 1000);
          });
        } else {
          this.$toast("请选择地址");
        }
      } else {
        this.$router.push("/submitOrder");
      }
    }
  },
  watch: {
    "$store.getters.getChecked": function() {
      this.zhongjia =
        this.$store.getters.getTotalPrice - this.$store.getters.getChecked;
    }
  },
  props: {
    flag: {
      type: Boolean
    }
  }
};
</script>

<style lang="less" scoped></style>
