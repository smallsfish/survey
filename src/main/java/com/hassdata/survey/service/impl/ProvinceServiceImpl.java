package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.ProvinceDao;
import com.hassdata.survey.po.Province;
import com.hassdata.survey.service.ProvinceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("provinceService")
public class ProvinceServiceImpl extends BaseServiceImpl<Province> implements ProvinceService {
    @Resource
    private ProvinceDao provinceDao;
    @Override
    public BaseDao<Province> getMapper() {
        return provinceDao;
    }
    @Override
    public List<Province> getScrollDataByLike(Province params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return provinceDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Province params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return provinceDao.getScrollByLikeCount(map);
    }
}
