// 下拉选择数据，可以更新
const selectMenu = [{
  label: '状态',
  select: [{
    value: 0,
    label: '已支付'
  },
  {
    value: 1,
    label: '未支付'
  },
  {
    value: 3,
    label: '劵支付'
  },
  {
    value: 4,
    label: '实付款'
  },
  {
    value: 5,
    label: '已作废'
  }
  ]
},
{
  label: '每页显示',
  select: [{
    value: 5,
    label: '5'
  },
  {
    value: 10,
    label: '10'
  },
  {
    value: 50,
    label: '50'
  },
  {
    value: 100,
    label: '100'
  }
  ]
}
]

export {
  selectMenu
}
