package com.hassdata.survey.controller.user;

import com.hassdata.survey.service.UserService;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class UserController {
    @Resource
    private UserService  userService;

    @RequestMapping(value = "getUser" , method = RequestMethod.GET)
    public ServerResponse getUser(){
        return null;
    }
}
