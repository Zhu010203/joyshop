<template>
    <div class="A">
        <!--导入图片作为背景图片-->
        <img src="src\assets\img\businessregister.jpg" />
        <div class="B">
            <div class="E">
            </div>
            <div class="D">
                <span class="X">商铺入驻</span>
                <!--创建一个表单 在表格中写登录输入框-->
                <el-form :model="form" label-width="120px">
                    <el-form-item label="商户名称" prop="name" :rules="{ required: true, message: '名称不能为空', trigger: 'blur' }">
                        <el-input v-model="form.name" style="width: 250px;" />
                    </el-form-item>
                    <el-form-item label="账户" prop="username"
                        :rules="{ required: true, message: '账号不能为空', trigger: 'blur' }">
                        <el-input v-model="form.username" style="width: 250px;" />
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="form.password" type="password" placeholder="默认为123456" style="width: 250px;" />
                    </el-form-item>
                    <el-form-item label="联系方式" prop="phone"
                        :rules="{ required: true, pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' }">
                        <el-input v-model="form.phone" style="width: 250px;" />
                    </el-form-item>
                    <el-form-item label="商户地址" prop="city">
                        <el-cascader v-model="selectedOptions" :options="options" filterable clearable
                            @change="handleChange()" style="width: 250px;" />
                    </el-form-item>
                    <el-form-item label="商铺介绍" prop="detail">
                        <el-input v-model="form.detail" style="width: 250px;" />
                    </el-form-item>
                    <el-form-item label="商铺头像">
                        <el-upload class="avatar-uploader" action="/api/photo/business" :show-file-list="false"
                            :on-success="handleAvatarSuccess">
                            <img v-if="form.img" :src="form.img" class="avatar" />
                            <el-icon v-else class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit" style="width: 200px;">注册</el-button>
                        <a href="/businesslogin">已有账号，去登陆</a>
                    </el-form-item>
                </el-form>

            </div>
        </div>
    </div>
</template>
  
<script  setup>
import { reactive } from 'vue'
import router from '../router'
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { pcaTextArr, codeToText } from 'element-china-area-data'


import { useRouter } from 'vue-router'

const form = reactive({
    name: null,
    username: null,
    password: null,
    phone: null,
    address: null,
    img: null,
    detail: null,
    status: '1'
})
const handleAvatarSuccess = (res) => {
    if (res.code == 1) {
        form.img = res.data
    }
}

const options = pcaTextArr
const selectedOptions = ref([])
const handleChange = () => {
    const province = ref('')
    const city = ref('')
    const country = ref('')
    for (let i = 0; i < selectedOptions.value.length; i++) {
        if (i === 0) { province.value = selectedOptions.value[i] }
        if (i === 1) { city.value = selectedOptions.value[i] }
        if (i === 2) { country.value = selectedOptions.value[i] }
    }
    form.address = province.value + "/" + city.value + "/" + country.value
}


const onSubmit = () => {
    axios.post('/api/business', form).then((res) => {
        if (res.data.code == 1) {
            ElMessage.success('成功入驻,快登录吧')
            router.push('/businesslogin')
        }else{
            ElMessage.error('已经存在该账号,请勿重复注册')
            form.username= null
        }
    })


}
const back = () => {
    router.push('/businesslogin')
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
    width: 1520px;
    height: 300px;
    position: absolute;
}

.D {
    border-radius: 20px;
    background-color: rgba(138, 136, 134, 0.8);
    width: 450px;
    height: 450px;
    margin-top: 200px;
    margin-left: 500px;
}

.D span {
    
    font-size: large;
}
.X{
    margin-left: 200px;
}
.avatar {
    width: 80px;
    height: 80px;
    display: block;
}

.avatar-uploader-icon {
    width: 80px;
    height: 80px;
    text-align: center;
    line-height: 80px;
}
</style>