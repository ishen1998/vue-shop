<template>
  <div class="header">
    <div class="left">
      <a href="/" id="logo">
        <svg width="34" height="34" class="tanuki-logo" viewBox="0 0 36 36">
          <path
            class="tanuki-shape tanuki-left-ear"
            fill="#e24329"
            d="M2 14l9.38 9v-9l-4-12.28c-.205-.632-1.176-.632-1.38 0z"
          />
          <path
            class="tanuki-shape tanuki-right-ear"
            fill="#e24329"
            d="M34 14l-9.38 9v-9l4-12.28c.205-.632 1.176-.632 1.38 0z"
          />
          <path class="tanuki-shape tanuki-nose" fill="#e24329" d="M18,34.38 3,14 33,14 Z" />
          <path
            class="tanuki-shape tanuki-left-eye"
            fill="#fc6d26"
            d="M18,34.38 11.38,14 2,14 6,25Z"
          />
          <path
            class="tanuki-shape tanuki-right-eye"
            fill="#fc6d26"
            d="M18,34.38 24.62,14 34,14 30,25Z"
          />
          <path
            class="tanuki-shape tanuki-left-cheek"
            fill="#fca326"
            d="M2 14L.1 20.16c-.18.565 0 1.2.5 1.56l17.42 12.66z"
          />
          <path
            class="tanuki-shape tanuki-right-cheek"
            fill="#fca326"
            d="M34 14l1.9 6.16c.18.565 0 1.2-.5 1.56L18 34.38z"
          />
        </svg>
      </a>
      <span class="title">汇道商管理平台</span>
    </div>
    <div class="right">
      <span class="name">{{ name }}</span>
      <i class="el-icon-switch-button" style="color: rgba(255, 87, 34, 1);"></i>
      <span class="close" @click="logoutAdmin">退出</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'heade',
  props: {
    name: {
      type: String,
      required: true
    }
  },
  methods: {
    // 退出
    logoutAdmin () {
      this.$emit('logoOut')
    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100px;
  .left {
    // width: 50%;
    margin-left: 30px;
    .title {
      margin-left: 30px;
      color: #f55d54;
      font-size: 32px;
    }
  }
  .right {
    // width: 50%;
    margin-right: 40px;
    > i {
      font-weight: 700;
      font-size: 22px;
      margin-right: 5px;
    }
    .name {
      font-size: 22px;
      margin-right: 40px;
    }
    .close {
      color: rgba(255, 87, 34, 1);
      cursor: pointer;
    }
  }
}
</style>
