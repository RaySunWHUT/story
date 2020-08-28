package com.story.dao;

import com.alibaba.fastjson.JSONObject;
import com.story.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
@Mapper
public interface UserDao {

    User getUserByUserAccount(String userAccount);

    JSONObject getRoleByUserAccount(String userAccount);

    List<JSONObject> listUsers(Integer currentBegin, Integer pageSize);

    Integer saveUser(JSONObject jsonObject);

    Integer updateUserPassword(JSONObject jsonObject);

    Integer updateUser(JSONObject jsonObject);

    JSONObject listOneUser(JSONObject jsonObject);

    List<JSONObject> listTypes();

    User getUserByUserId(Integer userId);

}