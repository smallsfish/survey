package com.hassdata.survey.controller.user;

import com.hassdata.survey.dto.AdminUser;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.util.FileUploadUtils;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class UserController {

    @Resource
    private AdminUserService adminUserService;

    @RequestMapping(value = "userCenter",method = RequestMethod.GET)
    public String getSystemLogin(){
        return "system/user/usercenter";
    }
    @RequestMapping(value = "getAddAdminUser",method = RequestMethod.GET)
    public String getAddAdminUser(){
        return "system/user/addAdminUser";
    }

    @RequestMapping(value = "getAdminUser",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getAdminUser(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer limit){
        if(page==null || limit==null){
            page=1;
            limit=30;
        }
        long count=adminUserService.getScrollCount(null);
        List<Admin_User> a_us=adminUserService.getScrollData(null,"id DESC",(page-1)*limit,limit);
        List<AdminUser> aus=new ArrayList<>();
        AdminUser adminUser=null;
        int aid=1;
        setAdminUserDTO(adminUser,a_us,aus,aid);
        return ServerResponse.createBySuccessForLayuiTable("请求成功",aus,count);
    }


    @RequestMapping(value = "addAdminUser" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addAdminUser(Admin_User admin_user, MultipartHttpServletRequest request, MultipartFile file){
        if(file.isEmpty()){
            return ServerResponse.createByErrorMessage("请上传头像！");
        }
        String fn=file.getOriginalFilename();
        String suffix=fn.substring(fn.lastIndexOf('.')+1,fn.length());
        long fileSize=file.getSize();
        if(fileSize>512000){
            return ServerResponse.createByErrorMessage("请上传小于500k的图片");
        }
        if(suffix.equals("jpg") || suffix.equals("JPG") ||suffix.equals("jpeg") ||suffix.equals("JPEG") ||suffix.equals("PNG") ||suffix.equals("png") ||suffix.equals("GIF") ||suffix.equals("gif"))
        {}else return ServerResponse.createByErrorMessage("请上传jpg/jpeg/png/gif格式的图片！");
        String path=request.getSession().getServletContext().getRealPath("uploadimage");
        String fileName=new Date().getTime()+"."+suffix;
        try {
            FileUploadUtils.uploadSingleImage(path,fileName,file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return  ServerResponse.createByErrorMessage("上传图片失败");
        }
        admin_user.setHeadimage(fileName);
        //adminUserService.save(admin_user);
        return ServerResponse.createBySuccessMessage("添加管理员成功");
    }

    @RequestMapping(value = "adminUserSearch" , method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse adminUserSearch(String account,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer limit ){
        if(page==null || limit==null){
            page=1;
            limit=30;
        }
        Admin_User admin_user=new Admin_User();
        admin_user.setAccount("%"+account+"%");
        long count=adminUserService.getScrollByLikeCount(admin_user);
        List<Admin_User> a_us=adminUserService.getScrollDataByLike(admin_user,"id DESC",(page-1)*limit,limit);
        List<AdminUser> aus=new ArrayList<>();
        AdminUser adminUser=null;
        int aid=1;
        setAdminUserDTO(adminUser,a_us,aus,aid);
        return ServerResponse.createBySuccessForLayuiTable("搜索成功",aus,count);
    }

    private void setAdminUserDTO(AdminUser adminUser,List<Admin_User> a_us,List<AdminUser> aus, int aid){
        for (Admin_User au : a_us){
            if(!au.getAccount().equals("admin")){
                adminUser=new AdminUser();
                adminUser.setId(au.getId());
                adminUser.setAid(aid);
                adminUser.setAccount(au.getAccount());
                adminUser.setHeadimage(au.getHeadimage());
                adminUser.setEmail(au.getEmail());
                adminUser.setRole(null);
                adminUser.setIdentifier(au.getIdentifier());
                adminUser.setCreatedatetime(au.getCreatedatetime());
                adminUser.setLastlogintime(au.getLastlogintime());
                adminUser.setRemarks(au.getRemarks());
                aus.add(adminUser);
                aid++;
            }
        }
    }
}
