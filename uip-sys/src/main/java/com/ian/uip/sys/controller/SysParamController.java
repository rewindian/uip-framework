package com.ian.uip.sys.controller;

import com.ian.uip.sys.entity.SysParam;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.service.SysParamService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/param")
@Api(tags = "系统参数", description = "系统参数配置接口")
public class SysParamController extends BaseController<SysParamService, SysParam> {
}
