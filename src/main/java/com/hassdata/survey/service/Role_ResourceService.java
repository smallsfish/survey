package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Role_Resource;
import com.hassdata.survey.po.User;

import java.util.List;

public interface Role_ResourceService extends BaseService<Role_Resource> {
    List<Role_Resource> getScrollDataByLike(Role_Resource params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Role_Resource params);
    int updateByRoleId(Role_Resource role_resource);
    int deleteByRoleId(Integer rid);
}
