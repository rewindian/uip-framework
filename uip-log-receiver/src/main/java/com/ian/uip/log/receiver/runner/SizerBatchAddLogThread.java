package com.ian.uip.log.receiver.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

@Component
@Slf4j
public class SizerBatchAddLogThread extends BatchAddLogThread {

    private static final int MAX_BATCH_SIZE = 50;

    @Override
    public void run() {
        int maxSize = null != sysLogConfig.getMaxSize() ? sysLogConfig.getMaxSize() : MAX_BATCH_SIZE;
        while (true) {
            if (SysLogQueue.getMyQueue().size() >= maxSize) {
                kafkaReceiver.doBatchAdd();
            }
            log.debug("----------------执行阻塞--------------");
            synchronized (Lock.class) {
                try {
                    Lock.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
