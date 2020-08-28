import Vue from 'vue'
import Vuex from 'vuex'
import storage from '../utils/storage'

Vue.use(Vuex)

export default new Vuex.Store({

    state: { // 数据保存在state内，在任何组件内通过this.$store.state.XX来读取

        user: {

            token: null,
            userAccount: null,
            password: null,

        },

        typeList: [],

        isShow: false,
        toWho: "请输入内容",
        
        //idea详情页评论总数
        commentCount:5,
        
        articleEye:527,
        articleStar:17,

        article: {

            title: null,
            content: null,
            visibility: null,
            likes: null,
            visits: null,
            typeId: null

        },

        publishedList: [],
        draftList: [],
        userInfo: [],

        comments: [
            {
              author: "可爱小豆",
              comment:
                "小怪兽，我又到日本了，你还好吗？",
              avatar: "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3659349001,709470916&fm=26&gp=0.jpg",
              // 二级评论
              secondComment: [
                {
                  author: "一天五顿",
                  comment:
                    "江南老贼没有心",
                  avatar: "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598440846929&di=d807df3b61bb0fe67495e10d74d27c31&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F-fo3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2Ff11f3a292df5e0feaf1add265d6034a85edf7292.jpg",
                  father: "可爱小豆"
                },
                {
                  author: "每天睡不着",
                  comment:
                    "我们都是小怪兽总有一天会被正义的奥特曼杀死",
                  avatar: "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598441062819&di=c769c5807c4bf726ce2f4bd711769441&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201804%2F09%2F20180409125847_qyjvc.jpg",
                  father: "可爱小豆"
                }
              ]
            },
            {
              author: "长得帅老的快",
              comment:
                "总有一些孤独曾在我们左右，也总有一些孤独离我们远去，让我们无处寻觅，一切都过去了，是的，阳光里，不会再有天使低下头",
              avatar: "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598441084162&di=f0f81105052d526fe6835e106743cf09&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201508%2F24%2F20150824153630_sRx4Q.jpeg",
              father: "不知名作者1"
            },
    
            {
              author: "不知名男作者",
              comment:
                "落尽樱花君不见，轻绘梨衣泪沾衣",
              avatar: "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598441084162&di=af30ddd332051148a0b0511050bebe6d&imgtype=0&src=http%3A%2F%2Fp5.gexing.com%2FGSF%2Ftouxiang%2F20200723%2F19%2F3e8be63ec6a8a32055a7d489a75378f9.jpg%40%2521200x200_3%3Frecache%3D20131108"
            }
          ]

    },


  

    // 用来直接修改state内的数据；在组件内，通过this.$store.commit(方法名)来执行mutations
    // mutations: 本质上是一个对象
    // mutations中主要是改变state初始值的方法, 用法: 通过传入state或额外参数
    // 利用vue的双向驱动进行state中值的修改;
    mutations: {

        addComment(state,data){
            state.comments[0].secondComment.push(data);
        },

        createUser(state, user) {

            state.user = user;

        },

        commentCount(state){
            state.commentCount++;
        },

        addEye(state){
            state.articleEye++;
        },

        addStar(state){
            state.articleStar++;
        },

        saveArticle(state, article) {

            state.article = article;

        },

        keepArticleType(state, type) {
            state.article.typeId = type;
        },

        setTypeList(state, typeList) {

            state.typeList = typeList;

        },


        setDraftList(state, draftList) {

            state.draftList = draftList;

        },

        setpublishedList(state, publishedList) {

            state.publishedList = publishedList;

        },


        setUserInfo(state, userInfo) {

            state.userInfo = userInfo;

        },

        changeShow(state) {

            state.isShow = !state.isShow;

        },

        setToWho(state, data) {

            state.toWho = data;

        }

    },

    // 提交的是mutation, 并且可以异步操作
    // 异步触发mutations中的方法
    // action中的自定义函数: 一个context(上下文)以及要变化的 “形参”
    // context与store实例具有相同的方法和属性, 故context.commit('')有效！
    // 作用：存在业务逻辑
    // action在组件内通过this.$store.dipatch(方法名)来触发
    actions: {

        // this.$store.dispatch('commitToken');
        // commitToken(context) {
        //     context.commit('setToken');
        // },

        // value为要变化的形参
        // this.$store.dispatch('setPassword', value);
        // setPassword(context, value) {
        //     context.password = value;
        // },

    },

    // 将各个组件中的computed中的方法提取出来
    // 实时监测state中变量值的变化
    // 可以通过this.$store.state.xxx获取变量值, 但getters更专业(类似于Bean的getter方法)
    getters: {

        // 方法名随意
        // _this.$store.getters.getUser
        getUser(state) {

            state.user = storage.get('user');
            return state.user;

        },

        getTypeList(state) {
            state.typeList = storage.get("typeList");
            return state.typeList;
        },

        getArticle(state) {

            state.article = storage.get('article');
            return state.article;

        },

        getUserInfo(state) {
            state.userInfo = storage.get("userInfo");
            return state.userInfo;
        },

        getPublishedList(state) {

            state.publishedList = storage.get("publishedList");
            return state.publishedList;

        },

        getDraftList(state) {

            state.draftList = storage.get("draftList");
            return state.draftList;

        }


    },

    modules: { // 用来将store分割到不同模块

    }

})