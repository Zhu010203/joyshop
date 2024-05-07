<template>
  <div>
    <div class="D">
      <div class="F">
        <img src="../img/bg.jpg" />
      </div>
      <div class="E">
        <div class="X">
          <a
            href="/businesslogin"
            style=" width: 250px;height: 200px;line-height: 200px;text-align: center;"
          >
            <font style="font-size: 30px;">
              <i class="iconfonts icon-fangzi1"></i> &nbsp;我要入驻
            </font>
          </a>
        </div>
        <div class="Y">
          <a
            href="/adminlogin"
            style=" width: 250px;height: 200px;line-height: 200px;text-align: center;"
          >
            <font style="font-size: 30px;">
              <i class="iconfonts icon-shezhichilun"></i> &nbsp;开发者接入
            </font>
          </a>
        </div>
        <div class="Z">
          <a
            href="/shoppinglogin"
            style=" width: 250px;height: 200px;line-height: 200px;text-align: center;"
          >
            <font style="font-size: 30px;">
              <i class="iconfonts icon-gouwuche"></i> &nbsp;我要购物
            </font>
          </a>
        </div>
      </div>
    </div>
    <div class="M">
      <div class="common-layout">
        <el-container>
          <el-header style="width: 100%;height: 150px;">
            <div>
              <div class="Q">服务介绍</div>
              <div class="P">Service</div>
            </div>
          </el-header>
          <el-container>
            <el-aside style="width: 700px;height: 500px;">
              <div class="box" @mouseenter="handleMouseEnter(1)">
                <svg class="iconfont" aria-hidden="true">
                  <use xlink:href="#icon-meishi" />
                </svg>&nbsp;美食Delicacy
              </div>

              <div class="box" @mouseenter="handleMouseEnter(2)">
                <svg class="iconfont" aria-hidden="true">
                  <use xlink:href="#icon-maoyi" />
                </svg>&nbsp;潮牌衣物Fashionable clothes
              </div>
              <div class="box" @mouseenter="handleMouseEnter(3)">
                <svg class="iconfont" aria-hidden="true">
                  <use xlink:href="#icon-tianniu_shuiguoshengxian" />
                </svg>&nbsp;水果生鲜Fresh food
              </div>
              <div class="box" @mouseenter="handleMouseEnter(4)">
                <svg class="iconfont" aria-hidden="true">
                  <use xlink:href="#icon-jiayongdianqi" />
                </svg>&nbsp;家用电器Home appliances
              </div>
              <div class="box" @mouseenter="handleMouseEnter(5)">
                <svg class="iconfont" aria-hidden="true">
                  <use xlink:href="#icon-shuma" />
                </svg>&nbsp;数码潮玩Digital Tide Play
              </div>
            </el-aside>
            <el-main style="width: 800px;height: 500px;">
              <img :src="imgUrl" class="iphoneinsade" />
              <img class="iphone" src="../img/phone.png" />
            </el-main>
          </el-container>
        </el-container>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from "vue";

const imgUrl = ref("/src/components/img/food.jpg");

const handleMouseEnter = id => {
  if (id == 1) {
    imgUrl.value = "/src/components/img/food.jpg";
  }
  if (id == 2) {
    imgUrl.value = "/src/components/img/clothes.jpg";
  }
  if (id == 3) {
    imgUrl.value = "/src/components/img/fruit.jpg";
  }
  if (id == 4) {
    imgUrl.value = "/src/components/img/applicance.jpg";
  }
  if (id == 5) {
    imgUrl.value = "/src/components/img/phone.jpg";
  }
};
</script>
<style scoped>
* {
  margin: -5px;
  padding: 0px;
}

.iconfont {
  font-family: "iconfont" !important;
  font-size: 10px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 40px;
  height: 40px;
}
.iconfonts {
  font-family: "iconfont" !important;
  font-size: 30px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
.box {
  width: 550px;
  margin-left: 150px;
  margin-top: 50px;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.6s ease;
}

.box:hover {
  color: orange;
  font-size: 30px;
  transform: scale(1.1);
  transition: all 0.3s ease;
}

.P {
  margin-top: 60px;
  font-family: "阿里妈妈灵动体";
  font-size: 80px;
  cursor: pointer;
  color: gray;
  position: relative;
  float: left;
  z-index: 0;
  margin-left: 600px;
  opacity: 0.5;
}

.Q {
  margin-top: 130px;
  font-family: "阿里妈妈灵动体";
  float: left;
  margin-left: 50px;
  position: absolute;
  z-index: 1;
  margin-left: 700px;
  cursor: pointer;
}

.el-container {
  margin: auto;
}

.el-header {
  margin-top: 5px;
  background-color: rgb(255, 255, 255);
}

.el-aside {
  margin-top: 6px;
  margin-left: -300px;
  background-color: antiquewhite;
}

.el-main {
  margin-top: 6px;
  margin-right: -300px;
  background-color: blanchedalmond;
}

.M {
  width: 1518px;
  height: 655px;
  float: left;
  margin-top: -170px;
  margin-left: -13px;
}

.D {
  font-size: 0;
  left: 0;
}

.F {
  float: left;
  width: 100%;
  height: 850px;
}

.X {
  width: 250px;
  height: 200px;
  background-color: rgba(0, 255, 255);
  float: left;
  position: absolute;
  margin-top: 520px;
  margin-left: 350px;
  display: flex;
  transition: all 0.6s ease;
}

.X:hover {
  z-index: 2;
  transform: scale(1.1);
  transition: all 0.6s ease;
  background-color: rgba(0, 255, 255);
}

.X a {
  text-decoration: none;
  cursor: pointer;
  color: black;
}

.Y {
  width: 250px;
  height: 200px;
  background-color: rgb(240, 198, 9);
  float: left;
  position: absolute;
  margin-top: 520px;
  margin-left: 600px;
  display: flex;
  z-index: 1;
  transition: all 0.6s ease;
}

.Y:hover {
  z-index: 2;
  transform: scale(1.1);
  transition: all 0.6s ease;
  background-color: rgb(240, 198, 9);
}

.Y a {
  text-decoration: none;
  cursor: pointer;
  color: black;
}

.Z {
  width: 250px;
  height: 200px;
  background-color: rgba(255, 0, 89);
  float: left;
  position: absolute;
  margin-top: 520px;
  margin-left: 850px;
  display: flex;
  z-index: 1;
  transition: all 0.6s ease;
}

.Z:hover {
  z-index: 2;
  transform: scale(1.1);
  transition: all 0.6s ease;
  background-color: rgba(255, 0, 89);
}

.Z a {
  text-decoration: none;
  cursor: pointer;
  color: black;
}
.iphoneinsade {
  position: absolute;
  margin-top: -7px;
  float: left;
  z-index: 1;
  margin-left: 212px;
}
.iphone {
  z-index: 2;
  position: absolute;
  margin-top: -20px;
  float: left;
  margin-left: 200px;
}
* {
  font-family: "阿里妈妈方圆体 VF Regular";
}
</style>