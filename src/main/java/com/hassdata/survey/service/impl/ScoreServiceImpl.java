package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.ScoreDao;
import com.hassdata.survey.po.Score;
import com.hassdata.survey.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("scoreService")
public class ScoreServiceImpl extends BaseServiceImpl<Score> implements ScoreService {
    @Resource
    private ScoreDao scoreDao;
    @Override
    public BaseDao<Score> getMapper() {
        return scoreDao;
    }

    @Override
    public List<Score> getUserWithQuestionnaireNumber(Integer uid) {
        return scoreDao.getUserWithQuestionnaireNumber(uid);
    }

    @Override
    public List<Score> getStudentWithQuestionnaireNumber(String sid) {
        return scoreDao.getStudentWithQuestionnaireNumber(sid);
    }
}
