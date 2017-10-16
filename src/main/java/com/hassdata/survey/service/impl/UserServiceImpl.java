package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.User;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Resource
    private UserDao userDao;
    @Override
    public BaseDao<User> getMapper() {
        return userDao;
    }
    @Override
    public List<User> getScrollDataByLike(User params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return userDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(User params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return userDao.getScrollByLikeCount(map);
    }
}
