package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Score;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ScoreDao extends BaseDao<Score> {
    List<Score> getUserWithQuestionnaireNumber(Integer uid);
    List<Score> getStudentWithQuestionnaireNumber(String sid);
}
