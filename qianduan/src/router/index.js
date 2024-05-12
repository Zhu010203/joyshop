import { createRouter, createWebHistory } from 'vue-router'

import Admins from '../components/admin/admins.vue'
import Adminlogin from '../view/Adminlogin.vue'
import Adminregister from '../view/Adminregister.vue'
import Adminhome from '../view/Adminhome.vue'
import Adminuser from '../components/admin/Adminuser.vue'
import Adminshop from '../components/admin/Shop.vue'
import Shopgoods from '../components/admin/Shopgoods.vue'
import Allgoods from '../components/admin/Allgoods.vue'
import Alltype from '../components/admin/Alltype.vue'
import Admindeveloper from '../components/admin/Developer.vue'
import Mainpage from '../view/Mainpage.vue'
import About from '../components/mainpage/About.vue'
import Feedback from '../components/mainpage/Feedback.vue'
import Firstpage from '../components/mainpage/Firstpage.vue'
import Open from '../components/mainpage/Open.vue'
import Problem from '../components/mainpage/Problem.vue'
import Businesslogin from '../view/Businesslogin.vue'
import Businessregister from '../view/Businessregister.vue'
import Businesshome from '../view/Businesshome.vue'
import Business from '../components/business/Business.vue'
import Businessgoods from '../components/business/Businessgoods.vue'
import Businessorder from '../components/business/Businessorder.vue'
import Businesstalk from '../components/business/Businesstalk.vue'
import Shoppinghome from '../view/Shoppinghome.vue'
import Typegoods from '../components/shopping/Typegoods.vue'
import Shoppinglogin from '../view/Shoppinglogin.vue'
import Shoppingregister from '../view/Shoppingregister.vue'
import Shoppingpay from '../components/shopping/Shoppingpay.vue'
import Goodsdetail from '../components/shopping/Goodsdetail.vue'
import Businessdetail from '../components/shopping/Businessdetail.vue'
import Userdetail from '../view/Userhome.vue'
import User from '../components/user/User.vue'
import Userorder from '../components/user/Userorder.vue'
import Usertalk from '../components/user/Usertalk.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', redirect: '/mainpage/firstpage' },
        {
            path: '/mainpage', component: Mainpage, children: [
                { path: 'about', component: About },
                { path: 'feedback', component: Feedback },
                { path: 'open', component: Open },
                { path: 'problem', component: Problem },
                { path: 'firstpage', component: Firstpage }
            ]
        },
        { path: '/adminregister', component: Adminregister },
        { path: '/adminlogin', component: Adminlogin },
        {
            path: '/adminhome', component: Adminhome, children: [
                { path: 'adminuser', component: Adminuser },
                { path: 'adminshop', component: Adminshop },
                { path: 'shopgoods', component: Shopgoods },
                { path: 'admindeveloper', component: Admindeveloper },
                { path: 'admins', component: Admins },
                { path: 'allgoods', component: Allgoods },
                { path: 'alltype', component: Alltype },
            ]
        },
        { path: '/businesslogin', component: Businesslogin },
        { path: '/businessregister', component: Businessregister },
        { path: '/shoppinglogin', component: Shoppinglogin },
        { path: '/shoppingregister', component: Shoppingregister },
        {
            path: '/shoppinghome', component: Shoppinghome, children: [
                { path: 'typegoods', component: Typegoods },
            ]
        },
        { path: '/goodsdetail', component: Goodsdetail },
        { path: '/businessdetail', component: Businessdetail },
        { path: '/shoppingpay', component: Shoppingpay },
        {
            path: '/businesshome', component: Businesshome, children: [
                { path: 'business', component: Business },
                { path: 'businessgoods', component: Businessgoods },
                { path: 'businessorder', component: Businessorder },
                { path: 'businesstalk', component: Businesstalk },
            ]
        },
        {
            path: '/userdetail', component: Userdetail, children: [
                { path: 'user', component: User },
                { path: 'userorder', component: Userorder },
                { path: 'usertalk', component: Usertalk },
            ]
        }
    ]
})
router.beforeEach((to, from, next) => {
    if (to.path === '/mainpage/firstpage') return next();
    if (to.path === '/businesslogin') return next();
    if (to.path === '/businessregister') return next();
    if (to.path === '/adminlogin') return next();
    if (to.path === '/shoppinglogin') return next();
    if (to.path === '/shoppingregister') return next();
    if (to.path === '/mainpage/open') return next();
    if (to.path === '/mainpage/problem') return next();
    if (to.path === '/mainpage/feedback') return next();
    if (to.path === '/mainpage/about') return next();
    //获取token
    const tokenStr = window.sessionStorage.getItem('token')
    if (!tokenStr) return next('/')
    next();
})


export default router