package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ResourceDao extends BaseDao<Resource> {
    List<Resource> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
    List<Resource> getResourceByAccount(String account);
    List<String> getResourceNameByRoleId(Integer rid);
    Integer getIdMax();
}
