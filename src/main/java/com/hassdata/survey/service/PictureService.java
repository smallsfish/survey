package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Pictures;

import java.util.List;

public interface PictureService extends BaseService<Pictures> {
    List<Pictures> getScrollDataByLike(Pictures params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Pictures params);
}
