package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Admin_User;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdminUserService extends BaseService<Admin_User> {
    List<Admin_User> getScrollDataByLike(Admin_User params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Admin_User params);
    Set<String> findRoleByAccount(String account);
    Set<String> findPermissionByAccount(String account);
}
