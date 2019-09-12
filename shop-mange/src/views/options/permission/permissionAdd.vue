<template>
  <div class="permissionAdd root-container">
    <Headline title="权限管理" label="新增权限" class="border" :middleLine="true"></Headline>
    <Content :selectMenu="selectMenu" @handleSubmit="onSubmit" @handleCancel="onCancel"></Content>
  </div>
</template>

<script>
import Headline from '../../../components/headline.vue'
import Content from '../../../components/options/permission/content.vue'
export default {
  data () {
    return {
      selectMenu: []
    }
  },
  created () {
    this.$http.Permission.getAllPermission().then(res => {
      this.selectMenu = res.data.data
    })
  },
  methods: {
    // 表单提交
    onSubmit (checkList, name) {
      this.$http.Permission.add(checkList, name).then(res => {
        this.$router.back(-1)
      })
      this.$message({
        type: 'success',
        message: '添加成功'
      })
    },
    onCancel () {
      this.$router.back(-1)
    }
  },
  components: {
    Headline,
    Content
  }
}
</script>

<style lang="scss" scoped>
</style>
