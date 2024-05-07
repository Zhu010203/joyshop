<template>
    <div class="O"><span class="W">常见问题</span>
    
        <el-scrollbar>
            <el-menu :default-openeds="['1', '6']" active-text-color="red">
                <span style="color: brown;" >在线支付问题</span>
                <hr style="width: 800px;"/>
                <el-sub-menu index="1-1">
                    <template #title class="V" >Q：在线支付取消订单后钱怎么返还？</template>
                    <el-menu-item index="1-1-1">
                        订单取消后，款项会在一个工作日内，直接返还到您的账户余额。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="1-2">
                    <template #title>Q：怎么查看退款是否成功？</template>
                    <el-menu-item index="1-2-1">
                        退款会在一个工作日之内到账户余额，可在“账号管理——我的账号”中查看是否到账。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="1-3">
                    <template #title>Q：账户里的余额怎么提现？</template>
                    <el-menu-item index="1-3-1">
                        “我的→余额”里提取到您的银行卡或者支付宝账号，另外，余额也可直接用于支付订单。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="1-4">
                    <template #title>Q：余额提现到账时间是多久？</template>
                    <el-menu-item index="1-4-1">
                        1-7个工作日内可退回您的支付账户。由于银行处理可能有延迟，具体以账户的到账时间为准。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="2-1">
                    <template #title>Q：申请退款后，商家拒绝了怎么办？</template>
                    <el-menu-item index="2-1-1">
                        申请退款后，如果商家拒绝，此时回到订单页面点击“退款申诉”，美团客服介入处理。
                    </el-menu-item>
                </el-sub-menu>
                
                <el-sub-menu index="2-2">
                    <template #title>Q：怎么取消退款呢？</template>
                    <el-menu-item index="2-2-1">
                        请在订单页点击“不退款了”，商家还会正常发货的。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="2-3">
                    <template #title>Q：前面下了一个在线支付的单子，由于未付款，订单自动取消了，这单会计算我的参与活动次数吗？</template>
                    <el-menu-item index="2-3-1">
                        不会，订单无效后，此时您再下单仍会享受活动的优惠。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="2-4">
                    <template #title>Q：为什么我用微信订餐，却无法使用在线支付？</template>
                    <el-menu-item index="2-4-1">
                        目前只有网页版购买，才能使用在线支付，请更换到网页版下单。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="3-1">
                    <template #title>Q：如何进行付款？</template>
                    <el-menu-item index="3-1-1">
                        现在支持货到付款与在线支付，其中微信版与手机触屏版暂不支持在线支付。
                    </el-menu-item>
                </el-sub-menu>
                <span style="color: brown;" >支付问题</span>
                <hr style="width: 800px;"/>
                <el-sub-menu index="3-2">
                    <template #title>Q：如何查看可以在线支付的商家？</template>
                    <el-menu-item index="3-2-1">
                        你可以在商家列表页寻找带有“付”标识的商家，提交订单时可以选择支付方式。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="3-3">
                    <template #title>Q：悦享购物支持哪些支付方式？</template>
                    <el-menu-item index="3-3-1">
                        现已支持余额、支付宝、网银（储蓄卡、信用卡）。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="3-4">
                    <template #title>Q：在线支付订单如何退款？</template>
                    <el-menu-item index="3-4-1">
                        商家接单前，您可以直接取消订单；商家接单后，您在点击“申请退款”，在线申请。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="4-1">
                    <template #title>Q：在线支付的过程中，订单显示未支付成功，款项却被扣了，怎么办？</template>
                    <el-menu-item index="4-1-1">
                        出现此问题，可能是银行/支付宝的数据没有即时传输，请您不要担心，稍后刷新页面查看。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="4-2">
                    <template #title>Q：在新用户享受的优惠中，新用户的条件是什么？</template>
                    <el-menu-item index="4-2-1">
                        新用户是指第一次在悦享购物平台下单的用户（同一设备、同一手机号、同一账户仅可享受一次）。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="4-3">
                    <template #title>Q：超时赔付是什么意思？</template>
                    <el-menu-item index="4-3-1">
                        超时赔付模式即：商家承诺一个送达时间和一个折扣,从用户下单时间开始计算。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="4-4">
                    <template #title>Q：为什么提示我“账户存在异常，无法下单”？</template>
                    <el-menu-item index="4-4-1">
                        有过虚假交易（编造不存在真实买卖的订单）；</el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="5-1">
                    <template #title>Q：如何取消订单？</template>
                    <el-menu-item index="5-1-1">
                        如果商家尚未接单，您可以在订单详情页通过“取消订单”功能进行取消;如果商家已接单，则需要您电话联系后由商家取消订单。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="5-2">
                    <template #title>Q：我的订单为什么被取消了？</template>
                    <el-menu-item index="5-2-1">
                        如果商家未接您的订单，为了保障您的权益，系统将会为您自动取消订单
                    </el-menu-item>
                </el-sub-menu>
                <span style="color: brown;">其他问题</span>
                <hr style="width: 800px;"/>
                <el-sub-menu index="5-3">
                    <template #title>Q：如何进行催单？</template>
                    <el-menu-item index="5-3-1">
                        您可以在订单状态页面点击“电话催单”按钮向商家商家催单。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="5-4">
                    <template #title>Q：刚下单发现信息填错了怎么办？</template>
                    <el-menu-item index="5-4-1">
                        如果商家尚未接单，您可以自主取消订单；如果商家已经接单，您可以电话联系商家后由对方取消订单。然后重新下一单。
                    </el-menu-item>
                </el-sub-menu>
                <el-sub-menu index="6-1">
                    <template #title>Q：我的订单是否被商家确认？</template>
                    <el-menu-item index="6-1-1">
                        App用户可以在商家确认您的订单时收到推送通知，并且订单状态会实时更新；手机触屏版及微信用户可以刷新订单状态页查看。
                    </el-menu-item>
                </el-sub-menu>


            </el-menu>
        </el-scrollbar>
    </div>
</template>
<script setup>
import { ref } from "vue"


</script>
<style scoped>
.O {
    margin-top: 50px;

    width: 900px;
    height: 1200px;
    margin-left: 280px;
    position: absolute;
    box-shadow: 0px -1px 0px 0px #161616,
        /*上边阴影 */

        -0.2px 0px 0px 0px #121212,
        /*左边阴影  */

        0.2px 0px 0px 0px #0f0e0e,
        /*右边阴影 */

        0px 1px 0px 0px #0f0f0f;
    /*下边阴影 */

}
.W{
    margin-left: 400px;
}

</style>