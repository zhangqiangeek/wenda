package com.wenda.util;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author evilhex.
 * @date 2018/6/12 下午7:32.
 */
public class ZkUtil {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static String ZK_URL = "127.0.0.1:2181";

    private static class ConnectHolder {

        private static final ZooKeeper INSTANCE = ZkUtil.connect();

    }

    private static ZooKeeper connect() {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(ZK_URL, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("已经触发了" + event.getType() + "事件！");
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        connectedSemaphore.countDown();
                    }
                }
            });
            connectedSemaphore.await();
            System.out.println(zooKeeper.getChildren("/", true));
            return zooKeeper;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 单例创建连接
     *
     * @return
     */
    public static ZooKeeper getInstance() {
        return ConnectHolder.INSTANCE;
    }

    /**
     * 创建节点
     *
     * @param path
     * @param zooKeeper
     * @return
     */
    public static String createNode(String path, ZooKeeper zooKeeper) {
        String result = null;
        try {
            result = zooKeeper.create(path, "string".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
