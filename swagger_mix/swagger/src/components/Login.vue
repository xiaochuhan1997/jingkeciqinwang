<template>

  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../assets/logo.png" alt="">
      </div>
      <el-form :model="loginForm" ref="LoginFormRef" :rules="loginFormRules" label-width="0px"
        class="login_form">
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" prefix-icon="UserFilled">
          </el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input type="password" v-model="loginForm.password" prefix-icon="Unlock">
          </el-input>
        </el-form-item>
        <!-- <el-form-item prop="remeber">
          <el-checkbox v-model="loginForm.remeber" label="记住密码" size="large" />
        </el-form-item> -->
        <!-- 按钮 -->
        <el-form-item class="btns">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="info" @click="resetLoginForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
      },
      loginFormRules: {
        username: [
          { required: true, message: '请输入登录名', trigger: 'blur' },
          {
            min: 3,
            max: 10,
            message: '登录名长度在 3 到 10 个字符',
            trigger: 'blur',
          },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 6,
            max: 15,
            message: '密码长度在 6 到 15 个字符',
            trigger: 'blur',
          },
        ],
      },
    };
  },
  methods: {
    resetLoginForm() {
      this.$refs.LoginFormRef.resetFields();
    },
    async login() {
      await this.$refs.LoginFormRef.validate(async (valid) => {
        if (!valid) return;
        const {data: res} = await this.$http.post(
          '/user/login',
          this.loginForm,
          {
            withCredentials: true,
          }
        );
        console.log(res);
        if (res.code !== 200) return this.$message.error(res.msg);
        this.$message.success('登录成功');
        localStorage.setItem('userInfo', JSON.stringify(res.data));
        // window.sessionStorage.setItem('token', res.data.token);
        this.$router.push('/home');
      });
    },
  },
};
</script>
<style lang="less" scoped>
.login_container {
//1. "./assets/bg.jpg": 表示相对路径
//2. "assets/bg.jpg": 被webpack解析为相对路径
//3."~@/assets/bg.jpg": 被webpack解析为 require(src/assets/bg.jpg) 动态引入
//@在webpack 被resolve.alias配置下等价于/src
//4."/assets/logo_blue.png": webpack不解析
  background: url("~@/assets/bg.jpg") no-repeat;
  background-size: 100% 100%;
  height: 100%;
}
.login_box {
  width: 450px;
  height: 300px;
  background: rgba(255, 255, 255, 0.805);
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}
.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
.btns {
  float: right;
}
</style>
