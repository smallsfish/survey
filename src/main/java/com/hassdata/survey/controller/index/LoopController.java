package com.hassdata.survey.controller.index;

import com.hassdata.survey.po.Loop;
import com.hassdata.survey.service.LoopService;
import com.hassdata.survey.util.FileUploadUtils;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class LoopController {

    @Resource
    private LoopService loopService;

    @RequestMapping(value = "loop",method = RequestMethod.GET)
    public String getSystemLoop(){
        return "system/web/loop/loopcenter";
    }

    @RequestMapping(value = "getLoopAdd",method = RequestMethod.GET)
    public String getLoopAdd(){
        return "system/web/loop/addPictureLoop";
    }

    @RequestMapping(value = "getLoopEditor",method = RequestMethod.GET)
    public String getLoopEditor(ModelMap map,Integer id){
        Loop loop=loopService.find(id);
        map.addAttribute("loop",loop);
        return "system/web/loop/editorPictureLoop";
    }



    @RequestMapping(value = "getLoopList",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getLoopList(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer limit){
        System.out.println(page+"_"+limit);
        if(page==null || limit==null){
            page=1;
            limit=30;
        }
        long count=loopService.getScrollCount(null);
        List<Loop> loopList = loopService.getScrollData(null, "id DESC", (page-1) * limit, limit);
        return ServerResponse.createBySuccessForLayuiTable("请求成功",loopList,count);
    }

    @RequestMapping(value = "loopAdd",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse loopAdd(Loop loop, MultipartHttpServletRequest request, MultipartFile file){
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传头像！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 2097152) {
            return ServerResponse.createByErrorMessage("请上传小于2M的图片");
        }
        if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("jpeg") || suffix.equals("JPEG") || suffix.equals("PNG") || suffix.equals("png") || suffix.equals("GIF") || suffix.equals("gif")) {
        } else return ServerResponse.createByErrorMessage("请上传jpg/jpeg/png/gif格式的图片！");
        String path = request.getSession().getServletContext().getRealPath("uploadLoop");
        System.out.println(path);
        String fileName = new Date().getTime() + "." + suffix;
        try {
            FileUploadUtils.uploadSingleImage(path, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("上传图片失败");
        }
        loop.setImageurl("uploadLoop/"+fileName);
        loopService.save(loop);
        return ServerResponse.createBySuccessMessage("轮播图添加成功");
    }


    @RequestMapping(value = "updateLoopImage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateLoopImage(Integer id, MultipartFile file, HttpServletRequest request, String image) {
        Loop loop = new Loop();
        loop.setId(id);
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传轮播图！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 2097152) {
            return ServerResponse.createByErrorMessage("请上传小于2M的图片");
        }
        if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("jpeg") || suffix.equals("JPEG") || suffix.equals("PNG") || suffix.equals("png") || suffix.equals("GIF") || suffix.equals("gif")) {
        } else return ServerResponse.createByErrorMessage("请上传jpg/jpeg/png/gif格式的图片！");
        String path = request.getSession().getServletContext().getRealPath("uploadLoop");
        String fileName = new Date().getTime() + "." + suffix;
        try {
            FileUploadUtils.uploadSingleImage(path, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("上传图片失败");
        }
        File file1 = new File(path + "/" + image.substring(image.indexOf("/")+1,image.length()));
        if (file1.exists()) {
            file1.delete();
        }

        loop.setImageurl("uploadLoop/"+fileName);
        loopService.updateParams(loop);
        return ServerResponse.createBySuccess("图片上传成功", "uploadLoop/" + fileName);
    }

    @RequestMapping(value = "loopEditor",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse loopEditor(Loop loop){
        loopService.updateParams(loop);
        return ServerResponse.createBySuccessMessage("轮播图信息修改成功");
    }

    @RequestMapping(value = "loopDel",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse loopDel(Integer id){
        if(loopService.delete(id)>0){
            return ServerResponse.createBySuccessMessage("轮播图删除成功");
        }else {
            return ServerResponse.createByErrorMessage("轮播图删除失败");
        }
    }

    @RequestMapping(value = "alterLoopSortOrIsShow",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse alterLoopSort(Loop loop){
        if(loopService.updateParams(loop)>0){
            if(loop.getIsshow()==null)
            return ServerResponse.createBySuccessMessage("排序重置成功");
            else{
                return ServerResponse.createBySuccessMessage("显示重置成功");
            }
        }else {
            if(loop.getIsshow()==null)
                return ServerResponse.createBySuccessMessage("排序重置失败");
            else{
                return ServerResponse.createBySuccessMessage("显示重置失败");
            }
        }
    }


}
