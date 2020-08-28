<template>
  <div class="public">

    <div class="form">
      <div class="do">
        <span>发布想法</span>
      </div>

      <div class="select">
        想法列表:
        <el-select v-model="selectedType" placeholder="请选择想法类型">
          <el-option
            v-for="item in typeList"
            :key="item.typeId"
            :label="item.typeName"
            :value="item.typeId">
          </el-option>
        </el-select>
      </div>

      <div class="btn">
        <el-button type="primary" @click.native="publish()">发布</el-button>
        <el-button @click.native="cancel()">取消</el-button>
      </div>
    </div> 

  </div>
</template>

<script>

import http from '@/utils/http';
import storage from '@/utils/storage';

export default {

  data() {
    
    return {

      typeList: [],
      selectedType: null,
      status: null

    };
  },

  created() {

    this.typeList = JSON.parse(this.$store.getters.getTypeList);

    var typeId = JSON.parse(this.$store.getters.getArticle).typeId;

    if (typeId != null) {

      this.selectedType = this.getTypeNameByTypeId(typeId);

    }

  },

  methods: {
    
    getTypeIdByTypeName(typeName) {

      for (var i = 0; i < this.typeList.length; i++) {

        if (typeName == this.typeList[i].typeName) {

            return this.typeList[i].typeId;

        }

      }

    },

    getTypeNameByTypeId(typeId) {

      for (var i = 0; i < this.typeList.length; i++) {

        if (typeId == this.typeList[i].typeId) {

            return this.typeList[i].typeName;

        }

      }

    },

    publish() { // 发布分为三种: 1. 已投递更新; 2. 草稿发布; 3. 从零开始创作

      var _this = this;

      var user = JSON.parse(_this.$store.getters.getUser);
      var article = JSON.parse(_this.$store.getters.getArticle);

      if (article.visibility == 1) {  // 修改

        this.status = "update";

      } else {
        
        this.status = "post";

      }

      // 若为从零开始创作
      if (article.ideaId == null) {

        this.status = "zero";

      } 

      http({

            // 假设后台需要的是表单数据这里你就可以更改
            headers: {

            "Content-Type": "application/json;charset=UTF-8"
            
            },

            method: 'post',
            url: 'http://localhost:8080/idea/ideaPost',

            data: {

                userAccount: user.userAccount,
                ideaId: article.ideaId,
                typeId: _this.selectedType,
                title: article.title,
                content: article.content,
                visibility: article.visibility,
                likes: article.likes,
                visits: article.visits,
                status: _this.status

            },

            responseType: 'json'

            }).then(function (res) {
              
              console.log(res);

              console.table(article);

                var code = res.code;
                var info = res.info;

                storage.remove("article");

                if (res.code == 200) {
                
                    _this.$message.success("提交成功！");
                    _this.$router.replace("/user/createCenter/draft");
                
                } else {
                    
                    _this.$message.error(info);

                }

            }).catch(function (err) {

              _this.$message.error("Public.vue: 系统错误！");
        
        });

    },

    cancel() {

      this.$router.push("/user/createIdea");
    
    }
  
  }

};
</script>

<style lang="less" scoped>

.btn {

  position: relative;

  left: 85px;
  top: 200px;

  float: left;

}

.do {
  font-size: 28px;
  font-weight: bold;
  color: black;
  position: relative;
  top: 10px;
  left: 10px;
}

.select {

  position: relative;
  top: 30px;
  left: 10px;

}


.public {
  opacity: 0.8;
  background-color: rgb(177, 175, 175);

  bottom: 0;

  left: 0;

  position: fixed;
  width: 100%;
  z-index: 998;
}

.form {
  position: absolute;
  transform: translate(-50%, 50%);
  left: 50%;
  right: 50%;
  width: 350px;
  height: 350px;
  background-color: rgb(231, 231, 231);
  z-index: 13;
}

</style>