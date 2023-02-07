package com.hf.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@SpringBootApplication
public class ThreadPoolMain {

    public static void main(String[] args) {
        SpringApplication.run(ThreadPoolMain.class, args);
    }

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(8);
        threadPoolTaskExecutor.setMaxPoolSize(60);
        threadPoolTaskExecutor.setQueueCapacity(100);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setThreadNamePrefix("hf-thread-");
        // rewrite
//        threadPoolTaskExecutor.setThreadFactory((runnable)->{
//            Thread thread = new Thread(runnable);
//            thread.setName("hf-thread-" + thread.getId());
//            thread.setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler());
//            return thread;
//        });
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        log.info("threadPoolTaskExecutor 线程池初始化完成");
        return threadPoolTaskExecutor;
    }

    /**
     * 线程池异常捕获：重写ThreadFactory方式
     */
    static class LoggingUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error(t + " uncaughtException " + e);
        }
    }
}
