package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Admin_Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface Admin_RoleDao extends BaseDao<Admin_Role> {
    List<Admin_Role> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
}
