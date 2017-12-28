package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.ScoreDao;
import com.hassdata.survey.po.Score;
import com.hassdata.survey.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Score> getQuestionnaireWithUserId(String id) {
        return scoreDao.getQuestionnaireWithUserId(id);
    }
    @Override
    public List<Score> getQuestionnaireWithStudentId(String id) {
        return scoreDao.getQuestionnaireWithStudentId(id);
    }

    @Override
    public List<Score> getOptionCountWidthQuesitonnaire(String questionnaireid, String questionid, String optionid) {
        Map<String,String> params=new HashMap<>();
        params.put("questionnaireid",questionnaireid);
        params.put("questionid",questionid);
        params.put("optionid",optionid);
        return scoreDao.getOptionCountWidthQuesitonnaire(params);
    }

    @Override
    public long getSelectCountByCountyId(String option, Integer countyid, String questionnaireid) {
        Map<String,Object> params=new HashMap<>();
        params.put("option",option);
        params.put("countyid",countyid);
        params.put("questionnaireid",questionnaireid);
        return scoreDao.getSelectCountByCountyId(params);
    }

    @Override
    public long getSelectCountByOptionId(String optionid, Integer countyid) {
        Map<String,Object> params=new HashMap<>();
        params.put("optionid",optionid);
        params.put("countyid",countyid);
        return scoreDao.getSelectCountByOptionId(params);
    }

}
