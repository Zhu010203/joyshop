<template>
  <div class="A">
    <!--导入图片作为背景图片-->
    <img src="src\assets\img\businesslogin.jpg" />
    <div class="B">
      <div class="E">
        <el-icon style="font-size: 10px;">
          <ArrowLeftBold />
        </el-icon>
        <span class="V" style="font-size: 20px;" @click="back">返回</span>
        <span class="C">悦享购物电商管理平台</span>
      </div>
      <div class="D">
        <!--创建一个表格 在表格中写登录输入框-->
        <table class="logintable" wideth="1000px" height="200px" cellspadding="20" align="center">
          <caption>悦享电商登录入口</caption>
          <tr>
            <th>
              <el-icon>
                <User />
              </el-icon>&nbsp;
            </th>
            <th>
              <el-input style="width: 200px;" v-model="username" placeholder="请输入商家账号" clearable />
            </th>
          </tr>
          <tr>
            <th>
              <el-icon>
                <Unlock />
              </el-icon>
            </th>
            <th>
              <el-input
                style="width: 200px;"
                v-model="password"
                type="password"
                placeholder="请输入商家密码"
                clearable
                @keyup.enter.native="login()"
                show-password
              />
            </th>
          </tr>
          <tr>
            <th></th>
            <el-button
              style="width: 200px;"
              color="red"
              plain
              @click="login()"
              :disabled="isDisable"
            >登录</el-button>
          </tr>
          <tr>
            <th></th>
            <el-button style="width: 200px;" color="blue" plain @click="register()">注册</el-button>
          </tr>
          <tr></tr>
        </table>
      </div>
    </div>
  </div>
</template>
<script setup>
import { useRouter } from "vue-router";
import { ref, h } from "vue";
import axios from "axios";
import { ElMessage } from "element-plus";
import router from "../router";
import { ElNotification } from "element-plus";

const username = ref("");
const password = ref("");
const count = ref("5");
const isDisable = ref(false);

const login = () => {
  if (username.value != "" && password.value != "") {
    axios
      .get("/api/business/login", {
        params: { username: username.value, password: password.value }
      })
      .then(res => {
        console.log("用户账号" + username.value + "密码" + password.value);
        if (res.data.code == 1) {
          if (res.data.data.status == 1) {
            router.push("/businesshome/business");
            sessionStorage.setItem("businessid", res.data.data.id);
            sessionStorage.setItem("token", res.data.data.id);
            ElMessage({ message: "登陆成功", type: "success" });
          } else {
            ElMessage.error("账号已被封禁,请联系管理员");
          }
        } else {
          count.value--;
          console.log(res.data);
          ElMessage.error("登陆失败,还有" + count.value + "次");
          username.value = "";
          password.value = "";
          if (count.value == 0) {
            isDisable.value = true;
          }
        }
      });
  } else {
    ElMessage({ message: "输入内容存在空值", type: "warning" });
  }
};

const register = () => {
  router.push("/businessregister");
};
const back = () => {
  router.push("/");
};
</script>
<style scoped>
* {
  border: 0px;
  margin-top: 0;
  padding-top: 0px;
}

.A {
  width: 100%;
  height: 100%;
  margin: -8px;
  position: relative;
  display: flex;
  align-items: stretch;
}

.B {
  width: 1520px;
  height: 300px;
  position: absolute;
}

.D {
  border-radius: 20px;
  width: 350px;
  height: 350px;
  background-color: rgba(134, 136, 138, 0.8);
  float: right;
  margin-top: 250px;
  margin-right: 600px;
}

.D table {
  margin-top: 50px;
  margin-left: 20px;
  opacity: 1;
}

.C {
  font-family: "阿里妈妈方圆体 VF Regular";
  font-size: 40px;
  line-height: 100px;
  margin-left: 480px;
}

.E {
  width: 100%;
  height: 100px;
  background-color: rgba(134, 136, 138, 0.8);
  position: fixed;
}

.el-icon {
  margin-left: 30px;
  cursor: pointer;
}

.el-icon:hover {
  transform: scale(1.1);
}

.V {
  font-family: "阿里妈妈方圆体 VF Regular";
  cursor: pointer;
}

.V:hover {
  transform: scale(2);
}
</style>