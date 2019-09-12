<template>
  <div class="container">
    <Footer></Footer>
    <!-- <button value="按钮" @click="gt" /> -->
    <div class="header">
      <van-nav-bar left-text="进货车" />
    </div>
    <!-- 王牌买手模块 -->
    <div style="padding-top:40px">
      <div class="hot" v-show="carData.length == 0 ? false : true">
        <van-icon name="shop-collect" />
        <span style="font-size:14px;">王牌买手</span>
        <van-button
          type="default"
          size="small"
          class="delbtn"
          @click="emptyShop"
          >{{ emptyShopText }}</van-button
        >
      </div>
      <van-checkbox-group
        v-model="result"
        @change="priceAll"
        v-show="emptyShopChecked"
      >
        <div class="car" v-for="item in carData" :key="item.trolleyId">
          <van-checkbox
            :key="item.trolleyId"
            :name="item.trolleyId"
            :disabled="false"
          ></van-checkbox>
          <div style="width:90%;padding-left:10px">
            <car :item="item" @changeC="change" :allgoods="true" />
          </div>
        </div>
      </van-checkbox-group>
      <!-- 删除购物车 -->
      <van-checkbox-group
        v-model="result1"
        @change="priceAll"
        v-show="!emptyShopChecked"
      >
        <div class="car" v-for="item in carData" :key="item.trolleyId">
          <van-checkbox
            :key="item.trolleyId"
            :name="item.trolleyId"
          ></van-checkbox>
          <div style="width:90%;padding-left:10px">
            <car :item="item" @changeC="change" :allgoods="true" />
          </div>
        </div>
      </van-checkbox-group>
      <!-- 购物车的商品组件 -->
      <div
        class="hot"
        style="padding-top:15px;padding-bottom:50px"
        v-show="carData.length == 0 ? true : false"
      >
        <img src="../assets/kk.jpg" width="100%" height="300px" />
        <h6 style="margin-bottom:20px;color:#999">空空如也~去逛逛吧</h6>
        <van-button type="info" @click="moveHome">点我去</van-button>
      </div>
    </div>
    <!-- 热门推荐模块 -->
    <div style="margin:10px 0;padding-bottom:80px">
      <div class="hot">
        <van-icon name="fire-o" />
        <span style="font-size:14px;margin-left:5px">热门推荐</span>
      </div>
      <productList />
    </div>
    <!-- 结算按钮 -->
    <div v-show="carData.length == 0 ? false : true">
      <van-submit-bar
        :price="totalPrice"
        button-text="提交订单"
        @submit="onSubmit"
        v-show="emptyShopChecked"
      >
        <van-checkbox v-model="AllChecked">全选</van-checkbox>
      </van-submit-bar>
      <van-submit-bar
        button-text="删除"
        @submit="onSubmit"
        v-show="!emptyShopChecked"
      >
        <van-checkbox v-model="AllChecked1" class="btn">全选</van-checkbox>
      </van-submit-bar>
    </div>
  </div>
</template>
<script>
import productList from "../components/product/productList";
import car from "../components/card/car";
import Footer from "../components/TabBar/footer.vue";
export default {
  data() {
    return {
      carData: [],
      checked: "",
      result: [],
      result1: [],
      arr: [],
      emptyShopText: "编辑",
      delText: "提交订单",
      emptyShopChecked: true
    };
  },
  created() {
    if (this.$store.getters.getLoginState == true) {
      var cId = this.$store.getters.getcId;
      this.$http
        .get(`/shop/Trolley/${cId}`)
        .then(res => {
          if (res.data.data[0] != null) {
            this.carData = res.data.data;
            var i = 0;
            if (this.carData.length != 0) {
              this.carData.forEach(item => {
                i += parseInt(
                  item.num * item.isActivity == "Y"
                    ? item.activityPrice
                    : item.price
                );
              });
              this.$store.commit("setTotalPrice", i);
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    } else {
      this.$toast("还未登陆，请先登陆");
      setTimeout(() => {
        this.$router.push({
          path: "/login",
          query: {}
        });
      }, 1000);
    }
  },
  components: { productList, car, Footer },
  methods: {
    moveHome() {
      if (!this.$store.getters.getLoginState == true) {
        this.$router.push("/login");
      }
      this.$router.push("/home");
    },
    change(i) {
      this.checked = i;
    },
    emptyShop() {
      this.result = [];
      this.result1 = [];
      this.emptyShopChecked = !this.emptyShopChecked;
      if (this.emptyShopChecked === false) {
        this.emptyShopText = "取消编辑";
      } else {
        this.emptyShopText = "编辑";
      }
    },
    priceAll() {},
    onSubmit() {
      if (this.emptyShopChecked === true) {
        if (this.result.length == 0) {
          this.$toast("请选择商品");
          return;
        }
        this.$router.push({
          path: "/submitOrder",
          query: { result: this.result }
        });
      } else {
        if (this.result1.length == 0) {
          this.$toast("请选择要删除的商品");
          return;
        } else {
          var batchTrolley = {
            cid: this.$store.getters.getcId,
            itemId: this.result1
          };
          this.$http
            .post("/shop/Trolley/batchTrolleyId/", batchTrolley)
            .then(res => {
              if (res.data.code == 200) {
                this.$router.push({
                  path: "/pay",
                  query: { msg: res.data.message }
                });
              } else {
                this.$toast(res.data.message);
              }
            });
        }
      }
    }
  },
  computed: {
    totalPrice() {
      return this.carData.reduce(
        (total, item) =>
          total +
          (this.result.indexOf(item.trolleyId) !== -1
            ? item.isActivity == "Y"
              ? parseFloat(item.activityPrice) * 100
              : item.price * item.num * 100
            : 0),
        0
      );
    },
    AllChecked: {
      get: function() {
        return this.result.length == this.carData.length ? true : false;
      },
      set: function(val) {
        if (val == false) {
          this.result = [];
        } else {
          this.carData.forEach(item => {
            if (this.result.includes(item.trolleyId)) {
              return;
            } else {
              this.result.push(item.trolleyId);
            }
          });
        }
      }
    },
    AllChecked1: {
      get: function() {
        return this.result1.length == this.carData.length ? true : false;
      },
      set: function(val) {
        if (val == false) {
          this.result1 = [];
        } else {
          this.carData.forEach(item => {
            if (this.result1.includes(item.trolleyId)) {
              return;
            } else {
              this.result1.push(item.trolleyId);
            }
          });
        }
      }
    }
  }
};
</script>
<style scoped lang="less">
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
.container {
  background-color: #f6f6f6;
  .header {
    width: 100%;
    background-color: white;
    z-index: 99;
    position: fixed;
    top: 0;
  }
  .hot {
    text-align: center;
    padding: 20px 0;
    background-color: white;
  }
  .van-submit-bar {
    bottom: 48px;
  }
  .delbtn {
    position: absolute;
    right: 5%;
  }
  .car {
    display: flex;
    background-color: white;
    margin-bottom: 10px;
    padding: 0 5px;
  }
  .btn {
    position: absolute;
    left: 0;
  }
}
</style>
