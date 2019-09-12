<template>
  <div class="permissionAdd root-container">
    <Headline title="账户管理" label="修改账户" class="border" :middleLine="true"></Headline>
    <Content :userId="$route.params.id" @handleSubmit="onHandleSub"></Content>
  </div>
</template>

<script>
import Headline from '../../../components/headline.vue'
import Content from '../../../components/options/account/content.vue'
export default {
  created () {},
  methods: {
    // 表单提交
    onHandleSub ({ value, userId, phone, name }) {
      this.$http.Account.update([value], {
        userId: userId,
        phone: phone,
        userName: name
      }).then(res => {
        this.$router.back(-1)
      })
      this.$message({
        type: 'success',
        message: '修改成功',
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
