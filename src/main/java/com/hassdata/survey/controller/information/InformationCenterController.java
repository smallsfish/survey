package com.hassdata.survey.controller.information;

import com.hassdata.survey.dto.UserDTO;
import com.hassdata.survey.po.*;
import com.hassdata.survey.service.*;
import com.hassdata.survey.util.MD5TUtils;
import com.hassdata.survey.util.ServerResponse;
import net.sf.ehcache.search.aggregator.Count;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class InformationCenterController {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private UserService userService;

    @Resource
    private ProvinceService provinceService;

    @Resource
    private CityService cityService;

    @Resource
    private CountyService countyService;

    @Resource
    private StudentService studentService;

    @Resource
    private ScoreService scoreService;

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String getInformationCenter() {
        return "system/information/informationcenter";
    }


    @RequestMapping(value = "getInfoList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getInfoList(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 30;
        }
        long count = userService.getScrollCount(null);
        List<User> userList = userService.getScrollData(null, "id DESC", (page - 1) * limit, limit);
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO userDTO = null;
        int aid = 0;
        setUserDTO(userList, userDTOList, aid);
        return ServerResponse.createBySuccessForLayuiTable("请求成功", userDTOList, count);
    }


    @RequestMapping(value = "searchInfoList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse searchInfoList(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit, HttpServletRequest request) {
        if (page == null || limit == null) {
            page = 1;
            limit = 30;
        }
        String name = request.getParameter("name");
        try {
            name= URLDecoder.decode(URLDecoder.decode(name,"UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setAccount("%" + name + "%");
        long count = userService.getScrollByLikeCount(user);
        List<User> userList = userService.getScrollDataByLike(user, "id DESC", (page - 1) * limit, limit);
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO userDTO = null;
        int aid = 0;
        setUserDTO(userList, userDTOList, aid);
        return ServerResponse.createBySuccessForLayuiTable("请求成功", userDTOList, count);
    }

    private void setUserDTO(List<User> userList, List<UserDTO> userDTOList, int aid) {
        UserDTO userDTO;
        for (User u : userList) {
            if (u.getStatus() != 1) continue;
            userDTO = new UserDTO();
            aid++;
            userDTO.setId(u.getId());
            userDTO.setAid(aid);
            userDTO.setSchoolname(u.getSchoolname());
            userDTO.setHeadmaster(u.getHeadmaster());
            userDTO.setAddress(u.getAddress());
            userDTO.setPlayhousename(u.getPlayhousename());
            userDTO.setBooknumber(u.getBooknumber());
            Student student = new Student();
            student.setUid(u.getId());
            userDTO.setChildrennumber(studentService.getScrollCount(student));
            userDTO.setWithquestionnairenumber(scoreService.getUserWithQuestionnaireNumber(u.getId()).size());
            if (u.getLastlogintime() == null) {
                userDTO.setLastlogintime("该用户暂未登录");
            } else {
                userDTO.setLastlogintime(format.format(u.getLastlogintime()));
            }
            userDTO.setRemarks(u.getRemarks());
            userDTOList.add(userDTO);
        }
    }

    @RequiresPermissions("info:delete")
    @RequestMapping(value = "userInfoDel", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse userInfoDel(Integer uid) {
        if (uid == null) {
            return ServerResponse.createByErrorMessage("操作失败!");
        }
        if (userService.delete(uid) > 0) {
            return ServerResponse.createBySuccessMessage("删除成功！");
        } else {
            return ServerResponse.createByErrorMessage("操作失败!");
        }
    }

    @RequiresPermissions("info:add")
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addUser(User user, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        ServerResponse x = userValidate(user);
        if (x != null) return x;
        user.setSchoolname(user.getAccount());
        user.setOperationuser(((Admin_User) session.getAttribute("CurrentAdminUser")).getId());
        user.setStatus(1);
        user.setPassword(MD5TUtils.threeMD5("123456"));
        userService.save(user);
        return ServerResponse.createBySuccessMessage("用戶创建成功");
    }

    private ServerResponse userValidate(User user) {
        if (user.getAccount().isEmpty()) {
            return ServerResponse.createByErrorMessage("用户名不能为空!");
        }
        if (user.getHeadmaster().isEmpty()) {
            return ServerResponse.createByErrorMessage("校长名称不能为空!");
        }
        if (user.getAddress().isEmpty()) {
            return ServerResponse.createByErrorMessage("学校地址不能为空!");
        }
        if (user.getPlayhousename().isEmpty()) {
            return ServerResponse.createByErrorMessage("留守儿童之家名称不能为空!");
        }
        if (user.getBooknumber() == null) {
            return ServerResponse.createByErrorMessage("图书数量不能为空!");
        }
        return null;
    }

    @RequiresPermissions("info:add")
    @RequestMapping(value = "getUserAdd", method = RequestMethod.GET)
    public String getAddUser(ModelMap map) {
        List<Province> provinceList=provinceService.getAll(null);
        map.addAttribute(provinceList);
        return "system/information/addUser";
    }

    @RequiresPermissions("info:update")
    @RequestMapping(value = "getEditorAdd", method = RequestMethod.GET)
    public String getEditorUser(Integer id, ModelMap map) {
        User user = userService.find(id);
        Integer cityid=countyService.find(user.getCountyid()).getCityid();
        Integer proviceid=provinceService.find( cityService.find(cityid).getProvinceid()).getId();
        City city=new City();
        city.setProvinceid(proviceid);
        List<City> citiyList=cityService.getAll(city);
        List<Province> provinceList=provinceService.getAll(null);
        County county=new County();
        county.setCityid(cityid);
        List<County> countyList = countyService.getAll(county);
        map.addAttribute(provinceList);
        map.addAttribute(citiyList);
        map.addAttribute(countyList);
        map.addAttribute("user", user);
        map.addAttribute("cityid",cityid);
        map.addAttribute("proviceid",proviceid);
        map.addAttribute("countyid",user.getCountyid());
        return "system/information/editorUser";
    }

    @RequiresPermissions("info:update")
    @RequestMapping(value = "editorUser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse editorUser(User user, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        ServerResponse x = userValidate(user);
        if (x != null) return x;
        user.setSchoolname(user.getAccount());
        user.setOperationuser(((Admin_User) session.getAttribute("CurrentAdminUser")).getId());
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            user.setPassword(MD5TUtils.threeMD5(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        userService.updateParams(user);
        return ServerResponse.createBySuccessMessage("用戶修改成功");
    }

}
