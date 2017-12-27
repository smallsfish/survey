package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.CityDao;
import com.hassdata.survey.po.City;
import com.hassdata.survey.service.CityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("cityService")
public class CityServiceImpl extends BaseServiceImpl<City> implements CityService {
    @Resource
    private CityDao cityDao;
    @Override
    public BaseDao<City> getMapper() {
        return cityDao;
    }
    @Override
    public List<City> getScrollDataByLike(City params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return cityDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(City params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return cityDao.getScrollByLikeCount(map);
    }

    @Override
    public int deleteByProvinceId(Integer provinceid) {
        return cityDao.deleteByProvinceId(provinceid);
    }
}
