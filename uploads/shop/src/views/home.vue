<template>
  <div>
    <Footer></Footer>
    <div class="header">
      <van-search
        @click.native="getSearch"
        placeholder="请输入搜索关键词"
        disabled
        v-model="value"
      />
    </div>
    <van-swipe :autoplay="3000" indicator-color="white">
      <van-swipe-item v-for="item in activity.returnImg" :key="item">
        <img :src="item" width="100%" height="200" @click="getActivity" />
      </van-swipe-item>
    </van-swipe>
    <van-grid>
      <van-grid-item
        @click="getLocalityList(value[1])"
        v-for="value in diZhi"
        :key="value[0]"
        :icon="value[0]"
        :text="value[1] + '货源'"
      />
    </van-grid>
    <div class="activity">
      <h3>超值活动</h3>
      <van-row>
        <van-col
          v-for="item in activity.HomeActivity"
          :key="item.activityInfo.activityName"
          span="12"
        >
          <div>
            <img @click="getActivity" :src="item.activityInfo.activityImg" />
            <!-- {{item.activityInfo.activityImg}} -->
          </div>
        </van-col>
      </van-row>
      <!-- <van-row>
        <van-col span="8">span: 8</van-col>
        <van-col span="8">span: 8</van-col>
        <van-col span="8">span: 8</van-col>
      </van-row>-->
    </div>
    <van-tabs>
      <van-tab v-for="index in 6" :key="index" :title="tabData[index - 1]">
        <van-list
          v-model="loading"
          :finished="finished"
          finished-text="没有更多了"
          @load="onLoad"
        >
          <div
            v-for="(item, index) in activity.HomeActivity"
            :key="index"
            class="van-cell__title"
          >
            <img
              @click="getActivity"
              width="100%"
              height="100px"
              :src="activity.HomeActivity[index].activityInfo.activityImg"
            />
            <van-swipe
              style="height:150px;margin-top:20px"
              indicator-color="white"
            >
              <van-swipe-item class="sw">
                <!-- <van-swipe-item   class="sw" v-for="i in num" :key="i"> -->
                <!-- <img v-for="a in 3" :key="a" :src="infos[index][j+a].coverImg" alt /> -->
                <img
                  @click="getActivity"
                  :src="infos[index][0].coverImg"
                  alt="图片加载失败"
                />
                <img
                  @click="getActivity"
                  :src="infos[index][1].coverImg"
                  alt="图片加载失败"
                />
                <img
                  @click="getActivity"
                  :src="infos[index][2].coverImg"
                  alt="图片加载失败"
                />
              </van-swipe-item>
              <van-swipe-item class="sw">
                <img
                  @click="getActivity"
                  :src="infos[index][3].coverImg"
                  alt="图片加载失败"
                />
                <img
                  @click="getActivity"
                  :src="infos[index][4].coverImg"
                  alt="图片加载失败"
                />
                <img
                  @click="getActivity"
                  :src="infos[index][5].coverImg"
                  alt="图片加载失败"
                />
              </van-swipe-item>
              <van-swipe-item class="sw">
                <img
                  @click="getActivity"
                  :src="infos[index][6].coverImg"
                  alt="图片加载失败"
                />
                <img
                  @click="getActivity"
                  :src="infos[index][7].coverImg"
                  alt="图片加载失败"
                />
                <img
                  @click="getActivity"
                  :src="infos[index][8].coverImg"
                  alt="图片加载失败"
                />
              </van-swipe-item>
            </van-swipe>
          </div>
        </van-list>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import Footer from "../components/TabBar/footer.vue";
export default {
  components: {
    Footer
  },
  data() {
    return {
      value: "",
      tabData: [
        "全部",
        "返场爆款",
        "每日上新",
        "工厂源货",
        "订货会",
        "鞋包配饰"
      ],
      list: [],
      loading: false,
      finished: false,
      activity: [],
      lunBO: [],
      infos: [],
      num: "",
      sum: "",
      diZhi: []
    };
  },
  methods: {
    getActivity() {
      this.$router.push("/activity");
    },
    getList() {
      this.$router.push("/homeActivity");
    },
    getSearch() {
      this.$router.push("/Search");
    },
    onLoad() {
      // 异步更新数据
      setTimeout(() => {
        for (let i = 0; i < 10; i++) {
          this.list.push(this.list.length + 1);
        }
        // 加载状态结束
        this.loading = false;

        // 数据全部加载完成
        if (this.list.length >= 40) {
          this.finished = true;
        }
      }, 500);
    },
    getLocalityList(value) {
      this.$router.push({
        name: "locality",
        path: "/locality",
        query: { chanDi: value }
      });
      // console.log(value)
    }
  },
  created() {
    this.$http
      .get("/shop/activity")
      .then(res => {
        console.log(res.data.data);
        this.activity = res.data.data;
        this.activity.HomeActivity.forEach(item => {
          this.infos.push(item.itemInfos);
        });
        this.sum = this.infos[0].length;
        this.num = Math.ceil(this.sum / 3);
        // console.log(this.infos);
        // console.log(this.sum, this.num);
      })
      .catch(err => {
        console.log(err);
      });
    this.$http
      .get("/shop/shop/getArray")
      .then(res => {
        console.log(res.data.data);
        this.diZhi = res.data.data;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>
<style scoped>
.sw {
  display: flex;
  justify-content: space-around;
}
.sw > img {
  width: 100px;
  height: 100px;
}
/* 顶部搜索 */
.header {
  background-color: rgba(165, 42, 42, 0.1);
  position: fixed;
  width: 100%;
  top: 0;
  left: 0;
  z-index: 999;
}
.van-search {
  background-color: rgba(255, 255, 255, 0) !important;
  width: 200px;
  /* position:absolute; */
  color: white;
}
.van-search__content {
  background-color: rgba(247, 248, 250, 0.8);
  border-radius: 20px;
}

/* 超值活动 */
.van-col > div {
  width: 100%;
  height: 100%;
  /* background-color: aquamarine; */
  text-align: center;
}
.van-col > div > img {
  width: 100%;
  height: 150px;
}

/* 活动图片 */
.img {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-around;
}

/* 活动块 */
.van-cell__title {
  height: 300px;
  background-color: rgb(241, 218, 218);
  margin: 15px;
}
</style>
