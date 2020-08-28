<template>
  <div>
    <div class="message-content-head">
      <span>已投递</span>
    </div>
    <myRecordinfo v-for="(item, index) in recordList" :key="index" :record="item"></myRecordinfo>

    <div class="pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :page-count="pageCount"
        :current-page="currentPage"
        :page-size="3"
        @current-change="currentchange"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import storage from "@/utils/storage";
import http from "@/utils/http";
import myRecordinfo from "@/components/createCenter/RecordInfo";
import myComment from "@/components/article/CommentArea";
export default {
  name: "message",

  inject:['reload'],

  components: { myRecordinfo, myComment },

  data() {
    
    return {
      
      show3: false,
      pageCount: 7,
      currentPage: 1,

      recordList: []
    
    };
  
  },
  
  methods: {
    
    currentchange: function(currentPage) {

      this.currentPage = currentPage;
      console.log(this.currentPage);
      console.log("发送http请求");
    
    }
  
  },

    created() {

    var _this = this;

    // if (!storage.get("publishedList")) {

      http({

        // 假设后台需要的是表单数据这里你就可以更改
        headers: {

        "Content-Type": "application/json;charset=UTF-8"
        
        },

        method: 'post',
        url: 'http://localhost:8080/idea/listAllIdeasVisible',

        data: {

          userAccount: JSON.parse(_this.$store.getters.getUser).userAccount,
          visibility: 1

        },

        responseType: 'json'

        }).then(function (res) {

            console.log(res);

            var code = res.code;
            var info = res.info;

            if (res.code == 200) {

              storage.set("publishedList", JSON.stringify(info));

              _this.$store.commit('setpublishedList', storage.get('publishedList'));

              _this.recordList = JSON.parse(_this.$store.getters.getPublishedList);

              // _this.$message.success("Record.vue: setPublishedList")
              
            } else {
                
                _this.$message.error(info);

            }

        }).catch(function (err) {

            _this.$message.error("Record.vue: 系统错误！");
    
        });

  },

  beforeMount() {
    
  }
};
</script>

<style lang="less">
.el-pagination {
  width: 500px;
}
</style>


<style lang="less" scoped>
.mycomment {
  transform: scale(0.74);
  position: relative;
  top: -120px;
  left: -95px;
}

.comment_box {
  width: 100%;
  height: 120px;
}

.transition-box {
  margin-bottom: 10px;
  width: 200px;
  height: 100px;
  border-radius: 4px;
  background-color: #409eff;
  text-align: center;
  color: #fff;
  padding: 40px 20px;
  box-sizing: border-box;
  margin-right: 20px;
}

.pagination {
  width: 400px;
  padding-left: 100px;
  padding-top: 30px;
}
.message-content-head {
  position: relative;
  width: 600px;
  height: 42px;
  border-radius: 4px;
  color: #666;
  padding: 0 16px;
  box-shadow: 0px 2px 4px 0px rgba(121, 146, 185, 0.54);
  span {
    line-height: 42px;
    font-size: 14px;
  }
  margin-bottom: 20px;
}
</style>