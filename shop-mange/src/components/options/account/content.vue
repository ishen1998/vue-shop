<template>
  <div class="content">
    <div class="item">
      <em class="title">手机号码：</em>
      <el-input
        class="input"
        v-model="phone"
        @change="handleChange"
        :disabled="userId !== ''?true:false"
        :suffix-icon="userId !== '' ? '':isSearch? 'el-icon-check':'el-icon-close'"
        placeholder="请输入开户者手机号"
      ></el-input>
      <el-button v-if="userId === ''" size="small" @click="handleSearch" class="btn">查询</el-button>
    </div>
    <div class="item">
      <em class="title">账户名称：</em>
      <el-input class="input" v-model="name" placeholder="请输入账户名称"></el-input>
    </div>
    <div class="item">
      <em class="title">账户角色：</em>
      <el-select class="input" v-model="value" placeholder="账户权限">
        <el-option
          v-for="item in selectMenu"
          :key="item.sysRoleId"
          :label="item.sysRoleNickname"
          :value="item.sysRoleId"
        ></el-option>
      </el-select>
    </div>
    <div class="btns">
      <el-button
        type="primary"
        style="backgroundColor: #F55D54;border: none;"
        @click="handleSub"
        class="btn"
      >提交</el-button>
      <el-button class="btn" @click="handleCancel">取消</el-button>
    </div>
  </div>
</template>

<script>
/**
 * account添加修改中内容的公共组件
 *
 * userId: 传入id代表是修改
 */
export default {
  props: {
    userId: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      value: '',
      phone: '',
      name: '',
      selectMenu: [],
      isSearch: false
    }
  },
  methods: {
    handleSub () {
      if (this.userId === '') {
        if (
          this.value === '' ||
          this.phone.trim() === '' ||
          this.name.trim() === '' ||
          !this.isSearch
        ) {
          this.$message({
            type: 'warning',
            message: '字段不能为空或手机号码没验证',
            lockScroll: false
          })
          return
        }
      } else {
        if (this.value === '' || this.name.trim() === '') {
          this.$message({
            type: 'warning',
            message: '字段不能为空',
            lockScroll: false
          })
          return
        }
      }
      this.$emit('handleSubmit', {
        value: this.value,
        userId: this.userId,
        phone: this.phone,
        name: this.name
      })
    },
    handleSearch () {
      if (this.phone.trim() === '') {
        this.$message({
          type: 'warning',
          message: '字段不能为空',
          lockScroll: false
        })
        return
      }
      this.$http.Account.verify(this.phone.trim()).then(res => {
        if (res.data.data === 'N') {
          this.isSearch = true
          this.$message({
            type: 'success',
            message: '存在该手机号',
            lockScroll: false
          })
        } else {
          this.isSearch = false
          this.$message({
            type: 'warning',
            message: '该手机号不存在'
          })
        }
      })
    },
    handleChange () {
      this.isSearch = false
      this.$message({
        type: 'warning',
        message: '已修改手机号，请重新验证',
        lockScroll: false
      })
    },
    handleCancel () {
      this.$router.back(-1)
    }
  },
  created () {
    if (this.userId !== '') {
      this.$http.Account.idSelect(this.userId).then(res => {
        this.phone = res.data.data.phone
      })
    }
    this.$http.Permission.getAllRole().then(res => {
      this.selectMenu = res.data.data
    })
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 25px 50px;
  .item {
    .title {
      width: 80px;
      height: 23px;
      line-height: 23px;
      color: rgba(77, 77, 77, 1);
      font-size: 16px;
      font-family: SourceHanSansSC-regular;
    }
    .input {
      margin-left: 10px;
      width: 201px;
      height: 46px;
      color: rgba(136, 136, 136, 1);
      font-size: 14px;
      font-family: Microsoft Yahei;
    }
    .btn {
      margin-left: 20px;
      width: 80px;
      height: 30px;
      border-radius: 4px;
      color: rgba(16, 16, 16, 1);
      font-size: 14px;
      font-family: Microsoft Yahei;
    }
  }
  .btns {
    margin-top: 300px;
    margin-left: 200px;
    margin-bottom: 20px;
    .btn {
      width: 191px;
      height: 45px;
      border-radius: 8px;
      font-size: 21px;
    }
  }
}
</style>
