package com.hassdata.survey.controller.index;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("prototype")
@RequestMapping("system")
public class NewsController {


    @RequestMapping(value = "getNewsCenter",method = RequestMethod.GET)
    public String getNewsCenter(){
        return "system/web/news/newscenter";
    }

    @RequestMapping(value = "getAddNews",method = RequestMethod.GET)
    public String getAddNews(){
        return "system/web/news/addNews";
    }

}
