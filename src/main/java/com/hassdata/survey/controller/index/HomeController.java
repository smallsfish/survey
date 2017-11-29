package com.hassdata.survey.controller.index;

import com.hassdata.survey.po.Loop;
import com.hassdata.survey.service.LoopService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("index")
@Scope("prototype")
public class HomeController {

    @Resource
    private LoopService loopService;

    @RequestMapping(value = "home",method = RequestMethod.GET)
    public String getIndexHome(ModelMap map){
        Loop loop=new Loop();
        loop.setIsshow(true);
        List<Loop> loops=loopService.getAll(loop,"sort desc");
        map.addAttribute("loops",loops);
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
