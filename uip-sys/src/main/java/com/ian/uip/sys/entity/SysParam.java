package com.ian.uip.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统参数
 */
@Data
@TableName("sys_param")
public class SysParam extends Model<SysParam> {
    /**
     *
     */
    private static final long serialVersionUID = 1147497729594356215L;

    private String id;

    private String paramCode;

    private String paramDesc;

    private String value;

    private String valueHis;

    private String updateBy;

    private Date updateDate;

    private String hide;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
