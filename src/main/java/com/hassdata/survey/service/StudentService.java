package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Student;

import java.util.List;

public interface StudentService extends BaseService<Student> {
    List<Student> getScrollDataByLike(Student params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Student params);
}
