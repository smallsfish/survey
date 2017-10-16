package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.User;

import java.util.List;

public interface UserService extends BaseService<User> {
    List<User> getScrollDataByLike(User params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(User params);
}
