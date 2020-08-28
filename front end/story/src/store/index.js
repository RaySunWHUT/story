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

        article: {

            ideaId: null,
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

    },

    // 用来直接修改state内的数据；在组件内，通过this.$store.commit(方法名)来执行mutations
    // mutations: 本质上是一个对象
    // mutations中主要是改变state初始值的方法, 用法: 通过传入state或额外参数
    // 利用vue的双向驱动进行state中值的修改;
    mutations: {

        createUser(state, user) {

            state.user = user;

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

            state.article = storage.get("article");
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