<template>
  <div class="home">
    <Heade @logoOut="backSystem" :name="this.$store.state.username"></Heade>
    <el-row
      :gutter="30"
      class="tac"
      style="margin: 0;padding-top: 32px;backgroundColor: rgba(240, 240, 242, 1);"
    >
      <el-col
        class="tac-col"
        style="backgroundColor: white;"
        :md="3"
        :lg="3"
        :xl="3"
        :sm="24"
        :xs="24"
      >
        <NavMenu></NavMenu>
      </el-col>
      <el-col
        class="tac-col-content"
        style="backgroundColor: rgba(240, 240, 242, 1);"
        :md="{ span: 21 }"
        :lg="{ span: 21}"
        :xl="{ span:21 }"
        :sm="{ span: 24 }"
        :sx="24"
      >
        <router-view></router-view>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Heade from '../layout/header.vue'
import NavMenu from '../layout/navMenu.vue'
import account from './../Http/loginRegister'
import { alertInfo } from './../assets/js/common.js'
export default {
  name: 'home',
  components: {
    Heade,
    NavMenu
  },
  methods: {
    backSystem () {
      // 清除token
      this.$util.removeLocal('token')
      this.$util.removeLocal('tokenHeader')
      // 检查
      if (!this.$util.getLocal('token')) {
        this.$router.push({
          name: 'login'
        })
      } else {
        // 原生删除
        window.localStorage.removeItem('token')
        window.localStorage.removeItem('tokenHeader')
        this.$router.push({
          name: 'login'
        })
      }
    },
    getUserInfo () {
      account.checkoutLoginStatus.call(
        this,
        { token: this.$util.getLocal('token') },
        alertInfo
      )
    }
  },
  created () {
    this.getUserInfo()
  }
}
</script>

<style lang="scss">
.home {
  height: auto;
  min-height: 100%;
  .tac {
    min-height: 700px;
    height: auto;
    padding: 0px 15px;
    .tac-col-content {
      padding-right: 0px !important;
    }
    .tac-col {
      min-height: 700px;
      padding-right: 0px !important;
      padding-left: 0px !important;
    }
  }
  .ls-label {
    font-size: 16px;
    color: rgba(16, 16, 16, 1);
  }
  .el-table th.is-leaf,
  .el-table td {
    border-bottom: 2px solid #e0e0e0;
  }
}
@media screen and (max-width: 990px) and (min-width: 300px) {
  div.tac-col {
    height: auto !important;
    margin-bottom: 20px;
    padding-left: 0px !important;
  }
  div.tac-co-content {
    padding-right: 0px !important;
    padding-left: 0px !important;
  }
  .el-collapse-item__header {
    border-bottom: none !important;
  }
}
</style>
