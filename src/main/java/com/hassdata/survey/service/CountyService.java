package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.County;

import java.util.List;

public interface CountyService extends BaseService<County> {
    List<County> getScrollDataByLike(County params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(County params);
}
