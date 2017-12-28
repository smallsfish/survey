package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Score;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ScoreDao extends BaseDao<Score> {
    List<Score> getUserWithQuestionnaireNumber(Integer uid);
    List<Score> getStudentWithQuestionnaireNumber(String sid);
    List<Score> getQuestionnaireWithUserId(String id);
    List<Score> getQuestionnaireWithStudentId(String id);
    List<Score> getOptionCountWidthQuesitonnaire(Map<String,String> params);
    long getSelectCountByCountyId(Map<String,Object> params);
    long getSelectCountByOptionId(Map<String,Object> params);
}
