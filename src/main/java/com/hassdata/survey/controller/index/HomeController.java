package com.hassdata.survey.controller.index;

import com.hassdata.survey.po.Loop;
import com.hassdata.survey.po.MessageBoard;
import com.hassdata.survey.service.LoopService;
import com.hassdata.survey.service.MessageBoardService;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("index")
@Scope("prototype")
public class HomeController {
    private SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private MessageBoardService messageBoardService;

    @Resource
    private LoopService loopService;

    @RequestMapping(value = "home",method = RequestMethod.GET)
    public String getIndexHome(ModelMap map){
        setLoopAttribute(map);
        return "index/home";
    }

    @RequestMapping(value = "news",method = RequestMethod.GET)
    public String getIndexNews(ModelMap map){
        setLoopAttribute(map);
        return "index/news";
    }

    @RequestMapping(value = "picture",method = RequestMethod.GET)
    public String getIndexPicture(ModelMap map){
        setLoopAttribute(map);
        return "index/pictures";
    }
    @RequestMapping(value = "video",method = RequestMethod.GET)
    public String getIndexVideo(ModelMap map){
        setLoopAttribute(map);
        return "index/video";
    }
    @RequestMapping(value = "msg",method = RequestMethod.GET)
    public String getIndexMsg(ModelMap map){
        setLoopAttribute(map);
        return "index/msg";
    }

    private void setLoopAttribute(ModelMap map) {
        Loop loop=new Loop();
        loop.setIsshow(true);
        List<Loop> loops=loopService.getAll(loop,"sort desc");
        map.addAttribute("loops",loops);
    }
    @RequestMapping(value = "submitMsg",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse submitMsg(MessageBoard messageBoard){
        MessageBoard board = new MessageBoard();
        board.setTelphone(messageBoard.getTelphone());
        MessageBoard messageBoard1=messageBoardService.getOne(board);
        System.out.println(messageBoard.getTelphone()+"+++++++++++++++++++++++++++++++++++++++++++++++++");
        if(messageBoard1==null){
            messageBoard.setCreatetime(new Date());
            messageBoard.setIsread(false);
            messageBoardService.save(messageBoard);
        }else{
            String oDate=simpleDateFormat1.format(messageBoard1.getCreatetime());
            String nDate=simpleDateFormat1.format(new Date());
            if(oDate.equals(nDate)){
                return ServerResponse.createByErrorMessage("对不起，您今日已经留言了，请明日再来");
            }else{
                messageBoard.setCreatetime(new Date());
                messageBoard.setIsread(false);
                messageBoardService.save(messageBoard);
            }
        }
        return ServerResponse.createBySuccessMessage("留言成功");
    }
}
