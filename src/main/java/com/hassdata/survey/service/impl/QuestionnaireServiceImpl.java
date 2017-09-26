package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.QuestionnaireDao;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.Questionnaire;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.QuestionnaireService;
import com.hassdata.survey.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("questionnaireService")
public class QuestionnaireServiceImpl extends BaseServiceImpl<Questionnaire> implements QuestionnaireService {
    @Resource
    private QuestionnaireDao questionnaireDao;
    @Override
    public BaseDao<Questionnaire> getMapper() {
        return questionnaireDao;
    }
}
