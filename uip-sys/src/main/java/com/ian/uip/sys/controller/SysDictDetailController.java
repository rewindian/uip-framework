package com.ian.uip.sys.controller;


import com.ian.uip.core.model.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "数据字典", description = "数据字典详情接口")
public class SysDictDetailController extends BaseController<SysDictDetailService, SysDictDetail> {

    @GetMapping("/listByCatalog")
    @ApiOperation(value = "查询字典详情", notes = "根据字典目录查询详情")
    public ResultBean listByCatalog(@RequestParam("catalog") String catalog) {
        return new ResultBean(baseService.listByCatalog(catalog));
    }

    @GetMapping("/reloadCache")
    @ApiOperation(value = "字典详情缓存刷新", notes = "刷新redis中数据字典详情缓存")
    public ResultBean reloadCache(@RequestParam("catalog") String catalog) {
        return new ResultBean(baseService.reloadCache(catalog));
    }
}
