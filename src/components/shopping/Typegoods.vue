<template>
    <div>
        <div>
            <el-input v-model="input" placeholder="请输入查询的id/商品名称" input-style="width:200px;"
                @keyup.enter.native="getGoods(input)"></el-input>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            价格位于<el-input-number v-model="num1" :min="0" :max="999999" controls-position="right" :precision="2"
                :step="1000" />
            到&nbsp;<el-input-number v-model="num2" :min="1" :max="999999" controls-position="right" :precision="2"
                :step="1000" />
            <el-button type="primary" @click="getbyprice" plain>根据价格搜索</el-button>
        </div>
        <h3 v-if="goodsData.length==0">查询商品不存在</h3>
        <el-card class="box-card" v-for="goods in goodsData" :key="goods.id">
            <template #header>
                <div class="card-header">
                    <el-image style="width: 100px; height: 100px" :src="goods.img" fit="contain" />
                    <h2 >{{ goods.name }}</h2>
                    <el-link  @click="gotodetail(goods.id)" target="_blank">查看商品详情</el-link>
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
import { ref,reactive } from 'vue'
import axios from 'axios'
import { ElMessage, inputEmits } from 'element-plus'
import { ElNotification } from 'element-plus'
import { useRouter } from "vue-router"
import { useRoute } from 'vue-router'

const route = useRoute()
const router = useRouter()
const userid = sessionStorage.getItem('userid')
const typeId = route.query.typeId

const input = ref('')
const num = ref(1)
const num1 = ref(0)
const num2 = ref(999999)
const dialogVisible = ref(false)
const goodsData = ref([])


const getGoodsByType = () => {
    axios.post('/api/goods/find', { type: typeId }).then((res) => {
        if (res.data.code == 1) {
            goodsData.value = res.data.data
        }
    })

}

getGoodsByType()

const getGoods = (id) => {

    if (id == '') {
        getGoodsByType()
    } else {
        if (!isNaN(id)) {
            axios.get("/api/goods/" + id).then((res) => {
                if (res.data.code == 1) {
                    goodsData.value = res.data.data
                    input.value = ''

                } else {
                    ElMessage('没有查到数据')
                }
            })
        } else {
            axios.post("/api/goods/find", { name: id }).then((res) => {
                if (res.data.code == 1) {
                    goodsData.value = res.data.data
                    input.value = ''

                } else {
                    ElMessage('没有查到数据')
                    input.value = ''
                }
            })
        }
    }
}
const getbyprice=() => {
    axios.post("/api/goods/price",  { low:num1.value,high:num2.value,type:typeId } ).then((res) => {
        if(res.data.code==1){
            goodsData.value = res.data.data
        }else{
            goodsData.value = []
        }
    })
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

const goods1 = ref({})
const addbasket1 = (goods) => {
    dialogVisible.value = true;
    num.value = 1
    goods1.value = goods
}

const gotodetail=(goodsid)=>{
    router.push({ path: '/goodsdetail', query: { goodsid:goodsid} })
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
    width: 440px;
    float: left;
    margin: 16px;
}
.box-card:hover {
    color: rgb(94, 204, 255);
    font-size: 16px;
    transform: scale(1.1);
    transition: all 0.3s ease;
}
*{
    font-family: "阿里妈妈刀隶体 Regular";
}

</style>