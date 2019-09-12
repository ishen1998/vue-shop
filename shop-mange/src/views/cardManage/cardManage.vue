<template>
  <div class="root-container cardManage">
    <Headline title="卡券管理" label="卡券张数">
      <el-button class="add-button" @click="toAddNewCard" type="danger">发布卡劵</el-button>
    </Headline>
    <Search :inputArr="inputArr" @to-search="filterCardByPhoneAndSize">
      <div class="select-group">
        <div class="select-item" v-for="(item, index) in options" :key="item.label">
          <em class="select-label">{{ item.label }}</em>
          <el-select v-model="condition[index]">
            <el-option
              v-for="unit in item.select"
              :key="unit.label"
              :label="unit.label"
              :value="unit.value"
            ></el-option>
          </el-select>
        </div>
      </div>
    </Search>
    <listPanel class="grap" title="卡劵信息">
      <el-table
        :data="tableData"
        header-align="center"
        class="table-container"
        :row-style="{ height: '164px' }"
        :style="{ width: '100%' }"
      >
        <el-table-column align="center" prop="ticketId" label="卡劵ID"></el-table-column>
        <el-table-column align="center" prop="createdBy" label="用户名"></el-table-column>
        <el-table-column align="center" prop="ticketRule" label="规则"></el-table-column>
        <el-table-column align="center" prop="ticketNum" label="发劵数量"></el-table-column>
        <el-table-column align="center" prop="ticketNum" label="实付金额"></el-table-column>
        <el-table-column align="center" prop="ticketStatus" label="状态">
          <template slot-scope="lineData">
            <span>{{ lineData.row.ticketStatus | cardStatusUse }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="updatedTime" label="激活时间"></el-table-column>
      </el-table>
    </listPanel>
    <Pageinate :total="total" :pageSize="pageSize" :receive="true" @receiveCurrent="getCurentPage" />
  </div>
</template>

<script>
import Headline from '../../components/headline.vue'
import Search from '../../components/search.vue'
import listPanel from '../../components/list.vue'
import { selectMenu } from './../../model/cardManage'
import Pageinate from '../../components/paginate.vue'
import cardModel from './../../Http/cardManage/index'
import { alertInfo } from '../../assets/js/common'
export default {
  components: {
    Headline,
    Search,
    listPanel,
    Pageinate
  },
  data () {
    return {
      options: selectMenu,
      // 默认值为第一个选项，选项数据在assets/model/cardManage.js中
      condition: [0, 5],
      inputArr: ['按手机号查找'],
      tableData: [],
      total: 0,
      current: 1,
      pageSize: 10
    }
  },
  methods: {
    // 跳转到新增页面
    toAddNewCard () {
      this.$router.push({
        path: '/newCardCustomer'
      })
    },
    // 事件函数
    getCurentPage (val) {
      if (val.currentPage !== undefined) {
        this.getCardList(val.currentPage, this.pageSize)
      }
    },
    // 组件函数
    getCardList (current, pageSize) {
      cardModel.getCardByPageSize(
        {
          current: current,
          size: pageSize
        },
        ({ data: { data } }) => {
          this.total = data.count
          this.tableData = data.ticketInfo
        },
        () => {}
      )
    },
    filterCardByPhoneAndSize (val) {
      function isEmpty (key, offset, that) {
        if (this[key].length === 0 || this[key][offset].length < 1) {
          // 如果input要单独处理，因为其需要手机号
          return alertInfo.call(that, {
            cont: key === 'input' ? '请填写手机号' : '请选择每页数量',
            type: 'warning'
          })
        }
        if (key === 'input' && !/^\d{11}$/g.test(this[key][offset])) {
          return alertInfo.call(that, {
            cont: '手机号格式错误',
            type: 'warning'
          })
        }
        return true
      }

      if (
        !isEmpty.apply(val, ['input', 0, this]) ||
        !isEmpty.apply(this, ['condition', 1, this])
      ) {
        return false
      }
      this.total = 0
      cardModel.getCardByPhoneAndPageSize(
        {
          phone: val.input[0],
          current: this.pageSize,
          size: this.condition[1]
        },
        ({ data: { data } }) => {
          this.tableData = data.customerTicket
        },
        ({ data }) => {
          alertInfo.call(this, {
            title: '查询失败',
            cont: data.msg,
            type: 'info'
          })
        }
      )
    },
    pageDataInit () {
      this.getCardList(this.current, this.pageSize)
    },
    deleteCardNeedId () {}
  },
  created () {
    this.pageDataInit()
  },
  filters: {
    cardStatusUse (val) {
      return val === 'Y' ? '正在使用期' : '失效'
    }
  }
}
</script>
