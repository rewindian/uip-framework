package com.ian.uip.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询用分页参数对象
 */
@Data
public class PageQO implements Serializable {

    public static final String DEFAULT_ORDER = "asc";

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    public static final Integer DEFAULT_CURRENT_PAGE = 1;

    /**
     * 默认每页10条数据
     */
    private Integer size = DEFAULT_PAGE_SIZE;
    /**
     * 默认第一页
     */
    private Integer current = DEFAULT_CURRENT_PAGE;
    /**
     * 分页字段名 多个使用逗号分隔
     */
    private String sort;
    /**
     * 按什么排序(asc,desc)
     */
    private String order = DEFAULT_ORDER;

}
