package com.hassdata.survey.controller.information;

import com.hassdata.survey.service.ScoreService;
import com.hassdata.survey.service.StudentService;
import com.hassdata.survey.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class InformationCenterController {

    @Resource
    private UserService userService;

    @Resource
    private StudentService studentService;

    @Resource
    private ScoreService scoreService;

    @RequestMapping(value = "info" , method = RequestMethod.GET)
    public String getInformationCenter(){
        return "system/information/informationcenter";
    }

}
