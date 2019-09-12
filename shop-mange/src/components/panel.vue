<template>
  <div class="panel">
    <div class="head">
      <div class="title">{{title}}</div>
      <div v-if="icon" class="el-icon-info right"></div>
      <el-button v-if="btn" type="text" class="btn">打印表格</el-button>
    </div>
    <slot />
  </div>
</template>

<script>
/**
 * 卡片头公用组件
 *
 * title: panel容器的标题
 * icon: 是否需要右侧的小图标
 * btn: 是否需要打印表格的按钮
 */
export default {
  props: {
    title: {
      type: String,
      required: true
    },
    icon: {
      type: Boolean,
      default: false
    },
    btn: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style lang="scss" scoped>
.panel {
  overflow: hidden;
  .head {
    height: 78px;
    line-height: 78px;
    background-color: rgba(224, 224, 224, 1);
    border: 1px solid rgba(224, 224, 224, 1);
    .title {
      float: left;
      margin-left: 20px;
      font-size: 18px;
    }
    .right {
      float: right;
      height: 100%;
      margin-right: 43px;
      margin-top: 30px;
      font-size: 18px;
    }
    .btn {
      float: right;
      color: blue;
      margin-right: 60px;
      margin-top: 20px;
      font-size: 18px;
    }
  }
}
</style>
