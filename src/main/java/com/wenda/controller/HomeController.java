package com.wenda.controller;

import com.wenda.model.HostHolder;
import com.wenda.model.Question;
import com.wenda.model.ViewObject;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evilhex on 2017/11/18.
 */
@Controller
public class HomeController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping(path = { "/", "/index" })
    public String index(Model model) {
        List<Question> questions = questionService.getLatestQuestions(0, 0, 10);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for (Question question : questions) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("user", userService.getUser(question.getId()));
            vos.add(viewObject);
        }
        model.addAttribute("vos", vos);

        return "index";
    }

    /**
     * 用户首页
     *
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(path = { "/user/{userId}" }, method = RequestMethod.GET)
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", questionService.getLatestQuestions(userId, 0, 10));
        return "index";
    }

}
