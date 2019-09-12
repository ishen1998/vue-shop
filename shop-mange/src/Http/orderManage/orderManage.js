import api from './apiGather'
import Http from '../http'

const http = new Http()

export default {
  getAll (current, size = 5) {
    const querys = {
      current,
      size
    }
    return http.get(api.all, querys)
  },
  search (querys) {
    return http.get(api.search, querys)
  },
  infoTop (orderId) {
    const querys = {
      orderId
    }
    return http.get(api.infoTop, querys)
  },
  infoDowm (orderId, current = 1, size = 10) {
    const querys = {
      orderId,
      current,
      size
    }
    return http.get(api.infoDowm, querys)
  }
}
