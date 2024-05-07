<template>
    <div class="common-layout">
        <el-container>
            <el-header style="width:100%;height: 100px;">
                <div style="text-align: center;font-size:2em">
                    悦享购物平台
                </div>
                <el-menu mode="horizontal" background-color="rgb(230, 230, 230)" text-color="black" active-text-color="red"
                    default-active="1">
                    <el-menu-item index="1" @click="getGoodsByType()">全部商品</el-menu-item>
                    <el-menu-item index="1-1" v-for="type in types" :key="type.id" @click="getGoodsByType(type.id)"
                        divided>{{ type.name }}</el-menu-item>
                    <el-menu-item index="2" @click="goToUser()">个人中心</el-menu-item>
                    <el-menu-item index="3" @click="back">退出登录</el-menu-item>
                </el-menu>
            </el-header>
            <el-main style="width:100%;height: 540px;">
                <Transition name="fade" mode="out-in">
                    <router-view :key="route.fullPath"></router-view>
                </Transition>
            </el-main>
            <el-footer style="width:100%;height: 100px;">
                <span>欢迎用户<el-link href="#" @click="goToUser()"  type="primary">{{ username }}</el-link>购物</span>
                <div style="float:right;">打开购物车<el-button  :icon="ShoppingCart" /></div>
            </el-footer>
        </el-container>
        <el-affix position="bottom" :offset="150" style="right:0%">
            <div style="float: right;display: inline-block;">
                <el-link @click="getBasket()"><img src="../assets/img/shoppingcar.webp"
                        style="width: 100px; hight:100px;"></el-link>
            </div>
        </el-affix>
        <Transition name="bounce">
            <el-dialog v-model="dialogVisible" title="购物车" width="70%" draggable>
                <span>{{ username }}的购物车</span>
                <el-table :data="backetgoods" stripe style="width: 100%" empty-text="暂无商品">
                    <el-table-column prop="name" label="商品名称" width="150" />
                    <el-table-column prop="type" label="商品类型" width="100" />
                    <el-table-column label="商品图片" width="100">
                        <template #default="scope">
                            <img :src="scope.row.img" class="avatar" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="detail" label="商品详情" width="200" />
                    <el-table-column prop="price" label="商品单价" width="100" />
                    <el-table-column prop="num" label="商品数量" width="100" />
                    <el-table-column label="操作">
                        <template #default="scope">
                            <el-button @click="pay(scope.row.id, scope.row.price, scope.row.num)">付款</el-button>
                            <el-button type="danger" @click="removeByid(scope.row.id)">从购物车移除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <template #footer>
                    <div style="float: left;">
                        <span>总价:{{ money }}</span>
                    </div>
                    <span class="dialog-footer">
                        <el-button type="danger" @click="cleanAll()">清空购物车</el-button>
                        <el-button type="primary" @click="payAll()">一键付款</el-button>
                    </span>
                </template>
            </el-dialog>
        </Transition>
    </div>
</template>
<script setup>
import { ref, watch } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ElNotification } from 'element-plus'
import { useRouter } from "vue-router"
import { useRoute } from 'vue-router'
import { ArrowDown,ShoppingCart } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const types = ref([])
const typeid = ref('')
const userid = sessionStorage.getItem('userid')
const username = sessionStorage.getItem('username')
const dialogVisible = ref(false)
const ispay = ref(false)
const money = ref(0.00)
const summoney = ref(0.00)


const getType = () => {
    axios.get('/api/goods/type/all').then((res) => {
        if (res.data.code == 1) {
            types.value = res.data.data
        }
    })
}
getType()

const getGoodsByType = (id) => {
    typeid.value = id
    router.push({ path: '/shoppinghome/typegoods', query: { typeId: id } })
}

const backetgoods = ref([])
const backetgoods1 = ref([])
const getBasket = () => {
    backetgoods.value = []
    money.value = 0.00
    dialogVisible.value = true
    axios.get('/api/basket/user/' + userid).then((res) => {
        if (res.data.code == 1) {
            backetgoods1.value = res.data.data
            backetgoods1.value.forEach((item) => {
                axios.get('/api/goods/' + item.goodsid).then((res) => {
                    if (res.data.code == 1) {
                        const goods = res.data.data
                        goods.num = item.num
                        axios.get('/api/goods/type/' + goods.type).then((res) => {
                            if (res.data.code == 1) {
                                const type = res.data.data
                                goods.type = type
                                money.value += goods.price * goods.num
                                backetgoods.value.push(goods)
                            }
                        })
                    }
                })
            })
        }
    })
}

const removeByid = (id) => {

    axios.get('/api/basket/goods', { params: { goodsid: id, userid: userid } }).then((res) => {
        if (res.data.code == 1) {
            ElMessage.success("移除成功")
            getBasket()
        } else { ElMessage.error('移除失败') }
    })
}

const cleanAll = () => {
    axios.delete('/api/basket/user/' + userid).then((res) => {
        if (res.data.code == 1) {
            getBasket()
        } else { ElMessage.error('移除失败') }
    })
}

const order = ref({})
const id1 = ref('')
const pay = (id, price, num) => {
    order.value = {}
    ispay.value = false
    id1.value = id
    summoney.value = price * num
    order.value.userid = userid
    order.value.pay = 1
    order.value.post = 0
    order.value.userGoods = [{ goodsid: id, num: num }]
    router.push({ path: '/shoppingpay', query: { money: summoney.value, goodsid: id, num: num, paymethod: 'payone' } })
}

const payAll = () => {
    ispay.value = false
    router.push({ path: '/shoppingpay', query: { money: money.value, paymethod: 'payall' } })
}

const goToUser = () => {
    const url = router.resolve({ path: '/userdetail/user' })
    // 打开新窗口
    window.open(url.href);
}
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
            router.push({ path: '/' })
            sessionStorage.removeItem('userid')
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
.common-layout {
    margin: 0px;
    padding: 0px;
}

.el-header {
    background-color: rgba(79, 218, 236, 0.379);

}

.el-main {
    background-color: rgb(210, 202, 202);
    background-image: url("../assets/img/adminhomebg.jpg");
}

.el-footer{
    background-color: rgb(230, 230, 230);
}
.select {
    width: 100px;
    height: 50px;
    text-align: center;
    line-height: 20px;
}

.select1 {
    width: 100px;
    height: 50px;
    background-color: #4c7a8e;
    text-align: center;
    line-height: 20px;
}

.flex {
    font-size: larger;
    text-align: center;
    line-height: 50px;
    height: 100px;

}

.example-showcase .el-dropdown+.el-dropdown {
    margin-left: 15px;
}

.el-row:last-child {
    margin-bottom: 0;
}

.el-col {
    border-radius: 4px;
}

.flex-grow {
    flex-grow: 1;
}

.avatar {
    width: 60px;
    height: 60px;
    display: block;
}

* {
    font-family: '阿里妈妈方圆体 VF Regular';
}
</style>
<style>
.bounce-enter-active {
    animation: bounce-in 0.5s;
}

.bounce-leave-active {
    animation: bounce-in 0.5s reverse;
}

@keyframes bounce-in {
    0% {
        transform: scale(0);
    }

    50% {
        transform: scale(1.25);
    }

    100% {
        transform: scale(1);
    }
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>