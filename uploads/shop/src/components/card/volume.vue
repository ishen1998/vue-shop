<template>
  <div>
    <!-- 优惠券单元格 -->
    <van-coupon-cell
      :coupons="coupons"
      :chosen-coupon="chosenCoupon"
      @click="getList"
      title="卡劵"
    />

    <!-- 优惠券列表 -->
    <van-popup v-model="showList" position="bottom">
      <van-coupon-list
        :show-exchange-bar="false"
        :coupons="coupons"
        :chosen-coupon="chosenCoupon"
        :disabled-coupons="disabledCoupons"
        @change="onChange"
        @exchange="onExchange"
      />
    </van-popup>
  </div>
</template>
<script>
export default {
  data() {
    return {
      chosenCoupon: -1,
      coupons: [],
      disabledCoupons: [],
      showList: false,
      List: []
    };
  },

  methods: {
    onChange(index) {
      this.showList = false;
      this.chosenCoupon = index;
    },
    onExchange() {
      this.List.forEach(item => {
        if (item.ticketStatus == "Y") {
          this.coupons.push({
            id: item.ticketId, //卡劵ID
            name: item.ticketTitle, //卡劵名称
            condition: item.ticketRule, //减免条件
            startAt: (item.startTime =
              Date.parse(new Date(item.startTime)) / 1000), //起始时间
            endAt: (item.endTime = Date.parse(new Date(item.endTime)) / 1000), //结束时间
            // description:item., //描述
            // reason:item., //不可用原因
            value: item.preferentialFee * 100,
            valueDesc: item.preferentialFee, //优惠卷金额
            unitDesc: "元" //单位
          });
        } else {
          this.disabledCoupons.push({
            id: item.ticketId, //卡劵ID
            name: item.ticketTitle, //卡劵名称
            condition: item.ticketRule, //减免条件
            startAt: (item.startTime =
              Date.parse(new Date(item.startTime)) / 1000), //起始时间
            endAt: (item.endTime = Date.parse(new Date(item.endTime)) / 1000), //结束时间
            // description:item., //描述
            // reason:item., //不可用原因
            value: item.preferentialFee * 100,
            valueDesc: item.preferentialFee, //优惠卷金额
            unitDesc: "元" //单位
          });
        }
      });

      // console.log(this.coupons);
      // console.log(this.disabledCoupons);
    },
    getList() {
      this.showList = !this.showList;
    }
  },
  created() {
    this.$http
      .get("/shop/selectAllTicket")
      .then(res => {
        console.log(res.data.data);
        this.List = res.data.data;
        this.onExchange();
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>
