package com.wenda.async;

/**
 * 异步事件的类型
 *
 * @author evilhex.
 * @date 2018/8/8 下午3:30.
 */
public enum EventType {
    LIKE(0), COMMENT(1), LOGIN(2), MAIL(3),FOLLOW(4),UNFOLLOW(5);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
