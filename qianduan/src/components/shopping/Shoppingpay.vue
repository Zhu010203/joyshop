<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <div class="A">
          <img src="src\assets\img\zhifubao.png" />
          <el-divider direction="vertical" class="E" />
        </div>
        <div class="B">我的收银台</div>
      </el-header>
      <el-main>
        <el-divider class="F" />
        <div class="C">
          正在使用支付宝交易
          <br />收款方：悦享购物平台
        </div>
        <div class="D">
          <el-popover
            placement="top-start"
            title="提示"
            :width="200"
            trigger="hover"
            content="打开支付宝扫一扫功能"
          >
            <template #reference>
              <div class="G" onmouseover>
                <img src="src\assets\img\erweima.png" />
              </div>
            </template>
          </el-popover>
          <div class="H">
            扫码支付{{ money }}（元）
            <br />
            支付方式:{{ paymethod }}
          </div>
          <el-button type="sucess" @click="back()">取消支付</el-button>&nbsp;
          <el-button type="sucess" @click="pay()">我已支付，点击继续</el-button>
          <br />&nbsp;&nbsp;
          <div style="font-size: 14px;color: #ff0000;  text-align: center;">剩余支付时间（超时自动关闭）</div>&nbsp;
          <div style="font-size: 14px;color: #ff0000;  text-align: center;" class="time">
            <i class="el-icon-time"></i>
            {{ min }}分钟 {{ sec }}秒
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>
import { ref } from "vue";
import axios from "axios";
import { ElMessage, inputEmits } from "element-plus";
import { ElNotification } from "element-plus";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";
const route = useRoute();
const router = useRouter();
const userid = sessionStorage.getItem("userid");
const ispay = ref(false);
const money = route.query.money;
const num = route.query.num;
const goodsid = route.query.goodsid;
const paymethod = route.query.paymethod;
const backetgoods1 = ref([]);
const order = ref({});
 
const setMessage = (selfid, goodsid) => {
  axios.get("/api/goods/" + goodsid).then(res => {
    if (res.data.code == 1) {
      const goods = res.data.data;
      const message = {};
      message.sender = selfid;
      message.receive = goods.businessid;
      const msg = "我购买了<" + goods.name + ">请尽快发货";
      message.message = msg;
      console.log(message);
      axios.post("/api/message", message).then(res => {
        if (res.data.code == 1) {
        }
      });
    }
  });
};
const pay = () => {
  if (paymethod == "payone") {
    payOne();
  }
  if (paymethod == "payall") {
    payAll();
  }
};

const payOne = () => {
  ispay.value = true;
  order.value = {};
  order.value.userid = userid;
  order.value.pay = 1;
  order.value.post = 0;
  order.value.userGoods = [{ goodsid: goodsid, num: num }];
  axios
    .get("/api/basket/goods", { params: { goodsid: goodsid, userid: userid } })
    .then(res => {
      if (res.data.code == 1) {
        axios.post("/api/order", order.value).then(res => {
          if (res.data.code == 1) {
            setMessage(userid, goodsid);
            router.push({
              path: "/shoppinghome/typegoods",
              query: { ispay1: ispay.value }
            });
            ElMessage.success("付款成功,商家会尽快发货");
          }
        });
      }
    });
};

const payAll = () => {
  ispay.value = true;
  axios.get("/api/basket/user/" + userid).then(res => {
    if (res.data.code == 1) {
      order.value = {};
      order.value.userid = userid;
      order.value.pay = 1;
      order.value.post = 0;
      backetgoods1.value = res.data.data;
      const usergoods = [];
      backetgoods1.value.forEach(item => {
        setMessage(userid, item.goodsid);
        usergoods.push({ goodsid: item.goodsid, num: item.num });
      });
      order.value.userGoods = usergoods;
      axios.delete("/api/basket/user/" + userid).then(res => {
        if (res.data.code == 1) {
          axios.post("/api/order", order.value).then(res => {
            if (res.data.code == 1) {
              router.push({
                path: "/shoppinghome/typegoods",
                query: { ispay1: ispay.value }
              });
              ElMessage.success("付款成功,商家会尽快发货");
            }
          });
        }
      });
    }else{
          ElMessage.error("购物篮不存在商品请重试")
        }
  });
};
const end = Date.parse(new Date()) + 15 * 60 * 1000;
const min = ref(0);
const sec = ref(0);
const msec = ref(15 * 60 * 1000);
const getTime = () => {
  const now = Date.parse(new Date());
  msec.value = end - now;
  if (msec.value != 0) {
    min.value = parseInt((msec.value / 1000 / 60) % 60);
    sec.value = parseInt((msec.value / 1000) % 60);
  } else {
    back();
  }
};

setInterval(() => {
  getTime();
}, "1000");

const back = () => {
  router.push({ path: "/shoppinghome/typegoods" });
  ElMessage.warning("已经取消支付");
};
</script>
<style scoped>
.el-header {
  width: 100%;
  height: 50px;
  background-color: rgb(192, 192, 186);
}

.el-main {
  width: 100%;
  height: 500px;
  background-color: gray;
}

.A {
  float: left;
  margin-left: 300px;
}

.E {
  height: 50px;
  vertical-align: baseline;
  background-color: #6c6969;
}

.F {
  margin-top: 0px;
  background-color: #5dbeff;
}

.B {
  margin-top: 25px;
  color: #000;
}

.C {
  width: 160px;
  margin-left: 450px;
}

.D {
  width: 700px;
  height: 400px;
  background-color: #ffffff;
  margin: auto;
}

.G {
  margin-left: 230px;
}

.H {
  margin-left: 280px;
}

.el-button {
  margin-left: 210px;
}
</style>
