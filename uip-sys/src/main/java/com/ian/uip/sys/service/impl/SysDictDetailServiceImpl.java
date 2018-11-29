package com.ian.uip.sys.service.impl;

import com.ian.uip.core.constant.CacheConstants;
import com.ian.uip.sys.entity.SysDictDetail;
import com.ian.uip.sys.dao.SysDictDetailMapper;
import com.ian.uip.sys.model.LabelValue;
import com.ian.uip.sys.service.SysDictDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据字典详情表 服务实现类
 * </p>
 *
 * @author Ian
 * @since 2018-11-28
 */
@Service
@CacheConfig(cacheNames = CacheConstants.DICT_PREFIX)
public class SysDictDetailServiceImpl extends ServiceImpl<SysDictDetailMapper, SysDictDetail> implements SysDictDetailService {

    @Override
    @Cacheable(key = "'dict_' + #catalog")
    public List<LabelValue> listByCatalog(String catalog) {
        return baseMapper.listByCatalog(catalog);
    }

    @Override
    @CachePut(key = "'dict_' + #catalog")
    public List<LabelValue> reloadCache(String catalog) {
        return baseMapper.listByCatalog(catalog);
    }
}
