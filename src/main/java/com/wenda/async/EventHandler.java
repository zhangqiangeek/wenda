package com.wenda.async;

import java.util.List;

/**
 * 事件处理接口
 *
 * @author evilhex.
 * @date 2018/8/8 下午6:33.
 */
public interface EventHandler {

    /**
     * 处理事件
     *
     * @param model 时间类型
     */
    void doHandle(EventModel model);

    /**
     * 获取关注点事件
     *
     * @return
     */
    List<EventType> getSupportEventTypes();

}
