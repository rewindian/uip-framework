package com.ian.uip.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.ian.uip.sys.entity.SysParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface SysParamMapper extends BaseMapper<SysParam> {

     List<SysParam> listQuerySysParam(@Param("paramCode") String paramCode, @Param("paramDesc") String paramDesc,
                                      Pagination page);

     int updateSysParamValueById(@Param("id") String id, @Param("value") String value,
                                 @Param("updateBy") String updateBy, @Param("updateDate") Date updateDate);

     int revertSysParamValueById(@Param("id") String id, @Param("updateBy") String updateBy,
                                 @Param("updateDate") Date updateDate);

     List<SysParam> queryAllSysParam();

     String queryValueByCode(@Param("paramCode") String paramCode);
}
