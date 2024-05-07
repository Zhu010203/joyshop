<template>
    <div >
        <div style="height: 50px" class="input">
            <span>
                <el-input v-model="input3" placeholder="请输入id或商户名" :prefix-icon="Search" style="width: 200px;"
                    @keyup.enter.native="SearchName(input3)">
                    <template #append>
                        <el-button :icon="Search" plain @click="SearchName(input3)" />
                    </template>
                </el-input>
            </span>
        </div>
        <el-table :data="tableData" style="width: 100%" class="tb">
            <el-table-column fixed prop="name" label="店铺名称" width="150" />
            <el-table-column prop="img" label="店铺头像" width="150">
                <template #default="scope">
                    <img :src="scope.row.img" style="width: 50px;height: 50px;" />
                </template>
            </el-table-column>
            <el-table-column prop="detail" label="店铺描述" width="150" />
            <el-table-column prop="address" label="店铺地址" width="200" />
            <el-table-column prop="status" label="当前状态" width="150">
                <template #default="scope">
                    <el-check-tag checked style="margin-right: 8px" @click="changeStatus(0, scope.row.id)"
                        v-if="scope.row.status == 1" type="success" size="large" hit="ture">正常</el-check-tag>
                    <el-check-tag @click="changeStatus(1, scope.row.id)" v-if="scope.row.status == 0" type='danger'
                        size="large" hit="ture">封禁</el-check-tag>
                </template>
            </el-table-column>
            <el-table-column prop="updateTime" label="上一次操作时间" width="280" />
            <el-table-column fixed="right" label="操作" width="200">
                <template #default="scope">
                    <el-button link type="primary" @click="see(scope.row.id)">查看详情</el-button>
                    <el-button link type="primary" @click="seegoods(scope.row.id)">查看商品</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-drawer v-model="drawer" title="详细信息" :with-header="false">
            <div style="width: 400px;" class="A" modal="false">
                <table wideth="1000px" height="100px" cellspadding="20" align="center">
                    <caption style="color: rgb(5, 5, 211);">详细信息</caption>
                    <tr>
                        <th>编号： </th>
                        <th><el-input v-model="form.id" placeholder="Please input" readonly disabled /></th>
                    </tr>
                    <tr>
                        <th>头像： </th>
                        <th><el-upload class="avatar-uploader" action="/api/photo/business"
                                :on-success="handleAvatarSuccess">
                                <img v-if="form.img" :src="form.img" class="avatar" style="width: 100px;height: 100px;" />
                                <el-icon v-else class="avatar-uploader-icon">
                                    <Plus />
                                </el-icon>
                            </el-upload></th>
                    </tr>
                    <tr>
                        <th>姓名： </th>
                        <th><el-input v-model="form.name" placeholder="Please input" /></th>
                    </tr>
                    <tr>
                        <th>店铺描述： </th>
                        <th><el-input v-model="form.detail" placeholder="Please input" /></th>
                    </tr>
                    <tr>
                        <th>店铺地址： </th>
                        <th><el-input v-model="form.address" placeholder="Please input" /></th>
                    </tr>
                    <tr>
                        <th>账号:</th>
                        <th><el-input v-model="form.username" placeholder="Please input" disabled/></th>
                    </tr>
                    <tr>
                        <th>联系方式：</th>
                        <th><el-input v-model="form.phone" placeholder="Please input" /></th>
                    </tr>
                    <tr>
                        <th><el-button @click="drawer = false">取消</el-button></th>
                        <th><el-button type="primary" @click="edit">修改</el-button></th>
                    </tr>
                </table>
            </div>
        </el-drawer>
        <el-pagination class="page" :page-size="pageInfo.pageSize" layout="prev, pager, next" :total="pageInfo.total"
            @current-change="currentChange" />
    </div>
</template>
<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { reactive } from '@vue/reactivity'
const router = useRouter()
const input3 = ref('')
const tableData = ref([])
const business = ref({})
const drawer = ref(false)
const pageInfo = reactive({
    //总记录数
    total: 0,
    //当前页码
    page: 1,
    //每页显示数据
    pageSize: 3
})
const form = ref({
    id: '',
    img: '',
    name: '',
    username: '',
    password: '',
    sex: '',
    phone: '',
    address: ''
})
//获取数据
const refresh = () => {
    axios.get('/api/business/', { params: { page: pageInfo.page, pageSize: pageInfo.pageSize } }).then((res) => {
        if (res.data.code == 1) {
            tableData.value = res.data.data.records
            for (const item of tableData.value) {
                const date = item.updateTime
                for (let i = 0; i < date.length; i++) {
                    if (date[i].toString().length == 1) {
                        date[i] = '0' + date[i]
                    }
                }
                const date1 = date[0] + "年" + date[1] + "月" + date[2] + "日" + date[3] + "时" + date[4] + '分' + date[5] + '秒'
                item.updateTime = date1
            }
            pageInfo.total = res.data.data.total
        }
    })
}
refresh()
//分页事件
const currentChange = (val) => {
    pageInfo.page = val
    refresh()

}
//根据店铺名称进行模糊查询
const SearchName = (id) => {
    if (id == '') {
        refresh()
        input3.value = ''
    }
    if (!isNaN(id)) {
        business.value.id = id
        axios.post('/api/business/find', business.value).then((res) => {
            if (res.data.code == 1) {
                tableData.value = res.data.data
            }
        })
        input3.value = ''
    } else {
        business.value.name = id
        axios.post('/api/business/find', business.value).then((res) => {
            if (res.data.code == 1) {
                tableData.value = res.data.data
            }
        })
        input3.value = ''
    }

}
//查看详情信息
const see = (id) => {
    drawer.value = true
    axios.get('/api/business/' + id).then((res) => {
        form.value = res.data.data
    })
}
const seegoods = (id) => {
    router.push({ path: '/adminhome/shopgoods', query: { businessid: id } })
}
//修改
const edit = () => {
    axios.put('/api/business', form.value).then((res) => {
        if (res.data.code == 1) {
            ElMessage({
                message: '编辑成功',
                type: 'success'
            })
        } else {
            ElMessage({
                message: '编辑失败',
                type: 'error'
            })
        }
        refresh()
    })
}

//通过id进行更改状态
const changeStatus = (status, id) => {
    
    axios.put("/api/business", { id: id, status: status }).then((res) => {
        if (res.data.code == 1) {
            axios.put("/api/goods/status",{businessid:id,status: status}).then((res)=>{
                if (status == 1) {
        ElMessage({
            message: '解禁成功',
            type: 'success'
        })
    } else {
        ElMessage({
            message: '封禁成功',
            type: 'error'
        })
    }
            })
        }
        refresh()
    })
}
</script>
<style scoped>
.input {
    float: left;
    margin-top: 20px;
    margin-left: 20px;
}
*{
  font-family: "阿里妈妈方圆体 VF Regular";
}
.page {
    margin-left: 500px;
}
.el-table{
background-image: url('../img/shopbg.jpg');
}
</style>