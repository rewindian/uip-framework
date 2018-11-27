package com.ian.uip.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ian.uip.core.annotation.AuthIgnore;
import com.ian.uip.core.annotation.SysLogIgnore;
import com.ian.uip.core.annotation.UserLogin;
import com.ian.uip.core.config.TokenConfig;
import com.ian.uip.core.constant.AuthConstants;
import com.ian.uip.core.exception.CustomException;
import com.ian.uip.core.model.ResultBean;
import com.ian.uip.core.model.UserLoginInfo;
import com.ian.uip.core.redis.RedisOperator;
import com.ian.uip.core.util.EncryptUtils;
import com.ian.uip.core.util.TokenUtils;
import com.ian.uip.sys.entity.SysUser;
import com.ian.uip.sys.service.SysUserService;
import com.ian.uip.sys.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 登录相关
 */
@RestController
@Slf4j
public class SysLoginController {

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 登录
     */
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    @AuthIgnore
    public ResultBean login(@RequestBody SysUser sysUser) {

        //用户信息
        SysUser user = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("username", sysUser.getUsername()));

        //账号不存在、密码错误
        if (user == null || !PasswordUtil.validatePassword(sysUser.getPassword(), user.getPassword(), user.getSalt())) {
            return new ResultBean(new CustomException("账号或密码不正确"));
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return new ResultBean(new CustomException("账号已被锁定,请联系管理员"));
        }
        String token = TokenUtils.generateToken();
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setId(user.getId());
        userLoginInfo.setUsername(user.getUsername());
        userLoginInfo.setDeptId(user.getDeptId());
        userLoginInfo.setDeptName(user.getDeptName());
        userLoginInfo.setEmail(user.getEmail());
        userLoginInfo.setMobile(user.getMobile());
        userLoginInfo.setToken(token);
        //加入资源
        //存入redis
        redisOperator.setDataWithExpire(AuthConstants.TOKEN_CACHE_PREFIX + token, userLoginInfo, tokenConfig.getExpireTime());
        return new ResultBean(userLoginInfo);
    }

    /**
     * 退出登录
     *
     * @param userLoginInfo
     * @return
     */
    @PostMapping("/sys/logout")
    public ResultBean logout(@UserLogin UserLoginInfo userLoginInfo) {
        String token = userLoginInfo.getToken();
        redisOperator.deleteData(AuthConstants.TOKEN_CACHE_PREFIX + token);
        return new ResultBean();
    }

    @GetMapping("/sys/test")
    public ResultBean test(@UserLogin UserLoginInfo userLoginInfo) {
        log.debug(userLoginInfo.getId());
        return new ResultBean(userLoginInfo);
    }

    @GetMapping("/sys/testLog")
    @SysLogIgnore
    public ResultBean testLog(@RequestParam("id") String id) {
        log.debug(id);
        return new ResultBean(id);
    }

    @PostMapping("/sys/testPost")
    public ResultBean testPost(@RequestBody Map<String, Object> map) {
        return new ResultBean(map);
    }


}
