<template>
  <div>
    <el-tooltip :content="'订单发货详情'" placement="top">
      <el-switch v-model="value" style="--el-switch-on-color:#ff4949; --el-switch-off-color:  #13ce66" active-value="0"
        inactive-value="1"  @click="getug()"/>
    </el-tooltip><span v-if="value==0">未发货订单</span><span v-if="value==1">已发货订单</span>
    <h2 v-if="orderDate[0] == null">暂时还没有订单</h2>
    <el-row>
      <el-col v-for="order in orderDate" :key="order.createTime" :span="8">
        <el-card :body-style="{ padding: '0px', width: '300px' }" class="item">
          <div style="padding: 14px;width: 300px;">
            <span>订单号:{{ order.id }}</span><br>
            下单人:
            <el-link href="#" @click="getUser(order.userid)" type="primary">{{ order.userid }}</el-link>
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
              <el-button type="danger" plain @click="update(order.id)" v-if="order.post == 0">发货</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-drawer v-model="drawer" title="用户详情" :with-header="false">
      用户id:{{ user.id }}<br>
      用户名称:{{ user.name }}<br>
      用户性别:{{ user.sex }}<br>
      用户地址:{{ user.address }}<br>
      用户电话:{{ user.phone }}<br>
    </el-drawer>
    <el-dialog v-model="usergoodsable" title="商品" width="50%" draggable>
      <el-table :data="goodsData1" style="width: 100%">
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
import { Search, SortUp, Food } from '@element-plus/icons-vue'
import { Share } from '@element-plus/icons-vue'

const drawer = ref(false)
const usergoodsable = ref(false)
const orderDate = ref([])
const ugDate = ref([])
const businessid = sessionStorage.getItem('businessid')
const goodsDate = ref([])
const usergoods = ref({})
const value = ref(1)
const ugs = ref([])
const order = ref({})
const getug = () => {
  orderDate.value = []
  order.value={}
  axios.get('/api/goods/business/' + businessid).then((res) => {
    if (res.data.code == 1) {
      goodsDate.value = res.data.data
      goodsDate.value.forEach((item) => {
        usergoods.value.goodsid = item.id
        axios.post('/api/order/findug', usergoods.value).then((res) => {
          if (res.data.code == 1) {
            ugs.value = res.data.data
            for (const item of ugs.value) {
              if (order.value.id != item.orderid) {
                order.value.id = item.orderid
                order.value.post=value.value
                axios.post('/api/order/find', order.value).then((res) => {
                  if (res.data.code == 1) {
                    for (const item of res.data.data) {
                      const date = item.createTime
                      const date1 = date[0] + "-" + date[1] + "-" + date[2] + "  " + date[3] + ":" + date[4] + ':' + date[5]
                      item.createTime = date1
                      item.userGoods = ugs.value
                      orderDate.value.push(item)
                    }
                  }
                })
              }
            }
          }
        })
      })
    }
  })
}
getug()

const setMessage = (selfid, userid) => {
  const message = {}
  message.sender = selfid
  message.receive = userid
  const msg = '已经发货,请注意查收'
  message.message = msg
  console.log(message)
  axios.post('/api/message', message).then((res) => {
    if (res.data.code == 1) {

    }
  })
}
const usergoodsDate = ref([])
const goodsData1 = ref([{}])
const getgoods = (orderid) => {
  goodsData1.value = []
  usergoodsable.value = true
  usergoods.value = {}
  usergoods.value.orderid = orderid
  axios.post('/api/order/findug', usergoods.value).then((res) => {
    if (res.data.code == 1) {
      usergoodsDate.value = res.data.data
      usergoodsDate.value.forEach((item) => {
        axios.get('/api/goods/' + item.goodsid).then((res) => {
          if (res.data.code == 1) {
            const goods3 = {}
            goods3.value = res.data.data
            goods3.value.num = item.num
            goodsData1.value.push(goods3.value)
          }
        })
      })
    }
  })
}

const order1 = ref({})
const update = (id) => {
  axios.get('/api/order/' + id).then((res) => {
    if (res.data.code == 1) {
      order1.value = res.data.data
      order1.value.post = 1
      console.log(order1.value)
      axios.put('/api/order', order1.value).then((res) => {
        if (res.data.code == 1) {
          setMessage(businessid, order1.value.userid)
          ElMessage.success('已发货')
          getug()
        } else { ElMessage.error('发货失败') }
      })

    }
  })
}
const user = ref({})
const getUser = (id) => {
  drawer.value = true
  axios.get('/api/user/' + id).then((res) => {
    if (res.data.code == 1) {
      user.value = res.data.data
    }
  })
}

</script>
<style scoped>
.bottom {
  width: 200px;
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 300px;
  height: 300px;
  display: block;
}

.item {
  margin-top: 10px;
  margin-right: 40px;
}

* {
  font-family: "阿里妈妈方圆体 VF Regular";
}

.item :hover {
  transform: scale(1.01);
}
</style>