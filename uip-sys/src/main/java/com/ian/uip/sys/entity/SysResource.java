package com.ian.uip.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统资源
 * </p>
 *
 * @author Ian
 * @since 2018-11-23
 */
@Data
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 名称
     */
    private String resourceName;
    /**
     * 上级id
     */
    private String parentId;
    /**
     * 权限
     */
    private String permission;
    /**
     * 资源类型
     */
    private String resourceType;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private Long sortOrder;
    /**
     * 子系统id
     */
    private String subsysId;

    /**
     * 图标
     */
    private String icon;


}
