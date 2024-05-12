<template>
  <div>
    <router-link to="/shoppinghome/typegoods">
      <el-button type="primary" plain>
        <el-icon>
          <Tickets />
        </el-icon>返回主页
      </el-button>
    </router-link>
    <div class="a">
      <div class="b">
        <img class="avatar" :src="goods.img" />
        <h2>{{ goods.name }}</h2>店铺详情:
        <el-link href="#" @click="gotobusiness" type="primary">{{ business.name }}</el-link>
      </div>
      <el-card class="card">
        <div>商品名称:{{ goods.name }}</div>
        <br />
        <div>商品类型:{{ goods.type }}</div>
        <br />
        <div>商品介绍:{{ goods.detail }}</div>
        <br />
        <div>商品价格:{{ goods.price }}</div>
        <br />
        <div>商品库存:{{ goods.stock }}</div>
        <br />
        <div>
          商品状态:
          <el-tag size="small" v-if="goods.status == 1">正常</el-tag>
          <el-tag size="small" type="danger" v-if="goods.status == 0">停售</el-tag>
        </div>
        <br />
      </el-card>
    </div>
    <hr />
    <h2>商品评论</h2>
    <div v-if="commentData.length==0">
      <h3>此商品暂无评论</h3>
    </div>
    <div class="usercomment" v-for="comment in commentData" :key="comment.id">
      <div class="commentdiv">
        <div class="leftuser">
          <el-tag size="small" type="warning" v-if="comment.usertype == '用户'">{{ comment.usertype }}</el-tag>
          <el-tag size="small" v-if="comment.usertype == '商家'">{{ comment.usertype }}</el-tag>
          <el-tag size="small" type="danger" v-if="comment.usertype == '封禁'">{{ comment.usertype }}</el-tag>
          <img :src="comment.userimg" class="avatar1" />
          <span>{{ comment.username }}</span>
        </div>
        <div class="commenttext">&nbsp;&nbsp;{{ comment.comment }}</div>
        <div style="float: right;">
          <span style="font-size: 0.3em;  float: right;">{{ comment.createTime }}</span>
          <el-button
            size="small"
            type="warning"
            :icon="Edit"
            v-if="comment.isuser==1"
            @click="update(comment.id,comment.comment,comment.score)"
          />
          <el-button
            size="small"
            type="danger"
            :icon="Delete"
            v-if="comment.isuser==1"
            @click="del(comment.id)"
          />
          <el-rate v-model="comment.score" disabled />
        </div>
      </div>
    </div>
    <el-pagination
      :page-size="pageInfo.pageSize"
      :pager-count="pageInfo.page"
      layout="prev, pager, next"
      :total="pageInfo.total"
      @current-change="currentChange"
    />
    <div class="comment">
      <el-input
        v-model="commentinput"
        maxlength="50"
        placeholder="我要评论,回车键进行评论"
        show-word-limit
        @keyup.enter.native="sender(commentinput)"
        type="textarea"
      />请评分:
      <el-rate v-model="score" />
    </div>
    <el-dialog v-model="edit" title="更改评论">
      <el-input
        v-model="updateinput"
        maxlength="50"
        placeholder="请输入评论"
        show-word-limit
        type="textarea"
      />请打分:
      <el-rate v-model="updatescore" />
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="edit = false">取消更改</el-button>
          <el-button type="primary" @click="editcomment()">确定更改</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive } from "vue";
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";
import { ElNotification } from "element-plus";
import { Delete, Edit } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";

const route = useRoute();
const router = useRouter();
const userid = sessionStorage.getItem("userid");
const id = route.query.goodsid;
const goods = ref({});
const business = ref({});
const commentinput = ref("");
const edit = ref(false);
const updatescore = ref(0);
const score = ref(5);
const pageInfo = reactive({
  //总记录数
  total: 0,
  //当前页码
  page: 1,
  //每页显示数据
  pageSize: 2
});
const currentChange = val => {
  pageInfo.page = val;
  refresh();
};
const commentData = ref([]);
//获取comment
const refresh = () => {
    commentData.value=[]
  axios
    .get("/api/comment", {
      params: {
        page: pageInfo.page,
        pageSize: pageInfo.pageSize,
        goodsid: goods.value.id
      }
    })
    .then(res => {
      if (res.data.code == 1) {
        pageInfo.total = res.data.data.total;
        for (const item of res.data.data.records) {
          const message = item;
          const date = item.createTime;
          for (let i = 0; i < date.length; i++) {
            if (date[i].toString().length == 1) {
              date[i] = "0" + date[i];
            }
          }
          const date1 =
            date[0] +
            "年" +
            date[1] +
            "月" +
            date[2] +
            "日" +
            date[3] +
            ":" +
            date[4] +
            ":" +
            date[5];
          message.createTime = date1;
          commentData.value.push(message);
        }
        getuser();
      }
    });
};
const getuser = () => {
  for (const comment of commentData.value) {
    axios.get("/api/user/" + comment.createUser).then(res => {
      if (res.data.code == 1) {
        const item = res.data.data;
        if (item.id == userid) {
          comment.isuser = 1;
        } else {
          comment.isuser = 0;
        }
        comment.username = item.name;
        comment.userimg = item.img;
        if (item.status == 1) {
          comment.usertype = "用户";
        } else {
          comment.usertype = "封禁";
        }
      }
    });
    axios.get("/api/business/" + comment.createUser).then(res => {
      if (res.data.code == 1) {
        const item = res.data.data;
        if (item.id == userid) {
          comment.isuser = 1;
        } else {
          comment.isuser = 0;
        }
        comment.username = item.name;
        comment.userimg = item.img;
        if (item.status == 1) {
          comment.usertype = "商家";
        } else {
          comment.usertype = "封禁";
        }
      }
    });
  }
  console.log(commentData.value);
};
const getgoods = () => {
  axios.get("/api/goods/" + id).then(res => {
    if (res.data.code == 1) {
      goods.value = res.data.data;
      axios.get("/api/goods/type/" + goods.value.type).then(res => {
        if (res.data.code == 1) {
          goods.value.type = res.data.data;
          axios.get("/api/business/" + goods.value.businessid).then(res => {
            if (res.data.code == 1) {
              business.value = res.data.data;
              refresh();
            }
          });
        }
      });
    }
  });
};
const gotobusiness = () => {
  router.push({ path: "/businessdetail", query: { goodsid: goods.value.id } });
};
getgoods();

const sender = commentinput1 => {
  const comment = {};
  comment.comment = commentinput1;
  comment.goodsid = id;
  comment.score = score.value;
  comment.createUser = userid;
  axios.post("/api/comment", comment).then(res => {
    if (res.data.code == 1) {
      ElMessage.success("评论成功");
      commentinput.value = "";
    } else {
      ElMessage.error("评论失败,请检查网络");
      commentinput.value = "";
    }
  });
};
const commentid1 = ref("");
const updateinput = ref("");
const update = (commentid, updateinput1, updatescore1) => {
  edit.value = true;
  commentid1.value = commentid;
  updateinput.value = updateinput1;
  updatescore.value = updatescore1;
};
const editcomment = () => {
  const comment = {};
  comment.id = commentid1.value;
  comment.score = updatescore.value;
  comment.comment = updateinput.value;
  axios.put("/api/comment", comment).then(res => {
    if (res.data.code == 1) {
      updateinput.value = "";
      ElMessage.success("更改成功");
      edit.value = false;
      refresh();
    } else {
      ElMessage.error("更改失败");
    }
  });
};
const del = commentid => {
  ElMessageBox.confirm("您将会删除该评论，是否继续", "Warning", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  })
    .then(() => {
      axios.delete("/api/comment/" + commentid).then(res => {
        if (res.data.code == 1) {
          ElMessage({
            type: "success",
            message: "删除成功"
          });
          refresh();
        } else {
          ElMessage({
            type: "danger",
            message: "删除失败"
          });
        }
      });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消删除"
      });
    });
};
</script>
<style scoped>
.a {
  width: auto;
  height: 300px;
}
.b {
  float: left;
  width: 300px;
  height: 300px;
}
.card {
  float: right;
  width: 1200px;
  height: 300px;
}
.avatar {
  width: 150px;
  height: 150px;
}
.avatar1 {
  width: 50px;
  height: 50px;
}
.comment {
  width: 800px;
  height: 200px;
}
.commentdiv {
  width: 800px;
  height: 50px;
}
.commenttext {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 500px;
  height: 50px;
}
.usercomment {
  width: 800px;
  height: 100px;
}
.leftuser {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 200px;
  height: 50px;
  float: left;
}

* {
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>