package com.wenda.controller;

import com.wenda.model.*;
import com.wenda.service.CommentService;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;
import com.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by evilhex on 2017/12/16.
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(path = { "/question/add" }, method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCommentCount(0);
            question.setCreatedDate(new Date());
            if(hostHolder.getUser()==null){
                //question.setUserId(WendaUtil.ANONYMOUS_USERID);
                return WendaUtil.getJSONString(999);
            }else {
                question.setUserId(hostHolder.getUser().getId());
            }
            if (questionService.addQuestion(question)>0){
                return WendaUtil.getJSONString(0);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            logger.error("增加题目失败");
        }
        return WendaUtil.getJSONString(1,"失败");

    }

    /**
     * 问题详情
     * @param qid
     * @return
     */
    @RequestMapping(path = {"/question/{qid}"},method = RequestMethod.GET)
    public String selectQuestion(Model model,@PathVariable("qid") int qid){
        Question question=questionService.selectById(qid);
        model.addAttribute("question",question);
        model.addAttribute("user",userService.getUser(question.getUserId()));

        List<Comment> commentList=commentService.getCommentsByEntity(qid, EntityType.ENTITY_COMMENT);
        List<ViewObject> comments=new ArrayList<ViewObject>();
        for (Comment comment:commentList){
            ViewObject vo=new ViewObject();
            vo.set("comment",comment);
            vo.set("user",userService.getUser(comment.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("comments",comments);
        return "detail";
    }


}
