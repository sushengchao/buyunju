package com.sugeladi.buyunju.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Component
public class ThreadPoolBiz {

    private static final Logger log = LoggerFactory.getLogger(
            ThreadPoolBiz.class);

    private ThreadPoolExecutor executor;

    public ThreadPoolBiz() {
        executor = new ThreadPoolExecutor(2, 16, 60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(512),
                new ThreadFactory() {
                    private int counter = 0;
                    private String prefix = "updateSharedStatus-pool-";

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, prefix + counter++);
                    }
                });
    }


}
