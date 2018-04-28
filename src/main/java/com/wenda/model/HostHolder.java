package com.wenda.model;

import org.springframework.stereotype.Component;

/**
 * 线程本地变量，保存线程跟用户的关系
 *
 * @author evilhex
 *         2017/12/10
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
