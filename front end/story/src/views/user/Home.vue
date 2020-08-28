<template>
  <!-- <el-main> -->
  <div>
    <!-- 四个session内容大致一样，就是获取对象不同，第一个是书籍，
    第二个是音乐，第三个是电影，第四个是随笔-->
    <img class="banner" src="@/assets/banner.jpg" alt="banner" />
    <section class="blog">
      <div class="container">
        <div class="row text-center clearfix">
          <div class="col-sm-8 col-sm-offset-2">
            <!-- idea的type -->
            <h2 class="title-one">书籍分享推荐</h2>
            <p class="blog-heading"></p>
          </div>
        </div>
        <div class="row">
          <!-- 渲染列表，包括用户头像，标题名，内容  只获取热度前六个-->
          <div class="col-sm-4" v-for="(book, index) in books" :key="index">
            <div class="single-blog" v-if="index < 6" @click="book.views++">
              <!-- 用户头像 -->
              <router-link :to="{path: '/ideaInfo', query: {ideaId: book.ideaId}}">
                <img v-bind:src="book.userImg" alt />
              </router-link>
              <!-- idea标题 -->
              <router-link :to="{path: '/ideaInfo', query: {ideaId: book.ideaId}}">
                <h2>{{book.title}}</h2>
              </router-link>

              <div class="blog-content">
                <!-- idea内容 -->
                <router-link :to="{path: '/ideaInfo', query: {ideaId: book.ideaId}}">
                  <p>{{book.content}}</p>
                </router-link>
              </div>

              <ul class="post-meta">
                <li @click="book.commentCount++">
                  <!-- 点击评论跳转到idea详情页 将整个book对象传到about-->
                  <router-link :to="{path: '/ideaInfo', query: {ideaId: book.ideaId}}">
                    <i class="glyphicon glyphicon-comment"></i>
                    {{book.commentCount}}
                  </router-link>
                </li>
                <!-- 点赞 +1-->
                <li @click="book.likes++">
                  <i class="glyphicon glyphicon-heart"></i>
                  {{book.likes}}
                </li>
                <!-- 热度 -->
                <div class="view">
                  <i class="glyphicon glyphicon-eye-open"></i>
                  {{book.views}}
                </div>
              </ul>
            </div>
          </div>
        </div>
        <div class="exchange-btn">
          <!-- 跳转到Book.vue 获取全部书籍分享列表 -->
          <button class="more">
            <router-link to="/book">
              更多
              <i class="glyphicon glyphicon-option-horizontal"></i>
            </router-link>
          </button>
        </div>
        <p class="blog-heading"></p>
      </div>
    </section>
    <section class="blog">
      <div class="container">
        <div class="row text-center clearfix">
          <div class="col-sm-8 col-sm-offset-2">
            <!-- idea的type -->
            <h2 class="title-one">音乐分享推荐</h2>
            <p class="blog-heading"></p>
          </div>
        </div>
        <div class="row">
          <!-- 渲染列表，包括用户头像，标题名，内容  只获取热度前六个-->
          <div class="col-sm-4" v-for="(music,index) in musics" :key="index">
            <div class="single-blog" v-if="index<6" @click="music.views++">
              <!-- 用户头像 -->
              <router-link :to="{path: '/ideaInfo', query: {ideaId: music.ideaId}}">
              <img v-bind:src="music.userImg" alt />
              </router-link>
              <!-- idea标题 -->
              <router-link :to="{path: '/ideaInfo', query: {ideaId: music.ideaId}}">
              <h2>{{music.title}}</h2>
              </router-link>
              
              <div class="blog-content">
                <!-- idea内容 -->
                <router-link :to="{path: '/ideaInfo', query: {ideaId: music.ideaId}}">
                <p>{{music.content}}</p>
                </router-link>
              </div>
              
              <ul class="post-meta">
                <li @click="music.commentCount++">
                  <!-- 点击评论跳转到idea详情页 将整个music对象传到about-->
                  <router-link :to="{path: '/ideaInfo', query: {ideaId: music.ideaId}}">
                    <i class="glyphicon glyphicon-comment"></i>
                    {{music.commentCount}}
                  </router-link>
                  
                </li>
                <!-- 点赞 +1-->
                <li @click="music.likes++">
                  <i class="glyphicon glyphicon-heart"></i>
                  {{music.likes}}
                </li>
                <!-- 热度 -->
                 <div class="view">
                  <i class="glyphicon glyphicon-eye-open"></i>
                  {{music.views}}
                </div>
              </ul>
            </div>
          </div>
        </div>
        <div class="exchange-btn">
          <!-- 跳转到Music.vue 获取全部音乐分享列表 -->
          <button class="more">
            <router-link to="/music">
              更多
              <i class="glyphicon glyphicon-option-horizontal"></i>
            </router-link>
          </button>
        </div>
        <p class="blog-heading"></p>
      </div>
    </section>
    <section class="blog">
      <div class="container">
        <div class="row text-center clearfix">
          <div class="col-sm-8 col-sm-offset-2">
            <!-- idea的type -->
            <h2 class="title-one">电影分享推荐</h2>
            <p class="blog-heading"></p>
          </div>
        </div>
        <div class="row">
          <!-- 渲染列表，包括用户头像，标题名，内容  只获取热度前六个-->
          <div class="col-sm-4" v-for="(video,index) in videos" :key="index">
            <div class="single-blog" v-if="index < 6" @click="video.views++">
              <!-- 用户头像 -->
              <router-link :to="{path: '/ideaInfo', query: {ideaId: video.ideaId}}">
              <img v-bind:src="video.userImg" alt />
              </router-link>
              <!-- idea标题 -->
              <router-link :to="{path: '/ideaInfo', query: {ideaId: video.ideaId}}">
              <h2>{{video.title}}</h2>
              </router-link>
              
              <div class="blog-content">
                <!-- idea内容 -->
                <router-link :to="{path: '/ideaInfo', query: {ideaId: video.ideaId}}">
                <p>{{video.content}}</p>
                </router-link>
              </div>
              
              <ul class="post-meta">
                <li @click="video.commentCount++">
                  <!-- 点击评论跳转到idea详情页 将整个music对象传到about-->
                  <router-link :to="{path: '/ideaInfo', query: {ideaId: video.ideaId}}">
                    <i class="glyphicon glyphicon-comment"></i>
                    {{video.commentCount}}
                  </router-link>
                  
                </li>
                <!-- 点赞 +1-->
                <li @click="video.likes++">
                  <i class="glyphicon glyphicon-heart"></i>
                  {{video.likes}}
                </li>
                <!-- 热度 -->
                 <div class="view">
                  <i class="glyphicon glyphicon-eye-open"></i>
                  {{video.views}}
                </div>
              </ul>
            </div>
          </div>
        </div>
        <div class="exchange-btn">
          <!-- 跳转到Video.vue 获取全部电影分享列表 -->
          <button class="more">
            <router-link to="/video">
              更多
              <i class="glyphicon glyphicon-option-horizontal"></i>
            </router-link>
          </button>
        </div>
        <p class="blog-heading"></p>
      </div>
    </section>
    <section class="blog">
      <div class="container">
        <div class="row text-center clearfix">
          <div class="col-sm-8 col-sm-offset-2">
            <!-- idea的type -->
            <h2 class="title-one">随笔推荐</h2>
            <p class="blog-heading"></p>
          </div>
        </div>
        <div class="row">
          <!-- 渲染列表，包括用户头像，标题名，内容  只获取热度前六个-->
          <div class="col-sm-4" v-for="(note, index) in notes" :key="index">
            <div class="single-blog" v-if="index < 6" @click="note.views++">
              <!-- 用户头像 -->
              <router-link :to="{path: '/ideaInfo', query: {item: note.ideaId}}">
              <img v-bind:src="note.userImg" alt />
              </router-link>
              <!-- idea标题 -->
              <router-link :to="{path: '/ideaInfo', query: {item: note.ideaId}}">
              <h2>{{note.title}}</h2>
              </router-link>
              
              <div class="blog-content">
                <!-- idea内容 -->
                <router-link :to="{path: '/ideaInfo', query: {item: note.ideaId}}">
                <p>{{note.content}}</p>
                </router-link>
              </div>
              
              <ul class="post-meta">
                <li @click="note.commentCount++">
                  <!-- 点击评论跳转到idea详情页 将整个note对象传到about-->
                  <router-link :to="{path: '/ideaInfo', query: {item: note.ideaId}}">
                    <i class="glyphicon glyphicon-comment"></i>
                    {{note.commentCount}}
                  </router-link>
                  
                </li>
                <!-- 点赞 +1-->
                <li @click="note.likes++">
                  <i class="glyphicon glyphicon-heart"></i>
                  {{note.likes}}
                </li>
                <!-- 热度 -->
                 <div class="view">
                  <i class="glyphicon glyphicon-eye-open"></i>
                  {{note.views}}
                </div>
              </ul>
            </div>
          </div>
        </div>
        <div class="exchange-btn">
          <!-- 跳转到Note.vue 获取全部随笔分享列表 -->
          <button class="more">
            <router-link to="/note">
              更多
              <i class="glyphicon glyphicon-option-horizontal"></i>
            </router-link>
          </button>
        </div>
        <p class="blog-heading"></p>
      </div>
    </section>
    <Footer></Footer>
  </div>
  <!-- </el-main> -->
</template>
<script>
// 列表的样式
import "../../assets/css/common.css";
import axios from "axios";
import Footer from "@/components/Footer.vue";
export default {
  data() {

    return {

      books: [],
      musics: [],
      videos: [],
      notes: [],
      instance: null

    };

  },

  components: {
    Footer
  },
  created() {
    this.instance = axios.create({
      baseURL: "http://localhost:8081",
      timeout: 1000
    });
    this.getbook();
    this.getmusic();
    this.getvideo();
    this.getnote();
  },
  methods: {

    getbook() {
    
    this.instance
        .get("/book.json")
        .then(res => {
          this.books = res.data;
          console.log(this.books);
        })
        .catch(err => {
          console.log(err);
        });
    },
    getmusic() {

      this.instance
        .get("/music.json")
        .then(res => {
          this.musics = res.data;
          console.log(this.musics);
        })
        .catch(err => {
          console.log(err);
        });

    },
    getvideo() {

      this.instance
        .get("/video.json")
        .then(res => {
          this.videos = res.data;
          console.log(this.videos);
        })
        .catch(err => {
          console.log(err);
        });

    },
    getnote() {

      this.instance
        .get("/note.json")
        .then(res => {
          this.notes = res.data;
          console.log(this.notes);
        })
        .catch(err => {
          console.log(err);
        });

    }

  }
  
};
</script>
<style lang="less" scoped>
.banner {
  width: 100%;
}
.exchange-btn {
  position: relative;
  float: right;
}
.more:hover {
  background: #8b857e;
}
.more {
  height: 30px;
  padding-right: 20px;
  padding-left: 20px;
  outline: none;
  border: none;
  background: #cfcfcf;
  font-size: 20px;
  border-radius: 10px;
  margin-left: 10px;
}
.more a {
  color: #fff;
  text-decoration: none;
}
.more i {
  position: relative;
  float: right;
  padding-left: 5px;
  top: 10px;
}
</style>