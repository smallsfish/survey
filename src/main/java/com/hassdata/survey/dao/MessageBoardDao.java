package com.hassdata.survey.dao;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.po.MessageBoard;
import com.hassdata.survey.po.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface MessageBoardDao extends BaseDao<MessageBoard> {
    List<MessageBoard> getScrollDataByLike(Map<String, Object> params);
    long getScrollByLikeCount(Map<String, Object> params);
}
