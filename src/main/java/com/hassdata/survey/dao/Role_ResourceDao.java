package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Role_Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface Role_ResourceDao extends BaseDao<Role_Resource> {
    List<Role_Resource> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
}
