<template>
  <div>
    <Footer></Footer>
    <van-nav-bar left-text="分类" />
    <div class="left">
      <van-sidebar @change="change(activeKey)" route v-model="activeKey">
        <van-sidebar-item v-for="item in fenLei" :key="item" :title="item" />
      </van-sidebar>
    </div>
    <!-- <router-view></router-view> -->
    <sortContent :name="name"></sortContent>
  </div>
</template>
<script>
import Footer from "../components/TabBar/footer.vue";
import sortContent from "../components/sort/sortContent.vue";
export default {
  components: {
    Footer,
    sortContent
  },
  data() {
    return {
      activeKey: 0,
      fenLei: [],
      name: "上装"
    };
  },
  created() {
    this.$http
      .get("/shop/category/getArray")
      .then(res => {
        // console.log(res);
        this.fenLei = res.data.data;
      })
      .catch(err => {
        console.log(err);
      });
    // console.log(this.name)
  },
  methods: {
    change(activeKey) {
      // console.log(index)
      this.name = this.fenLei[activeKey];
      // console.log(this.name);
    }
  }
};
</script>
<style scoped>
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
.left {
  float: left;
}
</style>
