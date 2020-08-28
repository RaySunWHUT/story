<template>
  <div class="main_box">
    <myArticle :ideaInfo="ideaInfo"></myArticle>

    <div class="CommentBotton" v-if="!flag" @click="getComment">
      <i>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="icon"
          viewBox="0 0 1024 1024"
          width="25"
          height="25"
          t="1598082296514"
          p-id="8643"
          version="1.1"
        >
          <path
            fill="#6f6f6f"
            d="M 928.677 0 H 95.323 a 36.1072 36.1072 0 0 0 -36.1072 36.1072 v 729.365 a 36.1072 36.1072 0 0 0 36.1072 36.1072 h 320.993 v -72.2144 H 131.791 V 72.2144 h 760.779 v 656.79 H 770.528 a 36.1072 36.1072 0 0 0 -20.22 6.13822 l -340.491 228.559 l 40.4401 60.299 l 331.464 -222.42 h 146.956 a 36.1072 36.1072 0 0 0 36.1072 -36.1072 V 36.1072 a 36.1072 36.1072 0 0 0 -36.1072 -36.1072 Z"
            p-id="8644"
          ></path>
          <path
            fill="#6f6f6f"
            d="M 329.659 400.79 m -51.9944 0 a 51.9944 51.9944 0 1 0 103.989 0 a 51.9944 51.9944 0 1 0 -103.989 0 Z"
            p-id="8645"
          ></path>
          <path
            fill="#6f6f6f"
            d="M 512 400.79 m -51.9944 0 a 51.9944 51.9944 0 1 0 103.989 0 a 51.9944 51.9944 0 1 0 -103.989 0 Z"
            p-id="8646"
          ></path>
          <path
            fill="#6f6f6f"
            d="M 694.341 400.79 m -51.9944 0 a 51.9944 51.9944 0 1 0 103.989 0 a 51.9944 51.9944 0 1 0 -103.989 0 Z"
            p-id="8647"
          ></path>
        </svg>
      </i>
      <span>展开评论</span>
    </div>

    <transition name="plus-icon">
      <myUserComment v-if="flag" :comments="comments"></myUserComment>
    </transition>
  </div>
</template>

<script>
import storage from "@/utils/storage";
import http from "@/utils/http";
import myArticle from "@/components/article/Article";
import myUserComment from "@/components/article/UserComment";

export default {
  components: { myArticle, myUserComment },
  
  created() {

    var ideaId = this.$route.query.ideaId;

    var _this = this;

    console.log(ideaId);

    http({

      // 假设后台需要的是表单数据这里你就可以更改
      headers: {

      "Content-Type": "application/json;charset=UTF-8"
      
      },

      method: 'post',
      url: 'http://localhost:8080/idea/listOneIdea',

      data: {
        
        ideaId: ideaId

      },

      responseType: 'json'

      }).then(function (res) {

          console.log(res);

          var code = res.code;
          var info = res.info;

          if (res.code == 200) {
              
              _this.ideaInfo.title = info.title;
              _this.ideaInfo.article = info.content;
              _this.ideaInfo.author = info.userAccount;
              _this.ideaInfo.star = info.likes;
              _this.ideaInfo.eye = info.visits;
          
          } else {
              
              _this.$message.error(info);

          }

      }).catch(function (err) {

        _this.$message.error("系统错误！");
  
  });




  },

  data() {
    return {
      flag: false,

      //  文章详情内容
      ideaInfo: {

        article: "",
        author: "",
        title: "",
        star: "",
        eye: ""
      
      },

      //评论总数存入Vuex吧
      commentCount: "",

      // 评论相关内容
      comments: [
        {
          author: "不知名男作者1",
          comment:
            "这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错",
          avatar: "https://i.niupic.com/images/2020/08/22/8yLU.jpg",
          // 二级评论
          secondComment: [
            {
              author: "不知名男作者1二级作者",
              comment:
                "这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错",
              avatar: "https://i.niupic.com/images/2020/08/22/8yLU.jpg",
              father: "不知名作者1"
            },
            {
              author: "不知名男作者2二级作者",
              comment:
                "这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错",
              avatar: "https://i.niupic.com/images/2020/08/22/8yLU.jpg",
              father: "不知名作者1"
            }
          ]
        },
        {
          author: "不知名男作者2",
          comment:
            "这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错",
          avatar: "https://i.niupic.com/images/2020/08/22/8yLU.jpg",
          father: "不知名作者1"
        },

        {
          author: "不知名男作者3",
          comment:
            "这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错这篇文章写的真不错",
          avatar: "https://i.niupic.com/images/2020/08/22/8yLU.jpg"
        }
      ]
    };
  },


  
  methods: {
    getComment() {
      this.flag = true;

      this.$axios({
        methods: "get",
        url: "/parameter/demoList"
      }).then(res => {
        this.comments = res.data.comments;
        console.log(this.comments);
      });
    }
  }
};
</script>

<style lang="less" scoped>
.main_box {
  width: 800px;
  height: auto;
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
}
.plus-icon-enter-active {
  transition: all 1s;
}

.plus-icon-enter {
  opacity: 0;
  margin-top: 500px;
}
.plus-icon-leave-active {
  transition: all 1s;
}
.plus-icon-leave-to {
  opacity: 0;
  margin-top: 500px;
}
.CommentBotton {
  width: 200px;
  height: 80px;
  left: 50%;
  margin-top: 100px;
  position: absolute;
  transform: translate(-50%);
  border: 1px solid #6f6f6f;
  border-radius: 2px;
  color: #6f6f6f;
  transition: all 0.3;
  i {
    position: absolute;
    top: 28px;
    left: 25px;
  }
  span {
    font-size: 20px;
    position: absolute;
    top: 30px;
    right: 50px;
  }
}
</style>