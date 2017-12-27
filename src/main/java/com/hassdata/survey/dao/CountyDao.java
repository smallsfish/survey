package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.County;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface CountyDao extends BaseDao<County> {
    List<County> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
    int deleteByCityId(Integer cityid);
}
