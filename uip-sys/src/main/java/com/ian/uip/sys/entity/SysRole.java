package com.ian.uip.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 描述
     */
    private String description;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
