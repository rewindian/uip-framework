package com.ian.uip.core.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ian.uip.core.annotation.AuthIgnore;
import com.ian.uip.core.config.TokenConfig;
import com.ian.uip.core.constant.AuthConstants;
import com.ian.uip.core.exception.AuthException;
import com.ian.uip.core.model.ResultBean;
import com.ian.uip.core.redis.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
@Order(1)
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private TokenConfig tokenConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isOption(request)) {
            return true;
        }

        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }

        //从header中获取token
        String token = request.getHeader(AuthConstants.HEADER_TOKEN);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(AuthConstants.PARAMETER_TOKEN);
        }

        //token为空
        if (StringUtils.isEmpty(token)) {
//            throw new AuthException("token不能为空");
            returnJson(response, "token不能为空");
            return false;
        } else {
            request.setAttribute(AuthConstants.PARAMETER_TOKEN, token);
        }

        //查询token信息
        String tokenKey = AuthConstants.TOKEN_CACHE_PREFIX + token;
        if (redisOperator.hasKey(tokenKey)) {
            if (null != tokenConfig.getExpireTimeExpand() && tokenConfig.getExpireTimeExpand()) {
                //包含token
                long expireTime = redisOperator.getExpire(tokenKey); //秒
                log.debug("{}的过期时间为{}秒！", tokenKey, expireTime);
                if (expireTime < tokenConfig.getExpireExpandThreshold()) { //延长过期时间
                    redisOperator.setExpire(tokenKey, tokenConfig.getExpireTime());
                    log.debug("{}的过期时间已更新为{}秒！", tokenKey, redisOperator.getExpire(tokenKey));
                }
            }
        } else {
            returnJson(response, "token无效");
//            throw new AuthException("token无效");
            return false;
        }

        return true;
    }

    private boolean isOption(HttpServletRequest httpservletrequest) {
        return "OPTIONS".equals(httpservletrequest.getMethod());
    }

    private void returnJson(HttpServletResponse response, String msg) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            ResultBean resultBean = new ResultBean(new AuthException(msg));
            writer.print(JSONObject.toJSONString(resultBean));
        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
