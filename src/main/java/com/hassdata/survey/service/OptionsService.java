package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Options;
import com.hassdata.survey.po.User;

public interface OptionsService extends BaseService<Options> {
    int deleteByQuestionid(String questionid);
}
