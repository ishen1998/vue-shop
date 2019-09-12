<template>
  <div class="block page-container">
    <div class="pageHandle">
      <span
        class="el-icon-caret-left"
        @click="prev"
        :style="{ color: this.currentPage <= 1 ? '#e0e0e0': 'black' }"
      ></span>
      <i v-text="currentPage + '/' + maxPage"></i>
      <span
        class="el-icon-caret-right"
        @click="next"
        :style="{ color: this.currentPage >= this.maxPage ? '#e0e0e0': 'black' }"
      ></span>
    </div>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-size="pageSize"
      layout="jumper"
      :total="total"
      :small="true"
      :current-page="currentPage"
    ></el-pagination>
    <button type="button" class="submit-button" @click="handleCurrentChange(currentPage)">跳至</button>
  </div>
</template>
<script>
/**
 * 参数说明： 最大条件 total  一页显示数量  pageSize 两者为必填项，页码从 1 开始计算
 * 获取参数： 获取参数必须传递 receive 为true
 *  可以通过监听 receivePage 接收当前的页码显示数量，
 *  可以通过监听 receiveCurrent 接收当前的页码，
 * ！注意：
 *  1-如果用户在当前页中，点击了跳转按钮，则这次跳转将不会被触发
 *
 */
export default {
  name: 'pageNumber',
  methods: {
    // 获取每页条数
    handleSizeChange (val) {
      if (this.$props.receive === true) {
        this.$emit('receivePage', { pageSize: val })
      }
    },
    // 获取当前页码
    handleCurrentChange (val) {
      this.currentPage = val
      if (this.$props.receive === true) {
        if (this.oldVal === this.currentPage) return false
        this.$emit('receiveCurrent', { currentPage: val })
      }
      this.oldVal = val
    },
    prev () {
      if (this.currentPage >= 2) {
        this.currentPage -= 1
      }
    },
    //
    next () {
      if (this.currentPage <= this.maxPage - 1) {
        this.currentPage += 1
      }
    },
    initValue () {
      this.currentPage = 1
    }
  },
  data () {
    return {
      currentPage: 1,
      oldVal: 1
    }
  },
  props: {
    pageSize: {
      type: Number,
      required: true
    },
    total: {
      type: Number,
      required: true
    },
    receive: {
      type: Boolean,
      default () {
        return false
      }
    }
  },
  watch: {
    currentPage (val) {
      this.$emit('receiveCurrent', { currentPage: val })
    }
  },
  computed: {
    maxPage () {
      this.initValue()
      if (this.$props.total === 0) {
        return 1
      } else {
        return Math.ceil(this.total / this.pageSize)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.page-container {
  display: flex;
  display: -ms-flexbox;
  display: -moz-flex;
  display: -webkit-flex;
  justify-content: space-around;
  width: 400px;
  border: none;
  align-items: center;
  -webkit-align-items: center;
  -ms-align-items: center;
  .pageHandle {
    display: flex;
    display: -ms-flexbox;
    display: -moz-flex;
    display: -webkit-flex;
    justify-content: space-around;
    span {
      font-size: 24px;
    }
    i {
      margin: 0px 15px;
    }
  }
  .submit-button {
    width: 60px;
    height: 30px;
  }
}
</style>
