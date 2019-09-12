let warning = 'warning'
let success = 'success'
let error = 'error'
const prompt = {
  'loading': {
    cont: '请等待数据响应'
  },
  'loginSuccess': {
    cont: '登录成功',
    type: success
  },
  'loginError': {
    cont: '信息填写未完整',
    type: error
  },
  'loginFaild': {
    cont: '登录失败',
    type: error
  },
  'registerError': {
    cont: '信息填写出错',
    type: warning
  }
}
/**
 * 提示框封装
 * @param {*} param0
 */
function alertInfo (params) {
  if (typeof params === 'string') {
    params = prompt[params]
  }
  // 如果之前已经开启，先进行关闭
  if (alertInfo.backMessage !== undefined) {
    alertInfo.backMessage.close()
    alertInfo.backMessage = undefined
  }
  alertInfo.backMessage = this.$message({
    message: params.cont,
    type: params.type,
    duration: 1500,
    showClose: true
  })
  return false
}

function remove (callback) {
  this.$confirm('此操作将永久删除, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    lockScroll: false
  })
    .then(() => {
      callback()
      this.$message({
        type: 'success',
        message: '删除成功!',
        lockScroll: false
      })
    })
    .catch(() => {
      this.$message({
        type: 'info',
        message: '已取消删除',
        lockScroll: false
      })
    })
}

function examineItem (property) {
  // 判断是否为字符串，注意instanceof不能判断字符类型
  if (this[property] === undefined || this[property] === null) {
    return true
  } else if (typeof this[property] === 'string') {
    return this[property].trim() === ''
    // 判断是否为字符串，注意typeof不能判断数组
  } else if (this[property] instanceof Array) {
    return this[property].length === 0
  } else if (typeof this[property] === 'number') {
    return this[property] === 0
  }
}
/**
 *
 * @param {错误信息的数组，其中成员格式为 this上的属性值为key，错误信息为键} errorInfo
 */
function examineInfoComlete (errorInfo) {
  const len = errorInfo.length
  for (var i = 0; i < len; i++) {
    for (var key in errorInfo[i]) {
      if (examineItem.call(this, key)) {
        // 返回缺失信息
        return errorInfo[i][key]
      }
    }
  }
  return true
}

export {
  alertInfo,
  remove,
  examineInfoComlete,
  examineItem
}
