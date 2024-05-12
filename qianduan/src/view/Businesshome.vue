<template>
    <div class="home">
        <div class="common-layout">
            <el-container>
                <el-header>
                    <img src="..\assets\img\jiazai.gif" style="width: 100px; hight:50px" />
                    <div style="float: right;">
                        <el-avatar :size="50" :src="business.img" />&nbsp;
                            <span class="text-large font-600 mr-3"> <span style="font-size: 2.3ex;">{{ business.name
                            }}</span></span>&nbsp;
                        <el-button type="danger" size="large" circle plain @click="logout"><el-icon>
                                <SwitchButton />
                            </el-icon></el-button>
                    </div>
                </el-header>
                <el-container>
                    <el-aside width="200px">
                        <el-scrollbar>
                            <el-menu text-color="blue" background-color="rgba(73, 219, 255, 0.3)">
                                <el-menu-item index="1" default-openeds="1">
                                    <el-icon>
                                        <House />
                                    </el-icon>
                                    
                                    <router-link to="/businesshome/business"><span
                                            style="font-size: large;">店铺管理</span></router-link>
                                </el-menu-item>
                                <el-menu-item index="2">
                                    <el-icon>
                                        <Tickets />
                                    </el-icon>
                                    <router-link to="/businesshome/businessorder"><span
                                            style="font-size: large;">订单管理</span></router-link>
                                </el-menu-item>
                                <el-menu-item index="3">
                                    <el-icon>
                                        <Goods />
                                    </el-icon>
                                    <router-link to="/businesshome/businessgoods"><span
                                            style="font-size: large;">商品管理</span></router-link>
                                </el-menu-item>
                                <el-menu-item index="4">
                                    <el-icon>
                                        <ChatLineRound />
                                    </el-icon>
                                    <router-link to="/businesshome/businesstalk"><span
                                            style="font-size: large;">聊天功能</span></router-link>
                                </el-menu-item>
                            </el-menu>
                        </el-scrollbar>
                    </el-aside>
                    <el-main><Transition name="fade" mode="out-in"><router-view></router-view></Transition></el-main>
                </el-container>
            </el-container>
        </div>
    </div>
</template>
<script setup>
import { ref, h } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ElNotification } from 'element-plus'
import { useRouter } from "vue-router"
import { useRoute } from 'vue-router'
import { UserFilled } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const id = sessionStorage.getItem('businessid')
const business = ref({})

const getBusiness = () => {
    axios.get('/api/business/' + id).then((res) => {
        if (res.data.code == 1) {
            business.value = res.data.data
        }
    })
}
getBusiness()
const logout = () => {
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
            router.push('/businesslogin')
    sessionStorage.removeItem('businessid')
    localStorage.removeItem('businessid')
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

</script>
<style scoped>
a {
    text-decoration: none;
}

.home {

    margin: -10px;
    width: auto;
    height: 900px;
    background-image: url('../assets/img/businesshome.jpg');
}
*{
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>
  