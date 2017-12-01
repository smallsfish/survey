package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.StudentDao;
import com.hassdata.survey.po.Student;
import com.hassdata.survey.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("studentService")
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {
    @Resource
    private StudentDao studentDao;
    @Override
    public BaseDao<Student> getMapper() {
        return studentDao;
    }

    @Override
    public List<Student> getScrollDataByLike(Student params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return studentDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Student params) {
        Map<String, Object> map = buildParams(params, null, null, null);
        return studentDao.getScrollByLikeCount(map);
    }
}
