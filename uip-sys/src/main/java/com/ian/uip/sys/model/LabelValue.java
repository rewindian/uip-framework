package com.ian.uip.sys.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LabelValue implements Serializable {

    private static final long serialVersionUID = -150511118224485079L;

    /**
     * 数据字典展示标签
     */
    private String label;

    /**
     * 数据字典实际值
     */
    private String value;

}
