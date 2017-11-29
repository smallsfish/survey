package com.hassdata.survey.controller.student;

import com.hassdata.survey.dto.StudentDTO;
import com.hassdata.survey.po.Student;
import com.hassdata.survey.service.ScoreService;
import com.hassdata.survey.service.StudentService;
import com.hassdata.survey.util.ServerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
        StudentDTO studentDTO=null;
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
        return ServerResponse.createBySuccessForLayuiTable("查询成功",studentDTOS,count);
    }

}
