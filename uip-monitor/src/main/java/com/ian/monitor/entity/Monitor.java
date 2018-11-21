package com.ian.monitor.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xhd
 * @since 2018-11-12
 */
@TableName("q_monitor")
public class Monitor implements Serializable {

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
     * 响应耗时
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
     * 请求API
     */
    private String requestApi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }
    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
    public Float getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Float responseTime) {
        this.responseTime = responseTime;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMappingMethod() {
        return mappingMethod;
    }

    public void setMappingMethod(String mappingMethod) {
        this.mappingMethod = mappingMethod;
    }

    public String getRequestApi() {
        return requestApi;
    }

    public void setRequestApi(String requestApi) {
        this.requestApi = requestApi;
    }
}
