package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Score;

import java.util.List;

public interface ScoreService extends BaseService<Score> {
    List<Score> getUserWithQuestionnaireNumber(Integer uid);
    List<Score> getStudentWithQuestionnaireNumber(String sid);
}