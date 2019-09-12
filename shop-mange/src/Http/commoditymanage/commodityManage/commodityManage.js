import HTTP from './../../http'
import commodityAPi from './apiGather'
import categoryApi from './../sortManage/apiGather'
var http = new HTTP()

export default {
  // 获取商品列表（按页码）
  getCommodityList (query, success, error) {
    http.get(commodityAPi.getCommodity, query)
      .then(success, error)
  },
  // 改变商品状态
  changeCommodityStatus (query, success, error) {
    http.get(commodityAPi.updateStatus, query)
      .then(success, error)
  },
  // 更新商品信息
  updateInfo (query, success, error) {
    http.put(commodityAPi.update, query)
      .then(success, error)
  },
  // 删除商品信息
  deleteCommodity (query, success, error) {
    http.delete(commodityAPi.delete, query)
      .then(success, error)
  },
  // 添加商品信息
  addCommodity (params, success, error) {
    http.post(commodityAPi.addCommodity, params)
      .then(success, error)
  },
  // 根据ID获取商品信息
  getCommodityById (query, success, error) {
    http.get(commodityAPi.getInfoById, query)
      .then(success, error)
  },
  // 获取全部分类
  getCategoryAll (success, error) {
    http.get(categoryApi.allCategory)
      .then(success, error)
  },
  // 根据分类和ID查询商品
  getCommodityByIdOrCategory (query, success, error) {
    http.get(categoryApi.byIdOrCategory, query)
      .then(success, error)
  }
}
