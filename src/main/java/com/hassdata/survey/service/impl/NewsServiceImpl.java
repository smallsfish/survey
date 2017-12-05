package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.NewsDao;
import com.hassdata.survey.po.News;
import com.hassdata.survey.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("newsService")
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {
    @Resource
    private NewsDao newsDao;
    @Override
    public BaseDao<News> getMapper() {
        return newsDao;
    }
    @Override
    public List<News> getScrollDataByLike(News params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return newsDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(News params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return newsDao.getScrollByLikeCount(map);
    }
}
