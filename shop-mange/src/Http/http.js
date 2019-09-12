import axios from 'axios'
import Util from '../util/util.js'
// 启用loading功能
import {
  Loading
} from 'element-ui'
const util = new Util()
axios.defaults.baseURL = 'http://192.168.3.88:8089/shop'
axios.defaults.headers.post['Content-Type'] = 'application/json'
// 设置超时
axios.defaults.timeout = 5 * 1000
// 不进行token验证的接口
const notIsTokenVaild = ['/sysuser/login', '/sysuser/insert', '/sysuser/role/AllSysRole']
class HTTP {
  static isShowLoading = false
  /**
   * get请求方法
   * @param {get地址} url
   * @param {get参数} querys
   *
   */
  get (url, querys = {}) {
    return new Promise((resolve, reject) => {
      axios
        .get(url, {
          params: querys
        })
        .then(res => {
          resolve(res)
        })
        .catch(err => {
          reject(err)
        })
    })
  }
  /**
   * get请求方法
   * @param {post地址} url
   * @param {post参数} params
   */
  post (url, params = {}) {
    return new Promise((resolve, reject) => {
      axios.post(url, params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  }
  /**
   * 请求拦截器，在拦截器中修改/添加请求信息
   */
  static interceptorsRequest () {
    axios.interceptors.request.use(config => {
      this.isShowLoading = Loading.service({
        fullscreen: true,
        background: 'rgba(0, 0, 0, 0.3)',
        spinner: 'el-icon-loading',
        text: 'loading..'
      })
      // 如果是非登錄入口，不添加Token验证
      if (!notIsTokenVaild.includes(config.url)) {
        // 拦截操作
        // 获取Token并添加到 config中
        let token = util.getLocal('token')
        let tokenHeader = util.getLocal('tokenHeader')
        if (typeof token === 'string' && typeof tokenHeader === 'string') {
          config.headers.common['Authorization'] = `${tokenHeader}${token}`
        }
      }
      return config
    }, err => {
      // 对请求错误做些什么
      return Promise.reject(err)
    })
  }
  /**
   * 响应拦截器，在拦截器中对响应进行resolve 、reject
   */
  static interceptorsResponse () {
    // 添加响应拦截器
    axios.interceptors.response.use(rsp => {
      this.isShowLoading.close()
      // 拦截操作
      if (this.statusJuage(rsp)) {
        return Promise.resolve(rsp)
      }
      return Promise.reject(rsp)
    }, err => {
      this.isShowLoading.close()
      return Promise.reject(err)
    })
  }
  /**
   * delete请求
   * @param {*请求接口地址} url
   * @param {*参数} querys
   */
  delete (url, querys = {}) {
    return new Promise((resolve, reject) => {
      axios
        .delete(url, {
          params: querys
        })
        .then(res => {
          resolve(res)
        })
        .catch(err => {
          reject(err)
        })
    })
  }
  put (url, params = {}) {
    return new Promise((resolve, reject) => {
      axios
        .put(url, params)
        .then(res => {
          resolve(res)
        })
        .catch(err => {
          reject(err)
        })
    })
  }
  // 错误代码判断，适用先阶段API
  static statusJuage (rsp) {
    if ((rsp.status === 200 && typeof rsp.data.code === 'string' && rsp.data.code.slice(0, 1) === '2') || rsp.data.code === 200) {
      return true
    } else {
      return false
    }
  }
  /**
   * @param {获取的数据} param0
   * @param {数据中需要迭代找出的键数组} keys
   * @param {需要赋值的key} key
   */
  assignValue ({
    data
  }, keys = [], valKey) {
    var dataKey = null
    for (var i = 0; i < keys.length; i++) {
      if (i === 0) {
        dataKey = data[keys[i]]
      } else {
        dataKey = dataKey[keys[i]]
      }
    }
    this[valKey] = dataKey
  }
}
/**
 * 调用拦截器
 */
HTTP.interceptorsResponse()
HTTP.interceptorsRequest()
export default HTTP
