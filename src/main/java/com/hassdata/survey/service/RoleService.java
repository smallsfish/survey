package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Role;

import java.util.List;

public interface RoleService extends BaseService<Role> {
    List<Role> getScrollDataByLike(Role params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Role params);
    Integer getIdMax();
}
