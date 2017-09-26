package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.OptionsDao;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.Options;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.OptionsService;
import com.hassdata.survey.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("optionsService")
public class OptionsServiceImpl extends BaseServiceImpl<Options> implements OptionsService {
    @Resource
    private OptionsDao optionsDao;
    @Override
    public BaseDao<Options> getMapper() {
        return optionsDao;
    }
}
