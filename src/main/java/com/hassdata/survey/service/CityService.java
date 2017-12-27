package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.City;

import java.util.List;

public interface CityService extends BaseService<City> {
    List<City> getScrollDataByLike(City params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(City params);
}
