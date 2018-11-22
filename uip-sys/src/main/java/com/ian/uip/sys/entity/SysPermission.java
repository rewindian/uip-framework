package com.ian.uip.sys.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;


/**
 * 资源权限
 */
@Data
public class SysPermission extends Model<SysPermission> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 名称
     */
    private String permissionName;

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
     * 资源ID
     */
    private String resourceId;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Long sortOrder;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
