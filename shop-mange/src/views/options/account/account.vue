<template>
  <div class="account root-container">
    <Headline class="border" title="账户管理" label="账户列表" :middleLine="true">
      <el-button type="text" class="right" style="color: blue;">
        <router-link tag="div" :to="{name: 'accountAdd'}">新增用户</router-link>
      </el-button>
    </Headline>
    <List class="list" title="账户信息">
      <el-table :data="mock" :row-style="table" style="width: 100%;">
        <el-table-column align="center" label="用户名" label-class-name="ls-label">
          <template slot-scope="scope">
            <span>{{scope.row.userName}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="角色" label-class-name="ls-label">
          <template slot-scope="scope">
            <span>{{scope.row.sysRoleNickname}}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column align="center" label-class-name="ls-label" label="名字">
          <template slot-scope="scope">
            <span>{{scope.row.sysUser.userName}}</span>
          </template>
        </el-table-column> -->
        <el-table-column align="center" label-class-name="ls-label" label="手机号">
          <template slot-scope="scope">
            <span>{{scope.row.phone}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label-class-name="ls-label" label="状态">
          <template slot-scope="scope">
            <span>{{scope.row.status === 'Y'?'正常':'禁用'}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label-class-name="ls-label" label="操作">
          <template slot-scope="scope">
            <el-button
              type="text"
              style="color: red;"
              @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button>
            <el-button
              type="text"
              style="color: blue;"
              @click="handleUpdate(scope.$index, scope.row)"
            >修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </List>
  </div>
</template>

<script>
import Headline from '../../../components/headline.vue'
import List from '../../../components/list.vue'
import { remove } from '../../../assets/js/common'
export default {
  data () {
    return {
      table: {
        height: '164px'
      },
      mock: []
    }
  },
  methods: {
    // 删除一项
    handleDelete (index, item) {
      remove.call(this, () => {
        this.mock.splice(index, 1)
        this.$http.Account.delete(item.sysUser.userId)
      })
    },
    // 跳转到修改页
    handleUpdate (index, item) {
      this.$router.push({ name: 'accountUpdate', params: { id: item.sysUser.userId } })
    }
  },
  created () {
    // 获取所有的数据
    this.$http.Account.getAll().then(res => {
      console.log(res)
      this.mock = res.data.data.records
    })
  },
  components: {
    Headline,
    List
  }
}
</script>

<style lang="scss">
.account {
  .list {
    padding: 0 11px 0 18px;
  }
}
</style>
