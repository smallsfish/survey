package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.NewstypeDao;
import com.hassdata.survey.po.Newstype;
import com.hassdata.survey.service.NewsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("newsTypeService")
public class NewsTypeServiceImpl extends BaseServiceImpl<Newstype> implements NewsTypeService {
    @Resource
    private NewstypeDao newstypeDao;
    @Override
    public BaseDao<Newstype> getMapper() {
        return newstypeDao;
    }
    @Override
    public List<Newstype> getScrollDataByLike(Newstype params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return newstypeDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Newstype params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return newstypeDao.getScrollByLikeCount(map);
    }
}
