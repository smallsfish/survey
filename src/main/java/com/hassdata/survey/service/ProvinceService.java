package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Province;

import java.util.List;

public interface ProvinceService extends BaseService<Province> {
    List<Province> getScrollDataByLike(Province params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Province params);
}
