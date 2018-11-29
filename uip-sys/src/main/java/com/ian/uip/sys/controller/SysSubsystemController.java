package com.ian.uip.sys.controller;


import com.ian.uip.sys.entity.SysSubsystem;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.service.SysSubsystemService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 子系统表 前端控制器
 * </p>
 *
 * @author Ian
 * @since 2018-11-23
 */
@RestController
@RequestMapping("/sys/subsystem")
@Api(tags = "子系统", description = "子系统相关接口")
public class SysSubsystemController extends BaseController<SysSubsystemService, SysSubsystem> {

}

