<template>
    <div>
        <router-link to="/shoppinghome/typegoods"><el-button type="primary" plain><el-icon>
                    <Tickets />
                </el-icon>返回商品详情</el-button></router-link>
        <el-descriptions title="店铺信息">
            <el-descriptions-item label="公司loge"><img :src="business.img" class="avatar"></el-descriptions-item>
            <el-descriptions-item label="公司名称"><el-link type="primary" href="#">{{ business.name
            }}</el-link></el-descriptions-item>
            <el-descriptions-item label="公司账号">{{ business.username }}</el-descriptions-item>
            <el-descriptions-item label="公司客服电话"><el-link type="primary" href="#">{{ business.phone
            }}</el-link></el-descriptions-item>
            <el-descriptions-item label="状态">
                <el-tag size="small" v-if="business.status == 1">正常</el-tag>
                <el-tag size="small" type="danger" v-if="business.status == 0">封禁</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="公司地址"><el-link type="primary" href="#">{{ business.address
            }}</el-link></el-descriptions-item>
        </el-descriptions>
        <el-button plain @click="talkwithbusiness">与商家客服联系</el-button>
        <el-divider />

        <el-card class="box-card" v-for="goods in goodsData" :key="goods.id">
            <template #header>
                <div class="card-header">
                    <el-image style="width: 100px; height: 100px" :src="goods.img" fit="contain" />
                    <h2>{{ goods.name }}</h2>
                    {{ goods.type }}
                </div>
            </template>
            {{ goods.detail }}
            <template #footer>
                价格:<el-text type="danger">{{ goods.price }}</el-text>
                <el-button type="danger" plain style="float:right;" @click="addbasket1(goods)" v-if="goods.status == 0"
                    disabled>加入购物车(商品已停售)</el-button>
                <el-button type="success" plain style="float:right;" @click="addbasket1(goods)"
                    v-if="goods.status == 1">加入购物车</el-button>
            </template>
        </el-card>

        <el-dialog v-model="dialogVisible" title="添加商品" width="70%" draggable>
            <span>请选择商品数量</span>
            <el-input-number v-model="num" :min="1" :max="999" />
            <template #footer>
                <div style="float: left;">
                    <span>总价:{{ goods1.price * num }}</span>
                </div>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addbasket(goods1)">
                        确认
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage, inputEmits } from 'element-plus'
import { ElNotification } from 'element-plus'
import { useRouter } from "vue-router"
import { useRoute } from 'vue-router'

const route = useRoute()
const router = useRouter()
const userid = sessionStorage.getItem('userid')
const id = route.query.goodsid
const goods = ref({})
const business = ref({})
const goodsData = ref([])
const dialogVisible = ref(false)

const num = ref(1)

const getgoods = () => {
    axios.get("/api/goods/" + id).then((res) => {
        if (res.data.code == 1) {
            goods.value = res.data.data
            axios.get('/api/business/' + goods.value.businessid).then((res) => {
                if (res.data.code == 1) {
                    business.value = res.data.data
                    axios.get('/api/goods/business/' + goods.value.businessid).then((res) => {
                        if (res.data.code == 1) {
                            goodsData.value = res.data.data
                            goodsData.value.forEach((item) => {
                                axios.get('/api/goods/type/' + item.type).then((res) => {
                                    if (res.data.code == 1) {
                                        item.type = res.data.data
                                    }
                                })
                            })
                        }
                    })
                }
            })
        }
    })
}
getgoods()
const goods1 = ref({})
const addbasket1 = (goods) => {
    dialogVisible.value = true;
    num.value = 1
    goods1.value = goods
}
const backetgoods1 = ref([])
const number = ref(0)
const addbasket = (goods) => {
    number.value = 0
    backetgoods1.value = []
    axios.get('/api/basket/user/' + userid).then((res) => {
        if (res.data.code == 1) {
            backetgoods1.value = res.data.data
            backetgoods1.value.forEach((item) => {
                const basket = {}
                basket.userid = userid
                basket.goodsid = goods.id
                if (item.goodsid == goods.id) {
                    const num1 = item.num + num.value
                    basket.num = num1
                    basket.id = item.id
                    axios.put('/api/basket', basket).then((res) => {
                        if (res.data.code == 1) {
                            dialogVisible.value = false

                        }
                    })
                } else {
                    number.value = number.value + 1
                    if (number.value == backetgoods1.value.length) {
                        const basket = {}
                        basket.userid = userid
                        basket.goodsid = goods.id
                        basket.num = num.value
                        axios.post('/api/basket', basket).then((res) => {
                            if (res.data.code == 1) {
                                dialogVisible.value = false
                            }
                        })
                    }
                }
            })
        } else {
            const basket = {}
            basket.userid = userid
            basket.goodsid = goods.id
            basket.num = num.value
            axios.post('/api/basket', basket).then((res) => {
                if (res.data.code == 1) {
                    dialogVisible.value = false
                }
            })
        }
    })
}

const talkwithbusiness = () => {
    const url = router.resolve({ path: '/userdetail/usertalk' })
    window.open(url.href);
}

</script>
<style scoped>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.box-card {
    font-family: "阿里妈妈刀隶体 Regular";
    width: 440px;
    float: left;
    margin: 16px;
}

.box-card:hover {
    color: rgb(89, 156, 108);
    font-size: 16px;
    transform: scale(1.1);
    transition: all 0.3s ease;
}

* {
    font-family: '阿里妈妈方圆体 VF Regular';
}
</style>