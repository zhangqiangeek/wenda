package com.wenda.dateroute;

/**
 * 使用ThreadLocal技术来记录当前线程中的数据源的key
 *
 * @author evilhex.
 * @date 2018/7/26 下午5:19.
 */
public class DynamicDataSourceHolder {

    /** 写库对应的数据源的key */
    private static final String MASTER = "master";
    /** 读库对应数据源的key */
    private static final String SLAVE = "slave";

    /**
     * 使用ThreadLocal记录当前线程的数据源的key
     */
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 设置数据源的key
     *
     * @param key
     */
    public static void putDataSourceKey(String key) {
        holder.set(key);
    }

    /**
     * 获取数据源的key
     *
     * @return
     */
    public static String getDataSourceKey() {
        return holder.get();
    }

    /**
     * 标记写库
     */
    public static void markMaster() {
        putDataSourceKey(MASTER);
    }

    /**
     * 标记读库
     */
    public static void markSlave() {
        putDataSourceKey(SLAVE);
    }
}
