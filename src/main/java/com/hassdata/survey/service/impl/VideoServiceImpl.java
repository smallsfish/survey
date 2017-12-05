package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.VideoDao;
import com.hassdata.survey.po.Video;
import com.hassdata.survey.service.VideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("videoService")
public class VideoServiceImpl extends BaseServiceImpl<Video> implements VideoService {
    @Resource
    private VideoDao videoDao;
    @Override
    public BaseDao<Video> getMapper() {
        return videoDao;
    }
    @Override
    public List<Video> getScrollDataByLike(Video params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return videoDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(Video params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return videoDao.getScrollByLikeCount(map);
    }
}
