<template>
  <div>
    <!-- 热门推荐列表 -->
    <van-list
      v-model="loading"
      :finished="finished"
      finished-text="没有更多了"
      @load="onLoad"
      :immediate-check="immediateCheck"
    >
      <div class="hotList">
        <div
          v-for="(item, i) in itemData"
          v-show="item.isShow == 'Y' ? true : false"
          :key="i"
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
        <!-- 结束热门推荐列表 -->
      </div>
    </van-list>
    <Top :top="top"></Top>
  </div>
</template>
<script>
import Top from "../top/getTop.vue";
export default {
  components: { Top },
  data() {
    return {
      itemData: [],
      current: 1,
      size: 10,
      error: false,
      loading: false,
      finished: false,
      listSum: 10,
      immediateCheck: false,
      top: window.pageYOffset
    };
  },
  methods: {
    moveInfo(id) {
      this.$router.push({
        name: "productDetails",
        path: "/productDetails",
        params: { id }
      });
    },
    getList() {
      this.$http
        .get(
          "/shop/shop/selectItemInfoList?current=" +
            this.current +
            "&size=" +
            this.size
        )
        .then(res => {
          this.itemData = res.data.data.records;
          this.listSum = res.data.data.total;
        })
        .catch(err => {
          console.log(err);
          this.$toast("出现意料之外的错误");
        });
    },
    onLoad() {
      this.size++;
      this.top = window.pageYOffset;
      this.getList();
      this.loading = false;
      if (this.itemData.length >= this.listSum) {
        this.finished = true;
      } else {
        this.finished = false;
      }
    },
    get() {
      this.top = window.pageYOffset;
    }
  },
  created() {
    this.getList();
  },
  mounted() {
    window.addEventListener("scroll", this.get);
  }
};
</script>
<style lang="less" scoped>
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
  .Nocolorred {
    color: rgb(90, 90, 90);
    // text-align: center;
    font-size: 15px;
    font-weight: 400;
    margin: 10px 10px;
    text-decoration: line-through;
  }
  .Price {
    margin: 10px 10px;
    font-weight: bold;
    color: red;
    // text-align: center;
  }
}
</style>
