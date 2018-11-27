package com.ian.uip.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.ian.uip.sys.entity.SysUser;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 */
public interface SysUserService extends IService<SysUser> {


    boolean save(SysUser sysUser);

    boolean updatePassword(SysUser sysUser);
}
