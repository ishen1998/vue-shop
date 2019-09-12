<template>
  <div class="headline" :style="line?'border-bottom: 2px solid rgb(224, 224, 224);':''">
    <div class="top" :style="middleLine?'border-bottom: 2px solid rgb(224, 224, 224);':''">
      <div class="title">{{title}}</div>
      <slot></slot>
    </div>
    <div class="label">{{label}}</div>
  </div>
</template>

<script>
/**
 * 管理页头公共组件
 *
 * title: 标题
 * label: 二级标题
 * line: 是否有下划线
 * middleLine: 是否有中间线
 */
export default {
  props: {
    title: {
      type: String,
      required: true
    },
    label: {
      type: String,
      required: true
    },
    line: {
      type: Boolean,
      default: false
    },
    middleLine: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style lang="scss" scoped>
.headline {
  position: relative;
  width: 100%;
  .top {
    overflow: hidden;
    margin: 0 15px;
    .title {
      float: left;
      margin-top: 12px;
      height: 52px;
      line-height: 52px;
      font-size: 20px;
      color: rgba(112, 112, 112, 1);
      font-family: SourceHanSansSC-regular;
    }
    .right {
      float: right;
      height: 52px;
      line-height: 52px;
      margin-right: 20px;
      font-size: 16px;
    }
  }
  .label {
    margin-left: 30px;
    height: 52px;
    line-height: 52px;
    color: rgba(0, 0, 0, 1);
    font-size: 18px;
    font-family: SourceHanSansSC-regular;
  }
}
</style>
