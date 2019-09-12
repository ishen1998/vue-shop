<template>
  <div class="userAnalyze root-container">
    <Headline title="用户分析" label="用户增长" :line="true"></Headline>
    <listPanel class="grap" title="数据增长汇报">
      <div class="echart-container">
        <v-chart :options="polar" class="userEchart" />
      </div>
    </listPanel>
  </div>
</template>

<script>
import Headline from '../../components/headline.vue'
import listPanel from '../../components/list.vue'
import { userCreateData } from '../../model/echart-data'
export default {
  data () {
    return {
      polar: null
    }
  },
  methods: {
    // 图表绘制初始化
    pageDataInit () {
      //  数据融合
      this.polar = userCreateData()
    }
  },
  components: {
    Headline,
    listPanel
  },
  mounted () {
    this.pageDataInit()
  }
}
</script>

<style lang="scss" scoped>
.userAnalyze {
  overflow: hidden;
  .el-collapse-item {
    margin-top: 20px;
  }
  .userEchart {
    width: 100%;
    height: 600px;
  }
}
</style>
