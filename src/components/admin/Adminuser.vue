<template>
    <div>
        <div style="height: 50px" class="input">
            <span class="check">
                <el-input v-model="input3" placeholder="请输入id或用户名" :prefix-icon="Search" style="width: 200px;" />&nbsp;
                <el-button type="primary" @click="SearchName">查询</el-button>&emsp;
            </span>

        </div>
        <el-table :data="tableData" style="width: 100%">
            <el-table-column fixed prop="name" label="姓名" width="120" />
            <el-table-column prop="img" label="头像" width="120">
                <template #default="scope">
                    <img :src="scope.row.img" style="width: 100px; height: 100px;" />
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="手机号" width="120" />
            <el-table-column prop="sex" label="性别" width="120" />
            <el-table-column prop="address" label="住址" width="200" />
            <el-table-column prop=" status" label="当前状态" width="120">
                <template #default="scope">
                    <el-check-tag checked style="margin-right: 8px" @click="changeStatus(0, scope.row.id)"
                        v-if="scope.row.status == 1" type="success" size="large" hit="ture">正常</el-check-tag>
                    <el-check-tag @click="changeStatus(1, scope.row.id)" v-if="scope.row.status == 0" type='danger'
                        size="large" hit="ture">封禁</el-check-tag>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="200">
                <template #default="scope">
                    <el-button link type="primary" @click="see(scope.row.id)">查看详情</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-drawer v-model="drawer" title="详细信息" :with-header="false">
            <div style="width: 400px;" class="A" modal="false">
                <table wideth="1000px" height="100px" cellspadding="20" align="center">
                    <caption style="color: rgb(5, 5, 211);">详细信息</caption>
                    <tr>
                        <th>编号： </th>
                        <th><el-input v-model="form.id" placeholder="Please input" readonly disabled/></th>
                    </tr>
                    <tr>
                        <th>头像： </th>
                        <th><el-upload class="avatar-uploader" action="/api/photo/user" :on-success="handleAvatarSuccess">
                                <img v-if="form.img" :src="form.img" class="avatar" style="width: 100px; height: 100px;" />
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
                        <th>性别： </th>
                        <th><el-input v-model="form.sex" placeholder="Please input" /></th>
                    </tr>
                    <tr>
                        <th>身份证： </th>
                        <th><el-input v-model="form.idNumber" placeholder="Please input" /></th>
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { reactive } from '@vue/reactivity'
const router = useRouter()
const input3 = ref('')
const tableData = ref([])
const user = ref({})
const drawer = ref(false)

const pageInfo = reactive({
    //总记录数
    total: 0,
    //当前页码
    page: 1,
    //每页显示数据
    pageSize: 2
})
const form = ref({
    id: '',
    img: '',
    name: '',
    username: '',
    password: '',
    sex: '',
    phone: '',
    idNumber: ''
})
const handleAvatarSuccess = (res) => {
    if (res.code == 1) {
        from.img = res.data
    }
}
//获取数据
const refresh = () => {
    axios.get('/api/user', { params: { page: pageInfo.page, pageSize: pageInfo.pageSize } }).then((res) => {
        if (res.data.code == 1) {
            tableData.value = res.data.data.records
            for (const item of tableData.value) {
                const date = item.updateTime
                for (let i = 0; i < date.length; i++) {
                    if (date[i].toString().length == 1) {
                        date[i] = '0' + date[i]
                    }
                }
                const date1 = date[0] + "年" + date[1] + "月" + date[2] + "日" + date[3] + "时" + date[4] + '分' + date[5]+'秒'
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
//进行姓名模糊查询
const SearchName = () => {
    user.value.name = input3.value
    axios.post('/api/user/find', user.value).then((res) => {
        if (res.data.code == 1) {
            ElMessage({
                message: '查询成功',
                type: 'success'
            })
            tableData.value = res.data.data
        } else {
            ElMessage({
                message: '没有相关数据',
                type: 'error'
            })
        }
    })
}
//通过id进行更改状态
const changeStatus = (status,id) => {
    if(status==1){
        ElMessage({
                message: '解禁成功',
                type: 'success'
            })
    }else{
        ElMessage({
                message: '封禁成功',
                type: 'error'    
            })
    }
    axios.put("/api/user" ,{id:id,status:status}).then((res) => {
        if (res.data.code == 1) {
        
        }
        refresh()
    })
}
//查看详情
const see = (id) => {
    drawer.value = true
    axios.get('/api/user/' + id).then((res) => {
        form.value = res.data.data
    })

}
//编辑
const edit = () => {
    console.log(form.value)
    axios.put('/api/user', form.value).then((res) => {
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
</script>
<style scoped>
.input {
    float: right;
    margin-top: 20px;
    margin-right: 20px;
}

.check {
    margin-right: 800px;
}

.page {
    margin-left: 500px;
}
.avatar {
    width: 80px;
    height: 80px;
    display: block;
}

.avatar-uploader-icon {
    width: 160px;
    height: 160px;
    text-align: center;
    line-height: 160px;
}
*{
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>