<template>
    <div>
        <div class="mt-4">
            <el-input v-model="input" placeholder="请输入商品id/名称" style="width: 300px;"
                @keyup.enter.native="getGoodsBy(input)">
                <template #append>
                    <el-button :icon="Search" plain @click="getGoodsBy(input)" />
                </template>
            </el-input>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <el-button type="success" plain @click=" outerVisible = true; goods1 = {}; add = true;">添加商品</el-button>
        </div>
        <el-table :data="goodsData" style="width: 100%" :row-class-name="tableRowClass" empty-text="暂无商品" :header-cell-style="{background:'rgba(255, 255, 255)',color:'#606266'}">
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
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button @click="handleEdit(scope.row.id)">编辑</el-button>
                    <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog v-model="outerVisible" title="商品信息" append-to-body draggable>
            <template #default>
                <table>
                    <tr v-if="goods1.id">
                        <td>商品id</td>
                        <td><el-input v-model="goods1.id" type="text" disabled style="width:300px" /></td>
                    </tr>
                    <tr>
                        <td>商品名称</td>
                        <td><el-input v-model="goods1.name" type="text" placeholder="请输入商品名" style="width:300px" /></td>
                    </tr>
                    <tr>
                        <td>商品类型</td>
                        <td><el-select v-model="goods1.type" class="m-2" placeholder="选择商品类型">
                                <el-option v-for="item in typeDate" :key="item.id" :value="item.id" :label="item.name" />
                            </el-select></td>
                    </tr>
                    <tr>
                        <td>商品图片</td>
                        <td><el-upload class="avatar-uploader" action="/api/photo/goods" :on-success="handleAvatarSuccess">
                                <img v-if="goods1.img" :src="goods1.img" class="avatar" />
                                <el-span v-else class="avatar-uploader-icon">
                                    点此增添图片
                                </el-span>
                            </el-upload></td>
                    </tr>

                    <tr>
                        <td>商品价格</td>
                        <td> <el-input-number v-model="goods1.price" :precision="2" :step="100" :min="0.01" :max="999999" /></td>
                    </tr>
                    <tr>
                        <td>商品库存</td>
                        <td> <el-input-number v-model="goods1.stock" :step="100" :min="0" :max="999999" /></td>
                    </tr>
                    <tr>
                        <td>商品描述</td>
                        <td><el-input v-model="goods1.detail" type="text" style="width:300px" /></td>
                    </tr>
                    <tr>
                        <td>商品状态</td>
                        <td> <el-switch v-model="value" class="mb-2"
                                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" active-text="在售"
                                inactive-text="停售" /></td>
                    </tr>

                </table>
            </template>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="outerVisible = false">取消</el-button>
                    <el-button type="primary" @click="update" v-if="goods1.id">
                        确认更改
                    </el-button>
                    <el-button type="primary" @click="addgoods" v-if="add">
                        确认添加
                    </el-button>
                </div>
            </template>
        </el-dialog>

        <el-dialog v-model="del" title="Warning" width="30%" align-center>
            <span>确定要删除商品吗?</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="del = false">取消</el-button>
                    <el-button type="danger" @click="delect()">
                        确定删除
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'

const outerVisible = ref(false)
const add = ref(false)
const del = ref(false)
const goodsData = ref([{}])
const type = ref({})
const value = ref(true)
const input = ref('')
const bussinessid = sessionStorage.getItem('businessid')

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
    axios.get('/api/goods/type/all').then((res) => {
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
    axios.get('api/goods/type/' + typeid).then((res) => {
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

const addgoods = () => {
    if (goods1.value.name != null && goods1.value.type != null && goods1.value.price != null ) {
        goods1.value.businessid = bussinessid;
        if (value.value) { goods1.value.status = 1 } else { goods1.value.status = 0 }
        axios.post('/api/goods', goods1.value).then((res) => {
            if (res.data.code == 1) {
                outerVisible.value = false
                add.value = false
                getGoods(bussinessid)
                ElMessage({ message: '添加成功', type: 'success', })
            } else { ElMessage.error('已经存在该商品请检查输入') }
        })
    } else { ElMessage.error('存在空值') }
}
const handleEdit = (id) => {
    outerVisible.value = true
    add.value = false
    axios.post('/api/goods/find', { id: id }).then((res) => {
        if (res.data.code == 1) {
            goods.value = res.data.data
            goods.value.forEach((item) => {
                goods1.value = item
                if (goods1.value.status == 1) {
                    value.value = true
                } else {
                    value.value = false
                }
            })
        }
    })
}
const update = () => {
    if (value.value) { goods1.value.status = 1 } else { goods1.value.status = 0 }
    axios.put('/api/goods', goods1.value).then((res) => {
        if (res.data.code == 1) {
            ElMessage({ message: '更改成功', type: 'success', })
            outerVisible.value = false
            getGoods(bussinessid)
        } else { ElMessage.error('更改失败') }
    })
}
const goodsid = ref()
const handleDelete = (id) => {
    del.value = true
    goodsid.value = id
}
const delect = () => {
    axios.delete('/api/goods/' + goodsid.value).then((res) => {
        if (res.data.code == 1) {
            ElMessage({ message: '删除成功', type: 'success', })
            del.value = false
            getGoods(bussinessid)
        } else { ElMessage.error('删除失败') }
    })
}
</script>
<style scoped>

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
    background: rgb(255, 134, 134) !important;
}

.el-table .success-row {
    background: rgb(255, 255, 255) !important;
}

* {
    font-family: "阿里妈妈方圆体 VF Regular";
}
</style>
