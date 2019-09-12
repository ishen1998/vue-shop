<template>
  <Panel :title="title" :icon="icon">
    <div class="cont-container">
      <el-input
        v-model.trim="input[index]"
        v-for="(item, index) in inputArr"
        :key="item"
        class="typing"
        :placeholder="item"
      ></el-input>
      <!--  需要下拉框请使用slot，example在cardManage.vue，样式归入common.css中 -->
      <slot />
      <!-- end -->
      <el-button type="danger" v-on:click="simple">{{ btVal }}</el-button>
    </div>
  </Panel>
</template>
<script>
import Panel from './panel'
export default {
  /**
   * 参数说明：  title为 面板标题，inputArr 为 输入框提示值，btVal 为按钮的文本值
   * 父级请监听 to-search 事件,在事件函数中，你可以得到填入的input值, 格式如下，序号对应输入框顺序
   *  [input1, input2...*]
   * ☆ 使用了slot请直接在父级组件中进行value的获取，并调用数据
   *
   */
  props: {
    title: {
      type: String,
      default () {
        return '搜索条件'
      }
    },
    inputArr: {
      type: Array,
      required: true
    },
    btVal: {
      type: String,
      default () {
        return '搜索'
      }
    },
    icon: {
      type: Boolean,
      default () {
        return false
      }
    }
  },
  data () {
    return {
      input: []
    }
  },
  components: {
    Panel
  },
  methods: {
    simple () {
      this.$emit('to-search', { input: this.input })
    }
  }
}
</script>
<style lang="scss" scoped>
.cont-container {
  display: flex;
  display: -ms-flexbox;
  display: -webkit-flex;
  justify-content: space-between;
  height: 110px;
  align-items: center;
  -webkit-align-items: center;
  -ms-align-items: center;
  -o-align-items: center;
  margin: 0px 24px;
  .el-input {
    width: 256px;
    height: 48px;
  }
  .el-button {
    width: 112px;
    height: 48px;
    margin-right: 62px;
  }
}
</style>
