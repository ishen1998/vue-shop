import api from './apiGather'
import Http from '../../http'

const http = new Http()

export default {
  getAll (current, size = 5) {
    const querys = {
      current,
      size
    }
    return http.get(api.all, querys)
  },
  add (params) {
    return http.post(api.add, params)
  },
  addCommodity (params) {
    return http.post(api.addCommodity, params)
  },
  delete (activityId) {
    const querys = {
      activityId
    }
    return http.get(api.delete, querys)
  },
  nameSearch (activityName, current = '1', size = '5') {
    const querys = {
      activityName,
      current,
      size
    }
    return http.get(api.nameSelect, querys)
  },
  updateStatus (activityId, activityStatus) {
    const querys = {
      activityId,
      activityStatus
    }
    return http.get(api.updateStatus, querys)
  },
  idSearch (activityId) {
    const querys = {
      activityId
    }
    return http.get(api.idSelect, querys)
  },
  deleteCommodity (itemId) {
    const params = {
      itemId
    }
    return http.post(api.deleteCommodity, params)
  },
  update (params) {
    return http.post(api.update, params)
  },
  getAllPX (msg = '', current = '1', size = '5') {
    const querys = {
      msg,
      current,
      size
    }
    return http.get(api.allPX, querys)
  }
}
