package com.ian.uip.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "uip.token")
@Data
public class TokenConfig {

    /**
     * Token过期时间(单位秒)
     */
    private Long expireTime = 1800L;

    /**
     * 是否在有请求访问时自动延长Token过期时间(并发量大时，建议关闭)
     */
    private Boolean expireTimeExpand = true;

    /**
     * 开启自动延长Token过期时间后启用该配置，当过期时间小于该值才会自动延长，延长到expire-time设定时长(单位秒)
     */
    private Long expireExpandThreshold = 600L;
}
