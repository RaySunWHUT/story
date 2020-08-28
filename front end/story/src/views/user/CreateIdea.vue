<template>
    <div class="md">

        <el-input placeholder="请输入标题..." v-model="title"></el-input>

        <mavon-editor 
            class="mavon"
            v-model="content" 
            ref="md" 
            @change="change" 
            style="min-height: 600px"
        ></mavon-editor>

        <div class="btn">
            <el-button type="primary" @click="submit()">提交</el-button>
            <el-button type="danger" @click="save()">保存</el-button>
        </div>
        
    <router-view class="router-view"></router-view>
    <Footer></Footer>
    </div>
    
</template>



<script>
import storage from '@/utils/storage';
import Footer from '@/components/Footer.vue'

import { mavonEditor } from 'mavon-editor'

import http from '@/utils/http';

export default {
    
    name: 'createIdea',

    // 注册
    components: {

        mavonEditor,
        Footer
    
    },

    data () {

        return {

            ideaId: null,
            title: '',
            content: '', // 输入的markdown
            html: '',    // 及时转的html
            visibility: null,
            likes: 0,
            visits: 0,
            typeId: null,
            action: null

        }

    },

    created() {

        var _this = this;

        var ideaId = this.$route.query.ideaId;
        var action = this.$route.query.action;

        this.action = action;
        this.ideaId = ideaId;

        // 若为草稿发布
        if (ideaId != null && action == "post") {

            var draftList = JSON.parse(this.$store.getters.getDraftList);

            for (var i = 0; i < draftList.length; i++) {
                
                if (draftList[i].ideaId == ideaId) {

                    _this.ideaId = draftList[i].ideaId;
                    _this.title = draftList[i].title;
                    _this.content = draftList[i].content;

                }

            }
        
        // 若为idea更新
        } else if (ideaId != null && action == "update") {

            var publishedList = JSON.parse(this.$store.getters.getPublishedList);

            var typeList = JSON.parse(_this.$store.getters.getTypeList);

            for (var i = 0; i < publishedList.length; i++) {
                
                if (publishedList[i].ideaId == ideaId) {
                    
                    _this.ideaId = publishedList[i].ideaId;
                    _this.title = publishedList[i].title;
                    _this.content = publishedList[i].content;

                    for (var j = 0; j < typeList.length; j++) {

                        if (typeList[j].typeName == publishedList[i].typeName) {

                            _this.typeId = typeList[j].typeId;
                        
                        }

                    }

                }

            }

        // 非修改和草稿发布, 而是直接进行创作！
        } else if (_this.action == null && _this.ideaId == null) {

            if (storage.get("title") != null) {
                _this.title = storage.get("title");
            }
            
            if (storage.get("content") != null) {
                _this.content = storage.get("content");
            }

        }
    
    },

    methods: {

        // 所有操作都会被解析重新渲染
        change (value, render) {
        
            // render 为 markdown 解析后的结果[html]
            this.html = render;
        
        },


        submit () {  // 提交

            var _this = this;

            this.visibility = 1;

            if (this.title == null || this.title == "") {

                this.$message.warning("标题不能为空!");

            } else if (this.content == null || this.content == "") {

                this.$message.warning("想法内容不能为空!");
            
            } else {    // 提交(直接创作, 则为ideaId = null, action = null)

                var obj = new Object();

                obj.ideaId = _this.ideaId;
                obj.title = _this.title;
                obj.content = _this.content;
                obj.html = _this.html;
                obj.likes = _this.likes;
                obj.visits = _this.visits;
                obj.visibility = _this.visibility;
                obj.typeId = _this.typeId;

                storage.set("article", JSON.stringify(obj));
                _this.$store.commit('saveArticle', storage.get('article'));
                _this.$router.push('/user/createIdea/public');

            }



        },

        save() {    // 保存

            var _this = this;
            
            this.visibility = 0;

            var obj = new Object();
            obj.ideaId = _this.ideaId;
            obj.title = _this.title;
            obj.content = _this.content;
            obj.html = _this.html;
            obj.likes = _this.likes;
            obj.visits = _this.visits;
            obj.visibility = _this.visibility;

                        
            console.log(obj);
            
            if (this.ideaId == null) {  // 若从零开始创作, 并保存为草稿, 默认类型为"随笔"

                obj.typeId = 4;
 
            } else {    // 其余情况保存为原类型

               obj.typeId =  _this.typeId;
            
            }


            storage.set("article", JSON.stringify(obj));
            _this.$store.commit('saveArticle', storage.get("article"));

            var user = JSON.parse(_this.$store.getters.getUser);
            
            http({

            // 假设后台需要的是表单数据这里你就可以更改
            headers: {

            "Content-Type": "application/json;charset=UTF-8"
            
            },

            method: 'post',
            url: 'http://localhost:8080/idea/storeIdea',

            data: {
              
                ideaId: _this.ideaId,
                userAccount: user.userAccount,
                title: _this.title,
                content: _this.content,
                typeId: 4,
                visibility: _this.visibility,
                likes: _this.likes,
                visits: _this.visits

            },

            responseType: 'json'

            }).then(function (res) {

                console.log(res);

                var code = res.code;
                var info = res.info;

                if (res.code == 200) {
                
                    _this.$message.success("提交成功！");
                    _this.$router.replace("/user/createCenter/draft");
                
                } else {
                    
                    _this.$message.error(info);

                }

            }).catch(function (err) {

              _this.$message.error("CreateIdea.vue: 系统错误！");
        
        });


        },

        listen(property, value) {

            if (property == "title") {

                this.title = value;
                storage.set("title", this.title);

            } else if (property == "content") {
                
                this.content = value;
                storage.set("content", this.content);
            
            }

        }

    },

    watch: {
        
        title(newVal, oldVal) {

            this.listen("title", newVal);
        
        },
        content(newVal, oldVal) {
        
            this.listen("content", newVal);
        
        }
    }

}
</script>

<style scoped lang="less">

// .el-input__inner {
//     -webkit-appearance: none;
//     background-color: #FFF;
//     background-image: none;
//     border-radius: 4px;
//     border: 1px solid #DCDFE6;
//     box-sizing: border-box;
//     color: #1c1d1f;
//     display: inline-block;
//     font-size: inherit;
//     height: 40px;
//     line-height: 40px;
//     outline: 0;
//     padding: 0 15px;
//     transition: border-color .2s cubic-bezier(.645,.045,.355,1);
//     width: 100%;
// }

.md {
    z-index: -10;
    width: 100%;
}

.md mavon-editor {

    width: 500px;
    z-index: -10;

}
.mavon{
    position: relative;
    z-index: 22;
}

.md button {

    padding: 14px 20px;
    font-size: 18px;
    margin: 12px;

}

.btn {
    min-width: 200px;
    margin-left: 85%;
}



.title span{
    width: 200px;
    height: 100px;
    border: 1px solid gray;
    border-radius: 4px;
    font-size: 20px;
    font-weight: 500;
    
}
.router-view{
    position: absolute;
    top: 0px;
    z-index: 999;
    width: 100%;
    height: 100%;
    background: #000;
}
</style>