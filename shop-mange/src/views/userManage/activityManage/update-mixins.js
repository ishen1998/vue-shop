import Activity from './addActivity.vue'

export default {
  data () {
    return {
      mode: 'update',
      list: []
    }
  },
  mixins: [Activity],
  created () {
    if (this.mode === 'update') {
      this.$http.activityManage.idSearch(this.$route.params.id).then(res => {
        this.list = res.data.data
        this.dataFill()
      })
    }
  },
  methods: {
    // 给修改页默认填值
    dataFill () {
      let img = {}
      this.activityName = this.list[0].activityName
      this.startTime = this.list[0].startTime
      this.endTime = this.list[0].endTime
      img.url = this.list[0].activityImg
      img.name = this.list[0].activityId
      this.activityRule = this.list[0].rule
      this.fileList.push(img)
      this.tableData = this.list[0].itemInfos
      this.checkList = this.tableData
    }
  }
}
