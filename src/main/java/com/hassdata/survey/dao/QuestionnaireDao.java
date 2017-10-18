package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Questionnaire;
import com.hassdata.survey.po.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface QuestionnaireDao extends BaseDao<Questionnaire> {
    List<Questionnaire> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
}
