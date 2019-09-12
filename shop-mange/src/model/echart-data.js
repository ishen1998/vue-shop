import echarts from 'echarts'

function userCreateData () {
  var option = null
  // Generate data
  var category = []
  var dottedBase = +new Date()
  var lineData = []
  var barData = []

  for (var i = 0; i < 20; i++) {
    var date = new Date(dottedBase += 3600 * 24 * 1000)
    category.push([
      date.getFullYear(),
      date.getMonth() + 1,
      date.getDate()
    ].join('-'))
    var b = Math.random() * 200
    var d = Math.random() * 200
    barData.push(b)
    lineData.push(d + b)
  }
  // option
  option = {
    backgroundColor: '#0f375f',
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: ['当月用户增长', '订单销量'],
      textStyle: {
        color: '#ccc'
      }
    },
    xAxis: {
      data: category,
      axisLine: {
        lineStyle: {
          color: '#ccc'
        }
      }
    },
    yAxis: {
      splitLine: {
        show: false
      },
      axisLine: {
        lineStyle: {
          color: '#ccc'
        }
      }
    },
    series: [{
      name: '当月用户增长',
      type: 'line',
      smooth: true,
      showAllSymbol: true,
      symbol: 'emptyCircle',
      symbolSize: 15,
      data: lineData
    }, {
      name: '订单销量',
      type: 'bar',
      barWidth: 10,
      itemStyle: {
        normal: {
          barBorderRadius: 5,
          color: new echarts.graphic.LinearGradient(
            0, 0, 0, 1,
            [{
              offset: 0,
              color: '#14c8d4'
            },
            {
              offset: 1,
              color: '#43eec6'
            }
            ]
          )
        }
      },
      data: barData
    }, {
      name: '当前增长数量',
      type: 'bar',
      barGap: '-100%',
      barWidth: 10,
      itemStyle: {
        normal: {
          color: new echarts.graphic.LinearGradient(
            0, 0, 0, 1,
            [{
              offset: 0,
              color: 'rgba(20,200,212,0.5)'
            },
            {
              offset: 0.2,
              color: 'rgba(20,200,212,0.2)'
            },
            {
              offset: 1,
              color: 'rgba(20,200,212,0)'
            }
            ]
          )
        }
      },
      z: -12,
      data: lineData
    }]
  }
  return option
}

export {
  userCreateData
}
