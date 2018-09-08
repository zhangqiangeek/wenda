package com.wenda.async.handler;

import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import com.wenda.model.Message;
import com.wenda.model.User;
import com.wenda.service.MessageService;
import com.wenda.service.UserService;
import com.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 点赞事件处理
 *
 * @author evilhex.
 * @date 2018/8/8 下午7:04.
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        System.out.println(model.getExts("questionId"));
        message.setContent("用户" + user.getName() + "赞了你的评论，http://127.0.0.1:8080/question/" + model.getExts("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
