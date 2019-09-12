<template>
  <div class="content">
    <div class="seach">
      <div class="label role">角色名称：</div>
      <el-input v-if="update == null" class="input" v-model="value" placeholder="请输入角色名称"></el-input>
      <div v-else class="label" style="color: rgb(112, 112, 112)">{{update}}</div>
    </div>
    <div class="check">
      <div class="label qx">权限选择：</div>
      <el-checkbox-group v-model="checkList">
        <el-checkbox class="item" v-for="item in selectMenu" :key="item.id" :label="item.name"></el-checkbox>
      </el-checkbox-group>
    </div>
    <div class="btns">
      <el-button
        type="primary"
        style="backgroundColor: #F55D54;border: none;"
        class="btn"
        @click="handleSub"
      >提交</el-button>
      <el-button class="btn" @click="handleCancel">取消</el-button>
    </div>
  </div>
</template>

<script>
/**
 * permission中增加修改的内容组件
 *
 * update: 传入的角色名称， 传入代表是修改页
 *
 * selectMenu: 传入权限信息
 */
export default {
  props: {
    update: {
      type: String
    },
    selectMenu: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      checkList: [],
      value: ''
    }
  },
  methods: {
    handleSub () {
      const list = []
      if (
        this.checkList.length === 0 ||
        (this.update === undefined && this.value.trim() === '')
      ) {
        this.$message({
          type: 'warning',
          message: '角色名或者权限不能为空',
          lockScroll: false
        })
        return
      }
      const name = this.update || this.value
      for (let i = 0; i < this.selectMenu.length; i++) {
        for (let j = 0; j < this.checkList.length; j++) {
          if (this.selectMenu[i].name === this.checkList[j]) {
            list.push(this.selectMenu[i].id)
          }
        }
      }
      this.$emit('handleSubmit', list, name)
    },
    handleCancel () {
      this.$emit('handleCancel')
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 0 49px;
  .seach {
    margin-top: 30px;
    .role {
      float: left;
    }
    .input {
      width: 300px;
      height: 45px;
      line-height: 45px;
    }
  }
  .check {
    .qx {
      margin: 30px 0;
    }
    .item {
      width: 20%;
      height: 45px;
      color: rgba(77, 77, 77, 1);
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
  .label {
    width: 83px;
    height: 45px;
    overflow: hidden;
    line-height: 45px;
    font-size: 16px;
    color: rgba(77, 77, 77, 1);
  }
}
</style>
