<template>
  <van-popup
    v-model="msg"
    round
    position="bottom"
    :style="{ height: '60%' }"
    :close-on-click-overlay="false"
  >
    <div>
      <div class="popup">
        <img :src="msgData.coverImg" width="20%" height="100px" />
        <div class="popup-right">
          <p style="margin-bottom:40px;">{{ msgData.itemName }}</p>
          <p style="color: #999">库存: {{ msgData.num }}(限购3件)</p>
          <p style="font-size:14px;font-weight:700;color:red">
            ￥{{ activityPrice }}
          </p>
        </div>
        <van-icon name="close" style="float: right;" @click="close" />
      </div>
      <div class="popup-color">
        <van-button round :type="btnStyle" size="small" @click="addColor"
          >红色</van-button
        >
      </div>
      <div class="popup-bottom">
        <p style="width:50%">M</p>
        <div>
          <span style="font-size:12px;color:#999;margin-right:20px"
            >￥{{ activityPrice }}</span
          >
          <van-stepper v-model="value" min="0" :disabled="!active" max="3" />
        </div>
      </div>

      <div style="font-size:12px; color:#999,">
        <p style="text-align:right;padding:5px 5px">
          共
          <span style="color:red">{{ this.value }}</span
          >件
          <span style="color:red"
            >￥{{ (activityPrice * this.value).toFixed(2) }}</span
          >
        </p>
      </div>
      <div class="addShopCar-btn">
        <van-button type="danger" size="large" @click="addShopCar"
          >加入购物车</van-button
        >
      </div>
    </div>
  </van-popup>
</template>

<script>
export default {
  data() {
    return {
      value: 0,
      btnStyle: "default",
      active: false,
      cId: "",
      cLoginName: "",
      activityPrice: 0,
      msgData: {}
    };
  },
  components: {
    // Volume
  },
  methods: {
    close() {
      this.$emit("setShow");
    },
    addColor() {
      if (this.btnStyle == "default") {
        this.btnStyle = "danger";
        this.active = true;
      } else {
        this.btnStyle = "default";
        this.value = 0;
        this.active = false;
      }
    },
    addShopCar() {
      if (this.msgData.isShow == "N") {
        this.$toast("该货物已下架");
        return;
      }
      if (this.value > 0) {
        var trolleyInfoEntity = {
          cid: this.cId,
          cloginName: this.cLoginName,
          itemId: this.msgData.itemId,
          itemName: this.msgData.itemName,
          num: this.value,
          price: this.activityPrice,
          itemImg: this.msgData.coverImg
        };
        this.$http.post("/shop/Trolley", trolleyInfoEntity).then(res => {
          if (res.data.code == 200) {
            this.$toast("添加成功");
            this.$store.commit("addCarNum", trolleyInfoEntity.num);
            this.$emit("setShow");
          } else {
            this.$toast(res.data.message);
          }
        });
      } else {
        this.$toast("请选择商品");
      }
    },
    isgoods() {
      this.$http
        .get(`/shop/shop/selectByPrimaryKey?itemId=${this.$route.params.id}`)
        .then(res => {
          this.msgData = res.data.data;
          console.log(res);
          this.activityPrice +=
            this.msgData.isActivity == "Y"
              ? parseFloat(this.msgData.priceAndStatuses[0].activityPrice)
              : this.msgData.price;
        });
    }
  },
  props: ["msg"],
  created() {
    this.isgoods();
    if (this.$store.getters.getLoginState == true) {
      this.$http
        .get(`/shop/selectByMobile?mobile=${this.$store.getters.getMobile}`)
        .then(res => {
          this.cId = res.data.data.cId;
          this.cLoginName = res.data.data.cLoginName;
        })
        .catch(err => {
          console.log(err);
          this.$toast("出现意料之外的错误");
        });
    }
  }
};
</script>

<style lang="less" scoped>
.popup {
  border-bottom: #f6f6f6 1px solid;
  display: flex;
  padding: 15px 15px;
  .popup-right {
    margin: 0 15px;
    font-size: 12px;
    width: 100%;
  }
}
.addShopCar-btn {
  position: absolute;
  bottom: 0px;
  width: 100%;
}
.popup-color {
  padding: 10px 10px;
  display: flex;
  border-bottom: #f6f6f6 1px solid;
}
.popup-bottom {
  display: flex;
  align-items: center;
  justify-content: space-around;
  border-bottom: #f6f6f6 1px solid;
  padding: 15px 0;
  div {
    display: flex;
    align-items: center;
  }
}
</style>
