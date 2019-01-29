package com.wenda.service;

import com.wenda.model.Question;
import com.wenda.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: evilhex
 * @Date: 2019-01-10 18:42
 */
@Service
public class TransactionService {
    @Resource
    private UserService userService;
    @Resource
    private QuestionService questionService;

    @Transactional
    public void transactionInsert() {
        User user = new User();
        userService.register("transactionTest", "transactionTest");
        Question question = new Question();
        question.setTitle("transactionTest");
        question.setContent("transactionTest");
        questionService.addQuestion(question);
    }
}
