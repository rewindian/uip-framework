package com.ian.uip.log.receiver.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ian.uip.core.model.SysLog;
import com.ian.uip.log.receiver.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class KafkaReciever {

    @Autowired
    private SysLogService sysLogService;

    @KafkaListener(id = "tut", topics = "uip.api.log")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //判断是否NULL
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            //获取消息
            Object message = kafkaMessage.get();

            log.debug("Receive： +++++++++++++++ Topic:" + topic);
            log.debug("Receive： +++++++++++++++ Record:" + record);
            log.debug("Receive： +++++++++++++++ Message:" + message);
            SysLog sysLog = JSON.parseObject(message.toString(), new TypeReference<SysLog>() {
            });
            sysLogService.insert(sysLog);

        }
    }
}
