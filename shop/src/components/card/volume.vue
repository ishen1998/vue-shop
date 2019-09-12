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
import { mapState } from "vuex";
export default {
  data() {
    return {
      chosenCoupon: this.$store.getters.getTickIndex,
      coupons: [],
      disabledCoupons: [],
      showList: false,
      List: [],
      mobile: this.$store.getters.getMobile,
      value: 0,
      kaJuanId: 0
      // totalPrice: this.$store.getters.getTotalPrice
    };
  },

  methods: {
    getTicket() {
      this.$http
        .get(
          "/shop/ticket/selectByCustomerMobile?" +
            "mobile=" +
            this.mobile +
            "&current=1&" +
            "size=100"
        )
        .then(res => {
          console.log(res.data.data.customerTicketPage);
          console.log(res.data.data);
          this.coupons = [];
          this.disabledCoupons = [];
          this.List = [];
          this.List = res.data.data.customerTicketPage;

          // this.List.forEach(item => {
          //   console.log(item.ticketInfo.customerTicketPage);
          // });
          this.onExchange();
        })
        .catch(err => {
          console.log(err);
        });
    },
    onChange(index) {
      this.getTicket();
      this.showList = false;
      this.chosenCoupon = index;
      this.value = this.coupons[index].value;
      this.kaJuanId = this.coupons[index].id;
      this.$store.commit("setValue", this.value);
      this.$store.commit("setKaJuanId", this.kaJuanId);
      // this.$store.commit('setTicketIndex',this.index)
      console.log(this.chosenCoupon);
    },
    onExchange() {
      this.List.forEach(item => {
        var condition = item.ticketInfo.ticketRule.split(",");
        console.log(Date.parse(new Date(item.ticketInfo.startTime)) / 1000);
        if (
          (item.ticketInfo.ticketStatus == "Y") &
          (this.totalPrice >= condition[0]) &
          (item.useStatus == 1)
        ) {
          // console.log(condition)
          this.coupons.push({
            id: item.ticketCustomerId, //卡劵ID
            name: item.ticketInfo.ticketTitle, //卡劵名称
            condition: "满" + condition[0] + "减" + condition[1], //减免条件
            manJian: condition[0],
            startAt: (item.ticketInfo.startTime =
              Date.parse(new Date(item.ticketInfo.startTime)) / 1000), //起始时间
            endAt: (item.ticketInfo.endTime =
              Date.parse(new Date(item.ticketInfo.endTime)) / 1000), //结束时间
            // description:item., //描述
            // reason:item., //不可用原因
            value: item.ticketInfo.preferentialFee * 100,
            valueDesc: item.ticketInfo.preferentialFee, //优惠卷金额
            unitDesc: "元" //单位
          });
        } else {
          this.disabledCoupons.push({
            id: item.ticketCustomerId, //卡劵ID
            name: item.ticketInfo.ticketTitle, //卡劵名称
            condition: "满" + condition[0] + "减" + condition[1], //减免条件
            startAt: (item.ticketInfo.startTime =
              Date.parse(new Date(item.ticketInfo.startTime)) / 1000), //起始时间
            endAt: (item.ticketInfo.endTime =
              Date.parse(new Date(item.ticketInfo.endTime)) / 1000), //结束时间
            // description:item., //描述
            // reason:item., //不可用原因
            value: item.ticketInfo.preferentialFee * 100,
            valueDesc: item.ticketInfo.preferentialFee, //优惠卷金额
            unitDesc: "元" //单位
          });
        }
      });

      // console.log(this.coupons);
      // console.log(this.disabledCoupons);
      // this.totalPrice >= 500
    },
    getList() {
      this.getTicket();
      this.showList = !this.showList;
      this.kaJuanId = this.$store.getters.getKaJuanId;
      console.log(this.totalPrice);
      console.log(this.kaJuanId);
    }
  },
  computed: {
    ...mapState(["totalPrice"])
  },
  created() {
    this.getTicket();
  }
};
</script>
