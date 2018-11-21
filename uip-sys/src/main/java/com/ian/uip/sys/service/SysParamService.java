package com.ian.uip.sys.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.ian.uip.sys.entity.SysParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysParamService extends IService<SysParam> {

    List<SysParam> querySysParamList(String paramCode, String paramDesc, Pagination page);

    int updateSysParamValue(String id, String value, String updateBy, Date updateDate);

    int revertSysParamValue(String id, String updateBy, Date updateDate);

    Map<String, String> getSysParams();

    Map<String, String> reloadSysParams();

    String queryValueByCode(String paramCode);
}
