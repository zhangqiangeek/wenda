package com.wenda.controller;

import com.wenda.model.EntityType;
import com.wenda.model.HostHolder;
import com.wenda.service.LikeService;
import com.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 点赞控制器
 *
 * @author evilhex.
 * @date 2018/7/20 下午6:03.
 */
@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 点赞
     *
     * @param commentId
     * @return
     */
    @RequestMapping(path = { "like" }, method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }

    /**
     * 点踩
     *
     * @param commentId
     * @return
     */
    @RequestMapping(path = { "dislike" }, method = RequestMethod.POST)
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }

}
