package com.ian.uip.log.receiver.runner;

import com.ian.uip.core.config.SysLogConfig;
import com.ian.uip.log.receiver.kafka.KafkaReceiver;
import org.springframework.beans.factory.annotation.Autowired;

public class BatchAddLogThread extends Thread {

    @Autowired
    protected KafkaReceiver kafkaReceiver;

    @Autowired
    protected SysLogConfig sysLogConfig;

}
