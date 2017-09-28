package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Question;
import com.hassdata.survey.po.User;
import org.springframework.stereotype.Component;

@Component
public interface QuestionDao extends BaseDao<Question> {
    int deleteByQuestionnaireId(String questionnaireid);
}
