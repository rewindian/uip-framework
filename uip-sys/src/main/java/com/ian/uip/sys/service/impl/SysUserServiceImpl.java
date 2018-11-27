package com.ian.uip.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ian.uip.core.constant.AuthConstants;
import com.ian.uip.core.exception.CustomException;
import com.ian.uip.core.util.EncryptUtils;
import com.ian.uip.sys.dao.SysUserMapper;
import com.ian.uip.sys.entity.SysUser;
import com.ian.uip.sys.service.SysParamService;
import com.ian.uip.sys.service.SysUserService;
import com.ian.uip.sys.util.PasswordUtil;
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
    public boolean save(SysUser user) {
        user.setCreateTime(new Date());
        String defaultPassword = sysParamService.queryValueByCode(AuthConstants.DEFAULT_PWD_PARAM_CODE);
        if (StringUtils.isEmpty(defaultPassword)) {
            defaultPassword = "123456";
        }
        String salt = PasswordUtil.generateSalt();
        user.setPassword(PasswordUtil.encodePassword(defaultPassword, salt));
        user.setSalt(salt);
        user.setStatus(1); //新增默认状态正常
        return retBool(baseMapper.insert(user));
        //保存用户与角色关系
    }

    @Override
    public boolean updatePassword(SysUser sysUser) {
        String id = sysUser.getId();
        SysUser originalUser = selectById(id);
        if (null == originalUser) {
            throw new CustomException("id:" + id + ",无法找到对应用户");
        }
        String salt = originalUser.getSalt();
        return PasswordUtil.validatePassword(sysUser.getPassword(), originalUser.getPassword(), salt) &&
                insert(new SysUser().setId(id).setPassword(PasswordUtil.encodePassword(sysUser.getNewPassword(), salt)));
    }


}
