package com.ian.uip.sys.service;

import com.ian.uip.sys.entity.SysDictDetail;
import com.baomidou.mybatisplus.service.IService;
import com.ian.uip.sys.model.LabelValue;

import java.util.List;

/**
 * <p>
 * 数据字典详情表 服务类
 * </p>
 *
 * @author Ian
 * @since 2018-11-28
 */
public interface SysDictDetailService extends IService<SysDictDetail> {

    /**
     * 根据目录查询，封装成label,value形式
     *
     * @param catalog
     * @return
     */
    List<LabelValue> listByCatalog(String catalog);

    /**
     * 刷新缓存
     *
     * @param catalog
     * @return
     */
    List<LabelValue> reloadCache(String catalog);
}
