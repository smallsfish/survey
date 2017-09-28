package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.QuestionTypeDao;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.QuestionType;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.QuestionTypeService;
import com.hassdata.survey.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("questionTypeService")
public class QuestionTypeServiceImpl extends BaseServiceImpl<QuestionType> implements QuestionTypeService {
    @Resource
    private QuestionTypeDao questionTypeDao;
    @Override
    public BaseDao<QuestionType> getMapper() {
        return questionTypeDao;
    }

    @Override
    public int deleteByQuestionnaireId(String questionnaireid) {
        return questionTypeDao.deleteByQuestionnaireId(questionnaireid);
    }
}
