<template>
  <div class="main_box">
    <div class="box_header">
      <div class="box_user">
        <div class="box_user_right">
          <div class="user_name">
            {{draft.title}}
            <span>&nbsp;&nbsp;&nbsp; 分类: {{draft.typeName}}</span>
          </div>
          <div class="comment_date">{{draft.updateTime}}</div>

          <div class="comment_delete" @click="deleteDraft()">删除</div>
          <div class="comment_reply" @click="post()">去发送</div>
        </div>
      </div>
      <div class="box_content" @click="edit()">{{article}}</div>
    </div>
  </div>
</template>


<script>
import http from "@/utils/http";
export default {

  name: "draftInfo",
  props: ["draft"],

  inject:['reload'],

  data() {

    return {

      ideaId: null,

    };

  },
  
  methods: {
    deleteDraft() {

      var _this = this;

    http({

      // 假设后台需要的是表单数据这里你就可以更改
      headers: {

      "Content-Type": "application/json;charset=UTF-8"
      
      },

      method: 'post',
      url: 'http://localhost:8080/idea/deleteIdea',

      data: {

        ideaId: _this.draft.ideaId

      },

      responseType: 'json'

      }).then(function (res) {

          console.log(res);

          var code = res.code;
          var info = res.info;

          if (res.code == 200) {
          
              _this.$message.success("删除成功!");
              _this.reload();
          
          } else {
              
              _this.$message.error(info);

          }

        }).catch(function (err) {

            _this.$message.error("系统错误！");

        });

    },

    post() {

      var _this = this;

      this.$router.push({
          
          path: '/user/createIdea',
          
          query: {
            ideaId: _this.draft.ideaId,
            action: "post"
          }

        })
    
    },

    edit() {
      
      var _this = this;

      this.$router.push({
        path: '/user/createIdea',
        query: {
          ideaId: _this.draft.ideaId,
          action: "post"
        }
      })

    }
  },

  computed: {
    article: function() {
        if (this.draft.content.length > 15) {
          return this.draft.content.substring(0, 15) + "...";
        } else {
          return this.draft.content;
        }
    }
  }
};
</script>

<style lang="less" scoped>
.main_box {
  width: 600px;
  padding-bottom: 30px;
  height: auto;
  color: #7d7d7d;
  font-size: 12px;
  box-shadow: 0px 2px 4px 0px rgba(121, 146, 180, 0.54);
}

.box_header {
  padding-top: 10px;
  span {
    font-size: 12px;
  }
}

// .user_avatar {
//   margin-left: 20px;
//   position: absolute;
//   width: 45px;
//   height: 45px;
//   box-shadow: 0px 2px 4px 0px rgba(121, 146, 180, 0.54);
//   border-radius: 50%;
//   img {
//     position: absolute;
//     left: 2px;
//     top: 2px;
//     width: 40px;
//     height: 40px;
//     border-radius: 50%;
//   }
// }

.user_name {
  font-size: 18px;
  font-weight: bold;
  position: absolute;
  left: 10px;
  top: 12px;
  span {
    margin-left: 10px;
  }
}

.comment_info {
  position: absolute;
  right: 90px;
}

.comment_reply {
  font-size: 10px;
  font-weight: bold;
  position: absolute;
  right: 50px;
  top: 15px;
  font-size: 12px;
  margin-top: 1 0px;
  transition: all 0.3s;
}
.comment_reply:hover {
  color: #20ace4;
  cursor: pointer;
}

.comment_delete {
  font-size: 15px;
  font-weight: bold;
  position: absolute;
  right: 10px;
  top: 15px;
  font-size: 12px;
  margin-top: 1 0px;
  transition: all 0.3s;
}

.comment_delete:hover {
  color: red;
  cursor: pointer;
}

.comment_date {
  font-weight: bold;
  position: absolute;
  left: 435px;
  top: 50px;
  font-size: 12px;
  margin-top: 30px;
  letter-spacing: 2px;
}

.box_user {
  position: relative;
  height: 60px;
}
.box_content {
  padding: 0px 20px;
  letter-spacing: 1px;
  line-height: 15px;
  font-size: 15px;
  // margin-top: 5px;
}

.box_content:hover {
  color: red;
  cursor: pointer;
}

.second_show-enter-active,
.second_show-leave-active {
  transition: all 1s ease;
}
.second_show-enter,
.second_show-leave-to {
  opacity: 0;
}
</style>