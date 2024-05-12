<template>
  <div class="A">
    <!--导入图片作为背景图片-->
    <img src="src\assets\img\shoppingregister.jpg" />
    <div class="B">
      <div class="E"></div>
      <div class="D">
        <span>用户注册</span>
        <!--创建一个表单 在表格中写登录输入框-->
        <el-form :model="form" label-width="100px">
          <el-form-item
            label="用户名："
            prop="name"
            :rules="{ required: true, message: '名称不能为空', trigger: 'blur' }"
          >
            <el-input v-model="form.name" style="width: 250px;" />
          </el-form-item>
          <el-form-item
            label="账户："
            prop="username"
            :rules="{ required: true, message: '账号不能为空', trigger: 'blur' }"
          >
            <el-input v-model="form.username" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="密码：">
            <el-input v-model="form.password" style="width: 250px;" placeholder="默认为123456" />
          </el-form-item>
          <el-form-item label="商铺头像">
            <el-upload
              class="avatar-uploader"
              action="/api/photo/user"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
            >
              <img v-if="form.img" :src="form.img" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon">
                <Plus />
              </el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item
            label="性别："
            prop="sex"
            :rules="{ required: true, message: '性别不能为空', trigger: 'blur' }"
          >
            <el-radio-group v-model="form.sex">
              <el-radio label="女" />
              <el-radio label="男" />
            </el-radio-group>
          </el-form-item>
          <el-form-item
            label="身份证："
            prop="idNumber"
            :rules="{ required: true,pattern:/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入正确身份证号', trigger: 'blur' }"
          >
            <el-input v-model="form.idNumber" style="width: 200px;" />
          </el-form-item>
          <el-form-item
            label="联系方式："
            prop="phone"
            :rules="{ required: true, pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' }"
          >
            <el-input v-model="form.phone" style="width: 200px;" />
          </el-form-item>
          <el-form-item label="家庭住址：">
            <!-- <el-input v-model="form.address" style="width: 250px;"/> -->
            <el-cascader v-model="selectvalue" :options="options" @change="handleChange" />
          </el-form-item>
          <el-form-item label="状态：" v-if="isNeedAudit">
            <el-input v-model="form.status" style="width: 250px;" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" style="width: 200px;">注册</el-button>
            <a href="/shoppinglogin">已有账号，去登陆</a>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
  
<script  setup>
import { reactive } from "vue";
import { useRouter } from "vue-router";
import { ref } from "vue";
import { ElMessage } from "element-plus";
import axios from "axios";
import { pcaTextArr } from "element-china-area-data";

const router = useRouter();
const isNeedAudit = ref(false);
const options = pcaTextArr;
const form = reactive({
  name: "",
  username: "",
  password: null,
  img: null,
  idNumber: "",
  sex: "",
  address: "",
  phone: "",
  status: "1"
});

const handleAvatarSuccess = res => {
  if (res.code == 1) {
    form.img = res.data;
  }
};

const selectvalue = ref([]);
const handleChange = () => {
  const province = ref("");
  const city = ref("");
  const country = ref("");
  for (let i = 0; i < selectvalue.value.length; i++) {
    if (i === 0) {
      province.value = selectvalue.value[i];
    }
    if (i === 1) {
      city.value = selectvalue.value[i];
    }
    if (i === 2) {
      country.value = selectvalue.value[i];
    }
  }
  form.address = province.value + "/" + city.value + "/" + country.value;
};
const onSubmit = () => {
  if (
    form.name == "" ||
    form.username == "" ||
    form.idNumber == "" ||
    form.phone == "" ||
    form.address == "" ||
    form.sex == ""
  ) {
    ElMessage({
      type: "error",
      message: "您还有未填项"
    });
  } else {
    axios.post("/api/user/find", { username: form.username }).then(res => {
      if (res.data.code == 1) {
        ElMessage.error("已经存在该用户,请勿重复注册");
        form.name = "";
        form.username = "";
        form.password == null;
        form.idNumber == "";
        form.phone == "";
        form.address == "";
        form.sex == "";
      } else {
        axios.post("/api/user", form).then(res => {
          if (res.data.code == 1) {
            ElMessage({
              type: "success",
              message: "注册成功"
            });
            router.push("/shoppinglogin");
          } else {
            ElMessage({
              type: "error",
              message: "注册失败"
            });
          }
        });
      }
    });
  }
};
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
  background-color: rgb(218, 191, 156, 0.8);
  width: 450px;
  height: 550px;
  margin-top: 150px;
  margin-left: 500px;
}
.D span {
  margin-left: 200px;
  font-size: large;
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
  