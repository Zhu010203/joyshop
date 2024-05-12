<template>
    <div class="divO">
        <div class="Q">
            <img src="../img/about.jpg" />
        </div>
        <div class="P">
            <span style="font-weight: 1000;">让购物更便捷</span>
        </div>
        <div class="R">
            <table wideth="1000px" height="300px" cellspadding="20" align="center">
                <!--表格标题-->
                <caption style="color: rgb(12, 12, 26);font-size: 50px;">关于我们</caption>
                <tr>
                    <th>作为一家购物平台，我们的目标不仅仅是赚取丰厚的利润。还希望能够让世界变得更加美好。
                        我们在气候行动、人权和数字平等领域的付出，证明了我们正全力以赴实现这一目标。</th>
                </tr>
                <tr>
                    <th>在过去 80 多年的时间里，我们一次次用行动证明，我们坚信未来的人类必定能够在创新的引导下，实现更多伟大的成就。</th>
                </tr>
                <tr>
                    <th>我们的技术 — 包括个人系统、打印机和 3D 打印解决方案等相关产品和服务组合 — 皆旨在实现意义深远的进步。
                        我们深知，无论何人、何地、何时，都可能产生绝妙的创意。</th>
                </tr>
                <tr>
                    <th>而一个创意就可能会改变世界。</th>
                </tr>
            </table>
            <table wideth="1000px" height="200px" cellspadding="20" align="center">
                <caption style="color: rgb(12, 12, 26);font-size: 30px;">我们的团队</caption>
                <tr>
                    <th>我们组建了一支由世界技术巨匠组成的团队。在悦享，我们的目标是打造创新的数字生活体验，不断颠覆传统。</th>
                </tr>
                <tr>
                    <th>欢迎支持我们</th>
                </tr>
                </table>
        </div>
    </div>
</template>
<script setup>


</script>
<style scoped>
.divO {
    
    margin-left: -13px;
    position: absolute;
}

.P {
    position: absolute;
    float: left;
    margin-top: 200px;
    color: rgb(255, 255, 255);
    background-color: rgba(92, 92, 92, 0.5);
    margin-left: 450px;
    font-size: 100px;
}

.Q {
    float: left;
}

.R {
    position: absolute;
    width: 100%;
    height: 710px;
    background-color: rgb(232, 232, 237);
    margin-top: 600px;
}
.R table{
    margin-top: 20px;
}
</style>