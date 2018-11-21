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

    private String name;

    private String parentId;

    private String permission;

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 资源ID
     */
    private String resourceId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
