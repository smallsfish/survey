package com.hassdata.survey.controller.user;

import com.hassdata.survey.dto.AdminUser;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public ServerResponse getAdminUser(@RequestParam(required = false) int page,@RequestParam(required = false) int limit){
        long count=adminUserService.getScrollCount(null);
        List<Admin_User> a_us=adminUserService.getScrollData(null,"id DESC",page-1,limit);
        List<AdminUser> aus=new ArrayList<>();
        AdminUser adminUser=null;
        int aid=1;
        for (Admin_User au : a_us){
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
            System.out.println(au.getRemarks());
            aid++;
        }
        return ServerResponse.createBySuccessForLayuiTable("请求成功",aus,count);
    }
}
