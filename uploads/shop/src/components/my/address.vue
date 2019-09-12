<template>
  <div>
    <div>
      <van-nav-bar
        title="地址管理"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <div>
      <van-address-list
        v-model="chosenAddressId"
        :list="list"
        :disabled-list="disabledList"
        @add="onAdd"
        @edit="onEdit"
      />
    </div>
  </div>
</template>

<script>
export default {
  created() {
    if (this.$store.getters.getLoginState == false) {
      this.$router.push("/my");
    }
    this.list = this.$store.getters.getAddress;
    if (this.list.length == 1) {
      this.chosenAddressId = this.list[0].id;
    } else {
      this.list.forEach(item => {
        if (item.isDefault == true) {
          this.chosenAddressId = item.id;
        }
      });
    }
  },
  data() {
    return {
      chosenAddressId: "1",
      list: [
        // {
        //   id: "1",
        //   name: "张三",
        //   tel: "13000000000",
        //   address: "浙江省杭州市西湖区文三路 138 号东方通信大厦 7 楼 501 室"
        // }
      ],
      disabledList: []
    };
  },
  methods: {
    onClickLeft() {
      console.log("返回");
      this.$router.go(-1);
    },
    onAdd() {
      this.$router.push("/addressEdit");
    },
    onEdit(item, index) {
      this.$toast("编辑地址:" + index);
    }
  }
};
</script>

<style lang="css" scoped>
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
</style>
