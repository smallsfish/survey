package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.CountyDao;
import com.hassdata.survey.po.County;
import com.hassdata.survey.service.CountyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("countyService")
public class CountyServiceImpl extends BaseServiceImpl<County> implements CountyService {
    @Resource
    private CountyDao countyDao;
    @Override
    public BaseDao<County> getMapper() {
        return countyDao;
    }
    @Override
    public List<County> getScrollDataByLike(County params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return countyDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(County params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return countyDao.getScrollByLikeCount(map);
    }
}
