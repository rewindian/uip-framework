package com.ian.uip.sys.controller;


import com.ian.uip.sys.entity.SysSubsystem;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.service.SysSubsystemService;
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
public class SysSubsystemController extends BaseController<SysSubsystemService, SysSubsystem> {

}

