package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.AdminUserDao;
import com.hassdata.survey.dao.Admin_RoleDao;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.Admin_Role;
import com.hassdata.survey.po.Admin_User;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.AdminUserService;
import com.hassdata.survey.service.Admin_RoleService;
import com.hassdata.survey.service.UserService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@Service("adminUserService")
public class AdminUserServiceImpl extends BaseServiceImpl<Admin_User> implements AdminUserService {
    @Resource
    private AdminUserDao adminUserDao;


    @Override
    public BaseDao<Admin_User> getMapper() {
        return this.adminUserDao;
    }

    @Override
    public List<Admin_User> getScrollDataByLike(Admin_User params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return adminUserDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Admin_User params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return adminUserDao.getScrollByLikeCount(map);
    }

    @Override
    public Set<String> findRoleByAccount(String account) {
        return adminUserDao.findRoleByAccount(account);
    }

    @Override
    public Set<String> findPermissionByAccount(String account) {
        return adminUserDao.findPermissionByAccount(account);
    }
}
