import api from './apiGather'
import Http from '../../http'

const http = new Http()

export default {
  getAll (current = 1, size = 10) {
    return http.get(`${api.all}/${current}/${size}`)
  },
  verify (userId) {
    return http.get(`${api.verify}/${userId}`)
  },
  add (roleId, phone, userName) {
    const params = {
      roleId,
      phone,
      userName
    }
    return http.post(api.add, params)
  },
  delete (userId) {
    const querys = {
      userId
    }
    return http.delete(api.delete, querys)
  },
  update (roleIds, sysUser) {
    const params = {
      roleIds,
      sysUser
    }
    return http.put(api.update, params)
  },
  idSelect (userId) {
    const querys = {
      userId
    }
    return http.get(api.idSelect, querys)
  }
}
