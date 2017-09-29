package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.ScoreDao;
import com.hassdata.survey.dao.StudentDao;
import com.hassdata.survey.po.Score;
import com.hassdata.survey.po.Student;
import com.hassdata.survey.service.ScoreService;
import com.hassdata.survey.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("scoreService")
public class ScoreServiceImpl extends BaseServiceImpl<Score> implements ScoreService {
    @Resource
    private ScoreDao scoreDao;
    @Override
    public BaseDao<Score> getMapper() {
        return scoreDao;
    }
}
