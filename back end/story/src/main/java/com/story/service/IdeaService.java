package com.story.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.story.domain.Idea;

import java.util.List;

public interface IdeaService {
    JSONObject saveIdea(JSONObject message);
    JSONObject updateIdea(JSONObject message);
    JSONObject deleteIdea(JSONObject message);
    JSONObject listIdeasByUserAccount(JSONObject message);  // 根据用户查找
    JSONObject listIdeasByTypeId(JSONObject message);  // 根据类型查找
    JSONObject listIdeasByTitle(JSONObject message);      // 根据标题查找
    JSONObject listIdeasByHeat(JSONObject message);     //根据热度
    JSONObject listAllIdeasVisible(JSONObject message); //可见的
    JSONObject listAllIdeasInvisible(JSONObject message);//不可见的
    JSONObject listOneIdea(JSONObject message);         //查一个idea
    JSONObject ideaPost(JSONObject message);   //更新可见性
    JSONObject storeIdea(JSONObject message);
}
