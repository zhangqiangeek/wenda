package com.wenda.async;

import com.alibaba.fastjson.JSONObject;
import com.wenda.util.JedisAdapter;
import com.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 事件的入口，统一发送事件
 *
 * @author evilhex.
 * @date 2018/8/8 下午6:14.
 */
@Service
public class EventProducer {

    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            //对象转化成字符串
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
