<template>
  <div>
    <van-nav-bar
      title="个人信息"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <div>
      <van-collapse v-model="activeNames">
        <van-collapse-item title="用户名" :value="username">
          <van-field
            v-model="username"
            placeholder="请输入新用户名"
            size="11"
          />
        </van-collapse-item>
      </van-collapse>
      <van-cell title="头像" is-link value="内容">
        <van-uploader
          :after-read="afterRead"
          preview-size="40px"
          v-model="fileList"
          :max-count="1"
        >
          <img :src="$store.state.tximg" width="40px" ref="txImg" />
        </van-uploader>
      </van-cell>

      <van-collapse v-model="activeNames">
        <van-collapse-item title="电话" name="2" :value="mobile">
          <van-field v-model="mobile" placeholder="请输入电话号码" size="11" />
        </van-collapse-item>
        <van-collapse-item title="性别" name="1" :value="sex">
          <van-radio-group
            v-model="radio"
            style="padding-left:80%"
            @change="changeSex"
          >
            <van-radio name="1" style="padding:5px 0">男</van-radio>
            <van-radio name="2" style="padding:5px 0">女</van-radio>
          </van-radio-group>
        </van-collapse-item>
      </van-collapse>
    </div>
    <van-button type="info" class="btn" @click="submit">确认保存</van-button>
  </div>
</template>

<script>
export default {
  created() {
    this.username = this.$route.query.cLoginName;
    this.mobile = this.$route.query.mobile;
    this.sex = this.$route.query.sex;
    if (this.$route.query.sex == "女") {
      this.radio = "2";
    } else {
      this.radio = "1";
    }
    if (this.$store.getters.getLoginState == false) {
      this.$router.push("/my");
    }
    this.$http.get(`/shop/selectByMobile?mobile=${this.mobile}`).then(res => {
      this.password = res.data.data.password;
    });
  },
  data() {
    return {
      fileList: [],
      activeNames: ["1"],
      value: "",
      radio: "1",
      sex: "",
      username: "",
      file: {},
      mobile: "",
      password: "",
      img: ""
    };
  },
  methods: {
    afterRead(file) {
      this.file = file;
      var data = new FormData();
      data.append("file", file.file);
      this.$http
        .post(`/shop/uploadFile`, data, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(res => {
          this.img = res.data.data;
        });
    },
    onClickLeft() {
      this.$router.go(-1);
    },
    changeSex() {
      this.sex = this.radio == 1 ? "男" : "女";
    },
    submit() {
      var data = {
        cId: this.$route.query.cId,
        cLoginName: this.username,
        password: this.password,
        mobile: this.mobile,
        img: this.img,
        sex: this.sex
      };
      this.$http.post("/shop/updateByPrimaryKey", data).then(res => {
        if (res.data.code == 200) {
          this.$toast.success({
            forbidClick: true, // 禁用背景点击
            message: "保存成功"
          });
          this.$store.commit("setTximg", this.img);
          setTimeout(() => {
            this.$router.push("/my");
          }, 2000);
          this.$store.commit("changeMobile", this.mobile);
        } else {
          this.$toast("用户名或手机号已存在");
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
.btn {
  position: fixed;
  bottom: 20px;
  left: 40%;
}
</style>
