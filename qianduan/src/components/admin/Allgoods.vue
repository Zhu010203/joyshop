<template>
  <div style="background-color:rgba(255, 255, 255, 0.1) ;">
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
      </el-input>
    </div>
    <el-table :data="goodsData" style="width: 1100px" :row-class-name="tableRowClass">
      <el-table-column prop="id" label="商品id" width="180" />
      <el-table-column prop="name" label="商品名称" width="150" />
      <el-table-column prop="type" label="商品类型" width="100" />
      <el-table-column label="商品图片" width="100">
        <template #default="scope">
          <img :src="scope.row.img" class="avatar" />
        </template>
      </el-table-column>
      <el-table-column prop="detail" label="商品详情" width="280" />
      <el-table-column prop="price" label="商品价格" width="100" />
      <el-table-column prop="stock" label="商品库存" width="100" />
      <el-table-column label="商品状态" width="100">
        <template #default="scope">
          <el-check-tag
            checked
            style="margin-right: 8px"
            @click="changeStatus(0, scope.row.id)"
            v-if="scope.row.status == 1"
            type="success"
            size="large"
            hit="ture"
          >在售</el-check-tag>
          <el-check-tag
            @click="changeStatus(1, scope.row.id)"
            v-if="scope.row.status == 0"
            type="danger"
            size="large"
            hit="ture"
          >停售</el-check-tag>
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
  </div>
</template>
<script setup>
import { ref,reactive } from "vue";
import axios from "axios";
import { ElMessage } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";

const route = useRoute();
const router = useRouter();
const outerVisible = ref(false);
const add = ref(false);
const del = ref(false);
const goodsData = ref([{}]);
const type = ref({});
const value = ref(true);
const input = ref("");
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
  getGoods();
};
const tableRowClass = ({ row, column, rowIndex, columnIndex }) => {
  if (row.status == "1") {
    return "success-row";
  } else {
    return "warning-row";
  }
};

const goods2 = ref({});

const findGoods = () => {
  axios.post("/api/goods/find", goods2.value).then(res => {
    if (res.data.code == 1) {
      goodsData.value = res.data.data;
      goodsData.value.forEach(item => {
        axios.get("/api/goods/type/" + item.type).then(res => {
          if (res.data.code == 1) {
            item.type = res.data.data;
          }
        });
      });
    } else {
      ElMessage.error("查询不到商品");
    }
  });
};
const getGoodsBy = id => {
  if (id == "") {
    getGoods();
  }
  if (!isNaN(id)) {
    goods2.value.id = id;
    findGoods();
    input.value = "";
  } else {
    goods2.value.name = id;
    findGoods();
    input.value = "";
  }
};

const getGoods = () => {
  axios
    .get("/api/goods", {
      params: {
        page: pageInfo.page,
        pageSize: pageInfo.pageSize
      }
    })
    .then(res => {
      if (res.data.code == 1) {
        pageInfo.total = res.data.data.total;
        goodsData.value = res.data.data.records;
        goodsData.value.forEach(item => {
          axios.get("/api/goods/type/" + item.type).then(res => {
            if (res.data.code == 1) {
              item.type = res.data.data;
            }
          });
        });
      } else {
        goodsData.value = [];
      }
    });
};
getGoods();

</script>
<style scoped>
.avatar {
  width: 60px;
  height: 60px;
  display: block;
}

.avatar-uploader-icon {
  width: 160px;
  height: 160px;
  text-align: center;
  line-height: 160px;
}

.el-table .warning-row {
  background: rgba(255, 133, 133, 0.537) !important;
}

.el-table .success-row {
  background: rgb(255, 255, 255) !important;
}

* {
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>
