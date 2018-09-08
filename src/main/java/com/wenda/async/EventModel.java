package com.wenda.async;

import com.wenda.model.EntityType;

import java.util.HashMap;
import java.util.Map;

/**
 * 异步事件
 *
 * @author evilhex.
 * @date 2018/8/8 下午3:33.
 */
public class EventModel {
    /** 事件类型 **/
    private EventType eventType;
    /** 触发者 **/
    private int actorId;
    /** 触发的事件类型 **/
    private int entityType;
    /** 触发的事件 **/
    private int entityId;

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }

    /** 触发的事件的拥有者 **/

    private int entityOwnerId;
    /** 扩展字段 **/
    private Map<String, String> exts=new HashMap<>();

    public EventModel() {
    }

    public EventModel(EventType type) {
        this.eventType = type;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public String getExts(String key) {
        return exts.get(key);
    }

    public EventModel setExts(String key, String value) {
        exts.put(key, value);
        return this;
    }
}
