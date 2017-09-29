package com.hassdata.survey.controller.data;

import com.hassdata.survey.service.ScoreService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class DataCenterController {

    @Resource
    private ScoreService scoreService;


    @RequestMapping(value = "data",method = RequestMethod.GET)
    public String getDataCenter(ModelMap map){

        return "system/data/datacenter";
    }
}
