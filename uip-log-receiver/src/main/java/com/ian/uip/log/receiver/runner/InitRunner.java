package com.ian.uip.log.receiver.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitRunner implements ApplicationRunner {

    @Autowired
    private SizerBatchAddLogThread sizerBatchAddLogThread;

    @Autowired
    private TimerBatchAddLogThread timerBatchAddLogThread;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //间隔30秒或者队列累计50条日志进行一次批量入库，降低数据库压力
        sizerBatchAddLogThread.start();
        timerBatchAddLogThread.start();
    }
}
