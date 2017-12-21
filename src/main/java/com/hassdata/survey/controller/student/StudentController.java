package com.hassdata.survey.controller.student;

import com.hassdata.survey.dto.StudentDTO;
import com.hassdata.survey.po.Score;
import com.hassdata.survey.po.Student;
import com.hassdata.survey.service.ScoreService;
import com.hassdata.survey.service.StudentService;
import com.hassdata.survey.util.ServerResponse;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("system")
@Scope("prototype")
public class StudentController {

    @Resource
    private StudentService studentService;

    @Resource
    private ScoreService scoreService;
    @RequestMapping(value = "getStudent",method = RequestMethod.GET)
    public String getStudentByUid(int uid, ModelMap map){
        map.addAttribute("uid",uid);
        return "system/information/studentmanager";
    }

    @RequestMapping(value = "getStudentList",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getStudentList(Student student, @RequestParam(required = false,defaultValue = "1") Integer page, @RequestParam(required = false,defaultValue = "30") Integer limit){
        long count=studentService.getScrollCount(student);
        List<Student> students=studentService.getScrollData(student,"id desc",(page-1)*limit,limit);
        List<StudentDTO> studentDTOS=new ArrayList<>();
        setSutdentDTO(students,studentDTOS);
        return ServerResponse.createBySuccessForLayuiTable("查询成功",studentDTOS,count);
    }

    @RequestMapping(value = "studentSearch",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse studentSearch(HttpServletRequest request,Integer uid, @RequestParam(required = false,defaultValue = "1") Integer page, @RequestParam(required = false,defaultValue = "30") Integer limit){
        String studentName= null;
        try {
            studentName = new String(request.getParameter("studentName").getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(studentName==null || studentName.equals("")){
            return ServerResponse.createByErrorMessage("请输入学生姓名");
        }
        Student student=new Student();
        student.setStudentname("%"+studentName+"%");
        student.setUid(uid);
        long count=studentService.getScrollByLikeCount(student);
        List<Student> students=studentService.getScrollDataByLike(student,"id desc",(page-1)*limit,limit);
        List<StudentDTO> studentDTOS=new ArrayList<>();
        setSutdentDTO(students, studentDTOS);
        return ServerResponse.createBySuccessForLayuiTable("搜索成功",studentDTOS,count);
    }

    @RequestMapping(value = "studentDel",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse studentDel(String id){
        studentService.deleteByStringId(id);
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    private void setSutdentDTO(List<Student> students, List<StudentDTO> studentDTOS) {
        StudentDTO studentDTO;
        int i=1;
        for(Student s: students){
            studentDTO=new StudentDTO();
            studentDTO.setAge(s.getAge());
            studentDTO.setAid(i);
            studentDTO.setClasses(s.getClasses());
            studentDTO.setGrade(s.getGrade());
            studentDTO.setId(s.getId());
            studentDTO.setStudentname(s.getStudentname());
            studentDTO.setSex(s.getSex());
            studentDTO.setQuestionnairenumber(scoreService.getStudentWithQuestionnaireNumber(s.getId()).size());
            studentDTOS.add(studentDTO);
            i++;
        }
    }

}
