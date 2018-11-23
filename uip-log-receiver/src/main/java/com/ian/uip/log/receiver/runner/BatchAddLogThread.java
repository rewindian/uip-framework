package com.ian.uip.log.receiver.runner;

import com.ian.uip.log.receiver.kafka.KafkaReceiver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class BatchAddLogThread extends Thread {

    @Autowired
    protected KafkaReceiver kafkaReceiver;

    long getCurrentTime() {
        return new Date().getTime();
    }

}
