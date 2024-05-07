<template>
    <div>
        <router-link to="/adminhome/adminshop">返回</router-link>
        <div class="mt-4">
            <el-input v-model="input" placeholder="请输入商品id/名称" style="width: 300px;"
                @keyup.enter.native="getGoodsBy(input)">
                <template #append>
                    <el-button :icon="Search" plain @click="getGoodsBy(input)" />
                </template>
            </el-input>
        </div>
        <el-table :data="goodsData" style="width: 100%" :row-class-name="tableRowClass">
            <el-table-column prop="id" label="商品id" width="180" />
            <el-table-column prop="name" label="商品名称" width="150" />
            <el-table-column prop="type" label="商品类型" width="100" />
            <el-table-column label="商品图片" width="100">
                <template #default="scope">
                    <img :src="scope.row.img" class="avatar" />
                </template>
            </el-table-column>
            <el-table-column prop="detail" label="商品详情" width="280" />
            <el-table-column prop="price" label="商品价格" width="100" />
            <el-table-column prop="stock" label="商品库存" width="100" />
            <el-table-column label="商品状态" width="100">
                <template #default="scope">
                    <el-check-tag checked style="margin-right: 8px" @click="changeStatus(0, scope.row.id)"
                        v-if="scope.row.status == 1" type="success" size="large" hit="ture">在售</el-check-tag>
                    <el-check-tag @click="changeStatus(1, scope.row.id)" v-if="scope.row.status == 0" type='danger'
                        size="large" hit="ture">停售</el-check-tag>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { useRouter } from "vue-router"
import { useRoute } from 'vue-router'

const route = useRoute()
const router = useRouter()
const outerVisible = ref(false)
const add = ref(false)
const del = ref(false)
const goodsData = ref([{}])
const type = ref({})
const value = ref(true)
const input = ref('')
const bussinessid = route.query.businessid

const tableRowClass = ({ row, column, rowIndex, columnIndex }) => {
    if (row.status == "1") {
        return "success-row"
    } else { return 'warning-row' }
}

const goods2 = ref({})

const findGoods = () => {
    axios.post('/api/goods/find', goods2.value).then((res) => {
        if (res.data.code == 1) {
            goodsData.value = res.data.data
            goodsData.value.forEach((item) => {
                axios.get('/api/goods/type/' + item.type).then((res) => {
                    if (res.data.code == 1) {
                        item.type = res.data.data
                    }
                })
            })
        } else { ElMessage.error('查询不到商品') }
    })
}
const getGoodsBy = (id) => {
    if (id == '') {
        getGoods(bussinessid)
    }
    if (!isNaN(id)) {
        goods2.value.id = id
        findGoods()
        input.value = ''
    } else {
        goods2.value.name = id
        findGoods()
        input.value = ''
    }
}

const getGoods = (id) => {
    axios.get('/api/goods/business/' + id).then((res) => {
        if (res.data.code == 1) {
            goodsData.value = res.data.data
            goodsData.value.forEach((item) => {
                axios.get('/api/goods/type/' + item.type).then((res) => {
                    if (res.data.code == 1) {
                        console.log(res.data.data)
                        item.type = res.data.data
                    }
                })
            })
        } else {
            goodsData.value = []
        }
    })
}
getGoods(bussinessid)

const typeDate = ref([{
    id: null,
    name: '',
    img: ''
}])
const getTypes = () => {
    axios.get('/api/goods/type').then((res) => {
        if (res.data.code == 1) {
            typeDate.value = res.data.data
        }
    })
}
getTypes()

const goods = ref([{}])
const goods1 = ref({})
const handleAvatarSuccess = (res) => {
    if (res.code == 1) {
        goods1.value.img = res.data
    }
}

const getType = (typeid) => {
    axios.get('api/goods/type' + typeid).then((res) => {
        if (res.data.code == 1) {
            goodsData.value = res.data.data
        }
    })
}

const changeStatus = (status, id) => {
    axios.post('/api/goods/find', { id: id }).then((res) => {
        if (res.data.code == 1) {
            goods.value = res.data.data
            goods.value.forEach((item) => {
                goods1.value = item
            })
            goods1.value.status = status
            axios.put('/api/goods', goods1.value).then((res) => {
                getGoods(bussinessid)
            })
        }
    })
}




</script>
<style >
.avatar {
    width: 60px;
    height: 60px;
    display: block;
}

.avatar-uploader-icon {
    width: 160px;
    height: 160px;
    text-align: center;
    line-height: 160px;
}

.el-table .warning-row {
    background: rgb(255, 142, 142) !important;
}

.el-table .success-row {
    background: rgb(219, 255, 238) !important;
}

* {
    font-family: "阿里妈妈方圆体 VF Regular";
}
</style>
