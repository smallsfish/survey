package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.LoopDao;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.Loop;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.LoopService;
import com.hassdata.survey.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("loopService")
public class LoopServiceImpl extends BaseServiceImpl<Loop> implements LoopService {
    @Resource
    private LoopDao loopDao;

    @Override
    public BaseDao<Loop> getMapper() {
        return loopDao;
    }
}
