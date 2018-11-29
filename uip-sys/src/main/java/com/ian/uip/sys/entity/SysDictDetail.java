package com.ian.uip.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典详情表
 * </p>
 *
 * @author Ian
 * @since 2018-11-28
 */
@Data
@Accessors(chain = true)
public class SysDictDetail extends Model<SysDictDetail> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 字典目录
     */
	private String dictCatalog;
    /**
     * 字典名称
     */
	private String dictName;
    /**
     * 字典值
     */
	private String dictValue;
    /**
     * 排序字段
     */
	private Integer dictOrder;
    /**
     * 描述
     */
	private String description;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
