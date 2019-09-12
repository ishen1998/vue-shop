import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import './plugins/element.js'
import Util from './util/util'
import store from './store/index'
// 引入共有类样式
import './assets/css/common.scss'
import HTTP from './Http/index'
// 引入vue-echart
import ECharts from 'vue-echarts'
import 'echarts/lib/chart/bar'
import 'echarts/lib/component/tooltip'
Vue.component('v-chart', ECharts)

// 定义Vue状态模式
Vue.config.productionTip = false
const util = new Util()

// 全局添加HTTP请求以及本地储存操作
Vue.prototype.$http = HTTP
Vue.prototype.$util = util

// 全局导航守卫，注册验证
router.beforeEach((to, from, next) => {
  if (to.name !== 'login') {
    // 获取Token进行验证
    const tokenStr = window.localStorage.getItem('token')
    // 验证token
    typeof tokenStr !== 'string' || tokenStr.length < 10
      ? next('/login')
      : next()
  }
  next()
})

// Vue根对象创建
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
