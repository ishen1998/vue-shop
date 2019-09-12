<template>
  <div class="root-container activityManage">
    <Headline title="用户管理" label="活动管理" />
    <Search :inputArr="inputArr" @to-search="handleSearch" />
    <listPanel class="grap" title="活动列表">
      <div>
        <el-button class="addActivity-link" type="danger" @click="toAddActivity">新增</el-button>
        <el-table
          :data="tableSearchData.length === 0 ?tableData:tableSearchData"
          :style="{ width: '100%' }"
          :row-style="{ height: '164px' }"
          header-align="center"
          class="table-container"
        >
          <el-table-column label="活动封面" width="180">
            <template slot-scope="scope">
              <img :style="{width: '100%',height: '100%'}" :src="scope.row.activityImg" alt />
            </template>
          </el-table-column>
          <el-table-column prop="activityName" label="活动名称" align="center"></el-table-column>
          <el-table-column prop="startTime" label="开始时间" align="center"></el-table-column>
          <el-table-column prop="endTime" label="结束时间" align="center"></el-table-column>
          <el-table-column prop="num" label="活动商品件数" align="center"></el-table-column>
          <el-table-column label="状态" align="center">
            <template slot-scope="scope">
              <span>{{scope.row.activityStatus === 'N'? '已结束':'进行中'}}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                style="color: black;"
                @click="handleDelete(scope.$index, scope.row)"
                type="text"
                size="small"
              >删除</el-button>
              <el-button
                style="color: black;"
                type="text"
                size="small"
                @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button>
              <el-button
                style="color: black;"
                @click="handleClick(scope.$index, scope.row)"
                type="text"
                size="small"
              >查看</el-button>
              <el-button
                type="text"
                @click="handleToggle(scope.$index, scope.row)"
                size="small"
              >{{scope.row.activityStatus === 'N'? '开启':'暂停'}}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </listPanel>
    <!-- Page组件 -->
    <Pageinate
      class="page-container"
      :pageSize="pageSize"
      :total="total"
      :receive="true"
      @receivePage="getPageSize"
      @receiveCurrent="getCurrentPage"
    />
  </div>
</template>

<script>
import Headline from '../../../components/headline.vue'
import Search from '../../../components/search.vue'
import listPanel from '../../../components/list.vue'
import Pageinate from '../../../components/paginate.vue'
import { remove } from '../../../assets/js/common'

export default {
  components: {
    Headline,
    Search,
    listPanel,
    Pageinate
  },
  data () {
    return {
      pageSize: 5,
      total: 0,
      inputArr: ['按活动名查找'],
      tableData: [],
      tableSearchData: [],
      searchContent: ''
    }
  },
  created () {
    this.getAll('1')
  },
  methods: {
    // 根据页面获取数据
    getAll (page) {
      this.$http.activityManage.getAll(page).then(res => {
        this.tableData = res.data.data.itemInfos
        this.total = res.data.data.itemCount
      })
    },
    // 跳转到修改页面
    handleEdit (index, item) {
      this.$router.push({
        name: 'activityManageUpdate',
        params: { id: item.activityId }
      })
    },
    // 状态切换
    handleToggle (index, item) {
      let status = 'N'
      if (item.activityStatus === 'N') {
        status = 'Y'
      }
      this.$http.activityManage.updateStatus(item.activityId, status).then(res => {
        this.tableData[index].activityStatus = status
        this.$message({
          type: 'success',
          message: '修改成功'
        })
      })
    },
    // 删除一项
    handleDelete (index, item) {
      remove.call(this, () => {
        this.$http.activityManage.delete(item.activityId).then(res => {
          if (this.tableSearchData.length === 0) {
            this.tableData.splice(index, 1)
            this.total = this.tableData.length
          } else {
            this.tableSearchData.splice(index, 1)
            this.total = this.tableSearchData.length
          }
        })
      })
    },
    getPageSize (event) { },
    // 页面改变时重新获取数据
    getCurrentPage ({ currentPage }) {
      if (this.tableSearchData.length === 0) {
        this.getAll(currentPage)
        return
      }
      this.getpageSearch(currentPage, 5)
    },
    // 跳转到新增页
    toAddActivity () {
      this.$router.push({
        path: '/activityadd'
      })
    },
    // 点击搜索获取数据
    handleSearch ({ input }) {
      if (input[0].trim() === '') {
        this.total = 1
        this.getAll('1')
        this.tableSearchData = []
        this.searchContent = []
        return
      }
      this.searchContent = input[0].trim()
      this.getpageSearch('1', 5)
    },
    // 根据页码和每页数量来查询搜索后的数据
    getpageSearch (page, pageSize) {
      this.$http.activityManage.nameSearch(this.searchContent, page, pageSize).then(res => {
        const list = res.data.data
        this.tableSearchData = list.itemInfos
        this.total = list.itemCount
        this.pageSize = pageSize
      })
    }
  }
}
</script>

<style lang="scss">
.grap {
  position: relative;
}
.addActivity-link {
  position: absolute;
  right: 59px;
  top: 8px;
  width: 90px;
  height: 48px;
}
</style>
