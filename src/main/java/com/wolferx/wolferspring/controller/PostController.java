package com.wolferx.wolferspring.controller;

import com.alibaba.fastjson.JSONObject;
import com.wolferx.wolferspring.domain.Post;
import com.wolferx.wolferspring.domain.User;
import com.wolferx.wolferspring.repository.PostRepository;
import com.wolferx.wolferspring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/post")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;
    @Autowired
    public UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Post> getAllPost() {

        return postRepository.findAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Map<String, Object> getPostByUserId(@PathVariable Long userId) {
        logger.info("Start getPostByUserId() for userId: " + userId);
        List<Post> posts =  postRepository.findByUserId(userId);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "get all posts by userId: " + userId);
        response.put("posts", posts);
        return response;
    }


    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> addPost(@RequestBody JSONObject requestBody) {
        JSONObject postJson = requestBody.getJSONObject("post");
        Long userId = postJson.getLong("userId");
        String slug = postJson.getString("slug");
        String title = postJson.getString("title");
        String tag = postJson.getString("tag");

        Post post = new Post(userId, slug, title, tag);
        postRepository.save(post);

        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("message", "post has been added successfully");
        response.put("post", post);
        return response;
    }
}
