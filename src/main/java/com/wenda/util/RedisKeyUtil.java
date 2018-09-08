package com.wenda.util;

/**
 * 确保key的生成不重复
 *
 * @author evilhex.
 * @date 2018/7/20 下午6:11.
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "like";
    private static String BIZ_DISLIKE = "disLike";
    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";

    /**
     * 生成likeKey
     *
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getLikeKey(int entityType, int entityId) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    /**
     * 生成disLikeKey
     *
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getDisLikeKey(int entityType, int entityId) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }

}
