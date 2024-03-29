import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    login: false,
    count: "",
    color: "",
    size: "",
    price: "",
    addShopCar: false,
    active: 0,
    shopCar: [],
    footprint: [],
    loginState: false,
    loginName: "",
    mobile: "",
    cId: "",
    carNum: 0,
    checked: true,
    totalPrice: 0,
    delPrice: 0,
    addressContent: [],
    tximg: ""
  },
  mutations: {
    getCount(state, count) {
      state.count = count;
    },
    getLogin(state, login) {
      state.login = login;
    },
    getColor(state, color) {
      state.color = color;
    },
    getSize(state, size) {
      state.size = size;
    },
    getPrice(state, price) {
      state.price = price;
    },
    getaddShopCar(state) {
      state.addShopCar = true;
    },
    getActive(state) {
      state.active = 0;
    },
    getShopCar(state, car) {
      //添加购物车
      var flag = false;
      state.shopCar.some(item => {
        if (item.itemId == car.itemId) {
          item.num += car.num;
          flag = true;
        }
      });
      if (flag == false) {
        state.shopCar.push(car);
      }
    },
    delShopCar(state, id) {
      //删除购物车
      state.shopCar.some(item => {
        if (item.itemId == id) {
          state.shopCar.splice(item, 1);
        }
      });
    },
    changeLoginState(state, mobile) {
      state.loginState = true; //登录状态，登陆成功之后触发
      state.mobile = mobile; //登陆之后储存用户的电话号码以便后面请求数据
    },
    setLoginName(state, username) {
      state.loginName = username;
    },
    changeMobile(state, mobile) {
      state.mobile = mobile; //用户修改后的电话号码
    },
    setcId(state, id) {
      state.cId = id; //设置登录后用户cId
    },
    setCarNum(state, num) {
      state.carNum = num;
    },
    addCarNum(state, num) {
      state.carNum += num;
    },
    changeChecked(state, delPrice) {
      state.checked = !state.checked;
      state.delPrice = delPrice;
    },
    setTotalPrice(state, totalPrice) {
      state.totalPrice = totalPrice;
    },
    setAddressContent(state, addressContent) {
      state.addressContent.push(addressContent);
    },
    setTximg(state, setTximg) {
      state.tximg = setTximg;
    }
  },
  actions: {},
  getters: {
    getShopCar(state) {
      return state.shopCar; //获取state里的ShopCar
    },
    getLoginState(state) {
      return state.loginState; //登录状态
    },
    getMobile(state) {
      return state.mobile; //登陆用户的电话号码
    },
    getcId(state) {
      return state.cId; //获取登录用户的cid
    },
    getCarNum(state) {
      return state.carNum;
    },
    getChecked(state) {
      return state.delPrice;
    },
    getTotalPrice(state) {
      return state.totalPrice;
    },
    getAddress(state) {
      return state.addressContent;
    },
    getLoginName(state) {
      return state.loginName;
    }
  }
});
