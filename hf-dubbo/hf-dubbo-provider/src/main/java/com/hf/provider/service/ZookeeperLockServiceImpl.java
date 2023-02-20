package com.hf.provider.service;

import com.hf.common.api.ZookeeperLockService;
import com.hf.provider.compent.ShardReentrantLockComponent;
import com.hf.provider.handler.BaseLockHandler;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@DubboService
public class ZookeeperLockServiceImpl implements ZookeeperLockService {

    private final static Logger LOGGER  = LogManager.getLogger(ZookeeperLockServiceImpl.class);

    /**
     * 表示开启多个线程并行执行
     */
    private static final int THREAD_COUNT = 300;

    @Autowired
    private ShardReentrantLockComponent lockComponent;

    /**
     * 用来实现具体逻辑，对该计数器加1
     */
    private int count;

    @Override
    public void distributeLock() {
        //要加锁节点的路径
        String path = "/path/test";
        //初始化一个拥有 100 个线程的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        //使用 CountDownLatch 实现线程的协调
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        for(int i = 0; i < THREAD_COUNT; i++) {
            final int index = i;
            //提交线程
            executorService.submit(() -> {
                //name 表示该线程的名称
                String name = "client" + (index + 1);
                //result 获取执行完业务逻辑后返回值
                String result = null;
                while (result == null) {
                    //result 为 null 表示没有的锁，会继续执行while循环去竞争获取锁
                    result = lockComponent.acquireLock(new BaseLockHandler<String>(path) {

                        //执行具体的业务逻辑
                        @Override
                        public String handler() {
                            //执行 count++
                            count++;
//                            try {
//                                //睡眠 50ms ,使测试结果更明显
//                                Thread.sleep(50);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                            //打印各个线程执行结果
                            LOGGER.info(name + "    执行业务方法，对count++ : " + count);

                            //执行成功后不要返回null，如果返回null，会继续执行while去竞争获取锁
                            return this.getPath();
                        }
                    });
                }
                //调用countDown方法，表示该线程执行完毕
                countDownLatch.countDown();
            });
        }
        //使该方法阻塞住，不然看不到效果
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("方法执行结束");
    }
}
