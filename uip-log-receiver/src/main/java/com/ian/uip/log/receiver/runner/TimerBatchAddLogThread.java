package com.ian.uip.log.receiver.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
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
        while (true) {
            try {
                new CountDown(ADD_INTERVAL_SECONDS);
                log.debug("--------------计时结束----------------");
                if (SysLogQueue.getMyQueue().size() > 0) {
                    kafkaReceiver.doBatchAdd();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class CountDown {
        private int curSec;

        CountDown(int limitSec) throws InterruptedException {
            this.curSec = limitSec;
//            log.debug("count down from " + limitSec + " s ");
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
//                    log.debug("Time remians " + --curSec + " s");
                }
            }, 0, 1000);
            TimeUnit.SECONDS.sleep(limitSec);
            timer.cancel();
//            log.debug("Time is out!");
        }

    }
}
