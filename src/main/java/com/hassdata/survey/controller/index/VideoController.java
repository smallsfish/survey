package com.hassdata.survey.controller.index;

import com.hassdata.survey.dto.VideoDTO;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.po.Video;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.service.VideoService;
import com.hassdata.survey.util.FileUploadUtils;
import com.hassdata.survey.util.ServerResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("system")
public class VideoController {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        Long count = videoService.getScrollCount(null);
        List<Video> videoList = videoService.getScrollData(null, "id desc", (page - 1) * limit, limit);
        List<VideoDTO> videoDTOS = new ArrayList<>();
        setVideoDTO(videoList, videoDTOS);
        return ServerResponse.createBySuccessForLayuiTable("查询成功", videoDTOS, count);
    }

    private void setVideoDTO(List<Video> videoList, List<VideoDTO> videoDTOS) {
        VideoDTO videoDTO;
        int i = 1;
        for (Video v : videoList) {
            videoDTO = new VideoDTO();
            videoDTO.setAid(i);
            videoDTO.setId(v.getId());
            videoDTO.setCreatetime(simpleDateFormat.format(v.getCreatetime()));
            videoDTO.setImageurl(v.getImageurl());
            videoDTO.setOperator(adminUserService.find(v.getOperator()).getAccount());
            videoDTO.setStatus(v.getStatus() ? "显示" : "隐藏");
            videoDTO.setVideotitle(v.getVideotitle());
            videoDTO.setVideourl(v.getVideourl());
            videoDTOS.add(videoDTO);
            i++;
        }
    }


    @RequiresPermissions("video:add")
    @RequestMapping(value = "getVideoAdd", method = RequestMethod.GET)
    public String getVideoAdd() {
        return "system/web/video/addVideo";
    }

    @RequiresPermissions("video:add")
    @RequestMapping(value = "addVideo", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addVideo(Video video, MultipartFile file, MultipartFile vfile, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Admin_User adminUser = (Admin_User) session.getAttribute("CurrentAdminUser");
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传缩略图！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 2 * 1024 * 1024) {
            return ServerResponse.createByErrorMessage("请上传小于2M的图片");
        }
        if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("jpeg") || suffix.equals("JPEG") || suffix.equals("PNG") || suffix.equals("png") || suffix.equals("GIF") || suffix.equals("gif")) {
        } else return ServerResponse.createByErrorMessage("请上传jpg/jpeg/png/gif格式的图片！");
        String path = request.getSession().getServletContext().getRealPath("uploadimage");
        String fileName = new Date().getTime() + "." + suffix;
        try {
            FileUploadUtils.uploadSingleImage(path, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("缩略图上传失败");
        }
        if (vfile.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传视频！");
        }
        String vfn = vfile.getOriginalFilename();
        String vsuffix = vfn.substring(vfn.lastIndexOf('.') + 1, vfn.length());
        if (vsuffix.equals("mp4")) {
        } else return ServerResponse.createByErrorMessage("请上传mp4格式的视频！");
        String vpath = request.getSession().getServletContext().getRealPath("uploadvideo");
        String vfileName = new Date().getTime() + "." + vsuffix;
        try {
            FileUploadUtils.uploadSingleImage(vpath, vfileName, vfile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("视频上传失败");
        }
        video.setCreatetime(new Date());
        video.setImageurl(fileName);
        video.setVideourl(vfileName);
        video.setOperator(adminUser.getId());
        videoService.save(video);
        return ServerResponse.createBySuccessMessage("视频保存成功");
    }

    @RequiresPermissions("video:delete")
    @RequestMapping(value = "videoDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse videoDel(Long id) {
        videoService.delete(id);
        return ServerResponse.createBySuccessMessage("视频删除成功");
    }

    @RequiresPermissions("video:update")
    @RequestMapping(value = "getEditorVideo", method = RequestMethod.GET)
    public String getEditorVideo(Long id, ModelMap map) {
        Video video = videoService.find(id);
        map.addAttribute("video", video);
        return "system/web/video/editorVideo";
    }


    @RequestMapping(value = "videoSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse videoSearch(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        Video video = new Video();
        String videoTitle = null;
        try {
            videoTitle = new String(request.getParameter("videoTitle").getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (videoTitle != null && !videoTitle.equals("")) {
            video.setVideotitle("%" + videoTitle + "%");
        }
        long count = videoService.getScrollByLikeCount(video);
        List<Video> videoList = videoService.getScrollDataByLike(video, "id desc", (page - 1) * limit, limit);
        List<VideoDTO> videoDTOS = new ArrayList<>();
        setVideoDTO(videoList, videoDTOS);
        return ServerResponse.createBySuccessForLayuiTable("查询成功", videoDTOS, count);
    }

    @RequiresPermissions("video:update")
    @RequestMapping(value = "updateVideoPicture", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateVideoPicture(Long id, MultipartFile file, HttpServletRequest request, String image) {
        Video video = new Video();
        video.setId(id);
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传缩略图！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 2 * 1024 * 1024) {
            return ServerResponse.createByErrorMessage("请上传小于2M的图片");
        }
        if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("jpeg") || suffix.equals("JPEG") || suffix.equals("PNG") || suffix.equals("png") || suffix.equals("GIF") || suffix.equals("gif")) {
        } else return ServerResponse.createByErrorMessage("请上传jpg/jpeg/png/gif格式的图片！");
        String path = request.getSession().getServletContext().getRealPath("uploadimage");
        String fileName = new Date().getTime() + "." + suffix;
        try {
            FileUploadUtils.uploadSingleImage(path, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("上传图片失败");
        }
        File file1 = new File(path + "/" + image);
        if (file1.exists()) {
            file1.delete();
        }
        video.setImageurl(fileName);
        videoService.updateParams(video);
        return ServerResponse.createBySuccess("缩略图修改成功", "uploadimage/" + fileName);
    }

    @RequiresPermissions("video:update")
    @RequestMapping(value = "updateVideoFile", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateVideoFile(Long id, MultipartFile file, HttpServletRequest request, String videoUrl) {
        Video video = new Video();
        video.setId(id);
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传视频！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        if (suffix.equals("mp4") || suffix.equals("MP4")) {
        } else return ServerResponse.createByErrorMessage("请上传mp4格式的视频！");
        String path = request.getSession().getServletContext().getRealPath("uploadvideo");
        String fileName = new Date().getTime() + "." + suffix;
        try {
            FileUploadUtils.uploadSingleImage(path, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("上传视频失败");
        }
        File file1 = new File(path + "/" + videoUrl);
        if (file1.exists()) {
            file1.delete();
        }
        video.setVideourl(fileName);
        videoService.updateParams(video);
        return ServerResponse.createBySuccess("视频修改成功", "uploadvideo/" + fileName);
    }

    @RequiresPermissions("video:update")
    @RequestMapping(value = "updateVideo", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateVideo(Video video) {
        videoService.updateParams(video);
        return ServerResponse.createBySuccessMessage("修改成功");
    }

}
