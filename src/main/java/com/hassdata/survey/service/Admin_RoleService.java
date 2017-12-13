package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Admin_Role;

import java.util.List;

public interface Admin_RoleService extends BaseService<Admin_Role> {
    List<Admin_Role> getScrollDataByLike(Admin_Role params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Admin_Role params);
}
