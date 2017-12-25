package com.hassdata.survey.controller.user;

import com.hassdata.survey.dto.UserDTO;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.service.UserService;
import com.hassdata.survey.util.ServerResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class UserController {
    @Resource
    private UserService userService;


    @Resource
    private AdminUserService adminUserService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String[] STATUS = new String[]{"未审核", "审核通过", "审核失败", "账号锁定"};

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getUser(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 30;
        }
        long count = userService.getScrollCount(null);
        List<User> userList = userService.getScrollData(null, "id DESC", (page - 1) * limit, limit);
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO userDTO = null;
        int i = 0;
        for (User u : userList) {
            if (u.getStatus() == 1) continue;
            userDTO = new UserDTO();
            userDTO.setAccount(u.getAccount());
            userDTO.setAddress(u.getAddress());
            userDTO.setAid(++i);
            userDTO.setHeadmaster(u.getHeadmaster());
            userDTO.setId(u.getId());
            if (u.getLastlogintime() == null)
                userDTO.setLastlogintime("该用户暂未登录过");
            else {
                userDTO.setLastlogintime(format.format(u.getLastlogintime()));
            }
            userDTO.setStatus(STATUS[u.getStatus()]);
            userDTO.setExplain(u.getExplains());
            if (u.getOperationuser() != null) {
                userDTO.setOperationuser(adminUserService.find(u.getOperationuser()).getAccount());
            }
            userDTO.setPlayhousename(u.getPlayhousename());
            userDTO.setSchoolname(u.getSchoolname());
            userDTO.setRemarks(u.getRemarks());

            userDTOList.add(userDTO);
        }
        return ServerResponse.createBySuccessForLayuiTable("请求成功", userDTOList, count);
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "userDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse userDel(Integer id) {
        userService.delete(id);
        return ServerResponse.createBySuccessMessage("用户删除成功！");
    }

    @RequiresPermissions("user:verify")
    @RequestMapping(value = "userAuditing", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse userAuditing(Integer id, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = userService.find(id);
        user.setStatus(1);
        user.setOperationuser(((Admin_User) session.getAttribute("CurrentAdminUser")).getId());
        userService.update(user);
        return ServerResponse.createBySuccessMessage("用户审核成功！");
    }


    @RequestMapping(value = "userSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse userSearch(HttpServletRequest request, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 30;
        }
        User user = new User();
        String account = request.getParameter("account");
        try {
            account= URLDecoder.decode(URLDecoder.decode(account,"UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        user.setAccount("%" + account + "%");
        long count = userService.getScrollByLikeCount(user);
        List<User> userList = userService.getScrollDataByLike(user, "id DESC", (page - 1) * limit, limit);
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO userDTO = null;
        int i = 0;
        for (User u : userList) {
            if (u.getStatus() == 1) continue;
            userDTO = new UserDTO();
            userDTO.setAccount(u.getAccount());
            userDTO.setAddress(u.getAddress());
            userDTO.setAid(++i);
            userDTO.setHeadmaster(u.getHeadmaster());
            userDTO.setId(u.getId());
            if (u.getLastlogintime() == null)
                userDTO.setLastlogintime("该用户暂未登录过");
            else {
                userDTO.setLastlogintime(format.format(u.getLastlogintime()));
            }
            userDTO.setPlayhousename(u.getPlayhousename());
            userDTO.setSchoolname(u.getSchoolname());
            userDTO.setRemarks(u.getRemarks());
            userDTO.setStatus(STATUS[u.getStatus()]);
            userDTO.setExplain(u.getExplains());
            if (u.getOperationuser() != null) {
                userDTO.setOperationuser(adminUserService.find(u.getOperationuser()).getAccount());
            }
            userDTOList.add(userDTO);
        }
        return ServerResponse.createBySuccessForLayuiTable("搜索成功", userDTOList, count);
    }
}
