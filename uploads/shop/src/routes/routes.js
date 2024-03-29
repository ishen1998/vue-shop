import Home from "../views/home.vue"; //底部首页
import Sort from "../views/sort.vue"; //底部分类
import Cart from "../views/cart.vue"; //底部购物车
import My from "../views/my.vue"; //底部个人中心
import Login from "../components/login/login.vue"; //登陆页面
import Register from "../components/login/register.vue"; //注册页面
import productList from "../components/product/productList.vue"; //商品列表
import sortList from "../components/product/sortList.vue";
import productDetails from "../components/product/productDetails.vue"; //商品详情
import order from "../components/my/Order.vue"; //订单详情
import afterSale from "../components/my/afterSale.vue"; //售后列表
import Activity from "../components/activity/activity.vue"; //活动页面
import Search from "../components/search/search.vue"; //活动页面
import Locality from "../components/locality/locality.vue"; //产地列表
import address from "../components/my/address.vue"; //地址选择
import personalInformation from "../components/my/PersonalInformation.vue"; //修改个人信息
import addressEdit from "../components/my/addressEdit.vue"; //地址编辑
import submitOrder from "../components/card/submitOrder.vue"; //提交订单
export default [
  {
    path: "/",
    redirect: "/home"
  },
  {
    path: "/home",
    component: Home
  },
  {
    name: "Sort",
    path: "/sort",
    component: Sort
  },
  {
    path: "/Cart",
    component: Cart
  },
  {
    path: "/My",
    component: My
  },
  {
    path: "/Search",
    component: Search
  },
  {
    path: "/productList",
    component: productList
  },
  {
    name: "sortList",
    path: "/sortList",
    component: sortList
  },
  {
    name: "productDetails",
    path: "/productDetails/:id",
    component: productDetails
  },
  {
    path: "/login",
    component: Login
  },
  {
    path: "/register",
    component: Register
  },
  {
    path: "/order/:orderid",
    component: order,
    name: "order"
  },
  {
    path: "/afterSale",
    component: afterSale
  },
  {
    path: "/activity",
    component: Activity
  },
  {
    name: "locality",
    path: "/locality",
    component: Locality
  },
  {
    path: "/address",
    component: address
  },
  {
    path: "/personalInformation",
    component: personalInformation
  },
  {
    path: "/addressEdit",
    component: addressEdit
  },
  {
    path: "/submitOrder",
    component: submitOrder
  }
];
