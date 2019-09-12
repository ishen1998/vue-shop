<template>
  <div>
    <!-- 头部 -->
    <van-nav-bar
      title="商品列表"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <!-- 搜索 -->
    <div class="search">
      <van-search
        @keydown.enter="getGoods"
        placeholder="请输入搜索的商品名称"
        v-model="value"
      />
      <van-button @click="getGoods" type="info">搜索</van-button>
    </div>
    <!-- 商品列表 -->
    <div class="hotList">
      <div
        v-show="item.isShow == 'Y' ? true : false"
        v-for="item in list"
        :key="item.itemId"
        class="div"
      >
        <div class="caritem" @click="moveInfo(item.itemId)">
          <img v-lazy="item.coverImg" />
          <p class="describe">{{ item.itemName }}</p>
          <p v-if="item.activityPrice ? true : false" class="Price">
            ￥{{ item.activityPrice }}
          </p>
          <p :class="item.activityPrice ? 'Nocolorred' : 'Price'">
            ￥{{ item.price }}
          </p>
        </div>
      </div>
    </div>
    <Top :top="top"></Top>
  </div>
</template>
<script>
import Top from "../top/getTop.vue";
export default {
  components: {
    Top
  },
  data() {
    return {
      list: [],
      id: this.$route.query.id,
      top: window.pageYOffset,
      value: ""
    };
  },
  methods: {
    getGoods() {
      this.$http
        .get(
          "/shop/shop/getShopCategoryName?categoryId=" +
            this.id +
            "&" +
            "itemName=" +
            this.value +
            "&" +
            "current=1" +
            "&" +
            "size=500"
        )
        .then(res => {
          console.log(res.data.data.records);
          this.list = res.data.data.records;
          this.list.every(item => {
            if (item.isShow == "N") {
              this.$toast({
                message: "该分类暂不可用",
                duration: 1000,
                forbidClick: true
              });
              this.$router.push("/sort");
            }
          });
          this.value2 = "a";
          if (this.list.length <= 0) {
            this.$toast("暂无此商品");
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    //详情页
    moveInfo(id) {
      this.$router.push({
        name: "productDetails",
        path: "/productDetails",
        params: { id }
      });
    },
    onClickLeft() {
      this.$router.go(-1);
    },
    getTop() {
      this.top = window.pageYOffset;
    }
  },
  mounted() {
    window.addEventListener("scroll", this.getTop);
  },
  created() {
    //查询子类id下的商品
    this.$http
      .get(
        "/shop/shop/getShopCategoryName?categoryId=" +
          this.id +
          "&" +
          "itemName=" +
          this.value +
          "&" +
          "current=1" +
          "&" +
          "size=500"
      )
      .then(res => {
        console.log(res);
        this.list = res.data.data.records;
        this.list.every(item => {
          if (item.isShow == "N") {
            this.$toast({
              message: "该分类暂不可用",
              duration: 1000,
              forbidClick: true
            });
            this.$router.push("/sort");
          }
        });
        if (this.list.length <= 0) {
          this.$toast("暂无商品");
        }
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>
<style lang="less" scoped>
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
.search {
  width: 100%;
  height: 50px;
  line-height: 50px;
  .van-search {
    width: 75%;
    float: left;
  }
  .Nocolorred {
    color: rgb(90, 90, 90);
    // text-align: center;
    font-size: 15px;
    font-weight: 400;
    margin: 10px 10px;
    text-decoration: line-through;
  }
  .van-button {
    width: 20%;
    float: left;
    height: 30px;
    margin-top: 10px;
    line-height: 30px;
  }
}
.van-dropdown-menu {
  width: 100%;
}
.hotList {
  display: flex;
  justify-content: space-between;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  margin: 10px 10px;
  .div {
    // position: relative;
    border-radius: 5px;
    margin: 10px 0;
    width: 48%;
    background-color: white;
  }
}
.caritem {
  position: relative;
  img {
    width: 100%;
    height: 200px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
  }
  .describe {
    margin: 10px 10px;
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 12px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }
  .Price {
    margin: 10px 10px;
    font-weight: bold;
    color: red;
  }
}
</style>
