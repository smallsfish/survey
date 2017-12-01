package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.Video;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface VideoDao extends BaseDao<Video> {
    List<Video> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
}
