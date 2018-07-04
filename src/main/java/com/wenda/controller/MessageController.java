package com.wenda.controller;

import com.wenda.model.HostHolder;
import com.wenda.model.Message;
import com.wenda.model.User;
import com.wenda.model.ViewObject;
import com.wenda.service.MessageService;
import com.wenda.service.UserService;
import com.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 站内信控制器
 *
 * @author evilhex.
 * @date 2018/7/4 下午4:22.
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    /**
     * 发送站内信
     *
     * @param toName
     * @param content
     * @return
     */
    @RequestMapping(path = { "msg/addMessage" })
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName, @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999, "未登录");
            }
            User fromUser = hostHolder.getUser();
            User toUser = userService.selectByName(toName);
            if (toUser == null) {
                return WendaUtil.getJSONString(1, "用户不存在");
            }
            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(fromUser.getId());
            message.setToId(toUser.getId());
            message.setContent(content);
            messageService.addMessage(message);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("发送站内信异常：{}", e.getMessage());
            return WendaUtil.getJSONString(1, "发信失败");
        }
    }

    /**
     * 站内信列表
     *
     * @param model
     * @return
     */
    @RequestMapping(path = { "msg/list" })
    public String getConversationList(Model model) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        int localUserId = hostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
        List<ViewObject> conversations = new ArrayList<ViewObject>();
        for (Message message : conversationList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("conversation", message);
            int targetid = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
            viewObject.set("user", userService.getUser(targetid));
            viewObject.set("unread",messageService.getConversationUnreadCount(localUserId,message.getConversationId()));
            conversations.add(viewObject);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }

    /**
     * 站内信详情
     *
     * @param model
     * @param conversationId
     * @return
     */
    @RequestMapping(path = { "msg/detail" })
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<ViewObject>();
            for (Message message : messageList) {
                ViewObject viewObject = new ViewObject();
                viewObject.set("message", message);
                viewObject.set("user", userService.getUser(message.getFromId()));
                messages.add(viewObject);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取详情失败：{}", e.getMessage());
        }
        return "letterDetail";
    }

}
