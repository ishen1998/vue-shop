<template>
  <div class="sortManage root-container">
    <Headline class="head" title="商品管理" label="分类管理">
      <router-link :to="{name: 'sortManageAdd'}">
        <el-button class="btn btn-right" type="danger">新增</el-button>
      </router-link>
    </Headline>
    <Search :inputArr="['请输入分类名称']" @to-search="search"></Search>
    <List class="list" :notTitle="true">
      <div>
        <el-table :data="mock" :row-style="list" style="width: 100%">
          <el-table-column align="center" label="编号" label-class-name="ls-label">
            <template slot-scope="scope">
              <span>{{scope.$index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="categoryId"
            align="center"
            label="分类id"
            label-class-name="ls-label"
          ></el-table-column>
          <el-table-column label="分类图" align="center" label-class-name="ls-label">
            <template slot-scope="scope">
              <img :style="{width: '100%',height: '100%'}" :src="scope.row.categoryImage" alt />
            </template>
          </el-table-column>
          <el-table-column
            prop="categoryName"
            align="center"
            label-class-name="ls-label"
            label="分类名"
          ></el-table-column>
          <el-table-column align="center" label-class-name="ls-label" label="启用/禁用">
            <template slot-scope="scope">
              <span
                :style="scope.row.categoryStatus === 'N'?'color: red;':''"
              >{{scope.row.categoryStatus === 'N'?'禁用':'启用'}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label-class-name="ls-label" label="操作">
            <template slot-scope="scope">
              <el-button
                type="text"
                :style="scope.row.categoryStatus === 'Y'?'color: red;':''"
                @click="handleToogle(scope.$index, scope.row)"
              >{{scope.row.categoryStatus === 'Y'?'禁用':'启用'}}</el-button>
              <el-button type="text" @click="handleInfo(scope.$index, scope.row)">编辑</el-button>
              <el-button type="text" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Paginate class="paginate" :total="total" :pageSize="pageSize" @receiveCurrent="updatePage"></Paginate>
      </div>
    </List>
  </div>
</template>

<script>
import Headline from '../../../components/headline.vue'
import Search from '../../../components/search.vue'
import List from '../../../components/list.vue'
import Paginate from '../../../components/paginate.vue'
import { remove } from '../../../assets/js/common'
export default {
  data () {
    return {
      input: '',
      pageSize: 5,
      total: 0,
      mock: [],
      list: {
        height: '164px'
      }
    }
  },
  methods: {
    // 点击搜索按钮
    search (index) {
      if (index.input[0] === '') {
        this.getAll()
        return
      }
      this.$http.sortManage.nameSearch(index.input[0]).then(res => {
        this.total = res.data.data.length
        this.mock = res.data.data
      })
    },
    // 从接口获取数据
    getAll (current = '1') {
      this.$http.sortManage.getAll(current).then(res => {
        this.mock = res.data.data
      })
      this.$http.sortManage.getCount().then(res => {
        this.total = res.data.data
      })
    },
    // 状态的切换
    handleToogle (index, item) {
      let message = '禁用成功!'
      let status = 'N'
      if (item.categoryStatus === 'N') {
        message = '启用成功!'
        status = 'Y'
      }
      this.$message({
        type: 'success',
        message,
        lockScroll: false
      })
      this.mock[index].categoryStatus = status
      this.$http.sortManage.updateStatus(item.categoryId, status)
    },
    // 删除一项
    handleDelete (index, item) {
      remove.call(this, () => {
        this.$http.sortManage.delete(item.categoryId).then(res => {
          this.mock.splice(index, 1)
          this.total = this.mock.length
        })
      })
    },
    // 切换页码
    updatePage ({ currentPage }) {
      this.getAll(currentPage)
    },
    // 进入编辑页面
    handleInfo (index, row) {
      this.$router.push({ name: 'sortManageUpdate', params: { id: row.categoryId } })
    }
  },
  created () {
    this.getAll()
  },
  components: {
    Headline,
    List,
    Search,
    Paginate
  }
}
</script>

<style lang="scss" scoped>
.sortManage {
  .head {
    .btn-right {
      position: absolute;
      right: 86px;
      top: 50px;
    }
  }
  .list {
    padding: 0 12px;
    overflow: hidden;
    .paginate {
      margin: 63px 0;
      float: right;
      margin-right: 135px;
    }
  }
  .btn {
    width: 112px;
    height: 42px;
  }
}
</style>
