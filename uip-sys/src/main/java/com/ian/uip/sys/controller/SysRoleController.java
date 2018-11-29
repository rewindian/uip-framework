package com.ian.uip.sys.controller;


import com.ian.uip.sys.entity.SysRole;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ian
 * @since 2018-11-22
 */
@RestController
@RequestMapping("/sys/role")
@Api(tags = "系统角色", description = "系统角色相关接口")
public class SysRoleController extends BaseController<SysRoleService, SysRole> {

}
