package com.hf.zk;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/27 22:20
 */
public class ZkMain {

        private final static String CONNECT_STRING = "192.168.1.108:2181,192.168.1.108:2182,192.168.1.108:2183";

        private final int SESSION_TIMEOUT = 200000;
        private ZooKeeper zooKeeper;

    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, watchedEvent -> {
            try {
                System.out.println("------");
                List<String> zooKeeperChildren = zooKeeper.getChildren("/", true);
                zooKeeperChildren.forEach(System.out::println);
                System.out.println("------");
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void create() throws InterruptedException, KeeperException {
        String nodeCreate = zooKeeper.create("/xhf", "xiehongfei".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(nodeCreate);
    }

    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        // 获取子节点信息，watch = true 开始监听执行初始化watchedEvent事件
        List<String> zooKeeperChildren = zooKeeper.getChildren("/", true);
        zooKeeperChildren.forEach(System.out::println);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
