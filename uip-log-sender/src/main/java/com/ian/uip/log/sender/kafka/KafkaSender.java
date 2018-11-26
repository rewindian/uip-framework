package com.ian.uip.log.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.ian.uip.core.config.SysLogConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaSender<T> {

    private Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private SysLogConfig sysLogConfig;

    /**
     * kafka 发送消息
     *
     * @param obj 消息对象
     */
    public void send(T obj) {
        String jsonObj = JSON.toJSONString(obj);
        logger.debug("------------ message = {}", jsonObj);
        String topic = null != sysLogConfig.getSenderTopic() ? sysLogConfig.getSenderTopic() : "uip.sys.log";
        //发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, jsonObj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.debug("Produce: The message failed to be sent:" + throwable.getMessage());
                logger.error(throwable.getMessage(), throwable);
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                logger.debug("Produce: The message was sent successfully:");
                logger.debug("Produce: _+_+_+_+_+_+_+ result: " + stringObjectSendResult.toString());
            }
        });
    }
}
