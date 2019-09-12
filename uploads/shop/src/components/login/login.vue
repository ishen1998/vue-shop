<template>
  <div class="login">
    <van-nav-bar left-text="登陆" left-arrow @click-left="onClickLeft" />
    <h1>登陆</h1>
    <input
      type="text"
      required
      placeholder="请输入手机号码或用户名"
      v-model="cLoginName"
    />
    <input
      type="password"
      required
      placeholder="请输入密码"
      v-model="password"
    />
    <br />
    <van-button type="info" @click="login">登陆</van-button>
  </div>
</template>
<script>
import { setTimeout } from "timers";
export default {
  data() {
    return {
      cLoginName: "",
      password: ""
    };
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    login() {
      var customer = {
        cLoginName: this.cLoginName,
        password: this.password
      };
      this.$http.post("/shop/login", customer).then(res => {
        if (res.data.code == 500) {
          this.$notify(res.data.msg);
        } else if (res.data.code == 200) {
          this.$store.commit("setcId", res.data.data.cId);
          this.$notify({
            message: "登陆成功",
            duration: 2000,
            background: "#1989fa"
          });
          this.$store.commit("changeLoginState", res.data.data.mobile); //储存登录用户的电话（调用接口获取数据要用）
          this.$store.commit("setLoginName", res.data.data.cLoginName); //储存登陆用户的姓名
          this.$store.commit("setTximg", res.data.data.img); //储存登陆用户的头像
          setTimeout(() => {
            this.$router.push("/my");
          }, 2000);
        }
      });
    }
  }
};
</script>
<style scoped>
.login {
  text-align: center;
}
.login > h1 {
  margin-top: 100px;
}
.login > input {
  border: 1px #333 solid;
  padding: 10px;
  border-radius: 20px;
  width: 250px;
  margin-top: 15px;
}
.van-button--normal {
  width: 60px;
  height: 60px;
  border-radius: 30px;
  margin-top: 10px;
  /* font-size: 18px; */
  padding: 0;
}
.van-nav-bar__text,
.van-nav-bar .van-icon {
  color: black;
}
</style>
