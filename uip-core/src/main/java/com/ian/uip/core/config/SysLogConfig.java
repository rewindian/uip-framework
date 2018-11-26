package com.ian.uip.core.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "uip.log.batch-add")
@Data
@Accessors(chain = true)
public class SysLogConfig {

    /**
     * 日志入库间隔(秒)
     */
    private Integer interval = 30;

    /**
     * 队列积累到该值 触发入库
     */
    private Integer maxSize = 50;

    /**
     * mybatis-plus批量插入单次数量
     */
    private Integer mybatisBatchSize = 100;

    /**
     * 生产者发送主题
     */
    private String senderTopic;

    /**
     * 消费者接收主题
     */
    private String[] receiverTopics;

    /**
     * KafkaListener注解的id
     */
    private String listenerId;

}
