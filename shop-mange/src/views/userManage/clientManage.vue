<template>
  <div class="root-container clientManage">
    <Headline title="用户管理" label="用户信息"></Headline>
    <Search :inputArr="inputArr" @to-search="searchMobile" />
    <listPanel title="活动列表" class="grap cont-container">
      <el-table
        :style="{ width: '100%' }"
        :data="tableData"
        :row-style="{ height: '164px' }"
        header-align="center"
        class="table-container"
        :empty-text="emptyText"
      >
        <el-table-column align="center" prop="avatar" label="用户头像">
          <template slot-scope="scope">
            <img style="width: 100%;height: 100%;" :src="scope.row.img" alt />
          </template>
        </el-table-column>
        <el-table-column align="center" prop="cLoginName" label="昵称"></el-table-column>
        <el-table-column align="center" prop="avatar" label="用户名"></el-table-column>
        <el-table-column align="center" prop="sex" label="性别"></el-table-column>
        <el-table-column align="center" prop="mobile" label="手机号"></el-table-column>
        <el-table-column align="center" prop="avatar" label="卡券张数"></el-table-column>
        <el-table-column align="center" prop="createTime" label="注册时间"></el-table-column>
      </el-table>
    </listPanel>
    <Pageinate :total="total" :pageSize="pageSize" :receive="true" @receiveCurrent="getCurentPage" />
  </div>
</template>

<script>
import Headline from '../../components/headline.vue'
import Search from '../../components/search.vue'
import listPanel from '../../components/list.vue'
import Guest from './../../Http/userManage/clientManage/clientManage'
import Pageinate from '../../components/paginate.vue'
var util = new Guest()
export default {
  data () {
    return {
      tableData: [],
      inputArr: ['按手机号查找'],
      emptyText: '该条件下无用户',
      total: 0,
      pageSize: 10
    }
  },
  components: {
    Headline,
    Search,
    listPanel,
    Pageinate
  },
  methods: {
    // 点击按钮根据手机号进行搜索, 若为空字符串则为不设条件，查询失败清空表格
    searchMobile ({ input: result }) {
      if (typeof result[0] === 'string' && result[0].trim() !== '') {
        util.getByMobileGuest(
          parseInt(result[0]),
          ({ data }) => {
            this.total = 1
            this.tableData = [data.data]
          },
          // 错误情况
          () => {
            this.tableData = []
          }
        )
      } else {
        this.pageDataInit()
      }
    },
    // 页面金数据初始化函数
    pageDataInit () {
      // 初始化 获取页面数据
      this.getCurentPage({ currentPage: 1 })
    },
    // 接收现在的页码
    getCurentPage (val) {
      if (val.currentPage !== undefined) {
        util.getSwitchPage(
          {
            current: val.currentPage,
            size: this.pageSize
          },
          ({ data: { data } }) => {
            this.total = data.count
            this.tableData = data.customerInfoList
          }
        )
      }
    }
  },
  // create 请求 全部 客户数据
  created () {
    this.pageDataInit()
  }
}
</script>
