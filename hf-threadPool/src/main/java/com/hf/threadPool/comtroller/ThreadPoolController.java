package com.hf.threadPool.comtroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ThreadPoolController {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @RequestMapping("/threadLog")
    public void testThreadLog() {
        log.info("print log");
    }

    @RequestMapping("/threadForEach")
    public void testForEach() {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPoolTaskExecutor.submit(() -> {
                try {
                    log.info("ThreadPool For Each Print" + finalI);
                    int a = 1/0;
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });
        }
    }
}
