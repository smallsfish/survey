package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Newstype;

import java.util.List;

public interface NewsTypeService extends BaseService<Newstype> {
    List<Newstype> getScrollDataByLike(Newstype params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Newstype params);
}
