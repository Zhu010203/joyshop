<template>
    <div class="common-layout">
        <el-container>
            <el-header>
                <div class="B">
                    <div class="C"><img src="../assets/img/yuexiang.png" /></div>
                    <div class="B1"><span>悦享购物平台</span></div>
                    <ul>
                        <li><span><router-link to="/mainpage/about">关于我们</router-link></span></li>
                        <li><span><router-link to="/mainpage/feedback">用户反馈</router-link></span></li>
                        <li><span><router-link to="/mainpage/problem">常见问题</router-link></span></li>
                        <li><span><router-link to="/mainpage/open">开放平台</router-link></span></li>
                        <li><span><router-link to="/mainpage/firstpage">首页</router-link></span></li>
                    </ul>
                </div>
            </el-header>
            <el-main>
                <router-view></router-view>
                
                <div class="A">
                    <ul>
                        <li><span><router-link to="/mainpage/about">关于我们</router-link></span></li>
                        <li><span><router-link to="/mainpage/feedback">用户反馈</router-link></span></li>
                        <li><span><router-link to="/mainpage/problem">常见问题</router-link></span></li>
                        <li><span><router-link to="/mainpage/open">开放平台</router-link></span></li>
                        <li><span><router-link to="/mainpage/firstpage">首页</router-link></span></li>
                    </ul>

                    <div class="H">
                        <span style="font-size: 30px;">品牌合作</span><br />
                        <span>www.yuexiang.com</span><br />
                        <span style="font-size: 25px;">客服电话:</span>&nbsp;
                        <span>9555-12138</span><br />
                        <span>周一至周五 9：00~5：00</span>
                    </div>

                    <div class="I">
                        <span style="font-size: 20px;">更多优惠尽在小程序</span><br />
                        <div class="K">
                            <img src="../assets/img/erweima1.png" />
                        </div>
                    </div>
                    <div class="J">
                        <hr>
                        <div class="L">
                            <span>河南师范大学联合东软教育集团研制</span>
                        </div>

                    </div>
                </div>
            </el-main>
        </el-container>
    </div>
</template>
<script setup>


</script>
<style scoped>
* {
    margin: -5px;
    padding: 0px;

}

.M {
    width: 1518px;
    height: 655px;
    float: left;
    background-color: blue;
    margin-top: 10px;
    margin-left: -13px;
}

.L {
    margin-top: 10PX;
    margin-left: 630PX;
    color: #f0e9e9;
}

.J {
    margin-top: 250px;
}

.K {
    margin-top: 30px;
    width: 120px;
    height: 120px;

}

.I {
    float: right;
    margin-top: 60px;
    margin-right: 120px;
    width: 200px;
    color: white;
}

.H {
    margin-top: 60px;
    float: left;
    margin-left: 400px;
    width: 300px;
    line-height: 40px;
    color: white;
}

.A ul {
    margin-top: 50px;
    margin-left: 200px;
    display: block;
    text-align: left;
    line-height: 40px;
    cursor: pointer;
    float: left;
    width: 80px;
}

.A ul li {
    list-style: none;
}

.A ul li a {
    text-decoration: none;
    color: white;
}

.B {
    width: 100%;
    height: 100px;
    background-image: url("../assets/img/handerbg.jpg");
    margin: auto;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 3;
}


.B ul li {
    list-style: none;
    float: right;
    margin-top: 50px;
    margin-right: 30px;
}

.B ul li span {
    width: 80px;
    height: 32px;
    text-decoration: none;
    color: white;
    float: right;
    display: block;
    text-align: center;
    line-height: 32px;
    cursor: pointer;
    font-family: '阿里妈妈方圆体 VF Regular';

}
a {
  text-decoration: none;
  color: rgba(248, 242, 242, 0.789);
}

.router-link-active {
  text-decoration: none;
  color: rgba(2, 2, 8, 0.784);
}
.C {
    float: left;
    margin-left: 100px;
    margin-top: 25px;
}
.B1 {
    width: 250px;
    float: left;
    color: #ffffff;
    margin-left:410px;
    margin-top: 25px;
    font-size: 40px;
}
.D {
    font-size: 0;
    left: 0;
}

.F {
    float: left;
    width: 100%;
}


.X {
    width: 250px;
    height: 200px;
    background-color: aqua;
    float: left;
    position: absolute;
    margin-top: 700px;
    margin-left: 350px;
    display: flex;

}

.X:hover {
    z-index: 2;
    transform: scale(1.1);
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
    margin-top: 700px;
    margin-left: 600px;
    display: flex;
    z-index: 1;
}

.Y:hover {
    z-index: 2;
    transform: scale(1.1);
}

.Y a {
    text-decoration: none;
    cursor: pointer;
    color: black;
}

.Z {
    width: 250px;
    height: 200px;
    background-color: rgb(229, 11, 87);
    float: left;
    position: absolute;
    margin-top: 700px;
    margin-left: 850px;
    display: flex;
    z-index: 1;
}

.Z:hover {
    z-index: 2;
    transform: scale(1.1);
}

.Z a {
    text-decoration: none;
    cursor: pointer;
    color: black;
}

.A {
    width: 1525px;
    height: 300px;
    background-color: rgb(45, 45, 45);
    position: absolute;
    left: 0px;
    margin-top: 1300px;


}</style>