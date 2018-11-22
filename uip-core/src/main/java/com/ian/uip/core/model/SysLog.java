package com.ian.uip.core.model;

import java.util.Date;
import java.io.Serializable;


import lombok.Data;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author Ian
 * @since 2018-11-22
 */
@Data
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 客户端IP
     */
    private String requestIp;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应状态码
     */
    private Integer responseCode;

    /**
     * 响应消息
     */
    private String responseMsg;

    /**
     * 响应耗时(ms)
     */
    private Float responseTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 控制器请求匹配方法
     */
    private String mappingMethod;

    /**
     * API路径
     */
    private String requestApi;


}
