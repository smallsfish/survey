package com.hassdata.survey.service.impl;

import com.hassdata.survey.base.BaseDao;
import com.hassdata.survey.base.BaseServiceImpl;
import com.hassdata.survey.dao.MessageBoardDao;
import com.hassdata.survey.dao.UserDao;
import com.hassdata.survey.po.MessageBoard;
import com.hassdata.survey.po.User;
import com.hassdata.survey.service.MessageBoardService;
import com.hassdata.survey.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("messageBoardService")
public class MessageBoardServiceImpl extends BaseServiceImpl<MessageBoard> implements MessageBoardService {
    @Resource
    private MessageBoardDao messageBoardDao;
    @Override
    public BaseDao<MessageBoard> getMapper() {
        return messageBoardDao;
    }
    @Override
    public List<MessageBoard> getScrollDataByLike(MessageBoard params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return messageBoardDao.getScrollDataByLike(map);
    }

    @Override
    public long getScrollByLikeCount(MessageBoard params) {
        Map<String, Object> map = buildParams(params ,null, null, null);
        return messageBoardDao.getScrollByLikeCount(map);
    }
}
