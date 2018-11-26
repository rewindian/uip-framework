package com.ian.uip.log.receiver.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
public class KafkaConfiguration implements InitializingBean {

    @Autowired
    private Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        String topics = environment.getProperty("uip.sys-log.receiver-topics");
        String id = environment.getProperty("uip.sys-log.listener-id");
        if (StringUtils.isEmpty(topics)) {
            System.setProperty("uip.sys-log.receiver-topics", "uip.sys.log");
        }
        if (StringUtils.isEmpty(id)) {
            System.setProperty("uip.sys-log.listener-id", "uip");
        }
    }
}
