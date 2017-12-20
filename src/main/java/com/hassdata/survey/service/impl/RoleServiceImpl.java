package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.RoleDao;
import com.hassdata.survey.po.Role;
import com.hassdata.survey.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Resource
    private RoleDao roleDao;
    @Override
    public BaseDao<Role> getMapper() {
        return roleDao;
    }
    @Override
    public List<Role> getScrollDataByLike(Role params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return roleDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Role params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return roleDao.getScrollByLikeCount(map);
    }

    @Override
    public Integer getIdMax() {
        return roleDao.getIdMax();
    }

}
