import AddCommodity from './addCommodity.vue'
import CommodityModel from '../../../Http/commoditymanage/commodityManage/commodityManage'
export default {
  data () {
    return {
      originalInfo: {}
    }
  },
  mixins: [AddCommodity],
  methods: {
    editPut (goodsInfo) {
      goodsInfo.itemId = this.$route.query.commodityId
      CommodityModel.updateInfo(
        goodsInfo,
        ({
          data
        }) => {
          this.$confirm('编辑成功', '提示', {
            confirmButtonText: '返回商品管理页',
            cancelButtonText: '留在当前页面',
            type: 'success',
            lockScroll: false
          }).then(() => {
            this.$router.push({
              name: 'commodityManage'
            })
          }).catch(rsp => {
            // 留在当前页码更新数据
            this.pageDataInit(this.$route.query.commodityId)
          })
        },
        ({
          data
        }) => {
          this.$alert('更改失败', data.message, {
            confirmButtonText: '确定'
          })
        }
      )
    },
    pageDataInit (id) {
      CommodityModel.getCommodityById({
        itemId: id
      }, ({
        data: {
          data
        }
      }) => {
        this.assignValue(data)
      }, ({
        data
      }) => {
        // 获取数据失败提示
        this.$alert(data.message, '错误', {
          confirmButtonText: '确定'
        })
      })
    },
    checkIsEdit ({
      type,
      commodityId
    }) {
      // 参数非法，返回错误
      if (type !== 'edit' || commodityId === undefined) {
        return false
      }
      // id长度错误，返回false
      if (commodityId.length === 0) return false
      // 校正成功
      return true
    },
    // 检查路由参数是否正常
    checkRouteParams (query) {
      if (this.checkIsEdit(query)) {
        this.pageDataInit(query.commodityId)
      } else {
        // 非法跳转，跳转回上一个页码
        this.$alert('编辑参数缺失，请检查是否操作失误', '错误', {
          confirmButtonText: '确定'
        })
        // 定时器跳转，不应当使用遮挡回调，会造成安全问题
        setTimeout(() => {
          this.$router.push({
            name: 'commodityManage'
          })
        }, 1000)
      }
    }
  },
  created () {
    // 判断参数
    this.mode = 'edit'
    this.checkRouteParams(this.$route.query)
  },
  updated () {}
}
