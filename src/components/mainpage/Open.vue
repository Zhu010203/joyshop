<template>
    <div>
        <div class="O">
            <div>
            <el-carousel trigger="click" height="700px">
                <el-carousel-item v-for="item in 4" :key="item">
                    
                </el-carousel-item>
            </el-carousel>
            </div>
            <el-tag class="ml-2" size="large" type="success" @click="num = 1" @mouseover="num = 1"
                disable-transitions>开放平台理念</el-tag>
            <el-divider direction="vertical" />
            <el-tag class="ml-2" size="large" type="info" @click="num = 2" @mouseover="num = 2">开放平台内容</el-tag>
            <el-divider direction="vertical" />
            <el-tag class="ml-2" size="large" type="warning" @click="num = 3" @mouseover="num = 3">开放平台面向对象</el-tag>
        </div>
        <div class="textbox">
            <div class="text">
                <span
                    v-if="num == 1">&nbsp;&nbsp;&nbsp;悦享购物开放平台旨在开放购物平台积累沉淀的解决方案，满足不同行业不同阶段合作伙伴的个性化需求，降低开发和购买成本，赋能每位合作伙伴，打造生态共赢。</span><br>
            </div>
            <div class="text">
                <span
                    v-if="num == 2">&nbsp;&nbsp;&nbsp;悦享购物开放平台是基于框计算先进技术和服务理念，而提供的开放数据分享暨对接平台。通过该平台，广大站长和开发者可以提交结构化的数据或资源，并在搜索结果中直接展现。</span><br>
            </div>
            <div class="text">
                <span v-if="num == 3">
                    &nbsp;&nbsp;&nbsp;悦享购物开放平台面向第三方应用开发者，提供API接口和相关开发环境的开放平台。
                    软件开发者可通过API来获取悦享用户信息（卖方和卖方用户信息，私有信息需要授权）、
                    悦享商品信息（悦享购物商品的名称、类目、型号、介绍等信息）悦享购物商品类目信息
                    （悦享购物商品索引及分类明细）悦享购物店铺信息（悦享购物店铺信息）、悦享购物商品管理悦享购物商品
                    的上传、编辑、修改等接口）等信息，并建立相应的电子商务应用。同时，作悦享购物开放平台将为开发者提
                    供整套悦享购物API的附加服务：测试环境、技术咨询、产品上架、版本管理、收费策略、市场销售、产品评估等等。
                </span>
            </div>
        </div>

    </div>
</template>
<script setup>
import { ref } from 'vue'

const num = ref(1)

</script>
<style scoped>
.O {
    background-color: rgb(252, 251, 251);
    text-align: center;
}

.text {
    float: left;
    font: 1.5em sans-serif;
}
.textbox {
    float:none;
}
.el-carousel__item:nth-child(2n) {
  background-image: url('../img/open1.jpg');
}

.el-carousel__item:nth-child(2n + 1) {
    background-image: url('../img/open2.jpg');
}
*{
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>