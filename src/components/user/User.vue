<template>
    <div style="background-color:rgba(73, 219, 255, 0.3)">
        <div style="background-color:rgba(63, 157, 181, 0.3)">
            <el-descriptions title="店铺信息">
                <el-descriptions-item label="用户头像"><img :src="user.img" class="avatar"></el-descriptions-item>
                <el-descriptions-item label="用户名"><el-link type="primary" href="#">{{user.name
                }}</el-link></el-descriptions-item>
                <el-descriptions-item label="用户账号">{{ user.username }}</el-descriptions-item>
                <el-descriptions-item label="用户电话"><el-link type="primary" href="#">{{ user.phone
                }}</el-link></el-descriptions-item>
                <el-descriptions-item label="状态">
                    <el-tag size="small" v-if="user.status == 1">正常</el-tag>
                    <el-tag size="small" v-if="user.status == 0">封禁</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="用户地址"><el-link type="primary" href="#">{{ user.address
                }}</el-link></el-descriptions-item>
            </el-descriptions>
        </div>
        <el-button type="primary" @click="outerVisible = true" plain><el-icon>
                <EditPen />
            </el-icon>更改信息</el-button>
        &nbsp;
        <el-dialog v-model="outerVisible" title="更改店铺信息" append-to-body>
            <template #default>
                <table>
                    <tr>
                        <td>用户名称</td>
                        <td><el-input v-model="user.name" type="text" placeholder="请输入店铺名" style="width:300px" /></td>
                    </tr>
                    <tr>
                        <td>用户账号</td>
                        <td><el-input v-model="user.username" type="text" disabled placeholder="请输入店铺名"
                                style="width:300px" /></td>
                    </tr>
                    <tr>
                        <td>用户头像</td>
                        <td><el-upload class="avatar-uploader" action="/api/photo/user"
                                :on-success="handleAvatarSuccess">
                                <img v-if="user.img" :src="user.img" class="avatar" />
                                <el-icon v-else class="avatar-uploader-icon">
                                    <Plus />
                                </el-icon>
                            </el-upload></td>
                    </tr>
                    <tr>
                        <td>用户地址</td>
                        <td> <el-cascader v-model="selectedOptions" :options="options" filterable clearable
                                style="width: 250px" @change="handleChange()" />
                        </td>
                    </tr>
                    <tr>
                        <el-button type="warning" plain @click="outerVisible1 = true"><el-icon>
                                <Tickets />
                            </el-icon>更改密码</el-button>
                    </tr>
                </table>
            </template>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="outerVisible = false">取消</el-button>
                    <el-button type="primary" @click="update">
                        确认更改
                    </el-button>
                </div>
            </template>
        </el-dialog>
        <el-dialog v-model="outerVisible1" title="更改密码">
            <template #default>
                <el-input v-model="pass" placeholder="请输入原来的密码" input-style="width:180px;" @blur="mima"/><br>
                <el-input type="password" v-model="newpass" placeholder="请输入新密码" input-style="width:180px;" /><br>
                <el-input type="password" v-model="newpass1" placeholder="请确认密码" input-style="width:180px;" /><br>
                <el-button type="warning" plain @click="change">确定更改</el-button>
            </template>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="outerVisible1 = false">取消</el-button>
                    <el-button type="primary" @click="update">
                        确认更改
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import md5 from 'js-md5';
import { pcaTextArr,regionData, codeToText } from 'element-china-area-data'

const id = sessionStorage.getItem('userid')
const user = ref({})
const outerVisible = ref(false)
const outerVisible1 = ref(false)
const istrue = ref(false)
const password = ref(null)
const pass = ref(null)
const newpass = ref(null)
const newpass1 = ref(null)
const options = pcaTextArr
const selectedOptions = ref([])
const getUser = () => {
    axios.get('/api/user/' + id).then((res) => {
        if (res.data.code == 1) {
            user.value = res.data.data
            password.value = user.value.password
            selectedOptions.value=user.value.address.split('/')
        }
    })
}
getUser()

const handleChange = () => {
    const province = ref('')
    const city = ref('')
    const country = ref('')
    for (let i = 0; i < selectedOptions.value.length; i++) {
        if (i === 0) { province.value = selectedOptions.value[i] }
        if (i === 1) { city.value = selectedOptions.value[i] }
        if (i === 2) { country.value = selectedOptions.value[i] }
    }
    user.value.address = province.value +"/"+ city.value +"/"+ country.value
}

const handleAvatarSuccess = (res) => {
    if (res.code == 1) {
        business.value.img = res.data
    }
}
const mima = () => {
    if (pass.value == '') {
        ElMessage.error('密码不能为空')
    } else if (md5(pass.value) == user.value.password) {
        istrue.value = true
        ElMessage({ message: '验证成功', type: 'success', })
    } else {
        ElMessage.error('密码不正确请重新输入')
        pass.value = ''
    }
}
const change = () => {
    if (istrue.value == true) {
        if (newpass1.value == '' || newpass.value == '') {
            ElMessage.error('密码不能为空')
        } else if (newpass.value.length > 20) {
            ElMessage.error('密码字符长度必须为1-20位')
            newpass.value = ''
            newpass1.value = ''
        } else if (newpass1.value == newpass.value) {
            business.value.password = newpass.value
            outerVisible1.value = false

        } else {
            ElMessage.error('两次输入密码不同')
            newpass.value = ''
            newpass1.value = ''
        }
    } else {
        ElMessage.error('请先验证')
    }
}
const update = () => {
    if (istrue.value == false) {user.value.password=undefined}
    axios.put('/api/user', user.value).then((res) => {
        if (res.data.code == 1) {
            ElMessage({ message: '更改成功', type: 'success', })
            outerVisible.value = false
        }
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
</style>