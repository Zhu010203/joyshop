<template>
    <div class="common-layout">
        <el-container>
            <el-aside width="200px">
                <el-input v-model="input" placeholder="请输入查询的用户名/id" input-style="width:150px;"
                    @keyup.enter.native="getuserbyid(input)"></el-input>
                <el-scrollbar>
                    <el-menu v-infinite-scroll="load" style="overflow: auto;height:450px">
                        <el-menu-item index="1" v-for="user in userData" :key="user.id" @click="gettalkuser(user.id)"
                            divided><el-badge v-if="user.num > 0" :value="user.num" class="item"><img v-if="user.num > 0" :src="user.img"
                                    class="avatar" /></el-badge>
                            <img v-if="user.num == 0" :src="user.img" class="avatar" />
                            <el-tag size="small" type="warning" v-if="user.type == '用户'">{{ user.type }}</el-tag>
                            <el-tag size="small" v-if="user.type == '商家'">{{ user.type }}</el-tag>
                            <el-tag size="small" type="danger" v-if="user.type == '封禁'">{{ user.type }}</el-tag>
                            {{ user.name }}
                        </el-menu-item>
                    </el-menu>
                </el-scrollbar>
            </el-aside>
            <el-container style="background-color: aliceblue;">
                <el-header height="50px">
                    <div style="text-align: center;"><span style="font-size: 2em;">{{ talkuser.name }}</span></div>
                </el-header>
                <el-main>
                    <div style="height: 400px;">
                        <div v-if="msgList.msgs.length == 0" style="text-align: center;">还没有信息哦</div>
                        <div v-for="msg in msgList.msgs" :key="msg.id" v-infinite-scroll="load" style="overflow: auto">
                            <div class="time"><span style="font-size: small;">{{ msg.createTime }}</span></div>
                            <div class="talk">
                                <div class="leftuser" v-if="msg.type == 'receive'">
                                    <img :src="talkuser.img" class="avatar" /><span>{{ talkuser.name }}</span>
                                </div>
                                <div class="rightuser" v-if="msg.type == 'sender'">
                                    <img :src="self.img" class="avatar" />{{ self.name }}
                                </div>
                                <div :class="msg.type">
                                    {{ msg.message }}</div>
                            </div>
                        </div>
                    </div>
                </el-main>
                <el-footer><el-input v-model="msg111" :autosize="{ minRows: 2, maxRows: 3 }" type="textarea"
                        placeholder="请输入内容" @keyup.enter.native="sender(msg111)" /><br>
                    <div style="float: right;"><el-button plain @click="sender(msg111)">发送消息</el-button></div>
                </el-footer>
            </el-container>
        </el-container>
    </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const selfid = sessionStorage.getItem('businessid')
const MessageData = ref([])
const msg111 = ref('')
const userData = ref([])
const input = ref('')
const self = ref({})
const talkuser = ref({})


const getUser = () => {
    userData.value = []
    axios.get('/api/user/all').then((res) => {
        if (res.data.code == 1) {
            for (const item of res.data.data) {
                const user = {}
                user.id = item.id
                user.name = item.name
                user.img = item.img
                if (item.status == 1) {
                    user.type = '用户'
                } else {
                    user.type = '封禁'
                }
                userData.value.push(user)
            }
        }
    })
    axios.get('/api/business/all').then((res) => {
        if (res.data.code == 1) {
            for (const item of res.data.data) {
                const user = {}
                user.id = item.id
                user.name = item.name
                user.img = item.img
                if (item.status == 1) {
                    user.type = '商家'
                } else {
                    user.type = '封禁'
                }
                userData.value.push(user)
            }
        }
        for (const item of userData.value) {
            axios.post('/api/message/message', { sender: item.id, receive: selfid, isnew: 1 }).then((res) => {
                item.num = 0
                if (res.data.code == 1) {
                    item.num = item.num + res.data.data.length
                }
                for (let i = 0; i < userData.value.length - 1; i++) {
            for (let j = i; j < userData.value.length; j++) {
              if (userData.value[i].num < userData.value[j].num) {
                const changeData = userData.value[i];
                userData.value[i] = userData.value[j];
                userData.value[j] = changeData;
              }
            }
          }
            })
        }
    })
}
getUser()
const getbyId = (id) => {
    userData.value = []
    axios.get('/api/user/' + id).then((res) => {
        if (res.data.code == 1) {
            const item = res.data.data
            const user = {}
            user.id = item.id
            user.name = item.name
            user.img = item.img
            userData.value.push(user)
        }
    })
    axios.get('/api/business/' + id).then((res) => {
        if (res.data.code == 1) {
            const item = res.data.data
            const user = {}
            user.id = item.id
            user.name = item.name
            user.img = item.img
            userData.value.push(user)
        }
    })
}
const getbyName = (id) => {
    userData.value = []
    axios.post('/api/user/find', { name: id }).then((res) => {
        if (res.data.code == 1) {
            for (const item of res.data.data) {
                const user = {}
                user.id = item.id
                user.name = item.name
                user.img = item.img
                userData.value.push(user)
            }
        }
    })
    axios.post('/api/business/find', { name: id }).then((res) => {
        if (res.data.code == 1) {
            for (const item of res.data.data) {
                const user = {}
                user.id = item.id
                user.name = item.name
                user.img = item.img
                userData.value.push(user)
            }
        }
    })
}
const getuserbyid = (id) => {
    if (id == '') {
        getUser()
    }
    if (!isNaN(id)) {
        getbyId(id)
        input.value = ''
    } else {
        getbyName(id)
        input.value = ''
    }
}
const gettalkbyId = (id) => {
    axios.get('/api/user/' + id).then((res) => {
        if (res.data.code == 1) {
            const item = res.data.data
            const user = {}
            user.id = item.id
            user.name = item.name
            user.img = item.img
            talkuser.value = user
        }
    })
    axios.get('/api/business/' + id).then((res) => {
        if (res.data.code == 1) {
            const item = res.data.data
            const user = {}
            user.id = item.id
            user.name = item.name
            user.img = item.img
            talkuser.value = user
        }
    })
}
const gettalkuser = (id) => {
    gettalkbyId(id)
    getMessage(id)
}

const getself = () => {
    axios.get('/api/business/' + selfid).then((res) => {
        if (res.data.code == 1) {
            self.value = res.data.data
        }
    })
}
getself()


const msgList = reactive({
    msgs: []
})

const getMessage = (talkid) => {
    msgList.msgs = []
    axios.put('/api/message', { sender: talkid, receive: selfid, isnew: 0 }).then((res) => {
            getUser()
            axios.post('/api/message/message', { sender: talkid, receive: selfid }).then((res) => {
                if (res.data.code == 1) {
                    for (const item of res.data.data) {
                        const message = item
                        const date = item.createTime
                        for (let i = 0; i < date.length; i++) {
                            if (date[i].toString().length == 1) {
                                date[i] = '0' + date[i]
                            }
                        }
                        const date1 = date[0] + "年" + date[1] + "月" + date[2] + "日" + date[3] + ":" + date[4] + ':' + date[5]
                        message.createTime = date1
                        message.type = 'receive'
                        msgList.msgs.push(message)
                    }
                    axios.post('/api/message/message', { sender: selfid, receive: talkid }).then((res) => {
                        if (res.data.code == 1) {
                            for (const item of res.data.data) {
                                const message = item
                                const date = item.createTime
                                for (let i = 0; i < date.length; i++) {
                                    if (date[i].toString().length == 1) {
                                        date[i] = '0' + date[i]
                                    }
                                }
                                const date1 = date[0] + "年" + date[1] + "月" + date[2] + "日" + date[3] + ":" + date[4] + ':' + date[5]
                                message.createTime = date1
                                message.type = 'sender'
                                msgList.msgs.push(message)
                            }
                            for (let i = 0; i < msgList.msgs.length - 1; i++) {
                                for (let j = i; j < msgList.msgs.length; j++) {
                                    if (msgList.msgs[i].createTime > msgList.msgs[j].createTime) {
                                        const changeData = msgList.msgs[i]
                                        msgList.msgs[i] = msgList.msgs[j]
                                        msgList.msgs[j] = changeData
                                    }
                                }
                            }
                        }
                    })
                } else {
                    axios.post('/api/message/message', { sender: selfid, receive: talkid }).then((res) => {
                        if (res.data.code == 1) {
                            for (const item of res.data.data) {
                                const message = item
                                const date = item.createTime
                                for (let i = 0; i < date.length; i++) {
                                    if (date[i].toString().length == 1) {
                                        date[i] = '0' + date[i]
                                    }
                                }
                                const date1 = date[0] + "年" + date[1] + "月" + date[2] + "日" + date[3] + ":" + date[4] + ':' + date[5]
                                message.createTime = date1
                                message.type = 'sender'
                                msgList.msgs.push(message)
                            }
                        }
                    })
                }
            })
    })
}

const sender = (msg1) => {
    if (msg1 != '') {
        const message = {}
        message.sender = selfid
        message.receive = talkuser.value.id
        message.message = msg1
        console.log(message)
        axios.post('/api/message', message).then((res) => {
            if (res.data.code == 1) {
                msg111.value = ''
                getMessage(talkuser.value.id)
            }
        })
    }
}
const count = ref(0)
const load = () => {
    count.value += 2
}

</script>
<style scoped>
a {
    text-decoration: none;
}

.home {

    margin: -10px;
    width: auto;
    height: 900px;
}

.avatar {
    width: 30px;
    height: 30px;
    display: block;
}

.talk {
    height: 70px;
    text-align: center;
}

.time {
    text-align: center;
}

.sender {
    text-align: left;
    width: 200px;
    height: 50px;
    float: right;
    background-image: url('../img/lefttalk.png');
}

.receive {
    text-align: left;
    width: 200px;
    height: 50px;
    float: left;
    background-image: url('../img/righttalk.jpg');
}

.leftuser {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    width: 100px;
    height: 50px;
    float: left;
}

.rightuser {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    width: 100px;
    height: 50px;
    float: right;
}

* {
    font-family: "阿里妈妈方圆体 VF Regular";
}
</style>
