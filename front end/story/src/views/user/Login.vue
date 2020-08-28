<template>
    <div class="all">
        <div class="content">
            <div class="form sign-in">
                <h2>欢迎回来</h2>
                <label>
                    <span>账号</span>
                    <input type="text" v-model="userAccount" />
                </label>
                <label>
                    <span>密码</span>
                    <input type="password" v-model="password" />
                </label>
                <p class="forgot-pass">
                    <a href="javascript:">忘记密码？</a>
                </p>
                <button type="button" class="submit" @click="login">登 录</button>
            </div>
            <div class="sub-cont">
                <div class="img">
                    <div class="img__text m--up">
                        <h2>还未注册？</h2>
                        <p>立即注册，发现大量机会！</p>
                    </div>
                    <div class="img__text m--in">
                        <h2>已有帐号？</h2>
                        <p>有帐号就登录吧，好久不见了！</p>
                    </div>
                    <div class="img__btn" @click="change">
                        <span class="m--up">注 册</span>
                        <span class="m--in">登 录</span>
                    </div>
                </div>
                <div class="form sign-up">
                    <h2>立即注册</h2>
                    <label>
                        <span>用户名</span>
                        <input type="text" v-model="registerAccount" />
                    </label>
                    <label>
                        <span>密码</span>
                        <input type="password" v-model="registerPassword" />
                    </label>
                    <label>
                        <span>确认密码</span>
                        <input type="password" v-model="confirmPassword" />
                    </label>
                    <button type="button" class="submit" @click="register()">注 册</button>
                </div>
            </div>
        </div>
        <Footer></Footer>
    </div>
</template>

<script>
import storage from '@/utils/storage';
import Footer from '@/components/Footer.vue';
import http from '@/utils/http';

// 列表的样式
import "@/assets/css/common.css";
export default {

    name: "Login",

    inject:['reload'],

    components: {

        Footer
    
    },

    data() {

        return {
            userAccount: null,
            password: null,
            registerAccount: null,
            registerPassword: null,
            confirmPassword: null,
        };

    },

    methods: {

        getUserInfo() {

            var _this = this;

            var user = JSON.parse(this.$store.getters.getUser);

            http({

                // 假设后台需要的是表单数据这里你就可以更改
                headers: {

                "Content-Type": "application/json;charset=UTF-8"
                
                },

                method: 'post',
                url: 'http://localhost:8080/user/listOneUser',

                data: {

                    userAccount: user.userAccount,

                },

                responseType: 'json'

                }).then(function (res) {

                    var code = res.code;
                    var info = res.info;

                    if (res.code == 200) {

                        // 创建userInfo
                        var userInfo = new Object();
                        userInfo.userId = info.userId;
                        userInfo.userAccount = info.userAccount;
                        userInfo.userName = info.userName;
                        userInfo.sign = info.sign;
                        userInfo.roleName = info.roleName;
                        userInfo.avatar = info.avatar;
                        userInfo.email = info.email;
                        userInfo.gender = info.gender;

                        // 提交userInfo
                        storage.set('userInfo', JSON.stringify(userInfo));
                        _this.$store.commit('setUserInfo', storage.get('userInfo'));
                    
                    } else {
                        
                        _this.$message.error(info);

                    }

                }).catch(function (err) {

                    _this.$message.error("Login.vue —— getUserInfo:系统错误！");
             
            });

        },
        login: function () {

            var _this = this

            _this.message = 'login'

            this.axios({

                method: 'post',

                url: 'http://localhost:8080/login',

                headers: {

                'Content-Type': 'application/json;charset=utf-8'

                },

                data: {

                userAccount: _this.userAccount,
                password: _this.password

                },

                responseType: 'json'

            }).then(function (response) {
                
                var info = response.data.info;
                var code = response.data.code;

                if (code == 200) {

                // 创建user对象
                var user = new Object();
                user.token = info;
                user.userAccount = _this.userAccount;
                user.password = _this.password;

                // 提交user(含token)
                storage.set('user', JSON.stringify(user));
                _this.$store.commit('createUser', storage.get('user'));
                
                _this.getUserInfo();

                _this.$router.replace('/');

                _this.reload();

                _this.$message.success('登录成功');

                } else if (code == 400 && info == 'incorrect') {

                _this.$message.error('用户名或密码输入错误！');

                } else {

                _this.$message.error('账号不存在！');

                }

            }).catch(function (error) {

                _this.$message.error('Login.vue —— login: 系统错误！');

            })

        },

        register: function () {

            var _this = this;

            if(this.registerAccount == null | this.registerPassword == null | this.confirmPassword == null) {

                this.$message.warning("关键信息不能为空!");
            
            } else if (this.registerPassword !== this.confirmPassword) {
                
                this.$message.warning("两次输入的新密码不一致!");
            
            } else {

            http({

                // 假设后台需要的是表单数据这里你就可以更改
                headers: {

                "Content-Type": "application/json;charset=UTF-8"
                
                },

                method: 'post',
                url: 'http://localhost:8080/register',

                data: {

                    userAccount: _this.registerAccount,
                    password: _this.registerPassword,
                    userName: '小光'

                },

                responseType: 'json'

                }).then(function (res) {

                    console.log(res);

                    var code = res.code;
                    var info = res.info;

                    if (res.code == 200) {
                    
                        _this.$message.success("注册成功！");
                        _this.change();
                    
                    } else {
                        
                        _this.$message.error(info);

                    }

                }).catch(function (err) {

                    _this.$message.error("系统错误！");
            
            });

            }
        },

        change: function () {

            document.querySelector(".content").classList.toggle("s--signup");
        
        },
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

footer{
    position: absolute;
    bottom: -650px;
}


*,
*:before,
*:after {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

.all {
    /* position: absolute; */
    position: relative;
    width: 100%;
    height: 85%;
    background-color: whitesmoke;
    background-size: cover;
}

input,
button {
    border: none;
    outline: none;
    background: none;
    font-family: "Open Sans", Helvetica, Arial, sans-serif;
}

.content {
    overflow: hidden;
    position: absolute;
    /* position: relative; */
    left: 50%;
    top: 50%;
    width: 900px;
    height: 550px;
    margin: -270px 0 0 -450px;
    background: #fff;
}

.form {
    position: relative;
    width: 640px;
    height: 100%;
    transition: -webkit-transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
    padding: 50px 30px 0;
}

.sub-cont {
    overflow: hidden;
    position: absolute;
    left: 640px;
    top: 0;
    width: 900px;
    height: 100%;
    padding-left: 260px;
    background: #fff;
    transition: -webkit-transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
}

.content.s--signup .sub-cont {
    -webkit-transform: translate3d(-640px, 0, 0);
    transform: translate3d(-640px, 0, 0);
}

button {
    display: block;
    margin: 0 auto;
    width: 260px;
    height: 36px;
    border-radius: 30px;
    color: #fff;
    font-size: 15px;
    cursor: pointer;
}

.img {
    overflow: hidden;
    z-index: 2;
    position: absolute;
    left: 0;
    top: 0;
    width: 260px;
    height: 100%;
    padding-top: 360px;
}

.img:before {
    content: "";
    position: absolute;
    right: 0;
    top: 0;
    width: 900px;
    height: 100%;
    background-image: url(../../assets/loginbg.jpg);
    background-size: cover;
    transition: -webkit-transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
}

.img:after {
    content: "";
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.6);
}

.content.s--signup .img:before {
    -webkit-transform: translate3d(640px, 0, 0);
    transform: translate3d(640px, 0, 0);
}

.img__text {
    z-index: 2;
    position: absolute;
    left: 0;
    top: 50px;
    width: 100%;
    padding: 0 20px;
    text-align: center;
    color: #fff;
    transition: -webkit-transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
}

.img__text h2 {
    margin-bottom: 10px;
    font-weight: normal;
}

.img__text p {
    font-size: 14px;
    line-height: 1.5;
}

.content.s--signup .img__text.m--up {
    -webkit-transform: translateX(520px);
    transform: translateX(520px);
}
.img__text.m--in {
    -webkit-transform: translateX(-520px);
    transform: translateX(-520px);
}

.content.s--signup .img__text.m--in {
    -webkit-transform: translateX(0);
    transform: translateX(0);
}

.img__btn {
    overflow: hidden;
    z-index: 2;
    position: relative;
    width: 100px;
    height: 36px;
    margin: 0 auto;
    background: transparent;
    color: #fff;
    text-transform: uppercase;
    font-size: 15px;
    cursor: pointer;
}
.img__btn:after {
    content: "";
    z-index: 2;
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    border: 2px solid #fff;
    border-radius: 30px;
}

.img__btn span {
    position: absolute;
    left: 0;
    top: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    transition: -webkit-transform 0.6s;
    transition: transform 0.6s;
    transition: transform 0.6s, -webkit-transform 0.6s;
}

.img__btn span.m--in {
    -webkit-transform: translateY(-72px);
    transform: translateY(-72px);
}

.content.s--signup .img__btn span.m--in {
    -webkit-transform: translateY(0);
    transform: translateY(0);
}

.content.s--signup .img__btn span.m--up {
    -webkit-transform: translateY(72px);
    transform: translateY(72px);
}

h2 {
    width: 100%;
    font-size: 26px;
    text-align: center;
}

label {
    display: block;
    width: 260px;
    margin: 25px auto 0;
    text-align: center;
}

label span {
    font-size: 12px;
    color: #909399;
    text-transform: uppercase;
}

input {
    display: block;
    width: 100%;
    margin-top: 5px;
    padding-bottom: 5px;
    font-size: 16px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.4);
    text-align: center;
}

.forgot-pass {
    margin-top: 15px;
    text-align: center;
    font-size: 12px;
    color: #cfcfcf;
}

.forgot-pass a {
    color: #cfcfcf;
}

.submit {
    margin-top: 40px;
    margin-bottom: 20px;
    background: #d4af7a;
    text-transform: uppercase;
}

.fb-btn {
    border: 2px solid #d3dae9;
    color: #8fa1c7;
}
.fb-btn span {
    font-weight: bold;
    color: #455a81;
}

.sign-in {
    transition-timing-function: ease-out;
}
.content.s--signup .sign-in {
    transition-timing-function: ease-in-out;
    transition-duration: 0.6s;
    -webkit-transform: translate3d(640px, 0, 0);
    transform: translate3d(640px, 0, 0);
}

.sign-up {
    -webkit-transform: translate3d(-900px, 0, 0);
    transform: translate3d(-900px, 0, 0);
}
.content.s--signup .sign-up {
    -webkit-transform: translate3d(0, 0, 0);
    transform: translate3d(0, 0, 0);
}
</style>
