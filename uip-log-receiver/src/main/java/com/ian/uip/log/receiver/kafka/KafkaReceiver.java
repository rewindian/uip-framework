package com.ian.uip.log.receiver.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ian.uip.core.config.SysLogConfig;
import com.ian.uip.core.model.SysLog;
import com.ian.uip.log.receiver.runner.SysLogQueue;
import com.ian.uip.log.receiver.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

@Component
@Slf4j
public class KafkaReceiver {

    private volatile List<SysLog> list = new ArrayList<>();

    private static final int MAX_BATCH_SIZE = 100;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysLogConfig sysLogConfig;

    @KafkaListener(id = "${uip.sys-log.listener-id}", topics = "${uip.sys-log.receiver-topics}")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.debug("----------kafka监听到新消息----------------");
        //判断是否NULL
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            //获取消息
            Object message = kafkaMessage.get();

            log.debug("Receive： +++++++++++++++ Topic:" + topic);
//            log.debug("Receive： +++++++++++++++ Record:" + record);
//            log.debug("Receive： +++++++++++++++ Message:" + message);
            SysLog sysLog = JSON.parseObject(message.toString(), new TypeReference<SysLog>() {
            });
//            sysLogService.insert(sysLog);
//            addLog(sysLog);
            try {
                SysLogQueue.getMyQueue().add(sysLog);
            } catch (Exception e) {
                if (e instanceof IllegalStateException) {
                    log.error("系统日志队列已满", e);
                } else {
                    log.error("系统日志入队异常", e);
                }
            }
            log.debug("--------------唤醒计数入库线程--------------");
            synchronized (Lock.class) {
                Lock.class.notify();
            }
        }
    }

    public synchronized void doBatchAdd() {
        log.debug("------------执行批量入库------------");
        int mybatisMaxBatchSize = null != sysLogConfig.getMybatisBatchSize() ? sysLogConfig.getMybatisBatchSize() : MAX_BATCH_SIZE;
        int maxSize = null != sysLogConfig.getMaxSize() ? sysLogConfig.getMaxSize() : MAX_BATCH_SIZE;
        try {
            while (!SysLogQueue.getMyQueue().isEmpty() && list.size() <= maxSize) {
                list.add(SysLogQueue.getMyQueue().poll());
            }
            sysLogService.insertBatch(list, mybatisMaxBatchSize);
        } catch (Exception e) {
            log.error("从队列获取日志对象异常", e);
        } finally {
            list.clear();
        }
    }
}
