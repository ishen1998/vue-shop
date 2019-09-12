import routerPath from './routerPath.js'
export default [{
  path: '/',
  redirect: {
    name: 'userAnalyze'
  }
},
{
  path: '/login',
  name: 'login',
  component: routerPath.login
},
{
  path: '/home',
  component: routerPath.home,
  redirect: '/useranalyze',
  children: [{
    path: '/useranalyze',
    name: 'userAnalyze',
    component: routerPath.userAnalyze
  },
  {
    path: '/orderanalyze',
    name: 'orderAnalyze',
    component: routerPath.orderAnalyze
  },
  {
    path: '/clientmanage',
    name: 'clientManage',
    component: routerPath.clientManage
  },
  {
    path: '/ordermanage',
    name: 'orderManage',
    component: routerPath.orderManage
  },
  {
    path: '/cardmanage',
    name: 'cardManage',
    component: routerPath.cardManage
  },
  {
    path: '/commoditymanage',
    name: 'commodityManage',
    component: routerPath.commodityManage
  },
  {
    path: '/activitymanage',
    name: 'activityManage',
    component: routerPath.activityManage
  },
  {
    path: '/sortmanage',
    name: 'sortManage',
    component: routerPath.sortManage
  },
  {
    path: '/sortmanageadd',
    name: 'sortManageAdd',
    component: routerPath.sortManageAdd
  },
  {
    path: '/sortmanageupdate/:id',
    name: 'sortManageUpdate',
    component: routerPath.sortManageUpdate
  },
  {
    path: '/account',
    name: 'account',
    component: routerPath.account
  },
  {
    path: '/accountadd',
    name: 'accountAdd',
    component: routerPath.accountAdd
  },
  {
    path: '/accountupdate/:id',
    name: 'accountUpdate',
    component: routerPath.accountUpdate
  },
  {
    path: '/permissionadd',
    name: 'permissionAdd',
    component: routerPath.permissionAdd
  },
  {
    path: '/permissionupdate/:id',
    name: 'permissionUpdate',
    component: routerPath.permissionUpdate
  },
  {
    path: '/permission',
    name: 'permission',
    component: routerPath.permission
  },
  {
    path: '/orderinfo',
    name: 'orderManageInfo',
    component: routerPath.orderManageInfo
  },
  {
    path: '/activityadd',
    name: 'activityManageAdd',
    component: routerPath.addActivity
  },
  {
    path: '/addcommodity',
    name: 'commodityManageAdd',
    component: routerPath.addCommodity
  },
  {
    path: '/editCommodity',
    name: 'commodityManageEdit',
    component: routerPath.editCommodity
  },
  {
    path: '/updateactivity/:id',
    name: 'activityManageUpdate',
    component: routerPath.updateActivity
  },
  {
    path: '/newCardCustomer',
    name: 'cardManageNewCardCustomer',
    component: routerPath.newCardCustomer
  }
  ]
}
]
