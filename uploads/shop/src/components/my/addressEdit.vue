<template>
  <div>
    <van-nav-bar
      title="地址编辑"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <div style="padding:50px 0">
      <van-address-edit
        :area-list="areaList"
        :address-info="info"
        show-postal
        show-delete
        show-set-default
        show-search-result
        :search-result="searchResult"
        @save="onSave"
        @delete="onDelete"
        @change-detail="onChangeDetail"
      />
    </div>
  </div>
</template>

<script>
import assets from "../../assets/area";
import { setTimeout } from "timers";
export default {
  created() {
    if (this.$store.getters.getLoginState == false) {
      this.$router.push("/my");
    }
  },
  data() {
    return {
      areaList: assets,
      searchResult: [],
      info: { tel: this.$store.getters.getMobile, isDefault: true },
      id: 0
    };
  },

  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    onSave(content) {
      this.$toast("保存成功");
      var cc = {};
      cc.id = this.$store.getters.getAddress.length + 1;
      cc.name = content.name;
      cc.tel = content.tel;
      cc.address = content.addressDetail;
      cc.isDefault = content.isDefault;
      this.$store.commit("setAddressContent", cc);
      setTimeout(() => {
        this.$router.go(-1);
      }, 2000);
    },
    onDelete() {
      this.$toast("delete");
    },
    onChangeDetail(val) {
      if (val) {
        this.searchResult = [
          {
            name: "湖南长沙",
            address: "湖大科技院东栋431君思集团"
          }
        ];
      } else {
        this.searchResult = [];
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
.van-nav-bar {
  position: fixed;
  z-index: 999;
  width: 100%;
}
</style>
