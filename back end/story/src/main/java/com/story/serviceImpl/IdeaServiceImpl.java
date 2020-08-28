package com.story.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.story.constants.Constants;
import com.story.dao.CommentDao;
import com.story.dao.IdeaDao;
import com.story.dao.UserDao;
import com.story.domain.Idea;
import com.story.domain.Type;
import com.story.domain.User;
import com.story.service.IdeaService;
import com.story.util.JSONUtil;
import com.story.util.MarkdownUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("ideaService")
@Transactional
public class IdeaServiceImpl implements IdeaService {

    @Resource
    private IdeaDao ideaDao;

    @Resource
    private CommentDao commentDao;

    @Resource
    private UserDao userDao;

    @Override
    public JSONObject saveIdea(JSONObject message) {

        //获取userId
        String userAccount = message.getString("userAccount");
        User user = userDao.getUserByUserAccount(userAccount);
        message.put("userId", user.getUserId());

        if (!message.containsKey("typeId")) {

            //获取typeId
            Type type = ideaDao.getTypeByTypeName(message);
            message.put("typeId",type.getTypeId());

        }

        Integer result = ideaDao.saveIdea(message);

        if (result != 1) {
            return JSONUtil.errorJSON(Constants.INSERT_FAILED);
        } else {
            return JSONUtil.successJSON(Constants.INSERT_SUCCESS);
        }

    }

    @Override
    public JSONObject updateIdea(JSONObject message) {

        if (!message.containsKey("typeId")) {
            //获取typeId
            Type type = ideaDao.getTypeByTypeName(message);
            message.put("typeId", type.getTypeId());
        }

        Integer result = ideaDao.updateIdea(message);

        if (result != 1) {
            return JSONUtil.errorJSON(Constants.UPDATE_FAILED);
        } else {
            return JSONUtil.successJSON(Constants.UPDATE_SUCCESS);
        }

    }

    @Override
    public JSONObject deleteIdea(JSONObject message) {

        Integer result = ideaDao.deleteIdea(message);

        if (result != 1) {

            return JSONUtil.errorJSON(Constants.DELETE_FAILED);

        } else {

            return JSONUtil.successJSON(Constants.DELETE_SUCCESS);

        }

    }

    /**
     * 根据userId查找idea
     */
    @Override
    public JSONObject listIdeasByUserAccount(JSONObject message) {
        //获取userId
        String userAccount = message.getString("userAccount");
        User user = userDao.getUserByUserAccount(userAccount);
        message.put("userId",user.getUserId());

        List<JSONObject> ideas = ideaDao.listIdeasByUserId(message);
        // 插入markdown格式字符
        for (JSONObject idea : ideas) {

            idea.put("markdownString",markdownStyle(idea));

        }

        if (ideas.size() > 0) {

            return JSONUtil.successJSON(ideas);

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }
    }

    /**
     * 根据typeId查找idea

     */
    @Override
    public JSONObject listIdeasByTypeId(JSONObject message) {

        if (!message.containsKey("typeId")) {

            //获取typeId
            Type type = ideaDao.getTypeByTypeName(message);
            message.put("typeId",type.getTypeId());

        }

        List<JSONObject> ideas = ideaDao.listIdeasByTypeId(message);

        // 插入markdown格式字符
        for (JSONObject idea : ideas) {
            idea.put("markdownString", markdownStyle(idea));
        }
        if (ideas.size() > 0) {

            return JSONUtil.successJSON(ideas);

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }
    }

    /**
     * 根据idea的title来查找id，用的是%title%
     */
    @Override
    public JSONObject listIdeasByTitle(JSONObject message) {

        String title = "%" + message.getString("title") + "%";
        message.put("title", title);
        List<JSONObject> ideas = ideaDao.listIdeasByTitle(message);
        // 插入markdown格式字符
        for (JSONObject idea : ideas) {
            idea.put("markdownString",markdownStyle(idea));
        }
        if (ideas.size() > 0) {

            return JSONUtil.successJSON(ideas);

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }
    }

    /**
     * 根据热度查找
     */
    @Override
    public JSONObject listIdeasByHeat(JSONObject message) {

        List<Idea> ideaList = ideaDao.listAllIdeas();

        for (Idea idea : ideaList) {

            Integer commentNum = commentDao.countComments(idea.getIdeaId());
            idea.setCommentCount(commentNum);
            idea.setMarkdownString(markdownStyle(idea));

        }

        //然后通过比较器来实现排序
        //降序排序
        ideaList.sort((idea1, idea2) -> {
            Integer idea1Heat = idea1.getCommentCount() + idea1.getLikes() + idea1.getVisits();
            Integer idea2Heat = idea2.getCommentCount() + idea2.getLikes() + idea2.getVisits();
            return idea2Heat.compareTo(idea1Heat);
        });

        if (ideaList.size() > 0) {

            return JSONUtil.successJSON(ideaList);

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }

    }


    @Override
    public JSONObject listOneIdea(JSONObject message) {

        JSONObject idea = ideaDao.listOneIdea(message);

        Integer userId = Integer.parseInt(idea.getString("userId"));

        User user = userDao.getUserByUserId(userId);

        idea.remove("userId");

        idea.put("userAccount", user.getUserName());

        idea.put("markdownString", markdownStyle(idea));

        return JSONUtil.successJSON(idea);

    }


    @Override
    public JSONObject ideaPost(JSONObject message) {

        Integer affectRows;

        if (message.getString("status").equals("post")) {

            message.put("visibility", 1);
            affectRows = ideaDao.updateIdeaVisibility(message);

        } else if (message.getString("status").equals("update")) {

            affectRows = ideaDao.updateIdea(message);

        } else {    // status: zero

            String userAccount = message.getString("userAccount");
            User user = userDao.getUserByUserAccount(userAccount);

            message.remove("userAccount");
            message.put("userId", user.getUserId());

            affectRows = ideaDao.saveIdea(message);

        }

        if (affectRows == 1) {

            return JSONUtil.successJSON(Constants.UPDATE_SUCCESS);

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }

    }


    @Override
    public JSONObject storeIdea(JSONObject message) {

        System.out.println(message);

        Integer result;

        // 存在ideaId
        if (message.containsKey("ideaId")) {

            result = ideaDao.updateIdea(message);

        } else {    // 不存在则保存为草稿

            String userAccount = message.getString("userAccount");
            User user = userDao.getUserByUserAccount(userAccount);

            message.remove("userAccount");
            message.put("userId", user.getUserId());

            result = ideaDao.saveIdea(message);

        }

        // 操作成功
        if (result == 1) {

            return JSONUtil.successJSON();

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }

    }

    @Override
    public JSONObject listAllIdeasVisible(JSONObject message) {

        //获取userId
        String userAccount = message.getString("userAccount");
        User user = userDao.getUserByUserAccount(userAccount);
        message.put("userId",user.getUserId());
        List<JSONObject> ideas = ideaDao.listAllIdeasByVisible(message);

        for (JSONObject idea: ideas) {

            String typeId = idea.getString("typeId");

            String typeName = ideaDao.getTypeNameByTypeId(typeId);

            idea.put("typeName", typeName);

            idea.remove("typeId");

        }

        if (ideas.size() > 0) {

            return JSONUtil.successJSON(ideas);

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }
    }

    @Override
    public JSONObject listAllIdeasInvisible(JSONObject message) {

        //获取userId
        String userAccount = message.getString("userAccount");
        User user = userDao.getUserByUserAccount(userAccount);
        message.put("userId",user.getUserId());
        List<JSONObject> ideas = ideaDao.listAllIdeasByInvisible(message);

        for (JSONObject idea: ideas) {

            String typeId = idea.getString("typeId");

            String typeName = ideaDao.getTypeNameByTypeId(typeId);

            idea.put("typeName", typeName);

            idea.remove("typeId");

        }

        if (ideas.size() > 0) {

            return JSONUtil.successJSON(ideas);

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }
    }

    private String markdownStyle(JSONObject message) {
        String table = "| " + message.getString("title") + "|\n" +
                "| ----- | ---- | ----- |\n" +
                "| " + message.getString("content") + "|\n" +
                "\n";
        return MarkdownUtils.markdownToHtmlExtensions(table);
    }

    private String markdownStyle(Idea idea) {
        String table = "| " + idea.getTitle() + "|\n" +
                "| ----- | ---- | ----- |\n" +
                "| " + idea.getContent() + "|\n" +
                "\n";
        return MarkdownUtils.markdownToHtmlExtensions(table);
    }
}
