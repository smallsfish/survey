package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.User;
import com.hassdata.survey.po.Video;

import java.util.List;

public interface VideoService extends BaseService<Video> {
    List<Video> getScrollDataByLike(Video params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Video params);
}
