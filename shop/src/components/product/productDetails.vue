<template>
  <div class="container">
    <van-nav-bar
      title="商品详情"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <!-- 轮播图 -->
    <div style="padding-top:50px">
      <van-swipe :autoplay="3000" indicator-color="red" style="height:350px">
        <van-swipe-item
          style="background-color:#ccc"
          v-for="(item, i) in ReturnImg"
          :key="i"
        >
          <img :src="item" width="100%" height="100%" />
        </van-swipe-item>
      </van-swipe>
    </div>
    <div class="next">
      <h3 v-show="activityPrice ? true : false" class="colorred">
        ￥{{ parseFloat(activityPrice) }}
      </h3>
      <h3 :class="activityPrice === '' ? 'colorred' : 'Nocolorred'">
        ￥{{ itemData.price }}
      </h3>

      <div style="text-align: center">
        <van-tag round type="primary">1件起批</van-tag>
      </div>
      <div class="introduce">
        <span v-html="itemData.itemDetail"></span>
      </div>
    </div>
    <div>
      <van-cell-group>
        <van-cell @click.native="getShow" title="选货" value="颜色、规格  >" />
        <van-cell title="服务" value="·精选货源  ·3天发货  >" />
      </van-cell-group>
    </div>
    <div style="background-color:#fff;margin-top:10px;padding:5px 5px">
      <h4>商品信息</h4>
      <div class="titleInfo">
        <ul class="Title">
          <li>品牌</li>
          <li>款号</li>
          <li>类别</li>
          <li>主要面料</li>
          <li>季节</li>
        </ul>
        <ul class="content">
          <li>精选品牌</li>
          <li>{{ itemData.itemId }}</li>
          <li>{{ itemData.categoryId }}</li>
          <li>{{ itemData.itemName }}</li>
          <li>--</li>
        </ul>
      </div>
    </div>

    <div style="background-color:#fff;margin-top:10px;padding:5px 5px">
      <h4 style="margin:5px 0">商品详情</h4>
      <div
        v-for="(item, i) in ReturnImg"
        :key="i"
        style="width:100%;height:100%"
      >
        <img :src="item" width="100%" height="100%" />
      </div>
    </div>

    <div>
      <popup :msg="show" @setShow="getShow" :msgData="itemData" />
    </div>

    <div style="z-index:999">
      <!-- 加入购物车 -->
      <!-- <settleAccounts @getShow="getShow" /> -->
      <van-goods-action style="z-index:99">
        <van-goods-action-icon
          icon-class="red"
          :icon="icon"
          text="收藏"
          @click="collection()"
        />
        <van-goods-action-icon icon="chat-o" text="咨询" />
        <van-goods-action-icon
          icon="cart-o"
          :info="carNum"
          text="购物车"
          @click="$router.push('/cart')"
        />
        <van-goods-action-button
          :disabled="checked"
          type="warning"
          text="加入购物车"
          @click="getShow"
        />
      </van-goods-action>
    </div>
  </div>
</template>
<script>
import popup from "../../components/card/popup.vue";
export default {
  components: {
    popup
  },
  data() {
    return {
      show: false,
      value: 0,
      itemData: {},
      carNum: 0,
      getShopCar: this.$store.getters.getShopCar,
      ReturnImg: [],
      checked: false,
      icon: "like-o",
      collecChecked: false,
      activityPrice: ""
    };
  },
  methods: {
    onBuyClicked(data) {
      this.$toast("buy:" + JSON.stringify(data));
    },
    onAddCartClicked(data) {
      this.$toast("add cart:" + JSON.stringify(data));
    },
    getShow() {
      if (this.$store.getters.getLoginState == true) {
        this.show = !this.show;
      } else {
        this.$dialog
          .confirm({
            title: "请先登录",
            message: "您还未登陆,需要去登陆吗"
          })
          .then(() => {
            this.$router.push("/my");
          })
          .catch(() => {
            // on cancel
          });
      }
    },
    onClickLeft() {
      this.$router.go(-1);
    },
    collection() {
      if (this.collecChecked === false) {
        const record = {
          cid: this.$store.getters.getcId,
          itemId: this.itemData.itemId
        };
        this.$http.post("/shop/collection", record).then(res => {
          if (res.data.message == "该商品已下架，无法加入收藏夹") {
            this.$toast(res.data.message);
          }
          if (res.data.message == "成功") {
            this.$toast(`收藏${res.data.message}`);
            this.collectionChecked();
          } else if (res.data.message == "商品已存在") {
            this.delCollection();
            this.$toast("取消收藏");
          }
        });
      }
    },
    delCollection() {
      this.$http
        .delete(
          `/shop/collection/${this.$store.getters.getcId}/${
            this.itemData.itemId
          }`
        )
        .then(() => {
          this.collectionChecked();
        });
    },
    goodsDetails() {
      this.$http
        .get(`/shop/shop/selectByPrimaryKey?itemId=${this.$route.params.id}`)
        .then(res => {
          if (res.data.data.isShow == "N") {
            this.$toast("该货物已下架");
            this.checked = true;
          }
          if (res.data.data.isActivity == "N") {
            this.$toast("活动已结束,按原价购买");
          }
          console.log(res.data.data);
          this.itemData = res.data.data;
          this.activityPrice =
            this.itemData.isActivity == "Y"
              ? this.itemData.priceAndStatuses[0].activityPrice
              : "";
        });
    },
    goodsRunImg() {
      this.$http
        .get(`/shop/shop/shopReturnImg?itemId=${this.$route.params.id}`)
        .then(res => {
          if (res.data.code == 200) {
            res.data.data.forEach(item => {
              this.ReturnImg.push(item);
            });
          }
        })
        .catch(err => {
          console.log(err);
          this.$toast("出现意料之外的错误");
        });
    },
    carInfo() {
      this.$http
        .get(`/shop/Trolley/${this.$store.getters.getcId}`)
        .then(res => {
          var i = 0;
          res.data.data.forEach(item => {
            if (item == null) {
              return;
            } else {
              i += item.num;
            }
          });
          this.$store.commit("setCarNum", i);
          this.carNum = this.$store.getters.getCarNum;
        });
    },
    collectionChecked() {
      this.$http
        .get(`/shop/collection/${this.$store.getters.getcId}`)
        .then(res => {
          var a = res.data.data.some(item => {
            return item.itemId === this.itemData.itemId;
          });
          this.icon = a ? "like" : "like-o";
        });
    }
  },
  created() {
    //商品详情数据
    this.goodsDetails();
    //商品轮播图数据
    this.goodsRunImg();
    if (this.$store.getters.getLoginState == true) {
      //购物车商品数量info
      this.carInfo();
      //收藏状态
      this.collectionChecked();
    } else {
      this.carNum = 0;
    }
  },
  updated() {
    this.carNum = this.$store.getters.getCarNum;
  }
};
</script>
<style lang="less" scoped>
.container {
  background-color: #f6f6f6;
  padding-bottom: 50px;
  .red {
    color: red;
  }
  .colorred {
    color: red;
    text-align: center;
    font-size: 20px;
    font-weight: 400;
    margin: 10px 0;
  }
  .Nocolorred {
    color: rgb(90, 90, 90);
    text-align: center;
    font-size: 15px;
    font-weight: 400;
    margin: 10px 0;
    text-decoration: line-through;
  }
  .next {
    margin-bottom: 10px;
    padding-bottom: 20px;
    background-color: white;
    .introduce {
      font-size: 13px;
      // width: 90%;
      padding: 0 40px;
      text-align: center;
    }
  }
  .titleInfo {
    display: flex;
    .Title {
      width: 140px;
      font-size: 12px;
      color: #999;
      li {
        margin: 10px 5px;
      }
    }
    .content {
      font-size: 12px;
      margin: 0 20px;
      li {
        margin: 10px 5px;
      }
    }
  }
  .van-nav-bar__text,
  .van-nav-bar .van-icon {
    color: black;
  }
  .van-nav-bar {
    position: fixed;
    z-index: 999;
    width: 100%;
  }
}
</style>
