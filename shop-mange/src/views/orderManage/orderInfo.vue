<template>
  <div class="orderInfo">
    <Headline title="订单管理" label="订单详情" :line="true"></Headline>
    <div>
      <ul class="content">
        <li
          v-for="(item, key) in items"
          :key="key"
        >{{item}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{info[key]}}</li>
      </ul>
    </div>
    <List title="商品列表">
      <el-table :data="mock" :row-style="table" style="width: 100%;">
        <el-table-column prop="orderDetailId" align="center" label="编号" label-class-name="ls-label"></el-table-column>
        <el-table-column prop="itemName" align="center" label="名称" label-class-name="ls-label"></el-table-column>
        <el-table-column align="center" label-class-name="ls-label" label="缩略图">
          <template slot-scope="scope">
            <img style="width: 100%;height: 100%" :src="scope.row.itemImg" />
          </template>
        </el-table-column>
        <el-table-column prop="rule" align="center" label-class-name="ls-label" label="规格属性"></el-table-column>
        <el-table-column prop="itemPrice" align="center" label-class-name="ls-label" label="价格"></el-table-column>
        <el-table-column prop="itemNum" align="center" label-class-name="ls-label" label="件数"></el-table-column>
        <el-table-column prop="priceTotal" align="center" label-class-name="ls-label" label="合计"></el-table-column>
        <el-table-column align="center" label-class-name="ls-label" label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="handleEdit(scope.$index, scope.row)">退货审核</el-button>
          </template>
        </el-table-column>
      </el-table>
    </List>
  </div>
</template>

<script>
import Headline from '../../components/headline.vue'
import List from '../../components/list.vue'
export default {
  data () {
    return {
      table: {
        height: '164px'
      },
      items: {
        orderNo: '订单号',
        consignee: '收货人名称',
        consigneeMobile: '收货人电话',
        consigneeAddress: '收货地址',
        shopName: '商品名称',
        rule: '颜色',
        numTotal: '数量',
        payAmount: '实际付款金额',
        balance: '余额支付金额',
        couponAmount: '优惠券支付金额',
        total: '积分支付金额',
        way: '支付方式',
        buyPhone: '购买人电话',
        createTime: '购买时间',
        payTime: '支付时间',
        info: '支付信息'
      },
      info: {},
      mock: []
    }
  },
  created () {
    this.$http.orderManage.infoTop(this.$route.query.id).then(res => {
      this.info = res.data.data
    })
    this.$http.orderManage.infoDowm(this.$route.query.id).then(res => {
      this.mock = res.data.data
    })
  },
  components: {
    Headline,
    List
  }
}
</script>

<style lang="scss" scoped>
.orderInfo {
  background-color: white;
  padding: 0 15px;
  .content {
    margin: 20px 0;
    list-style: none;
  }
}
</style>
