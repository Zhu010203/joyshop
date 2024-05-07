<template>
    <div>
    <el-form :model="form" label-width="120px" :disabled="isDisable">
        <el-form-item label="编号：" readonly>
            <el-input v-model="form.id" style="width: 200px;" disabled="true" />
        </el-form-item>
        <el-form-item label="姓名：">
            <el-input v-model="form.name" style="width: 200px;" />
        </el-form-item>
        <el-form-item label="账户：">
            <el-input v-model="form.username" style="width: 200px;" disabled />
        </el-form-item>
        <el-form-item label="头像：">
            <el-upload class="avatar-uploader" action="/api/photo/user" :on-success="handleAvatarSuccess">
                <img v-if="form.img" :src="form.img" class="avatar" style="width: 100px; height: 100px;" />
                <el-icon v-else class="avatar-uploader-icon">
                    <Plus />
                </el-icon>
            </el-upload>
        </el-form-item>
       
        <el-form-item label="当前状态">
            <el-radio-group v-model="form.status">
                <el-radio :label="1" size="large">在线</el-radio>
                <el-radio :label="0" size="large">离线</el-radio>
            </el-radio-group>
        </el-form-item>
    </el-form>
    <el-dialog v-model="dialogFormVisible" title="更改密码">
        <table width="500px" height="100px" align="center">
            <tr>
                <th> 初始密码：</th>
                <th><el-input v-model="password1" style="width: 250px;" placeholder="请输入初始密码" /></th>
            </tr>
            <tr>
                <th>新密码：</th>
                <th><el-input v-model="xinpass1" style="width: 250px;" /></th>
            </tr>
            <tr>
                <th> 确认新密码：</th>
                <th> <el-input v-model="xinpass2" style="width: 250px;" /></th>
            </tr>
        </table>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="queren">
                    确认
                </el-button>
            </span>
        </template>
    </el-dialog>
    <el-button type="primary" @click="edit" class="A">修改信息</el-button>
    <el-button type="primary" @click="back">取消</el-button>
    <el-button type="primary" @click="sub" v-if="isNeedAudit">提交</el-button>
    <el-button type="primary" @click="dialogFormVisible = true" v-if="isNeedAudit">修改密码</el-button>
</div>
</template>
<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import md5 from 'js-md5';
const dialogFormVisible = ref(false)
const userid = sessionStorage.getItem('adminid')
const isDisable = ref(true)
const isNeedAudit = ref(false)
const flag = ref(false)
const form = ref({})
const password1 = ref('')
const xinpass1 = ref('')
const xinpass2 = ref('')
//展示个人信息
const getItem = () => {
    axios.get('/api/admin/' + userid).then((res) => {
        if (res.data.code == 1) {
            form.value = res.data.data    
        }
    })
}
getItem()

//编辑信息
const edit = () => {
    isDisable.value = false
    isNeedAudit.value = true

}
//取消编辑
const back = () => {
    getItem()
    isDisable.value = true
    isNeedAudit.value = false
}
//提交编辑
const sub = () => {
    if (flag.value == false) {
        form.value.password = undefined
    } else { }
    axios.put('/api/admin', form.value).then((res) => {
        if (res.data.code == 1) {
            ElMessage({
                message: '修改成功',
                type: 'success'
            })
        } else {
            ElMessage({
                message: '修改失败',
                type: 'error'
            })
        }
        getItem()
    })
    isDisable.value = true
    isNeedAudit.value = false
}
const queren = () => {
    if (password1.value == '') {
        ElMessage({
            message: '初始密码不能为空',
            type: 'warning'
        })
    } else
        if (md5(password1.value) != form.value.password) {
            ElMessage({
                message: '与初始密码不同',
                type: 'warning'
            })
        } else {
            if (xinpass1.value != xinpass2.value) {
                ElMessage({
                    message: '两次密码不一样',
                    type: 'warning'
                })
            } else {
                form.value.password = xinpass1.value
                flag.value = true
                axios.put('/api/admin', form.value).then((res) => {
                    if (res.data.code == 1) {
                        ElMessage({
                            message: '修改成功',
                            type: 'success'
                        })
                    } else {
                        ElMessage({
                            message: '修改失败',
                            type: 'error'
                        })
                    }
                    getItem()
                    password1.value = '',
                        xinpass1.value = '',
                        xinpass2.value = ''
                    dialogFormVisible.value = false
                })
            }
        }
}
</script>
<style scoped>
.A {
    margin-left: 400px;

}
*{
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>