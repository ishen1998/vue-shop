import sortManageAdd from './sortManageAdd.vue'

export default {
  data () {
    return {
      id: '',
      mode: 'update'
    }
  },
  mixins: [sortManageAdd],
  created () {
    this.$http.sortManage.idSearch(this.$route.params.id).then(res => {
      this.defaultData(res.data.data)
    })
  },
  methods: {
    defaultData (data) {
      let img = {}
      this.title = '修改分类'
      this.id = data.categoryId
      this.sortName = data.categoryName
      img.url = data.categoryImage
      img.name = data.categoryId
      this.fileList.push(img)
      this.selectSort = data.listParent
    }
  }
}
