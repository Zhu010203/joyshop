<template>
  <div style="background-color:rgba(255, 255, 255, 0.2) ;">
    <div class="mt-4">
      <el-input
        v-model="input"
        placeholder="请输入商品id/名称"
        style="width: 300px;"
        @keyup.enter.native="getGoodsBy(input)"
      >
        <template #append>
          <el-button :icon="Search" plain @click="getGoodsBy(input)" />
        </template>
      </el-input>&nbsp;&nbsp;&nbsp;&nbsp;
      <el-button type="warning" plain @click="add">添加类别</el-button>
    </div>
    <el-table :data="typeData" border style="width: 1000px">
      <el-table-column prop="id" label="类别id" width="200" />
      <el-table-column prop="name" label="类别名称" width="200" />
      <el-table-column label="类别图片" width="200">
        <template #default="scope">
          <img :src="scope.row.img" class="avatar" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template #default="scope">
            <el-button  plain @click="edit(scope.row)" >更改信息</el-button>
            <el-button type="danger" plain @click="del(scope.row.id)" >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :page-size="pageInfo.pageSize"
      :pager-count="pageInfo.page"
      layout="prev, pager, next"
      :total="pageInfo.total"
      @current-change="currentChange"
    />
    <el-drawer v-model="drawer" title="添加类别" :with-header="false">
            <div style="width: 400px;" class="A" modal="false">
                <table wideth="1000px" height="100px" cellspadding="20" align="center">
                    <tr>
                        <th>名称 </th>
                        <th><el-input v-model="type1.name" placeholder="Please input" /></th>
                    </tr>
                    <tr>
                        <th>头像： </th>
                        <th><el-upload class="avatar-uploader" action="/api/photo/type" :on-success="handleAvatarSuccess">
                                <img v-if="type1.img" :src="type1.img" class="avatar" style="width: 100px; height: 100px;" />
                                <el-icon v-else class="avatar-uploader-icon">
                                    <Plus />
                                </el-icon>
                            </el-upload></th>
                    </tr>
                   
                    <tr>
                        <th><el-button @click="drawer = false">取消</el-button></th>
                        <th v-if="issave"><el-button type="warning" @click="addtype">添加</el-button></th>
                        <th v-if="!issave"><el-button type="primary" @click="update">更改</el-button></th>
                    </tr>
                </table>
            </div>
        </el-drawer>
  </div>
</template>
<script setup>
import { ref, reactive } from "vue";
import axios from "axios";
import { ElMessage , ElMessageBox } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";

const route = useRoute();
const router = useRouter();
const typeData = ref([]);
const input = ref("");
const issave =ref(true)
const drawer=ref(false)
const pageInfo = reactive({
  //总记录数
  total: 0,
  //当前页码
  page: 1,
  //每页显示数据
  pageSize: 5
});
const currentChange = val => {
  pageInfo.page = val;
  getType();
};
const getType = () => {
  axios
    .get("/api/goods/type", {
      params: {
        page: pageInfo.page,
        pageSize: pageInfo.pageSize
      }
    })
    .then(res => {
      if (res.data.code == 1) {
        typeData.value = res.data.data.records;
        pageInfo.total = res.data.data.total;
      }
    });
};
getType();
const type1=reactive({
    id:'',
    name:'',
    img:''
})
const handleAvatarSuccess = (res) => {
    if (res.code == 1) {
        type1.value.img = res.data
    }
}
const type=ref({})
const getGoodsBy = id => {
  if (id == "") {
    getType();
  }
  if (!isNaN(id)) {
    type.value.id = id;
    findtype();
    input.value = "";
  } else {
    type.value.name = id;
    findtype();
    input.value = "";
  }
};
const findtype=()=>{
    axios.post('/api/goods/find/type',type.value).then((res)=>{
        if(res.data.code==1){
            typeData.value=res.data.data
        }else{ElMessage.warning("没有查询到相关数据")}
    })
}
const edit=(row)=>{
    drawer.value=true
    type1.id=row.id
    type1.name=row.name
    type1.img=row.img
    issave.value=false
}
const add=()=>{
    type1.name=''
    type1.img=''
    drawer.value=true
    issave.value=true
}
const addtype=()=>{
    if(type1.id==''){type1.id=undefined}
    if(type1.img==''){type1.img=undefined}
    console.log(type1.value)
    axios.post('/api/goods/type',type1).then((res)=>{
        if(res.data.code==1){
            ElMessage.success("添加成功")
            drawer.value=false
            getType();
        }else{ElMessage.error("添加失败")}
    })
}
const update=()=>{
    axios.put('/api/goods/type',type1).then((res)=>{
        if(res.data.code==1){
            ElMessage.success("更改成功")
            drawer.value=false
            getType();
        }else{ElMessage.error("更改失败")}
    })
}
const del=(id)=>{
    ElMessageBox.confirm(
    '您将会删除该类别，是否继续',
    'Warning',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
        axios.delete('/api/goods/type/'+id).then((res)=>{
        if(res.data.code==1){
            ElMessage.success("删除成功")
            drawer.value=false
            getType();
        }else{ElMessage.error("删除失败")}
    })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}
</script>
<style scoped>
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