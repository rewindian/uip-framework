package com.ian.uip.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author Ian
 * @since 2018-11-28
 */
@Data
@Accessors(chain = true)
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 字典目录(禁止重复)
     */
	private String dictCatalog;
    /**
     * 描述
     */
	private String description;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
