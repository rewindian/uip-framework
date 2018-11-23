package com.ian.uip.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 子系统表
 * </p>
 *
 * @author Ian
 * @since 2018-11-23
 */
@Data
public class SysSubsystem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 子系统名称
     */
    private String subsysName;
    /**
     * 描述
     */
    private String description;


}
