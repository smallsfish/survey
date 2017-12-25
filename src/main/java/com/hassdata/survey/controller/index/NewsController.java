package com.hassdata.survey.controller.index;

import com.hassdata.survey.dto.AdminUser;
import com.hassdata.survey.dto.NewsDTO;
import com.hassdata.survey.dto.NewsTypeDTO;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.po.News;
import com.hassdata.survey.po.Newstype;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.service.NewsService;
import com.hassdata.survey.service.NewsTypeService;
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
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("system")
public class NewsController {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private NewsTypeService newsTypeService;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private NewsService newsService;

    @RequiresPermissions("news:add")
    @RequestMapping(value = "getNewsCenter", method = RequestMethod.GET)
    public String getNewsCenter(ModelMap map) {
        List<Newstype> newstypeList = newsTypeService.getAll(null);
        map.addAttribute("newsType", newstypeList);
        return "system/web/news/newscenter";
    }

    @RequiresPermissions("news:add")
    @RequestMapping(value = "getAddNews", method = RequestMethod.GET)
    public String getAddNews(ModelMap map) {
        List<Newstype> newstypeList = newsTypeService.getAll(null);
        map.addAttribute("newsType", newstypeList);
        return "system/web/news/addNews";
    }

    @RequiresPermissions("news:add")
    @RequestMapping(value = "getAddNewsType", method = RequestMethod.GET)
    public String getAddNewsType() {
        return "system/web/news/addNewType";
    }

    @RequestMapping(value = "newsSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse newsSearch(HttpServletRequest request, Integer status, Integer newstype, @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        News news = new News();
        String newstitle = request.getParameter("newstitle");
        try {
            newstitle= URLDecoder.decode(URLDecoder.decode(newstitle,"UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!newstitle.equals("")) {
            news.setNewstitle("%" + newstitle + "%");
        }
        if (status != 2) {
            news.setStatus(status == 1 ? true : false);
        }
        if (newstype != 0) {
            news.setNewstype(newstype);
        }
        long count = newsService.getScrollByLikeCount(news);
        List<News> newsList = newsService.getScrollDataByLike(news, "id desc", (page - 1) * limit, limit);
        List<NewsDTO> newsDTOList = new ArrayList<>();
        setNewsDTO(newsList, newsDTOList);
        return ServerResponse.createBySuccessForLayuiTable("搜索成功", newsDTOList, count);
    }

    @RequiresPermissions("news:add")
    @RequestMapping(value = "addNewsType", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addNewsType(Newstype newstype) {
        if (newstype.getName().equals("")) {
            return ServerResponse.createByErrorMessage("请填写类型名称");
        }
        newsTypeService.save(newstype);
        return ServerResponse.createBySuccessMessage("类型添加成功");
    }


    @RequestMapping(value = "getNewsTypeList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getNewsTypeList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        long count = newsTypeService.getScrollCount(null);
        List<Newstype> newstypeList = newsTypeService.getScrollData(null, "id desc", (page - 1) * limit, limit);
        List<NewsTypeDTO> newsTypeDTOs = new ArrayList<>();
        NewsTypeDTO newsTypeDTO = null;
        int i = 1;
        for (Newstype nt : newstypeList) {
            newsTypeDTO = new NewsTypeDTO();
            newsTypeDTO.setAid(i);
            newsTypeDTO.setId(nt.getId());
            newsTypeDTO.setName(nt.getName());
            i++;
            newsTypeDTOs.add(newsTypeDTO);
        }
        return ServerResponse.createBySuccessForLayuiTable("类型添加成功", newsTypeDTOs, count);
    }

    @RequiresPermissions("news:delete")
    @RequestMapping(value = "newstypeDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse newstypeDel(Integer id) {
        if (newsTypeService.delete(id) > 0) {
            News news = new News();
            news.setNewstype(id);
            List<News> newsList = newsService.getAll(news);
            for (News n : newsList) {
                newsService.delete(n.getId());
            }
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @RequiresPermissions("news:add")
    @RequestMapping(value = "addNews", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addNews(News news, MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Admin_User adminUser = (Admin_User) session.getAttribute("CurrentAdminUser");
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传缩略图！");
        }
        if (news.getCreatetime() == null) {
            news.setCreatetime(new Date());
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
        news.setImageurl(fileName);
        news.setOperator(adminUser.getId());
        newsService.save(news);
        return ServerResponse.createBySuccessMessage("创建资讯成功");
    }

    @RequestMapping(value = "getNewsList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getNewsList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "30") Integer limit) {
        long count = newsService.getScrollCount(null);
        List<News> newsList = newsService.getScrollData(null, "id desc", (page - 1) * limit, limit);
        List<NewsDTO> newsDTOList = new ArrayList<>();
        setNewsDTO(newsList, newsDTOList);
        return ServerResponse.createBySuccessForLayuiTable("查询成功", newsDTOList, count);
    }

    private void setNewsDTO(List<News> newsList, List<NewsDTO> newsDTOList) {
        NewsDTO newsDTO;
        int i = 1;
        for (News n : newsList) {
            newsDTO = new NewsDTO();
            newsDTO.setAid(i);
            newsDTO.setComeform(n.getComeform());
            newsDTO.setCreatetime(simpleDateFormat.format(n.getCreatetime()));
            newsDTO.setId(n.getId());
            newsDTO.setImageurl(n.getImageurl());
            newsDTO.setNewscontent(n.getNewscontent());
            newsDTO.setNewstitle(n.getNewstitle());
            newsDTO.setNewstype(newsTypeService.find(n.getNewstype()).getName());
            newsDTO.setOperator(adminUserService.find(n.getOperator()).getAccount());
            newsDTO.setStatus(n.getStatus() ? "显示" : "隐藏");
            i++;
            newsDTOList.add(newsDTO);
        }
    }

    @RequiresPermissions("news:delete")
    @RequestMapping(value = "newsDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse newsDel(Long id) {
        newsService.delete(id);
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @RequiresPermissions("news:update")
    @RequestMapping(value = "getEditorNews", method = RequestMethod.GET)
    public String getEditorNews(Long id, ModelMap map) {
        News news = newsService.find(id);
        map.addAttribute("news", news);
        List<Newstype> newstypeList = newsTypeService.getAll(null);
        map.addAttribute("newsType", newstypeList);
        return "system/web/news/editorNews";
    }

    @RequiresPermissions("news:update")
    @RequestMapping(value = "updateNewsImage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateAdminUserHeadImage(Long id, MultipartFile file, HttpServletRequest request, String image) {
        News news = new News();
        news.setId(id);
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
        news.setImageurl(fileName);
        newsService.updateParams(news);
        return ServerResponse.createBySuccess("缩略图修改成功", "uploadimage/" + fileName);
    }

    @RequiresPermissions("news:update")
    @RequestMapping(value = "updateNews", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateNews(News news) {
        newsService.updateParams(news);
        return ServerResponse.createBySuccessMessage("修改成功");
    }

    @RequiresPermissions("news:update")
    @RequestMapping(value = "getEditorNewType", method = RequestMethod.GET)
    public String getEditorNewType(Integer id, ModelMap map) {
        Newstype newstype = newsTypeService.find(id);
        map.addAttribute("newType", newstype);
        return "system/web/news/editorNewType";
    }

    @RequiresPermissions("news:update")
    @RequestMapping(value = "editorNewsType", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse editorNewsType(Newstype newstype) {
        newsTypeService.updateParams(newstype);
        return ServerResponse.createBySuccessMessage("类型修改成功");
    }

}
