<template>
  <div class="permissionUpdate root-container">
    <Headline title="权限管理" label="修改权限" class="border" :middleLine="true"></Headline>
    <Content
      :update="name"
      :selectMenu="selectMenu"
      @handleSubmit="onSubmit"
      @handleCancel="onCancel"
    ></Content>
  </div>
</template>

<script>
import Headline from '../../../components/headline.vue'
import Content from '../../../components/options/permission/content.vue'
export default {
  data () {
    return {
      selectMenu: [],
      name: '',
      id: ''
    }
  },
  methods: {
    // 表单提交
    onSubmit (list) {
      this.$http.Permission.update(list, this.id)
      this.$message({
        type: 'success',
        message: '修改成功',
        lockScroll: false
      })
      this.$router.back(-1)
    },
    onCancel () {
      this.$router.back(-1)
    }
  },
  created () {
    this.name = this.$route.params.name
    this.id = this.$route.params.id
    this.$http.Permission.getAllPermission().then(res => {
      this.selectMenu = res.data.data
    })
  },
  components: {
    Headline,
    Content
  }
}
</script>

<style lang="scss" scoped>
</style>
