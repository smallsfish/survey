package com.hassdata.survey.controller.index;

import com.hassdata.survey.dto.VideoDTO;
import com.hassdata.survey.po.Video;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.service.VideoService;
import com.hassdata.survey.util.ServerResponse;
import net.sf.jsqlparser.schema.Server;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("system")
public class VideoController {

    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private VideoService videoService;


    @RequestMapping(value = "getVideoCenter", method = RequestMethod.GET)
    public String getVideoCenter() {
        return "system/web/video/videocenter";
    }

    @RequestMapping(value = "getVideoList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getVideoList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
       Long count=videoService.getScrollCount(null);
        List<Video> videoList=videoService.getScrollData(null,"id desc",(page-1)*limit,limit);
        List<VideoDTO> videoDTOS=new ArrayList<>();
        setVideoDTO(videoList, videoDTOS);
        return ServerResponse.createBySuccessForLayuiTable("查询成功",videoDTOS,count);
    }

    private void setVideoDTO(List<Video> videoList, List<VideoDTO> videoDTOS) {
        VideoDTO videoDTO;
        int i=1;
        for (Video v:videoList){
            videoDTO=new VideoDTO();
            videoDTO.setAid(i);
            videoDTO.setId(v.getId());
            videoDTO.setCreatetime(simpleDateFormat.format(v.getCreatetime()));
            videoDTO.setImageurl(v.getImageurl());
            videoDTO.setOperator(adminUserService.find(v.getOperator()).getAccount());
            videoDTO.setStatus(v.getStatus() ? "显示":"隐藏");
            videoDTO.setVideotitle(v.getVideotitle());
            videoDTO.setVideourl(v.getVideourl());
            videoDTOS.add(videoDTO);
            i++;
        }
    }


    @RequestMapping(value = "getVideoAdd",method = RequestMethod.GET)
    public String getVideoAdd(){
        return "system/web/video/addVideo";
    }

}
