import api from './apiGather'
import Http from '../../http'

const http = new Http()

export default {
  getAllRole () {
    return http.get(api.allRole)
  },
  getAllRP () {
    return http.get(api.allRP)
  },
  getAllPermission () {
    return http.get(api.allPermission)
  },
  add (permissionIds, roleNickname) {
    const params = {
      permissionIds,
      roleNickname
    }
    return http.post(api.add, params)
  },
  delete (sysRoleId) {
    const querys = {
      sysRoleId
    }
    return http.delete(api.delete, querys)
  },
  update (permissionIds, roleId) {
    const params = {
      permissionIds,
      roleId
    }
    return http.put(api.update, params)
  }
}
