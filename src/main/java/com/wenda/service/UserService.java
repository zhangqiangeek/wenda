package com.wenda.service;

import com.wenda.dao.LoginTicketDao;
import com.wenda.dao.UserDao;
import com.wenda.model.LoginTicket;
import com.wenda.model.User;
import com.wenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author evilhex
 *         2017/11/18
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;

    /**
     * 用户注册功能实现
     *
     * @param name
     * @param password
     * @return
     */
    public Map<String, String> register(String name, String password) {
        Map<String, String> map = new HashMap<String, String>(16);
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            map.put("msg", "用户名称或密码不能为空");
            return map;
        }
        if (userDao.selectByName(name) != null) {
            map.put("msg", "用户名称已经注册，请更换名称");
            return map;
        }
        //密码加salt
        User user = new User();
        user.setName(name);
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(0, 10));
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        userDao.addUser(user);

        String ticket = addTicket(user.getId());
        map.put("ticket", ticket);

        return map;

    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    public Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<String, String>(16);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            map.put("msg", "用户名称或密码不能为空");
            return map;
        }
        User user = userDao.selectByName(username);
        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码不正确");
            return map;
        }
        String ticket = addTicket(user.getId());
        map.put("ticket", ticket);

        return map;

    }

    /**
     * 注销
     *
     * @param ticket
     */
    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket, 1);
    }

    public User getUser(int id) {
        return userDao.selectById(id);
    }

    /**
     * 增加ticket
     *
     * @param userId
     * @return
     */
    public String addTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600 * 24 * 100 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicket.setStatus(0);
        loginTicketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    /**
     * 根据用户名称查找用户
     *
     * @param name
     * @return
     */
    public User selectByName(String name) {
        return userDao.selectByName(name);
    }
}
