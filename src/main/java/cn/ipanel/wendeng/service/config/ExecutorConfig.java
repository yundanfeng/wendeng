package cn.ipanel.wendeng.service.config;

import cn.ipanel.wendeng.service.spider.req.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorConfig {

    private int corePoolSize = 10;
    private int maxPoolSize = 50;
    private int queueCapacity = 30000;
    private int awaitTerminationSeconds = 1000;

    @Bean
    public Executor myAsync() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("wendengExecutor-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        executor.initialize();
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
        return executor;
    }

    @Bean
    public BlockingQueue<Item> itemQueue() {
        return new ArrayBlockingQueue<>(100000);
    }
}
