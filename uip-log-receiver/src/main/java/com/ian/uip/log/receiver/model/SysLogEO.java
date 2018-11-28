package com.ian.uip.log.receiver.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SysLogEO extends BaseRowModel {

    private String id;

    /**
     * 请求路径
     */
    @ExcelProperty(value = "请求路径", index = 0)
    private String requestUrl;

    /**
     * 客户端IP
     */
    @ExcelProperty(value = "客户端IP", index = 1)
    private String requestIp;

    /**
     * 请求方法
     */
    @ExcelProperty(value = "请求方法", index = 2)
    private String requestMethod;

    /**
     * 请求参数
     */
    @ExcelProperty(value = "请求参数", index = 3)
    private String requestParams;

    /**
     * 响应状态码
     */
    @ExcelProperty(value = "响应状态码", index = 4)
    private Integer responseCode;

    /**
     * 响应消息
     */
    @ExcelProperty(value = "响应消息", index = 5)
    private String responseMsg;

    /**
     * 响应耗时(ms)
     */
    @ExcelProperty(value = "响应耗时(ms)", index = 6)
    private Float responseTime;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", format = "yyyy/MM/dd HH:mm:ss", index = 7)
    private Date createTime;

    /**
     * 访问令牌
     */
    @ExcelProperty(value = "访问令牌", index = 8)
    private String token;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名", index = 9)
    private String userName;

    /**
     * 控制器请求匹配方法
     */
    @ExcelProperty(value = "控制器请求匹配方法", index = 10)
    private String mappingMethod;

    /**
     * API路径
     */
    @ExcelProperty(value = "API路径", index = 11)
    private String requestApi;
}
