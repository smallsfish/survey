package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.StudentDao;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.Student;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.StudentService;
import com.hassdata.survey.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("studentService")
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {
    @Resource
    private StudentDao studentDao;
    @Override
    public BaseDao<Student> getMapper() {
        return studentDao;
    }
}
