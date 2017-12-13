package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.ResourceDao;
import com.hassdata.survey.service.ResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl<com.hassdata.survey.po.Resource> implements ResourceService {
    @Resource
    private ResourceDao resourceDao;

    @Override
    public BaseDao<com.hassdata.survey.po.Resource> getMapper() {
        return resourceDao;
    }

    @Override
    public List<com.hassdata.survey.po.Resource> getScrollDataByLike(com.hassdata.survey.po.Resource params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return resourceDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(com.hassdata.survey.po.Resource params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return resourceDao.getScrollByLikeCount(map);
    }

    @Override
    public List<com.hassdata.survey.po.Resource> getResourceByAccount(String account) {
        return resourceDao.getResourceByAccount(account);
    }
}
