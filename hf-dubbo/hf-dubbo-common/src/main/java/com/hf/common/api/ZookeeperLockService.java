package com.hf.common.api;

public interface ZookeeperLockService {

    /**
     * Zookeeper实现分布式锁
     */
    void distributeLock();
}
