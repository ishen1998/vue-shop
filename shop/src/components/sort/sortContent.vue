<template>
  <div>
    <van-grid :gutter="10" :column-num="3">
      <van-grid-item
        v-model="activeKey"
        v-for="item in fenleiList"
        :key="item.categoryId"
        :icon="item.categoryImage"
        :text="item.categoryName"
        @click="getSortList(item.categoryId)"
      />
    </van-grid>
  </div>
</template>

<script>
export default {
  props: ["name"],
  data() {
    return {
      fenleiList: [],
      activeKey: 0
    };
  },
  methods: {
    getSortList(id) {
      this.$router.push({
        name: "sortList",
        path: "/sortList",
        query: { id: id }
      });
    }
  },
  created() {},
  watch: {
    name(va) {
      this.$http
        .get("/shop/category/selectByParent?listParent=" + va)
        .then(res => {
          this.fenleiList = res.data.data.list;
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>
