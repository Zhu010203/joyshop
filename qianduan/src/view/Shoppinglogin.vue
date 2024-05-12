<template>
    <div class="A">
        <!--导入图片作为背景图片-->
<img src="src\assets\img\shoppinglogin.jpg"/>
        <div class="B">
            <div class="E">
                        <el-icon style="font-size: 10px;"><ArrowLeftBold /></el-icon><span class="F" style="font-size: 20px;" @click="back">返回</span>
                        <span class="C">悦享购物平台</span>
                    </div>
            <div class="D">
                <!--创建一个表格 在表格中写登录输入框-->
                <table wideth="1000px" height="200px" cellspadding="20" align="center">
                    <!--表格标题-->
                    <caption style="color: rgb(5, 5, 211);">用户登录</caption>
                    <tr>
                        <th><el-icon>
                                <User />
                            </el-icon></th>
                        <th><el-input v-model="username" type="username" placeholder="请输入账号" />
                        </th>
                    </tr>
                    <tr>
                        <th><el-icon>
                                <Unlock />
                            </el-icon></th>
                        <th><el-input v-model="password" type="password" placeholder="请输入密码" show-password style="width: 200px;" @keyup.enter.native="login()"/>
                        </th>
                    </tr>
                    <tr>
                        <th></th>
                        <th><el-button type="primary" style="width: 100%;" @click="login" :loading="isloading"  
                                :disabled="isDisable">登录</el-button></th>
                    </tr>
                    <tr>
                        <th></th>
                        <th><el-button type="success" style="width: 100%;" @click="register"
                                :disabled="isDisable">免费注册</el-button></th>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</template>
<script setup>
import { useRouter } from 'vue-router'
import { ref } from "vue"
import { ElMessage } from 'element-plus'
import axios from 'axios';
const count = ref('5')
const router = useRouter()
const username = ref("")
const password = ref("")
const isDisable = ref(false)
const isloading = sessionStorage.getItem('userid')
const login = () => {
    axios.get('/api/user/login', { params: { username: username.value, password: password.value } }).then((res) => {
        if (username.value == '' || password.value == '') {
            ElMessage({
                message: '账号或密码不能为空',
                type: 'warning'
            })
        } else if (res.data.code == 1) {
            if (res.data.data.status == 1) {
            sessionStorage.setItem('userid', res.data.data.id)
            sessionStorage.setItem('username', res.data.data.name)
            sessionStorage.setItem('token', res.data.data.id)
            router.push('/shoppinghome/typegoods')
            //判断密码是否错误 如果错误弹框并提示剩余次数
            }else{ ElMessage.error('账号已被封禁,请联系管理员')}
        } else if (res.data.code == 0) {
            count.value--
            username.value = ''
            password.value = ''
            ElMessage.error('账号或密码错误，还剩' + count.value + '次')
            //当次数为零 禁用按钮
            if (count.value == 0) {
                isDisable.value = true
            }
        }
    })
}
const register = () => {
router.push('/shoppingregister')
}
const back=()=>{
    router.push('/')
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
.C {
    font-family: '阿里妈妈方圆体 VF Regular';
    font-size: 40px;
    line-height: 100px;
    margin-left:520px;
    
}
.E{
    width: 100%;
    height: 100px;
    background-color:rgba(252, 187, 201, 0.8);
    position: fixed;
   
}
.el-icon{
    margin-left: 30px;
    cursor: pointer;
}
.B {
    width: 1520px;
    height: 300px;
    position: absolute;


}

.D {
    border-radius: 20px;
    width: 350px;
    height: 350px;
    background-color:rgba(247, 231, 234, 0.8);
    float: right;
    margin-top: 250px;
    margin-right: 600px;
   
}

.D table {
    margin-top: 50px;
    margin-left: 30px;
    opacity: 1;
}
.F{
    font-family: '阿里妈妈方圆体 VF Regular';
    cursor: pointer;
}
.F :hover{
    transform: scale(2);
}
</style>