package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.Admin_RoleDao;
import com.hassdata.survey.po.Admin_Role;
import com.hassdata.survey.service.Admin_RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("admin_RoleService")
public class Admin_RoleServiceImpl extends BaseServiceImpl<Admin_Role> implements Admin_RoleService {
    @Resource
    private Admin_RoleDao admin_RoleDao;
    @Override
    public BaseDao<Admin_Role> getMapper() {
        return admin_RoleDao;
    }
    @Override
    public List<Admin_Role> getScrollDataByLike(Admin_Role params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return admin_RoleDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Admin_Role params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return admin_RoleDao.getScrollByLikeCount(map);
    }

    @Override
    public int updateAdminRoleByAid(Admin_Role admin_role) {
        return admin_RoleDao.updateAdminRoleByAid(admin_role);
    }
}
