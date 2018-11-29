package com.ian.uip.sys.controller;


import com.ian.uip.sys.entity.SysResource;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.service.SysResourceService;
import io.swagger.annotations.Api;
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
@Api(tags = "系统资源", description = "系统资源相关接口")
public class SysResourceController extends BaseController<SysResourceService, SysResource> {

}

