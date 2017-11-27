package com.hassdata.survey.service;

import com.hassdata.survey.base.BaseService;
import com.hassdata.survey.po.MessageBoard;
import com.hassdata.survey.po.User;

import java.util.List;

public interface MessageBoardService extends BaseService<MessageBoard> {
    List<MessageBoard> getScrollDataByLike(MessageBoard params, String orderBy, Integer fromIndex, Integer pageSize);
    long getScrollByLikeCount(MessageBoard params);
}
