<template>
  <div>
      <div>
        <div class="form-wrapper">
          <div class="form-signin wow fadeInUp">
            <div class="login-wrap">
              <div class="userImage">
                <span>头像：</span>
                <el-upload
                  class="avatar-uploader"
                  action="https://jsonplaceholder.typicode.com/posts/"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
                >
                  <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </div>
              <div class="uName">
                <span>昵称：</span>
                <el-input
                  type="text"
                  v-model="userName"
                  :disabled="state"
                  style="width: 300px;"
                ></el-input>
              </div>
              <div class="checkbox">
                <span>性别：</span>
                  <el-radio
                    label="男"
                    v-model="gender"
                    :disabled="state"
                  ></el-radio>
                  <el-radio
                    label="女"
                    v-model="gender"
                    :disabled="state"
                  ></el-radio>
              </div>
              <div class="mail">
                <span>邮箱：</span>
                <el-input
                  type="email"
                  v-model="email"
                  :disabled="state"
                  style="width: 300px;"
                ></el-input>
              </div>
              <div class="personalSignature">
                <span>个性签名：</span>
                <el-input 
                  class="texta"
                  type="textarea" 
                  v-model="sign" 
                  cols="50" 
                  rows="8" 
                  :disabled="state"
                  style="width: 300px; height: 200px;"  
                ></el-input>
              </div>
              <button class="btn btn-lg btn-login" @click="save()">{{message}}</button>
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import Footer from "@/components/Footer.vue";
import http from '@/utils/http';
import storage from '@/utils/storage';
export default {

  name: "PersonalInfo",
  
  inject: ['reload'],

  components: {
    Footer
  },

  created() {
  
    var userInfo = JSON.parse(storage.get('userInfo'));

    console.table(userInfo);

    this.userName = userInfo.userName;
    this.email = userInfo.email;
    this.sign = userInfo.sign;
    this.gender = userInfo.gender;


  },

  data() {

    return {    

      message: "修改",
      userName:"",
      gender: "",
      email:"",
      sign: "",
      imageUrl: "",
      state: true,
      avatar: ""

    };

  },

  methods: {

    save: function() {

      var _this = this;

      if (this.message == "修改") {

        this.state = false;
        this.message = "保存";
      
      } else {

        this.message = "修改";      
        this.state = true;
        
        this.avatar = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598440846929&di=d807df3b61bb0fe67495e10d74d27c31&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F-fo3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2Ff11f3a292df5e0feaf1add265d6034a85edf7292.jpg";

        http({

              // 假设后台需要的是表单数据这里你就可以更改
              headers: {

              "Content-Type": "application/json;charset=UTF-8"
              
              },

              method: 'post',
              url: 'http://localhost:8080/user/updateUser',

              data: {

                  userAccount: JSON.parse(_this.$store.getters.getUser).userAccount,
                  userName: _this.userName,
                  avatar: _this.avatar,
                  gender: _this.gender,
                  email: _this.email,
                  sign: _this.sign

              },
        
              responseType: 'json'

              }).then(function (res) {

                  var code = res.code;
                  var info = res.info;

                  if (res.code == 200) {

                    var userStorageInfo = JSON.parse(storage.get('userInfo'));

                    var userInfo = new Object();
                    
                    userInfo.userId = userStorageInfo.userId;
                    userInfo.roleName = userStorageInfo.roleName;
                    userInfo.userAccount = userStorageInfo.userAccount;

                    userInfo.userName = _this.userName;
                    userInfo.sign = _this.sign;
                    userInfo.avatar = _this.avatar;
                    userInfo.email = _this.email;
                    userInfo.gender = _this.gender;

                    // 覆盖旧的userInfo
                    storage.set("userInfo", JSON.stringify(userInfo));
                    // 刷新！
                    _this.reload();

                    _this.$message.success("修改成功！");
                  
                  } else {

                      _this.$message.success("修改失败！");
                      _this.$message.error(info);
                      
                  }

              }).catch(function (err) {

                  console.log("PersonCenter.vue: save() 错误！");
              
        });

      }

    },
    
    handleAvatarSuccess(res, file) {
    
      this.imageUrl = URL.createObjectURL(file.raw);
    
    },
    
    beforeAvatarUpload(file) {
      
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {

        this.$message.error("上传头像图片只能是 JPG 格式!");

      }

      if (!isLt2M) {

        this.$message.error("上传头像图片大小不能超过 2MB!");

      }

      return isJPG && isLt2M;
    
    }
  
  }

};
</script>

<style scoped>
.form-wrapper {
  padding-bottom: 70px;
}
.form-signin {
  width: 600px;
  margin-left: 55px;
  margin-top: 10px;
  background: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  border-radius: 3px;
  -moz-border-radius: 3px;
  -webkit-border-radius: 3px;
}
.userImage {
  height: 78px;
  overflow: hidden;
  z-index: -2;
  margin-bottom: 40px;
}
.login-wrap {
  padding: 20px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
}
.form-signin span {
  font-size: 20px;
  margin-left: 35px;
}
/* .form-signin input[type="text"] {
  margin-left: 20px;
  margin-bottom: 15px;
  border-radius: 3px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border: 2px solid #eaeaea;
  box-shadow: none;
  font-size: 20px;
} */
.form-signin .checkbox {
  text-align: left;
  font-weight: normal;
  font-weight: 300;
  font-size: 20px;
  margin-bottom: 14px;
}
.form-signin .checkbox > input {
  margin-right: 90px;
}
.mail {
  text-align: left;
  font-weight: normal;
  font-weight: 300;
  font-size: 20px;
  margin-bottom: 14px;
}
.personalSignature textarea {
  margin-left: 60px;
  margin-top: 20px;
  background: #fff;
  border-radius: 3px;
  -moz-border-radius: 3px;
  -webkit-border-radius: 3px;
}
.form-signin .btn-login {
  margin-left: 190px;
  margin-top: 50px;
  width: 25%;
  background: #48cfad;
  color: #fff;
  text-transform: uppercase;
  font-weight: 300;
  margin-bottom: 20px;
  border-radius: 3px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  -webkit-transition: all 0.3s ease;
  -moz-transition: all 0.3s ease;
  -ms-transition: all 0.3s ease;
  -o-transition: all 0.3s ease;
  transition: all 0.3s ease;
  border: 1px solid transparent;
}
.avatar-uploader {
  display: inline;
  vertical-align: middle;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  border: 2px dashed #b3b3b3;
  border-radius: 6px;
  width: 78px;
  height: 78px;
  line-height: 78px;
  text-align: center;
  margin-right: 150px;
}
.avatar {
  width: 78px;
  height: 78px;
  display: block;
}


.personalSignature span {

  position: relative;
  top: -160px;
  left: -40px;
  
}

.texta {

  position: relative;
  top: 20px;
  left: -40px;

}

</style>
