package com.ian.uip.sys.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.entity.SysDict;
import com.ian.uip.sys.service.SysDictService;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author Ian
 * @since 2018-11-28
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController extends BaseController<SysDictService, SysDict> {



}
