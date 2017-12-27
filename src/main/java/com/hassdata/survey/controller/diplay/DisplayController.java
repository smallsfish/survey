package com.hassdata.survey.controller.diplay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.po.Score;
import com.hassdata.survey.po.Student;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.*;
import com.hassdata.survey.util.MD5TUtils;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Controller
@Scope("prototype")
@RequestMapping("display")
public class DisplayController {

    @Resource
    private UserService userService;
    @Resource
    private StudentService studentService;
    @Resource
    private ScoreService scoreService;

    private String questionnaireId;
    private String qeustionId;
    private ArrayList<Score> scores;




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
    public String displayLogin(User user, ModelMap map, HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String path, @RequestParam(required = false) String parameter) {
        HttpSession session = request.getSession(true);
        if (parameter != null && !path.equals("")) {
            map.addAttribute("path", path);
        }
        if (path != null && !path.equals("")) {
            map.addAttribute("parameter", parameter);
        }
        if (user.getAccount().equals("") || user.getAccount() == null) {
            map.addAttribute("error", "请输入用户名或密码");
        }
        if (user.getPassword().equals("") || user.getPassword() == null) {
            map.addAttribute("error", "请输入用户名或密码");
        }
        String contextPath=request.getContextPath();
        System.out.println(contextPath+"===================================`");
        String password = MD5TUtils.threeMD5(user.getPassword());
        user.setPassword(null);
        User userS = userService.getOne(user);
        if (userS == null) {
            session.setAttribute("error", "用户名或密码错误");
        } else {
            if (!userS.getPassword().equals(password)) {
                session.setAttribute("error", "用户名或密码错误");
            } else {
                userS.setPassword(null);
                userS.setLastlogintime(new Date());
                session.setAttribute("CurrentUser", userS);
                userService.updateParams(userS);
                if(path!=null && parameter!=null && !path.equals("") && !parameter.equals("")){
                    session.setAttribute("parameter",parameter);
                    return "redirect:/"+contextPath+path+"?id="+parameter;
                }else{
                    map.addAttribute("id",userS.getId());
                    return "display/user/updateUserPassword";
                }
            }
        }
        return "redirect:/"+contextPath+"display/login";
    }

    @RequestMapping(value = "updateUserPassword",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateUserPassword(User user,HttpSession session){
        user.setPassword(MD5TUtils.threeMD5(user.getPassword()));
        userService.updateParams(user);
        session.removeAttribute("CurrentUser");
        return ServerResponse.createBySuccessMessage("修改密码成功,即将退出");
    }

    @RequestMapping(value = "submitQuestionnaire", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse submitQuestionnaire(@RequestBody String submitJson, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("CurrentUser");
        JSONArray jsonArray = (JSONArray) JSON.parse(submitJson);
        JSONObject jsonObject = null;
        JSONObject oldJsonObject = null;
        Student student = new Student();
        student.setUid(user.getId());
        String sid = UUID.randomUUID().toString();
        student.setId(sid);
        Score score = null;
        String options = "";
        scores = new ArrayList<>();
        for (int i = 0; i <= jsonArray.size() - 1; i++) {
            jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            if (name.equals("studentname")) {
                student.setStudentname(jsonObject.getString("value"));
            } else if (name.equals("grade")) {
                student.setGrade(jsonObject.getString("value"));
            } else if (name.equals("classes")) {
                student.setClasses(jsonObject.getString("value"));
            } else if (name.contains("'_'")) {
                String[] ids = name.split("'_'");
                questionnaireId = ids[0];
                qeustionId = ids[2];
                options += jsonObject.getString("value") + "/";

            } else {
                if (i > 4) {
                    setScore(user, options, student);
                    options = "";
                }

            }
            if (i == jsonArray.size() - 1) {
                setScore(user, options, student);
                options = "";
            }
        }
        Student s=new Student();
        s.setStudentname(student.getStudentname());
        s.setUid(student.getUid());
        s.setClasses(student.getClasses());
        s.setGrade(student.getGrade());
        Student st=studentService.getOne(s);
        if(st!=null) {
            Score sc = new Score();
            sc.setQuestionnaireid(questionnaireId);
            sc.setSid(st.getId());
            if(scoreService.getOne(sc)!=null){
                return ServerResponse.createByErrorMessage("你已经填写过此问卷，无需重复填写");
            }
        }
        studentService.save(student);
        scoreService.saveBatch(scores);
        return ServerResponse.createBySuccessMessage("问卷提交成功");
    }

    private void setScore(User user, String options, Student student) {
        Score score;
        score = new Score();
        score.setUid(user.getId());
        score.setSid(student.getId());
        score.setQuestionnaireid(questionnaireId);
        score.setQuestionid(qeustionId);
        score.setOptions(options);
        scores.add(score);
    }


}
