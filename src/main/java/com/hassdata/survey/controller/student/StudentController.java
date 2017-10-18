package com.hassdata.survey.controller.student;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class StudentController {
    @RequestMapping(value = "getStudent",method = RequestMethod.GET)
    public String getStudentByUid(int uid){
        return "system/information/studentmanager";
    }
}
