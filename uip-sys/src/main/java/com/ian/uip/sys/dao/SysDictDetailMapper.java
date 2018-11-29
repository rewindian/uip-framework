package com.ian.uip.sys.dao;

import com.ian.uip.sys.entity.SysDictDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ian.uip.sys.model.LabelValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据字典详情表 Mapper 接口
 * </p>
 *
 * @author Ian
 * @since 2018-11-28
 */
public interface SysDictDetailMapper extends BaseMapper<SysDictDetail> {

    List<LabelValue> listByCatalog(@Param("catalog") String catalog);
}