package com.ian.uip.sys.controller;


import com.ian.uip.core.model.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.entity.SysDictDetail;
import com.ian.uip.sys.service.SysDictDetailService;

/**
 * <p>
 * 数据字典详情表 前端控制器
 * </p>
 *
 * @author Ian
 * @since 2018-11-28
 */
@RestController
@RequestMapping("/sys/dict/detail")
public class SysDictDetailController extends BaseController<SysDictDetailService, SysDictDetail> {

    @GetMapping("/listByCatalog")
    public ResultBean listByCatalog(@RequestParam("catalog") String catalog) {
        return new ResultBean(baseService.listByCatalog(catalog));
    }

    @GetMapping("/reloadCache")
    public ResultBean reloadCache(@RequestParam("catalog") String catalog) {
        return new ResultBean(baseService.reloadCache(catalog));
    }
}
