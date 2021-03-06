package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Admin_User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public interface AdminUserDao extends BaseDao<Admin_User>{
    List<Admin_User> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
    Set<String> findRoleByAccount(String account);
    Set<String> findPermissionByAccount(String account);
    int getIdMax();
}
