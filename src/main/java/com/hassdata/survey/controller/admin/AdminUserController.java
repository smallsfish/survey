package com.hassdata.survey.controller.admin;

import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.util.MD5TUtils;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class AdminUserController {
    @Resource
    private AdminUserService adminUserService;
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
}
