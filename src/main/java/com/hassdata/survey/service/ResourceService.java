package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.Resource;

import java.util.List;

public interface ResourceService extends BaseService<Resource> {
    List<Resource> getScrollDataByLike(Resource params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(Resource params);
    List<Resource> getResourceByAccount(String account);
    List<String> getResourceNameByRoleId(Integer rid);
}
