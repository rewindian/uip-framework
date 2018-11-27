package com.ian.uip.sys.controller;


import com.ian.uip.sys.entity.SysResource;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.service.SysResourceService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ian
 * @since 2018-11-23
 */
@RestController
@RequestMapping("/sys/resource")
public class SysResourceController extends BaseController<SysResourceService, SysResource> {

}

