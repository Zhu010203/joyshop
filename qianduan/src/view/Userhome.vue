<template>
    <div class="home">
        <div class="common-layout">
            <el-container>
                <el-header>
                    <router-link to="/shoppinghome/typegoods"><img src="/src/assets/img/jiazai.gif"
                            style="width: 100px; hight:50px" /></router-link>
                    <div style="float: right;">
                        <el-avatar :size="50" :src="user.img" />&nbsp;
                        <span class="text-large font-600 mr-3"> <span style="font-size: 2.3ex;">{{ user.name
                        }}</span></span>&nbsp;
                        <el-button type="danger" size="large" circle plain @click="logout"><el-icon>
                                <SwitchButton />
                            </el-icon></el-button>
                    </div>
                </el-header>
                <el-container>
                    <el-aside width="150px" >
                        <el-scrollbar>
                            <el-menu background-color="rgba(73, 219, 255, 0.1)">
                                <el-menu-item index="1" default-openeds="1" >
                                    <el-icon>
                                        <House />
                                    </el-icon>
                                    <router-link to="/userdetail/user"><span
                                            style="font-size: large;">个人信息</span></router-link>
                                </el-menu-item>
                                <el-menu-item index="2">
                                    <el-icon>
                                        <Tickets />
                                    </el-icon>
                                    <router-link to="/userdetail/userorder"><span
                                            style="font-size: large;">查看订单</span></router-link>
                                </el-menu-item>
                                <el-menu-item index="3">
                                    <el-icon>
                                        <ChatLineRound />
                                    </el-icon>
                                    <router-link to="/userdetail/usertalk"><span
                                            style="font-size: large;">聊天功能</span></router-link>
                                </el-menu-item>
                            </el-menu>
                        </el-scrollbar>
                    </el-aside>
                    <el-main><router-view></router-view></el-main>
                </el-container>
            </el-container>
        </div>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useRouter } from "vue-router"
import { useRoute } from 'vue-router'

const route = useRoute()
const router = useRouter()
const id = sessionStorage.getItem('userid')
const user = ref({})

const getUser = () => {
    axios.get('/api/user/' + id).then((res) => {
        if (res.data.code == 1) {
            user.value = res.data.data
        }
    })
}
getUser()
const logout = () => {
    router.push('/shoppinglogin')
    sessionStorage.removeItem('userid')
    localStorage.removeItem('userid')
}


</script>
<style scoped>
a {
    text-decoration: none;
}

.home {
background-image: url('../assets/img/userhomebg.jpg');
    margin: -10px;
    width: auto;
    height: 900px;
}
*{
    font-family: "阿里妈妈方圆体 VF Regular";
}
</style>
  