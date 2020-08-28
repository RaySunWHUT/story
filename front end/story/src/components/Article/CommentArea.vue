<template>
  <div class="main_box3">
    <!-- 发送评论对话框组件   -->
    <div class="userComment"></div>
    <div style="margin: 0px 0;font-size:18px;color:#656d78">发表评论</div>
    <div class="textarea">
      <el-input
        type="textarea"
        :placeholder="this.$store.state.toWho"
        v-model="textarea"
        maxlength="100"
        show-word-limit
      ></el-input>
    </div>
    <div class="footer">
      <el-button @click="send">发表评论</el-button>
    </div>
  </div>
</template>

<script>
import storage from "@/utils/storage";
import http from "@/utils/http";
export default {
  name: "myCommentArea",
  data() {
    return {
      textarea: ""
    };
  },
  methods: {
    send() {
      alert("发布成功");
      var comment = {
        author: "我没有名字",
        comment: this.textarea,
        avatar: "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598440846929&di=d807df3b61bb0fe67495e10d74d27c31&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F-fo3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2Ff11f3a292df5e0feaf1add265d6034a85edf7292.jpg",
        father: "可爱小豆"
      };
      this.$store.commit("addComment",comment);
      this.$store.commit("commentCount");
      this.textarea = ""
    }
  }
};
</script>

<style>
.el-textarea__inner {
  min-height: 100px;
  height: 150px;
  color: #656d78;
}
</style>


<style lang="less" scoped>
.main_box3 {
  margin-top: 100px;
  width: 800px;
  padding-bottom: 30px;
  height: auto;
}
.textarea {
  margin-top: 50px;
  min-height: 150px;
}

.footer {
  margin-top: 30px;
  span {
    font-size: 10px;
  }
}
</style>