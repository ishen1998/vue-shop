<template>
  <div class="container-car">
    <div style="padding:10px 10px" @click="moveInfo($props.item.itemId)">
      <img
        :src="this.$props.item.coverImg || this.$props.item.itemImg"
        width="100"
        height="120"
      />
    </div>
    <div class="flex" @click="moveInfo($props.item.itemId)">
      <p class="describe">{{ this.$props.item.itemName }}</p>
      <div class="div" v-show="$props.disnone == false ? false : true">
        <p>红色:M | {{ $props.item.num || $props.item.itemNum }}件</p>
      </div>
      <div
        style="display: flex;align-items: center;justify-content:space-between;padding-top:5px"
      >
        <div>
          <span
            style="color:rgb(163, 163, 163); font-size:12px"
            v-show="$props.disnone == false ? false : true"
            >商品总计:
          </span>
          <span
            style="color:red;font-weight:bold"
            v-show="$props.disnone == false ? false : true"
          >
            ￥{{
              $props.item.priceTotal ||
                ($props.item.isActivity == "Y"
                  ? $props.item.activityPrice
                  : $props.item.price * $props.item.num)
            }}
          </span>
          <span
            style="color:red;font-weight:bold"
            v-show="$props.disnone == false ? true : false"
            >￥{{ $props.item.price }}
          </span>
        </div>
        <van-button
          type="danger"
          size="mini"
          round
          @click.stop="
            edit(
              $store.getters.getcId,
              $props.item.itemId,
              $props.item.price * $props.item.num
            )
          "
          v-if="this.$props.flag ? false : true"
          >删除
        </van-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  created() {},
  data() {
    return {
      checked: false
    };
  },
  methods: {
    moveInfo(id) {
      if (this.$props.flag) {
        return;
      } else {
        this.$router.push({ name: "productDetails", params: { id } });
      }
    },
    edit(cId, itemId, delPrice) {
      if (this.$props.disnone === false) {
        this.$dialog
          .confirm({
            title: "提示信息",
            message: "确认删除该收藏商品吗?"
          })
          .then(() => {
            this.$http.delete(`/shop/collection/${cId}/${itemId}`).then(res => {
              if (res.data.code == 200) {
                this.$router.push({
                  path: "/pay",
                  query: { msg: "删除收藏成功" }
                });
              } else {
                this.$toast(res.message);
              }
            });
          })
          .catch(() => {
            this.$toast("已取消");
          });

        return;
      }
      this.$store.commit("changeChecked", delPrice);
      this.$emit("changeC", this.$store.getters.getChecked);
      this.$http.delete(`/shop/Trolley/${cId}/${itemId}`).then(res => {
        if (res.data.code == 500) {
          this.$toast(res.data.message);
          return;
        } else {
          this.$router.push({ path: "/pay", query: { msg: "删除成功" } });
        }
      });
    }
  },
  props: ["item", "flag", "checkeds", "NoAll", "allgoods", "disnone"]
};
</script>

<style scoped lang="less">
.container-car {
  overflow-x: hidden;
  background-color: white;
  display: flex;
  align-items: center;
  position: relative;
  .flex {
    width: 55%;
    display: flex;
    flex-direction: column;
    margin-top: 40px;
    position: absolute;
    top: -27%;
    left: 38%;
    .describe {
      margin: 10px 0;
      overflow: hidden;
      text-overflow: ellipsis;
      font-size: 12px;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
    }

    .div {
      border: 1px solid #ccc;
      background-color: #f6f6f6;
      width: 99%;
      padding: 5px 5px;
      height: 40px;
      overflow-y: scroll;
      p {
        font-size: 12px;
        color: rgb(163, 163, 163);
      }
    }
  }
}
</style>
