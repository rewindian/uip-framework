package com.ian.uip.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ian.uip.sys.dao.SysParamMapper;
import com.ian.uip.sys.entity.SysParam;
import com.ian.uip.sys.service.SysParamService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam> implements SysParamService {

    public List<SysParam> querySysParamList(String paramCode, String paramDesc, Pagination page) {
        return baseMapper.listQuerySysParam(paramCode, paramDesc, page);
    }

    public int updateSysParamValue(String id, String value, String updateBy, Date updateDate) {
        return baseMapper.updateSysParamValueById(id, value, updateBy, updateDate);
    }

    public int revertSysParamValue(String id, String updateBy, Date updateDate) {
        return baseMapper.revertSysParamValueById(id, updateBy, updateDate);
    }

    public Map<String, String> getSysParams() {
        return loadSysParams();
    }

    public Map<String, String> reloadSysParams() {
        return loadSysParams();
    }

    public String queryValueByCode(String paramCode) {
        return baseMapper.queryValueByCode(paramCode);
    }

    private Map<String, String> loadSysParams() {
        List<SysParam> list = baseMapper.selectList(new EntityWrapper<>());
        return list.stream().collect(Collectors.toMap(SysParam::getParamCode, SysParam::getValue));
    }

}
