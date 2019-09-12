<template>
  <div>
    <div>
      <van-nav-bar
        style="position: fixed; top:0; width:100%; z-index:9999"
        title="我的收藏"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <div class="flex">
      <p style="padding-left:120px">商品收藏</p>
      <p v-text="edit" @click="del"></p>
    </div>
    <div>
      <van-checkbox-group v-model="result">
        <div class="car" v-for="(item, i) in itemData" :key="i">
          <van-checkbox
            :key="item.id"
            :name="item.id"
            v-show="edit === '编辑' ? false : true"
          ></van-checkbox>
          <div style="width:90%">
            <car :item="item" :disnone="false" />
          </div>
        </div>
      </van-checkbox-group>
    </div>
  </div>
</template>
<script>
import car from "../card/car";
export default {
  data() {
    return {
      itemData: [],
      result: [],
      edit: "编辑"
    };
  },
  created() {
    if (this.$store.getters.getLoginState === false) {
      this.$router.push("/login");
      return;
    }
    this.$http
      .get(`/shop/collection/${this.$store.getters.getcId}`)
      .then(res => {
        let brr = [];
        res.data.data.forEach(item => {
          this.$http
            .get(`/shop/shop/selectByPrimaryKey?itemId=${item.itemId}`)
            .then(a => {
              let tourData = a.data.data;
              tourData.id = item.id;
              brr.push(tourData);
            })
            .catch(err => {
              console.log(err);
              this.$toast("出现意料之外的错误");
            });
        });
        this.itemData = brr;
      })
      .catch(err => {
        console.log(err);
        this.$toast("出现意料之外的错误");
      });
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    del() {
      if (this.edit === "编辑") {
        this.edit = "批量删除";
      } else {
        if (this.result.length === 0) {
          this.edit = "编辑";
        } else {
          let batchDeleteCollection = {
            cid: this.$store.getters.getcId,
            id: this.result
          };
          this.$http
            .post("/shop/collection/batchDelete", batchDeleteCollection)
            .then(res => {
              if (res.data.code != 200) {
                this.$toast(res.data.message);
              } else {
                this.$router.push({
                  path: "/pay",
                  query: { msg: res.data.message }
                });
              }
            })
            .catch(err => {
              console.log(err);
              this.$toast("出现意料之外的错误");
            });
        }
      }
    }
  },
  components: { car }
};
</script>
<style lang="less" scoped>
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
.flex {
  display: flex;
  justify-content: space-around;
  padding-top: 50px;
  font-size: 14px;
}
.car {
  display: flex;
  margin: 5px 5px;
}
</style>
