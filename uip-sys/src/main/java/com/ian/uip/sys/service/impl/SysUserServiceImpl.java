package com.ian.uip.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ian.uip.core.constant.AuthConstants;
import com.ian.uip.core.util.EncryptUtils;
import com.ian.uip.sys.dao.SysUserMapper;
import com.ian.uip.sys.entity.SysUser;
import com.ian.uip.sys.service.SysParamService;
import com.ian.uip.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysParamService sysParamService;

    @Override
    @Transactional
    public void save(SysUser user) {
        user.setCreateTime(new Date());
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        String defaultPassword = sysParamService.queryValueByCode(AuthConstants.DEFAULT_PWD_PARAM_CODE);
        if (StringUtils.isEmpty(defaultPassword)) {
            defaultPassword = "123456";
        }
        user.setPassword(EncryptUtils.md5(defaultPassword + salt));
        user.setSalt(salt);
        baseMapper.insert(user);
        //保存用户与角色关系
    }


}
