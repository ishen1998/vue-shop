import apiGather from './apiGather'
import Http from '../../http'

const http = new Http()

export default {
  getAll (page = '1', pageSize = '5') {
    const querys = {
      page,
      pageSize
    }
    return http.get(apiGather.all, querys)
  },
  sortAdd (params) {
    return http.post(apiGather.add, params)
  },
  sortAll () {
    return http.get(apiGather.sortAll)
  },
  nameSearch (categoryName, page = '1', pageSize = '5') {
    const querys = {
      categoryName,
      page,
      pageSize
    }
    return http.get(apiGather.nameSearch, querys)
  },
  idSearch (categoryId) {
    const querys = {
      categoryId
    }
    return http.get(apiGather.idSearch, querys)
  },
  delete (categoryId) {
    const querys = {
      categoryId
    }
    return http.get(apiGather.delete, querys)
  },
  /* eslint-disable */
  update(params) {
    return http.post(apiGather.update, params)
  },
  /* eslint-enable */
  updateStatus (categoryId, categoryStatus) {
    const querys = {
      categoryId,
      categoryStatus
    }
    return http.get(apiGather.updateStatus, querys)
  },
  getCount () {
    return http.get(apiGather.count)
  }
}
