package com.story.controller;

import com.alibaba.fastjson.JSONObject;
import com.story.domain.Idea;
import com.story.service.IdeaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = {"/idea"}, method = RequestMethod.POST)
public class IdeaController {

    private static Logger logger = Logger.getLogger(IdeaController.class);

    @Autowired
    private IdeaService ideaService;

    @RequestMapping(value = {"/saveIdea"}, method = RequestMethod.POST)
    public JSONObject saveIdea(@RequestBody JSONObject message) {

        return ideaService.saveIdea(message);

    }

    @RequestMapping(value = {"/updateIdea"}, method = RequestMethod.POST)
    public JSONObject updateIdea(@RequestBody JSONObject message) {

        return ideaService.updateIdea(message);

    }

    @RequestMapping(value = {"/deleteIdea"}, method = RequestMethod.POST)
    public JSONObject deleteIdea(@RequestBody JSONObject message) {

        return ideaService.deleteIdea(message);

    }

    @RequestMapping(value = {"/listIdeasByUserAccount"}, method = RequestMethod.POST)
    public JSONObject listIdeasByUserAccount(@RequestBody JSONObject message) {

        return ideaService.listIdeasByUserAccount(message);

    }

    @RequestMapping(value = {"/listIdeasByTypeName"}, method = RequestMethod.POST)
    public JSONObject listIdeasByTypeName(@RequestBody JSONObject message) {

        return ideaService.listIdeasByTypeId(message);

    }

    @RequestMapping(value = {"/listIdeasByTitle"}, method = RequestMethod.POST)
    public JSONObject listIdeasByTitle(@RequestBody JSONObject message) {

        return ideaService.listIdeasByTitle(message);

    }

    @RequestMapping(value = {"/listIdeasByHeat"}, method = RequestMethod.POST)
    public JSONObject listIdeasByHeat(@RequestBody JSONObject message) {

        return ideaService.listIdeasByHeat(message);

    }

    @RequestMapping(value = {"/listOneIdea"}, method = RequestMethod.POST)
    public JSONObject listOneIdea(@RequestBody JSONObject message) {

        return ideaService.listOneIdea(message);

    }

    @RequestMapping(value = {"/ideaPost"}, method = RequestMethod.POST)
    public JSONObject ideaPost(@RequestBody JSONObject message) {

        return ideaService.ideaPost(message);

    }

    @RequestMapping(value = {"/listAllIdeasVisible"}, method = RequestMethod.POST)
    public JSONObject listAllIdeasVisible(@RequestBody JSONObject message) {

        return ideaService.listAllIdeasVisible(message);

    }

    @RequestMapping(value = {"/listAllIdeasInvisible"}, method = RequestMethod.POST)
    public JSONObject listAllIdeasInvisible(@RequestBody JSONObject message) {

        return ideaService.listAllIdeasInvisible(message);

    }


    @RequestMapping(value = {"/storeIdea"}, method = RequestMethod.POST)
    public JSONObject storeIdea(@RequestBody JSONObject message) {

        return ideaService.storeIdea(message);

    }


}