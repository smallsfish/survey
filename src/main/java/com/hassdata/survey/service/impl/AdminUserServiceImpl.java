package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.AdminUserDao;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("adminUserService")
public class AdminUserServiceImpl extends BaseServiceImpl<Admin_User> implements AdminUserService {
    @Resource
    private AdminUserDao adminUserDao;
    @Override
    public BaseDao<Admin_User> getMapper() {
        return this.adminUserDao;
    }
}
