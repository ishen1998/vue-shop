import HTTP from './http'
var http = new HTTP()
let defaultErrorHandler = function () {

}
/**
 * 登录函数
 *  @param {警告信息} userInfo
 * @param {成功回调} success
 * @param {失败回调*} error
 */
function login (userInfo, success, error = defaultErrorHandler) {
  http.post('/sysuser/login', userInfo).then(success, error)
}

/**
 * 注册封装函数
 * @param {警告信息} userInfo
 * @param {成功回调} success
 * @param {失败回调*} error
 */
function register (userInfo, success, error = defaultErrorHandler) {
  http.post('/sysuser/insert', userInfo).then(success, error)
}
/**
 * 获取全部分类
 */
register.getRoleAll = function (that) {
  http.get('/sysuser/role/AllSysRole').then(
    ({
      data: {
        data
      }
    }) => {
      that.roleList = data
    },
    () => {
      that
        .$confirm('目前注册不可用', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
          lockScroll: false
        })
        .then(() => {
          that.activeName = 'login'
        }, defaultErrorHandler)
    }
  )
}

/**
 * 登录系统后进行token再次验证
 * @param {token} param0
 * @param {警告回调函数} alertInfo
 */
function checkoutLoginStatus ({
  token
}, alertInfo) {
  http
    .get('/sysuser/checkToken/' + token)
    .then(({
      data: {
        data
      }
    }) => {
      // 登录再次验证
      this.$store.commit('setUserName', {
        username: data.sysUserName
      })
      this.$store.commit('setUserAuthority', {
        authority: data.Authority
      })
    })
    .catch(() => {
      // 验证登录失败
      alertInfo.call(this, {
        cont: '登录状态异常,请重新登录',
        type: 'error'
      })
      this.$router.push({
        path: '/login'
      })
    })
}
export default {
  login: login,
  register: register,
  checkoutLoginStatus
}
