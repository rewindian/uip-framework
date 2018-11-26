package com.ian.uip.log.receiver.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TimerBatchAddLogThread extends BatchAddLogThread {

    /**
     * 入库时间间隔
     */
    private static final int ADD_INTERVAL_SECONDS = 30;

    @Override
    public void run() {
        int interval = null != sysLogConfig.getInterval() ? sysLogConfig.getInterval() : ADD_INTERVAL_SECONDS;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(interval);
                log.debug("--------------计时结束----------------");
                if (SysLogQueue.getMyQueue().size() > 0) {
                    kafkaReceiver.doBatchAdd();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
