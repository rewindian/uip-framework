package com.ian.uip.sys.controller;

import com.ian.uip.sys.entity.SysParam;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.service.SysParamService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/param")
public class SysParamController extends BaseController<SysParamService, SysParam> {
}
