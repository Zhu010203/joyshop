<template>
  <div class="O">
    <div class="R">&emsp;反馈通道</div>
    <div class="T">
      <table style="width: 400px;height: 30px; color: #000;">
        <tr>
          <th>客服电话：</th>
          <th>9555-12138</th>
          <th>客服微信：</th>
          <th>悦享购物</th>
        </tr>
      </table>
      <div class="U">
        客服不受理商务合作事宜，提交商务合作信息请点击进入<a href="/businesslogin">我要开店</a>，在线提交合作信息
      </div>

    </div>
    <hr style="width: 800px;" />
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="120px" class="demo-ruleForm" :size="formSize"
      status-icon>
      <el-form-item label="用户名：" prop="name" style="width: 600px;">
        <el-input v-model="ruleForm.name" />
      </el-form-item>
      <el-form-item label="投诉店铺：" prop="shopname" style="width: 600px;">
        <el-input v-model="ruleForm.name" />
      </el-form-item>
      <el-form-item label="购买商品：" prop="productname" style="width: 600px;">
        <el-input v-model="ruleForm.name" />
      </el-form-item>
      <el-form-item label="下单时间：" required>
        <el-col :span="11">
          <el-form-item prop="date1">
            <el-date-picker v-model="ruleForm.date1" type="date" label="Pick a date" placeholder="请选择日期"
              style="width: 100%" />
          </el-form-item>
        </el-col>
        <el-col class="text-center" :span="2">
          <span class="text-gray-500">-</span>
        </el-col>
        <el-col :span="11">
          <el-form-item prop="date2">
            <el-time-picker v-model="ruleForm.date2" label="Pick a time" placeholder="请选择日期" style="width: 100%" />
          </el-form-item>
        </el-col>
      </el-form-item>
      <el-form-item label="投诉原因：" prop="type">
        <el-checkbox-group v-model="ruleForm.type">
          <el-checkbox label="商品质量差" name="type" />
          <el-checkbox label="服务态度不好" name="type" />
          <el-checkbox label="价格不合适" name="type" />
          <el-checkbox label="店铺违规" name="type" />
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="是否实名：" prop="resource">
        <el-radio-group v-model="ruleForm.resource">
          <el-radio label="是" />
          <el-radio label="否" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="投诉详情：" prop="desc">
        <el-input v-model="ruleForm.desc" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(ruleFormRef)" style="width: 100px;">
          提交
        </el-button>

      </el-form-item>
    </el-form>
    <div class="W">
      为了您能更好的接收到我们对您反馈的回复，我们建议您
      <el-button type="primary" @click="login" style="width: 100px;">
        登录
      </el-button>
      <el-button type="primary" @click="register" style="width: 100px;">
        注册悦享购物
      </el-button>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
const router = useRouter()
const login = () => {
  router.push('/shoppinglogin')
}
const register = () => {
  router.push('/shoppingregister')
}
interface RuleForm {
  name: string
  shopname: string
  productname: string
  date1: string
  date2: string

  type: string[]
  resource: string
  desc: string
}

const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  name: '',
  shopname: '',
  productname: '',
  date1: '',
  date2: '',

  type: [],
  resource: '',
  desc: '',
})
const rules = reactive<FormRules<RuleForm>>({
  name: [
    { required: true, message: '请输入您的用户名', trigger: 'blur' },
    { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
  ],
  shopname: [
    {
      required: true,
      message: '请输入购买商品的店铺名',
      trigger: 'change',
    },
  ],
  productname: [
    {
      required: true,
      message: '请输入购买的商品名',
      trigger: 'change',
    },
  ],
  date1: [
    {
      type: 'date',
      required: true,
      message: '请选择日期',
      trigger: 'change',
    },
  ],
  date2: [
    {
      type: 'date',
      required: true,
      message: '请选择日期',
      trigger: 'change',
    },
  ],
  type: [
    {
      type: 'array',
      required: true,
      message: '请选择投诉原因',
      trigger: 'change',
    },
  ],
  resource: [
    {
      required: true,
      message: '请选择是否要实名',
      trigger: 'change',
    },
  ],
  desc: [
    { required: true, message: '请输入投诉详情', trigger: 'blur' },
  ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      ElMessage({
        type: 'success',
        message: '提交成功',

      })
    } else {
      ElMessage({
        type: 'error',
        message: '提交失败，您有未填写项',

      })
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
<style scoped>
.O {
  margin-top: 50px;
  width: 900px;
  height: 1200px;
  margin-left: 280px;
  position: absolute;
  box-shadow: 0px -0.2px 0px 0px #161616,
    /*上边阴影 */

    -0.2px 0px 0px 0px #121212,
    /*左边阴影  */

    1px 0px 0px 0px #0f0e0e,
    /*右边阴影 */

    0px 1px 0px 0px #0f0f0f;
  /*下边阴影 */

}

.R {
  height: 30PX;
  width: 800PX;
  line-height: 30PX;
  background-color: pink;
  margin-top: 10PX;
  margin-left: 50px;
  text-align: center;
}

.T {
  margin-left: 50px;
  margin-top: 10PX;
}

.U {
  margin-top: 10PX;
  margin-left: 10px;
}

.el-form {
  margin-top: 50px
}

.W {
  margin-left: 80px;
  margin-top: 200PX;
}
*{
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>