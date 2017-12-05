package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.PicturetypeDao;
import com.hassdata.survey.po.Picturetype;
import com.hassdata.survey.service.PictureTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("pictureTypeService")
public class PictureTypeServiceImpl extends BaseServiceImpl<Picturetype> implements PictureTypeService {
    @Resource
    private PicturetypeDao pictureTypeDao;
    @Override
    public BaseDao<Picturetype> getMapper() {
        return pictureTypeDao;
    }
    @Override
    public List<Picturetype> getScrollDataByLike(Picturetype params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return pictureTypeDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Picturetype params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return pictureTypeDao.getScrollByLikeCount(map);
    }
}
