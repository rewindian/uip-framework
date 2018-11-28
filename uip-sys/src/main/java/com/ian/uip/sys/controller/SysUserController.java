package com.ian.uip.sys.controller;

import com.ian.uip.core.model.ResultBean;
import com.ian.uip.core.validator.Assert;
import com.ian.uip.core.validator.ValidatorUtils;
import com.ian.uip.core.validator.group.AddGroup;
import com.ian.uip.core.validator.group.UpdateGroup;
import com.ian.uip.sys.entity.SysUser;
import com.ian.uip.sys.model.BaseController;
import com.ian.uip.sys.service.SysUserService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController<SysUserService, SysUser> {

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    @Override
    @PostMapping
    public ResultBean insert(@RequestBody SysUser sysUser) {
        ValidatorUtils.validateEntity(sysUser, AddGroup.class);
        return new ResultBean(baseService.save(sysUser));
    }

    /**
     * 修改信息
     * @param sysUser
     * @return
     */
    @Override
    @PutMapping
    public ResultBean update(@RequestBody SysUser sysUser) {
        ValidatorUtils.validateEntity(sysUser, UpdateGroup.class);
        sysUser.setPassword(null);
        sysUser.setSalt(null);
        return new ResultBean(baseService.updateById(sysUser));
    }

    /**
     * 修改密码
     * @param sysUser
     * @return
     */
    @PutMapping("/updatePassword")
    public ResultBean updatePassword(@RequestBody SysUser sysUser) {
        Assert.isBlank(sysUser.getId(), "id不能为空");
        Assert.isBlank(sysUser.getPassword(), "密码不能为空");
        Assert.isBlank(sysUser.getNewPassword(), "新密码不能为空");
        return new ResultBean(baseService.updatePassword(sysUser));
    }
}
