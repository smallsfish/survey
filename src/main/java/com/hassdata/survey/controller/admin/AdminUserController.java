package com.hassdata.survey.controller.admin;

import com.hassdata.survey.dto.AdminUser;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.util.FileUploadUtils;
import com.hassdata.survey.util.MD5TUtils;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class AdminUserController {



    @Resource
    private AdminUserService adminUserService;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String getSystemLogin(){
        return "system/login";
    }
    @RequestMapping(value = "exit",method = RequestMethod.GET)
    @ResponseBody
    public String exitSystem(HttpServletRequest request){
        HttpSession session=request.getSession(true);
        if(session.getAttribute("CurrentAdminUser")!=null){
            session.removeAttribute("CurrentAdminUser");
        }
        return "success";
    }
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String getSystemIndex(){
        return "index";
    }
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse systemLogin(HttpServletRequest request, Admin_User adminUser, @RequestParam(required = false) Integer remind){
        HttpSession session=request.getSession(true);
        if(adminUser.getAccount().isEmpty()){
            return ServerResponse.createByErrorMessage("请输入账号！");
        }else if(adminUser.getPassword().isEmpty()){
            return ServerResponse.createByErrorMessage("请输入密码！");
        }
        if(remind!=null){
            System.out.println("记住密码");
        }
        Admin_User au=null;
        adminUser.setPassword(MD5TUtils.threeMD5(adminUser.getPassword()));
        if((au=adminUserService.getOne(adminUser))!=null){
            session.setAttribute("CurrentAdminUser",au);
            au.setLastlogintime(new Date());
            adminUserService.update(au);
            return ServerResponse.createBySuccessMessage("登陆成功！");
        }else{
            return ServerResponse.createByErrorMessage("账号或者密码错误");
        }
    }

    @RequestMapping(value = "userCenter", method = RequestMethod.GET)
    public String getUserCenter() {
        return "system/user/usercenter";
    }

    @RequestMapping(value = "getAddAdminUser", method = RequestMethod.GET)
    public String getAddAdminUser() {
        return "system/user/addAdminUser";
    }

    @RequestMapping(value = "getEditorAdminUser", method = RequestMethod.GET)
    public String getEditorAdminUser(Integer id, ModelMap map) {
        Admin_User admin_user = adminUserService.find(id);
        AdminUser adminUser = new AdminUser();
        adminUser.setId(admin_user.getId());
        adminUser.setAccount(admin_user.getAccount());
        adminUser.setHeadimage(admin_user.getHeadimage());
        adminUser.setEmail(admin_user.getEmail());
        adminUser.setRole(null);
        adminUser.setIdentifier(admin_user.getIdentifier());
        adminUser.setRemarks(admin_user.getRemarks());
        adminUser.setCreatedatetime(format.format(admin_user.getCreatedatetime()));
        if (admin_user.getLastlogintime() == null) {
            adminUser.setLastlogintime("该用户没有登录过");
        } else {
            adminUser.setLastlogintime(format.format(admin_user.getLastlogintime()));
        }
        map.addAttribute("adminUser", adminUser);
        return "system/user/adminEditor";
    }

    @RequestMapping(value = "adminUserDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse adminUserDel(Integer id) {
        adminUserService.delete(id);
        return ServerResponse.createBySuccessMessage("管理员删除成功！");
    }

    @RequestMapping(value = "getAdminUser", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getAdminUser(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 30;
        }
        long count = adminUserService.getScrollCount(null);
        List<Admin_User> a_us = adminUserService.getScrollData(null, "id DESC", (page - 1) * limit, limit);
        List<AdminUser> aus = new ArrayList<>();
        AdminUser adminUser = null;
        int aid = 1;
        count = setAdminUserDTO(adminUser, a_us, aus, aid, count);
        return ServerResponse.createBySuccessForLayuiTable("请求成功", aus, count);
    }


    @RequestMapping(value = "addAdminUser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addAdminUser(Admin_User admin_user, MultipartHttpServletRequest request, MultipartFile file) {
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传头像！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 512000) {
            return ServerResponse.createByErrorMessage("请上传小于500k的图片");
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
        admin_user.setHeadimage(fileName);
        admin_user.setPassword(MD5TUtils.threeMD5("000000"));
        admin_user.setCreatedatetime(new Date());
        adminUserService.save(admin_user);
        return ServerResponse.createBySuccessMessage("添加管理员成功");
    }

    @RequestMapping(value = "adminUserSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse adminUserSearch(String account, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 30;
        }
        Admin_User admin_user = new Admin_User();
        admin_user.setAccount("%" + account + "%");
        long count = adminUserService.getScrollByLikeCount(admin_user);
        List<Admin_User> a_us = adminUserService.getScrollDataByLike(admin_user, "id DESC", (page - 1) * limit, limit);
        List<AdminUser> aus = new ArrayList<>();
        AdminUser adminUser = null;
        int aid = 1;
        count = setAdminUserDTO(adminUser, a_us, aus, aid, count);
        return ServerResponse.createBySuccessForLayuiTable("搜索成功", aus, count);
    }


    private long setAdminUserDTO(AdminUser adminUser, List<Admin_User> a_us, List<AdminUser> aus, int aid, long count) {
        for (Admin_User au : a_us) {
            if (!au.getAccount().equals("admin")) {
                adminUser = new AdminUser();
                adminUser.setId(au.getId());
                adminUser.setAid(aid);
                adminUser.setAccount(au.getAccount());
                adminUser.setHeadimage(au.getHeadimage());
                adminUser.setEmail(au.getEmail());
                adminUser.setRole(null);
                adminUser.setIdentifier(au.getIdentifier());
                adminUser.setCreatedatetime(format.format(au.getCreatedatetime()));
                if (au.getLastlogintime() == null) {
                    adminUser.setLastlogintime("该用户没有登录过");
                } else {
                    adminUser.setLastlogintime(format.format(au.getLastlogintime()));
                }
                adminUser.setRemarks(au.getRemarks());
                aus.add(adminUser);
                aid++;
            } else {
                count--;
            }
        }
        return count;
    }

    @RequestMapping(value = "updateAdminHeadImage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateAdminUserHeadImage(Integer id, MultipartFile file, HttpServletRequest request, String image) {
        Admin_User admin_user = new Admin_User();
        admin_user.setId(id);
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("请上传头像！");
        }
        String fn = file.getOriginalFilename();
        String suffix = fn.substring(fn.lastIndexOf('.') + 1, fn.length());
        long fileSize = file.getSize();
        if (fileSize > 512000) {
            return ServerResponse.createByErrorMessage("请上传小于500k的图片");
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
        admin_user.setHeadimage(fileName);
        adminUserService.updateParams(admin_user);
        return ServerResponse.createBySuccess("图片上传成功", "uploadimage/" + fileName);
    }

    @RequestMapping(value = "updateAdminUser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateAdminUser(Admin_User admin_user) {
        adminUserService.updateParams(admin_user);
        return ServerResponse.createBySuccessMessage("更新成功");
    }
}
