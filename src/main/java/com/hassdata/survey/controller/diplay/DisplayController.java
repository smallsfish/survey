package com.hassdata.survey.controller.diplay;

import com.hassdata.survey.po.User;
import com.hassdata.survey.service.UserService;
import com.hassdata.survey.util.MD5TUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@Scope("prototype")
@RequestMapping("display")
public class DisplayController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getDisplayLogin(@RequestParam(required = false) String path, @RequestParam(required = false) String parameter, ModelMap map) {
        if (parameter != null) {
            map.addAttribute("path", path);
        }
        if (path != null) {
            map.addAttribute("parameter", parameter);
        }
        return "display/login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String displayLogin(User user, ModelMap map, HttpServletRequest request, @RequestParam(required = false) String path, @RequestParam(required = false) String parameter) {
        HttpSession session = request.getSession(true);
        if (parameter != null) {
            map.addAttribute("path", path);
        }
        if (path != null) {
            map.addAttribute("parameter", parameter);
        }
        if (user.getAccount().equals("") || user.getAccount() == null) {
            map.addAttribute("error", "请输入用户名或密码");
        }
        if (user.getPassword().equals("") || user.getPassword() == null) {
            map.addAttribute("error", "请输入用户名或密码");
        }

        String password = MD5TUtils.threeMD5(user.getPassword());
        user.setPassword(null);
        User userS = userService.getOne(user);
        if (userS == null) {
            map.addAttribute("error", "用户名或密码错误");
        } else {
            if (!userS.getPassword().equals(password)) {
                map.addAttribute("error", "用户名或密码错误");
            } else {
                userS.setPassword(null);
                userS.setLastlogintime(new Date());
                session.setAttribute("CurrentUser", userS);
                userService.updateParams(userS);
                return "redirect:"+path+"?id="+parameter;
            }
        }
        return "display/login";
    }
}
