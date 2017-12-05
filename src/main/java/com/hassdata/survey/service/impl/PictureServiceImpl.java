package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.PictureDao;
import com.hassdata.survey.po.Pictures;
import com.hassdata.survey.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("pictureService")
public class PictureServiceImpl extends BaseServiceImpl<Pictures> implements PictureService {
    @Resource
    private PictureDao pictureDao;
    @Override
    public BaseDao<Pictures> getMapper() {
        return pictureDao;
    }
    @Override
    public List<Pictures> getScrollDataByLike(Pictures params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return pictureDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Pictures params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return pictureDao.getScrollByLikeCount(map);
    }
}
