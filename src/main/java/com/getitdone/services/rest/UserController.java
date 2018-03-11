package com.getitdone.services.rest;

import com.getitdone.services.core.IUserService;
import com.getitdone.services.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/users")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService service;

    /**
     * POST: url: /users
     * body: User json
     * return: userId
     * @param user
     * @param response
     */
    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createUser(@RequestBody User user, HttpServletResponse response){
        logger.info("createUser request with data: {}", user);
        try {
            String id = service.createUser(user);
            response.setStatus(201);
            return "Location: /users"+id;
        } catch (JSONException e){
            response.setStatus(404);
            return "Not valid JSON";
        }
    }

    /**
     * GET: url: /users/{id}
     * return: User json
     * @param id
     */
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable String id) {
        logger.info("getUser API userId: {}", id);
        User user =  service.getUser(id);
        return user;
    }


    /**
     * GET: /users
     * return: Json array of Users
     * @param request
     * @param response
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(HttpServletRequest request, HttpServletResponse response){
        logger.info("getUsers API: {}");
        String start = request.getParameter("start");
        String records =  request.getParameter("records");

        start = (StringUtils.isEmpty(start) || !StringUtils.isNumeric(start)) ? "0" : start ;
        records= (StringUtils.isEmpty(start) || !StringUtils.isNumeric(records)) ? "10" : start ;

        int page = "0".equals(start) ? 1 : Integer.valueOf(start) ;
        int size = Integer.valueOf(records) ;
        List<User> users = service.getAllUsers(page, size);
        return users;
    }

    /**
     * PUT: /users/{id}
     * body: User json
     * @param user
     * @param response
     * @return
     */
    @RequestMapping(method= RequestMethod.PUT, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody User user, HttpServletResponse response){
        logger.info("update request with data: {}", user);
        response.setStatus(501);
        //TODO: implement update user
    }

    /**
     * DELETE: /users/{id}
     * @param user
     * @param response
     * @return
     */
    @RequestMapping(method= RequestMethod.DELETE, value = "/{id}")
    public void delete(@RequestBody User user, HttpServletResponse response){
        logger.info("delete request with data: {}", user);
        //TODO: implement delete user
        response.setStatus(501);
    }





}
