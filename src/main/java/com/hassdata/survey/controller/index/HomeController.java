package com.hassdata.survey.controller.index;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("index")
@Scope("prototype")
public class HomeController {

    @RequestMapping(value = "home",method = RequestMethod.GET)
    public String getIndexHome(){
        return "index/home";
    }
    @RequestMapping(value = "news",method = RequestMethod.GET)
    public String getIndexNews(){
        return "index/news";
    }
    @RequestMapping(value = "picture",method = RequestMethod.GET)
    public String getIndexPicture(){
        return "index/pictures";
    }
    @RequestMapping(value = "video",method = RequestMethod.GET)
    public String getIndexVideo(){
        return "index/video";
    }
    @RequestMapping(value = "msg",method = RequestMethod.GET)
    public String getIndexMsg(){
        return "index/msg";
    }
}
