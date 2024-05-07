<template>
    <div style="background-color:rgba(233, 251, 255, 0.5);">
        <el-input v-model="input" placeholder="请输入查询的id/管理员名称" input-style="width:200px;"
            :prefix-icon="Search"></el-input>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="primary" @click="getadmin(input)">搜索</el-button>
        <el-button type="primary" plain @click="getsuper(1)">所有管理员</el-button>
        <el-button type="warning" plain @click="addadmin">添加管理员</el-button>
        <el-table :data="adminData" stripe style="br" :default-sort="{ prop: 'id', order: 'ascending' }"
            :row-class-name="tableRowClassName">
            <el-table-column prop="id" label="Id" sortable width="200" />
            <el-table-column prop="name" label="管理员姓名" width="150" />
            <el-table-column prop="username" label="管理员账号" width="150" />
            <el-table-column label="管理员图片" width="150">
                <template #default="scope">
                    <img v-if="scope.row.img" :src="scope.row.img" class="avatar" />
                 </template>
            </el-table-column>
            <el-table-column label="管理员状态" width="200">
                <template #default="scope">
                    <el-tag type="success" effect="dark" v-if="scope.row.status == 1">正常管理员</el-tag>
                    <el-tag type="warning" effect="dark" v-if="scope.row.status == 0">废弃管理员</el-tag>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination v-if="istrue" layout="prev, pager, next" :total="pageInfo.total" :page-size="pageInfo.pageSize"
            @current-change="currentChange" />
    </div>
</template>

<script setup >
import { ref, reactive } from 'vue'
import axios from 'axios'
import { Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { useRouter } from "vue-router";

const router = useRouter()
const input = ref('')
const adminData = ref([])
const admin = ref({})
const istrue=ref(true)
const pageInfo = reactive({
    //总记录数
    total: 0,
    //当前页码
    pageIndex: 1,
    //每页显示数量
    pageSize: 8,

})
//分页事件用来接收当前页码
const currentChange = (val) => {
    pageInfo.pageIndex = val;
    getAll()

}
//查询所有
const getAll = () => {
    axios.get("/api/admin", { params: { page: pageInfo.pageIndex, pageSize: pageInfo.pageSize } }).then((res) => {
        if (res.data.code == 1) {
            adminData.value = res.data.data.records
            pageInfo.total = res.data.data.total
            istrue.value=true
        }
    })
}

getAll()
//根据输入框传入的id查询并渲染到页面,
//隐藏分页按扭
//判断id类型分别进行id,name查询
const getadmin = (id) => {
    
    if (id == '') {
        getAll()
    } else {
        if (!isNaN(id)) {
            axios.get("/api/admin/" + id).then((res) => {
                if (res.data.code == 1) {
                    admin.value = res.data.data
                    adminData.value = []
                    adminData.value.push(admin.value)
                    input.value = ''
                    istrue.value=false
                }else{
                    ElMessage('没有查到数据')
                }
            })
        } else {
            axios.post("/api/admin/find", { name: id }).then((res) => {
                if (res.data.code == 1) {
                    adminData.value = res.data.data
                    input.value = ''
                    istrue.value=false
                } else {
                    ElMessage('没有查到数据')
                    input.value = ''
                }
            })
        }
    }
}
//根据权限查询
const getsuper=(num)=>{
    axios.post("/api/admin/find" , { status : num }).then((res) => {
                if (res.data.code == 1) {
                    adminData.value = res.data.data
                }else{
                    ElMessage('没有查到数据')
                }
            })
}

const addadmin = () => {
    router.push("/adminregister")
}


</script>

<style scoped>
.avatar {
  width: 50px;
  height: 50px;
  display: block;
}
*{
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>