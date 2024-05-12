<template>
  <div class="common-layout">
    <el-container>
      <el-header style="width:100%;height: 100px;">
        <div class="A">
          <el-avatar :size="32" class="mr-3"><img :src="img" /></el-avatar>
          <span class="text-large font-600 mr-3"> 欢迎{{ username }} </span>
          <span class="C" style="color:blue">
            悦享购物开发者管理平台
          </span>
        </div>
        <div class="B">
          <el-button type="danger" @click="back" circle="true" size="large" plain="true"><el-icon>
              <SwitchButton />
            </el-icon></el-button>
        </div>
        <div class="D">
          <el-button type="success" @click="register" :disabled="isDisable" plain>注册新管理员</el-button>
        </div>
      </el-header>
      <el-container>
        <el-aside style="width: 210px;height: 630px;">
          <el-scrollbar>
            <el-menu :default-openeds="['1', '2', '3','4']"  background-color="rgba(73, 219, 255, 0.1)">
              <el-sub-menu index="1">
                <template #title>
                  <el-icon>
                    <Shop />
                  </el-icon>店铺管理
                </template>
                <el-menu-item index="1-1"><router-link to="/adminhome/adminshop">店铺详情</router-link></el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="2">
                <template #title>
                  <el-icon>
                    <Avatar />
                  </el-icon>用户管理
                </template>
                <el-menu-item index="2-1"><router-link to="/adminhome/adminuser">用户详情</router-link></el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="3">
                <template #title>
                  <el-icon>
                    <Paperclip />
                  </el-icon>开发者管理
                </template>
                <el-menu-item index="3-1"><router-link to="/adminhome/admindeveloper">开发者详情</router-link></el-menu-item>
                <el-menu-item index="3-2"><router-link to="/adminhome/admins">所有管理员</router-link></el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="4">
                <template #title>
                  <el-icon>
                    <Paperclip />
                  </el-icon>商品管理
                </template>
                <el-menu-item index="3-1"><router-link to="/adminhome/allgoods">所有商品</router-link></el-menu-item>
                <el-menu-item index="3-2"><router-link to="/adminhome/alltype">所有类别</router-link></el-menu-item>
              </el-sub-menu>
            </el-menu>
          </el-scrollbar>
        </el-aside>
        <el-main><router-view></router-view></el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script setup>
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
const username = sessionStorage.getItem('adminname')
const img = sessionStorage.getItem('adminimg')
const router = useRouter()
//退出登录
const back = () => {
  ElMessageBox.confirm(
    '您将会退出登录，是否继续',
    'Warning',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
      router.push('/adminlogin')
      localStorage.removeItem('adminid')
      sessionStorage.removeItem('adminid')
      sessionStorage.removeItem('adminname')
      sessionStorage.removeItem('adminimg')
      ElMessage({
        type: 'success',
        message: '退出成功',

      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消退出',
      })
    })
}
const register = () => {
  router.push('/adminregister')
}
</script>
<style scoped>
.common-layout {
  top: 0px;
  left: 0px;
  background-image: url("../assets/img/adminhomebg.jpg");
}

* {
  margin: 0px;
  padding: -1px;
  top: 0px;
  left: 0px;
}

.el-header {
  top: -4px;
  left: 0px;
}

a {
  text-decoration: none;
  color: rgba(0, 0, 0, 0.789);
}

.router-link-active {
  text-decoration: none;
  color: rgba(2, 2, 8, 0.784);
}

.A {
  float: left;
  margin-top: 30px;
}

.B {
  line-height: 100px;
  float: right;
}

.D {
  line-height: 100px;
  float: right;
  margin-right: 100px;
}

.C {
  margin-left: 300PX;
  font-size: 30px;
}

* {
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>