package com.wenda.service;

import com.wenda.dao.MessageDao;
import com.wenda.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息服务逻辑处理
 *
 * @author evilhex.
 * @date 2018/7/4 下午4:07.
 */
@Service
public class MessageService {
    @Autowired
    MessageDao messageDao;

    @Autowired
    SensitiveService sensitiveService;

    /**
     * 发送站内信
     *
     * @param message
     * @return
     */
    public int addMessage(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDao.addMessage(message) > 0 ? message.getId() : 0;
    }

    /**
     * 获取站内信的详情
     *
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDao.getConversationDetail(conversationId, offset, limit);
    }

    /**
     * 获取站内信列表
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDao.getConversationList(userId, offset, limit);
    }

    /**
     * 获取未读的站内信的数量
     *
     * @param userId
     * @param conversationId
     * @return
     */
    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDao.getConversationUnreadCount(userId, conversationId);
    }

}
