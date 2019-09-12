<template>
  <div class="list">
    <div class="title" :style="notTitle?'height: 19px;':''">{{title}}</div>
    <slot />
  </div>
</template>

<script>
/**
 * 表格头公有组件
 *
 * 参数：
 *  title 表格标题
 *
 *  notTitle 是否具有标题
 *
 *
 */
export default {
  name: 'list-cmp',
  props: {
    title: {
      type: String
    },
    notTitle: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style lang="scss" scoped>
  .list {
    .title {
      width: 100%;
      height: 60px;
      line-height: 60px;
      font-size: 18px;
      border-bottom: 4px solid #E0E0E0;
      color: rgb(94, 94, 94);
    }
  }
</style>
