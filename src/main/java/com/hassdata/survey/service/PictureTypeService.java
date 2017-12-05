package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Picturetype;

import java.util.List;

public interface PictureTypeService extends BaseService<Picturetype> {
    List<Picturetype> getScrollDataByLike(Picturetype params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Picturetype params);
}
