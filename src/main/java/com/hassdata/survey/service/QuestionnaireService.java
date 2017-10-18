package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Questionnaire;
import com.hassdata.survey.po.User;

import java.util.List;

public interface QuestionnaireService extends BaseService<Questionnaire> {
    List<Questionnaire> getScrollDataByLike(Questionnaire params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Questionnaire params);
}
