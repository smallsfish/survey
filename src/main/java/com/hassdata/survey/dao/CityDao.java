package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.City;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface CityDao extends BaseDao<City> {
    List<City> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
}
