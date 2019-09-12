<template>
  <div class="permissionAdd root-container">
    <Headline title="账户管理" label="新增账户" class="border" :middleLine="true"></Headline>
    <Content @handleSubmit="onHandleSub"></Content>
  </div>
</template>

<script>
import Headline from '../../../components/headline.vue'
import Content from '../../../components/options/account/content.vue'
export default {
  created () {},
  methods: {
    // 表单提交
    onHandleSub ({ value, phone, name }) {
      this.$http.Account.add(value, phone, name).then(res => {
        this.$router.back(-1)
      })
      this.$message({
        type: 'success',
        message: '添加成功',
        lockScroll: false
      })
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
