package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.QuestionDao;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.Question;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.QuestionService;
import com.hassdata.survey.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("questionService")
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {
    @Resource
    private QuestionDao questionDao;
    @Override
    public BaseDao<Question> getMapper() {
        return questionDao;
    }

    @Override
    public int deleteByQuestionnaireId(String questionnaireid) {
        return questionDao.deleteByQuestionnaireId(questionnaireid);
    }
}
