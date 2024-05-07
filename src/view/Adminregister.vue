<template>
    <div class="A">
        <!--导入图片作为背景图片-->

<img src="src\assets\img\adminregister.webp"/>
        <div class="B">
            <div class="D">
                <div class="C">
                    <el-icon class="E" style="font-size: 10px;"><ArrowLeft /></el-icon><span  @click="back" class="F" >返回</span>
                   </div>
                <!--创建一个表格 在表格中写登录输入框-->
                <table wideth="1000px" height="250px" cellspadding="30" align="center">
                    <!--表格标题-->
                   
                    <tr>
                        <th><el-icon>
                                <Document />
                            </el-icon></th>
                        <th><el-input v-model="reg.name" type="text" placeholder="请输入管理员名称" />
                        </th>
                    </tr>
                    <tr>
                        <th><el-icon>
                                <User />
                            </el-icon></th>
                        <th><el-input v-model="reg.username" type="username" placeholder="请输入登录账号" />
                        </th>
                    </tr>
                    <tr>
                        <th><el-icon>
                                <Unlock />
                            </el-icon></th>
                        <th><el-input v-model="reg.password" type="password" placeholder="请输入密码(默认为123456)" show-password />
                        </th>
                    </tr>
                    <tr>
                        <th><el-icon>
                                <Unlock />
                            </el-icon></th>
                        <th><el-input v-model="reg.password1" placeholder="请再次输入密码" show-password></el-input></th>
                    </tr>
                    <tr>
                        <th></th>
                        <th><el-input v-model="reg.status" v-if="isNeedAudit"></el-input></th>
                    </tr>
                    <tr>
                        <th></th>
                        <th><el-button type="success" style="width: 100%;" @click="register">注册</el-button></th>
                    </tr>
                    <tr>
                        <th></th>
                        <th><a href="/adminlogin">已有账号，去登陆</a></th>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</template>
<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
const router=useRouter()
const isNeedAudit=ref(false)
const reg = ref({
    name: '',
    username: '',
    password: null,
    password1: null,
    status:1
})
//注册管理员
const register = () => {
    axios.post('/api/admin/find',{username:reg.username}).then((res)=>{
        if(res.data.code==1){
            ElMessage.error('已经存在该管理员,请勿重复注册')
            reg.value.name=''
            reg.value.username=''
            reg.value.password= ''
            reg.value.password1=''
        }else{
    if (reg.value.password != reg.value.password1) {
        ElMessage({
            message: '密码不一致',
            type: 'error'
        })
    } else {
        axios.post("/api/admin", reg.value).then((res) => {
            if (res.data.code == 1) {
                ElMessage({
                    message: '注册成功',
                    type: 'success'
                })
                back()
            } else {
                ElMessage({
                    message: '注册失败',
                    type: 'error'
                })
            }
        })
    }
    }
})
}
//返回adminhome页面
const back=()=>{
    router.push('/adminhome')
}
</script>
<style scoped>
.A {
    width: 100%;
    height: 100%;
    margin: -8px;
    position: relative;
    display: flex;
    align-items: stretch;

}

.B {
    width: 1250px;
    height: 300px;
    position: absolute;
    opacity: 1;
    margin-top: 100px;


}



.D {
    border-radius: 20px;
    width: 400px;
    height: 400px;
    background-color: rgb(237, 241, 245,0.8);
    float: right;
    margin-top: 100px;
    margin-right: 300px;

}

.D table {
    margin-top: 50px;
}
.C{
    margin-top: 10px;
    margin-left: 10px;
}
.E{
    margin-left: 20px;
    margin-top: 10px;
}
.F{
    cursor: pointer;
}
*{
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>