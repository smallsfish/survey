package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Pictures;
import com.hassdata.survey.po.Video;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface PictureDao extends BaseDao<Pictures> {
    List<Pictures> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
}
