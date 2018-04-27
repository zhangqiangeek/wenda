package com.wenda.service;

import com.wenda.dao.QuestionDao;
import com.wenda.model.HostHolder;
import com.wenda.model.Question;
import com.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

/**
 * @author evilhex
 *         2017/11/18
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private SensitiveService sensitiveService;

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

    /**
     * 增加题目
     *
     * @param question
     * @return
     */
    public int addQuestion(Question question) {
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        System.out.println("到这里正常的");
        return questionDao.addQuestion(question) > 0 ? question.getId() : 0;
    }

    /**
     * 根据问题id查找
     *
     * @param id
     * @return
     */
    public Question selectById(int id) {
        return questionDao.selectById(id);
    }
}
