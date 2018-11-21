package com.ian.uip.core.resolver;

import com.ian.uip.core.annotation.LoginUser;
import com.ian.uip.core.constant.AuthConstants;
import com.ian.uip.core.model.UserLoginInfo;
import com.ian.uip.core.redis.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisOperator redisOperator;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserLoginInfo.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object token = request.getAttribute(AuthConstants.PARAMETER_TOKEN, RequestAttributes.SCOPE_REQUEST);
        if (token == null) {
            return null;
        }

        //查询token信息
        String tokenKey = AuthConstants.TOKEN_CACHE_PREFIX + token;
        if (!redisOperator.hasKey(tokenKey)) {
            return null;
        }
        return redisOperator.getData(tokenKey);
    }
}
