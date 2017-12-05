package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.News;
import com.hassdata.survey.po.User;

import java.util.List;

public interface NewsService extends BaseService<News> {
    List<News> getScrollDataByLike(News params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(News params);
}
