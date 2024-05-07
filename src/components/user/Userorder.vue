<template>
    <div>
        <h2 v-if="orderData[0] == null">暂时还没有订单</h2>
        <el-row>
            <el-col v-for="order in orderData" :key="order.createTime" :span="8">
                <el-card :body-style="{ padding: '0px', width: '300px' }" class="item">
                    <div style="padding: 14px;width: 300px;">
                        <span>订单号:{{ order.id }}</span><br>
                        <br>
                        <el-tag class="ml-2" type="success" v-if="order.pay == 1">已付款</el-tag>
                        <el-tag class="ml-2" type="danger" v-if="order.pay == 0">未付款</el-tag>&nbsp;
                        <el-tag class="ml-2" type="success" v-if="order.post == 1">已发货</el-tag>
                        <el-badge is-dot v-if="order.post == 0">
                            <el-tag class="ml-2" type="danger">未发货</el-tag>
                        </el-badge>
                        <br>
                        <span>下单时间:{{ order.createTime }}</span>
                        <div class="bottom">
                            <el-button type="primary" plain @click="getgoods(order.id)">查看商品</el-button>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-dialog v-model="usergoodsable" title="商品" width="50%" draggable>
            <el-table :data="goodsData" style="width: 100%">
                <el-table-column prop="id" label="商品id" width="180" />
                <el-table-column prop="name" label="商品名称" width="150" />
                <el-table-column label="商品图片" width="100">
                    <template #default="scope">
                        <img :src="scope.row.img" class="avatar" />
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="商品价格" width="100" />
                <el-table-column prop="num" label="商品数量" width="100" />
            </el-table>
        </el-dialog>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const usergoodsable=ref(false)
const orderData = ref([])
const id = sessionStorage.getItem('userid')
const goodsData = ref([])

const getOrder = () => {
    orderData.value = []
    axios.get('/api/order/user/' + id).then((res) => {
        if (res.data.code == 1) {
            for (const item of res.data.data) {
                const date = item.createTime
                const date1 = date[0] + "-" + date[1] + "-" + date[2] + "  " + date[3] + ":" + date[4] + ':' + date[5]
                item.createTime = date1
                orderData.value.push(item)
            }
        }
    })
}
getOrder()

const getgoods = (orderid) => {
    goodsData.value=[]
    axios.post('/api/order/findug', { orderid: orderid }).then((res) => {
        if (res.data.code == 1) {
            for (const item of res.data.data) {
                axios.get('/api/goods/' + item.goodsid).then((res) => {
                    const goods = res.data.data
                    goods.num = item.num
                    goodsData.value.push(goods)
                })
            }
            usergoodsable.value=true
        }
    })
}

</script>