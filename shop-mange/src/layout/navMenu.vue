<template>
  <el-menu
    :default-active="this.$route.name | routerName"
    class="el-menu-vertical-demo"
    style="border: none;"
  >
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-pie-chart"></i>
        <span>统计</span>
      </template>
      <router-link tag="li" :to="{ name: 'userAnalyze' }">
        <el-menu-item index="userAnalyze">用户分析</el-menu-item>
      </router-link>
      <router-link tag="li" :to="{ name: 'orderAnalyze' }">
        <el-menu-item index="orderAnalyze">订单分析</el-menu-item>
      </router-link>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-folder"></i>
        <span>用户管理</span>
      </template>
      <router-link tag="li" :to="{ name: 'clientManage' }">
        <el-menu-item index="clientManage">客户管理</el-menu-item>
      </router-link>
      <router-link tag="li" :to="{ name: 'cardManage' }">
        <el-menu-item index="cardManage">卡券管理</el-menu-item>
      </router-link>
      <router-link tag="li" :to="{ name: 'activityManage' }">
        <el-menu-item index="activityManage">活动管理</el-menu-item>
      </router-link>
    </el-submenu>
    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-folder"></i>
        <span>商品管理</span>
      </template>
      <router-link tag="li" :to="{ name: 'commodityManage' }">
        <el-menu-item index="commodityManage">商品管理</el-menu-item>
      </router-link>
      <router-link tag="li" :to="{ name: 'sortManage' }">
        <el-menu-item index="sortManage">分类管理</el-menu-item>
      </router-link>
    </el-submenu>
    <el-submenu index="4">
      <template slot="title">
        <i class="el-icon-folder"></i>
        <span>订单管理</span>
      </template>
      <router-link tag="li" :to="{ name: 'orderManage' }">
        <el-menu-item index="orderManage">订单管理</el-menu-item>
      </router-link>
    </el-submenu>
    <el-submenu index="5">
      <template slot="title">
        <i class="el-icon-setting"></i>
        <span>设置</span>
      </template>
      <router-link tag="li" :to="{ name: 'account' }">
        <el-menu-item index="account">账户管理</el-menu-item>
      </router-link>
      <router-link tag="li" :to="{ name: 'permission' }">
        <el-menu-item index="permission">权限管理</el-menu-item>
      </router-link>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  filters: {
    // 进入子路由依然选中navMenu
    routerName (value) {
      // role: 所有子路由的后缀加在这个数组中，且name必须由父路由和后缀组成
      const arr = ['Add', 'Update', 'Info', 'Edit', 'NewCardCustomer']
      for (let i = 0; i < arr.length; i++) {
        if (value.indexOf(arr[i]) !== -1) {
          return value.replace(arr[i], '')
        }
      }
      return value
    }
  }
}
</script>

<style lang="scss" scoped>
.is-active {
  color: #f55d54 !important;
}
.el-menu-vertical-demo .el-menu-item {
  min-width: 0;
}
</style>
