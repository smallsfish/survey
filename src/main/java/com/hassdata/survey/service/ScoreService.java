package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Score;

import java.util.List;
import java.util.Map;

public interface ScoreService extends BaseService<Score> {
    List<Score> getUserWithQuestionnaireNumber(Integer uid);
    List<Score> getStudentWithQuestionnaireNumber(String sid);
    List<Score> getQuestionnaireWithUserId(String id);
    List<Score> getQuestionnaireWithStudentId(String id);
    List<Score> getOptionCountWidthQuesitonnaire(String questionnaireid,String questionid,String optionid);
}