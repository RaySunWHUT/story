<template>
  <!-- <el-header style="height: auto; width: 100%; border: 10px solid red"> -->
    <header class="head-section">
    <div class="navbar navbar-default navbar-static-top container">
      <div class="navbar-header">
        <button
          class="navbar-toggle"
          data-target=".navbar-collapse"
          data-toggle="collapse"
          type="button"
        >
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <!-- 项目名 -->
        <a class="navbar-brand" href="/">
          拾一片光阴
          <span>与你诉说</span>
        </a>
      </div>

      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
            <li>
              <input class="el-input__inner" placeholder="search something..." autofocus="true" v-model="title">
            </li>
            <li>
              <img src="@/assets/search.png" width="20px" height="20px" style="margin-top: 10px;" @click="search()">
            </li>

          <li>
            <router-link to="/book">书评</router-link>
          </li>
          <li>
            <router-link to="/music">乐评</router-link>
          </li>
          <li>
            <router-link to="/video">影评</router-link>
          </li>
          <li>
            <router-link to="/note">随笔</router-link>
          </li>

          <!-- <li v-for="(typeName, typeId) in typeList" :key="typeId">
            <router-link :to="/{{typeName}}"></router-link>
          </li> -->

          <li>

          <el-dropdown>
            <span class="el-dropdown-link">
              <el-avatar shape="circle" :src="squareUrl"></el-avatar>
            </span>
            <el-dropdown-menu slot="dropdown">

              <div v-show="show0">
                <router-link to="/login"><el-dropdown-item>登录</el-dropdown-item></router-link>
              </div>

              <div v-show="show1">
                <router-link to="/user/createCenter/personCenter"><el-dropdown-item>个人中心</el-dropdown-item></router-link>
                <router-link to="/user/createCenter/draft"><el-dropdown-item>创作中心</el-dropdown-item></router-link>
                <router-link to="/user/createCenter/messageInfo"><el-dropdown-item>消息中心</el-dropdown-item></router-link>
                <router-link to="/user/updatePassword"><el-dropdown-item>修改密码</el-dropdown-item></router-link>
                <el-dropdown-item @click.native="exit()">退出</el-dropdown-item>
              </div>
              


            </el-dropdown-menu>
          </el-dropdown>
          </li>
        </ul>
      </div>
    </div>
  </header>
  <!-- </el-header> -->
</template>

<script>

import storage from "../utils/storage.js"
import http from "../utils/http.js"

export default {
  
  inject:['reload'],

  name: 'navigation',

  data() {

    return {

      title: null, 

      show0: null,
      show1: null,
      // squareUrl: "G:\Visual StudioCode\VScodeSource\story\src\assets\demo.jpg",
      squareUrl:"https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png",

      typeList: [{
        typeId : 0,
        typeName: "书评"
      }],
      
    }

  },

  created() {

    // 若用户已登录
    if (JSON.parse(storage.get("user")) != null) {

      this.squareUrl = "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100";
      this.show0 = false;
      this.show1 = true;

    } else {  // 用户未登录

      this.show0 = true;
      this.show1 = false;

    }

    var _this = this;
    document.onkeydown = function (e) {
      
      if (window.event.keyCode == 13 && _this.title != null && _this.title != "") {
        _this.search();
      }

    }

  },

  methods: {

    search() {

      var _this = this;

      if (_this.title != null && _this.title != "") {

        http({

            // 假设后台需要的是表单数据这里你就可以更改
            headers: {

            "Content-Type": "application/json;charset=UTF-8"
            
            },

            method: 'post',
            url: 'http://localhost:8080/idea/search',

            data: {

              title: _this.title

            },

            responseType: 'json'

            }).then(function (res) {

                console.log(res);

                var code = res.code;
                var info = res.info;

                if (res.code == 200) {
                
                    console.log(info);
                
                } else {

                    console.log(info);
                    _this.$message.error(info);

                }

            }).catch(function (err) {

                _this.$message.error("Navigation.vue: 系统错误!");
            
        });
      
      } else {

        _this.$message.warning("请输入搜索信息!");

      }

    },
    exit() {

      console.log("exit")

      var _this = this;

      _this.message = 'logout'

      this.axios({

        method: 'post',

        url: 'http://localhost:8080/user/logout',

        headers: {

          'Content-Type': 'application/json;charset=utf-8'

        },

        data: {

          message: _this.message

        },

        responseType: 'json'

      }).then(function (response) {

        var info = response.data.info;
        var code = response.data.code;

        console.log(response);

        if (code == 200) {

          storage.clear();
          
          _this.$router.replace('/');
          
          _this.$message.success('退出成功!');
          
          _this.reload();  // 刷新界面


        } else if (code == 400) {
          
          _this.$message.error('遭遇异常！')
       
        } else {
        
          _this.$message.error('未知错误...')
        
        }
      
      }).catch(function (error) {

          console.log(error);
      
          _this.$message.error('系统错误！')
      
      })

    }
  }

}
</script>

<style lang="less" scoped>

.navbar-brand {
  position: relative;
}

// 过渡的方向与所处的相对位置有关
.el-input__inner {

  -webkit-appearance: none;
  background-color: #FFF;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #DCDFE6;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 40px;
  line-height: 40px;
  outline: 0;
  padding: 0 15px;
  transition: border-color .2s cubic-bezier(.645,.045,.355,1);
  width: 180px;  
  transition: width 2s;
  -moz-transition: width 2s; /* Firefox 4 */
  -webkit-transition: width 2s; /* Safari and Chrome */
  -o-transition: width 2s; /* Opera */
  position: absolute;
  top: 30px;
  right: 0px;

}


.navbar-nav:hover .el-input__inner{
    width: 300px;

}


.navbar-nav img:hover {
  cursor: pointer;
}

.el-dropdown-link {
    cursor: pointer;
    // color: #409EFF;
}

.profile-top {
  align-items: center;
  background: #dfecf1;
  height: 50px;
}
.pic {
  position: relative;
  float: left;
  left: 35%;
  width: 25%;
}
.pic img {
  width: 100%;
  border-radius: 50%;
  border: 5px solid #fff;
}

.head-section {
  border-bottom: 1px solid #eee;
}
.head-section .navbar {
  min-height: 85px;
  margin-bottom: 0;
}
.head-section .navbar-default {
  background-color: #fff;
}
.navbar-default {
  border: none;
}
.navbar-brand {
  color: #bcc0cd;
  font-size: 30px;
  font-weight: 100;
  line-height: 30px;
  margin-top: 30px;
  padding: 0;
}
a,
a:hover,
a:focus {
  text-decoration: none;
  outline: none;
}
.navbar-brand span {
  color: #48cfad;
  font-size: 20px;
}
.head-section .navbar-collapse ul.navbar-nav {
  float: right;
  z-index: 199;
}
.head-section .navbar-nav > li {
  padding-bottom: 25px;
  padding-top: 30px;
  padding-left: 35px;
}
.search {
  width: 50px;
  height: 35px;
  padding: 2px;
  border: 2px solid #dadada;
}
.search i {
  font-size: 20px;
}
.dropdown img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  padding: 2px;
  border: 1px solid #dadada;
}
.dropdown-menu {
  margin-top: -5px;
  padding: 0;
  box-shadow: none;
}
.dropdown-menu i {
  background: #f36868;
  float: right;
  padding: 3px;
  font-size: 13px;
  display: block;
  color: #fff;
  border-radius: 4px;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  -o-border-radius: 4px;
  font-style: normal;
}
.dropdown-menu a {
  color: #999;
  font-size: 14px;
  font-weight: 300;
  background: none;
}
.dropdown-menu li:hover {
  background: #dfdfdff5;
  color: #fff;
}
.head-section .navbar-nav > li > a {
  padding-bottom: 5px;
  padding-top: 5px;
  border-radius: 3px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  margin-left: 2px;
  line-height: 30px;
  -webkit-transition: all 0.3s ease;
  -moz-transition: all 0.3s ease;
  -ms-transition: all 0.3s ease;
  -o-transition: all 0.3s ease;
  transition: all 0.3s ease;
}
.head-section .nav li a,
.head-section .nav li.active ul.dropdown-menu li a {
  color: #999;
  font-size: 20px;
  font-weight: 800;
  background: none;
}
.head-section .navbar-nav > li > a:hover {
  background: #9ea3a2;
  color: #fff;
}
</style>