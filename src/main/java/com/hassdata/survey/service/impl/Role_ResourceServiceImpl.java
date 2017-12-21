package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.Role_ResourceDao;
import com.hassdata.survey.po.Role_Resource;
import com.hassdata.survey.service.Role_ResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("role_ResourceService")
public class Role_ResourceServiceImpl extends BaseServiceImpl<Role_Resource> implements Role_ResourceService {
    @Resource
    private Role_ResourceDao role_ResourceDao;
    @Override
    public BaseDao<Role_Resource> getMapper() {
        return role_ResourceDao;
    }
    @Override
    public List<Role_Resource> getScrollDataByLike(Role_Resource params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return role_ResourceDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Role_Resource params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return role_ResourceDao.getScrollByLikeCount(map);
    }

    @Override
    public int updateByRoleId(Role_Resource role_resource) {
        return role_ResourceDao.updateByRoleId(role_resource);
    }

    @Override
    public int deleteByRoleId(Integer rid) {
        return role_ResourceDao.deleteByRoleId(rid);
    }
}
