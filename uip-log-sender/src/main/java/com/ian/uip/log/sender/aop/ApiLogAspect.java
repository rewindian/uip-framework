package com.ian.uip.log.sender.aop;

import com.alibaba.fastjson.JSON;
import com.ian.uip.core.annotation.SysLogIgnore;
import com.ian.uip.core.annotation.UserLogin;
import com.ian.uip.core.constant.AuthConstants;
import com.ian.uip.core.model.ResultBean;
import com.ian.uip.core.model.SysLog;
import com.ian.uip.core.model.UserLoginInfo;
import com.ian.uip.core.redis.RedisOperator;
import com.ian.uip.core.util.ExceptionHandleUtils;
import com.ian.uip.log.sender.kafka.KafkaSender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Order(0)
@Component
public class ApiLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogAspect.class);

    private static final String EXEC = "execution(public com.ian.uip.core.model.ResultBean *(..)) && !@annotation(com.ian.uip.core.annotation.SysLogIgnore)";

    private static final String REQUEST_PARAMETERS = "requestParameters";

    private static final String PATH_PARAMETERS = "pathParameters";

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private KafkaSender<SysLog> kafkaSender;

    @Pointcut(EXEC)
    public void controllerLog() {
    }

    @Around("controllerLog()")
    public Object around(ProceedingJoinPoint pjp) {
        long beginTime = System.currentTimeMillis();
        SysLog sysLog = new SysLog();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
        Method method = joinPointObject.getMethod();
        Annotation[] methodAnnos = method.getDeclaredAnnotations();
        boolean flag = false;
        for (Annotation methodAnno : methodAnnos) {
            if (SysLogIgnore.class.equals(methodAnno.annotationType())) {
                flag = true;
                break;
            }
        }
        if (flag || "OPTIONS".equals(request.getMethod())) {
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                LOGGER.error(throwable.getMessage(), throwable);
                return null;
            }
        } else {
            Object[] args = pjp.getArgs();
            //所有参数
            Map<String, Object> params = new HashMap<>();
            //路径参数
            Map<String, Object> pathVars = new LinkedHashMap<>();
            //请求参数
            Map<String, Object> reqVars = new HashMap<>();

            String[] parameterNames = joinPointObject.getParameterNames();
            Annotation[][] parameterAnnos = method.getParameterAnnotations();
//            String contentType = request.getContentType();
            for (int i = 0; i < args.length; i++) {
                Object argValue = args[i];
                if (argValue instanceof HttpServletRequest || argValue instanceof HttpServletResponse || argValue instanceof Exception) {
                    continue;
                }
                if (parameterAnnos[i].length > 0) {
                    for (Annotation pAnno : parameterAnnos[i]) {
                        //不记录自行注入的UserLoginInfo
                        if (UserLogin.class.equals(pAnno.annotationType())) {
                            continue;
                        }
                        if (RequestBody.class.equals(pAnno.annotationType())) {
                            reqVars.put(parameterNames[i], isBaseTypeOrString(argValue) ? argValue : JSON.toJSONString(argValue));
                        } else if (PathVariable.class.equals(pAnno.annotationType())) {
                            String pAnnoValue = ((PathVariable) pAnno).value();
                            pathVars.put(pAnnoValue, isBaseTypeOrString(argValue) ? argValue : JSON.toJSONString(argValue));
                        } else if (RequestParam.class.equals(pAnno.annotationType())) {
                            String pAnnoValue = ((RequestParam) pAnno).value();
                            reqVars.put(pAnnoValue, isBaseTypeOrString(argValue) ? argValue : JSON.toJSONString(argValue));
                        } else if (RequestAttribute.class.equals(pAnno.annotationType())) {
                            String pAnnoValue = StringUtils.isEmpty(((RequestAttribute) pAnno).value()) ? parameterNames[i]
                                    : ((RequestAttribute) pAnno).value();
                            reqVars.put(pAnnoValue, isBaseTypeOrString(argValue) ? argValue : JSON.toJSONString(argValue));
                        }
                    }
                } else {
                    reqVars.put(parameterNames[i], isBaseTypeOrString(argValue) ? argValue : JSON.toJSONString(argValue));
                }
            }
            params.put(PATH_PARAMETERS, JSON.toJSONString(pathVars));
            String requestUri = request.getRequestURI();
            if (!pathVars.isEmpty()) {
                for (Map.Entry<String, Object> entry : pathVars.entrySet()) {
                    String key = entry.getKey();
                    String value = null == entry.getValue() ? "" : entry.getValue().toString();
                    if (requestUri.contains(value)) {
                        requestUri = requestUri.replaceFirst(value, "{" + key + "}");
                    }
                }
            }
            if ("GET".equals(request.getMethod()) && reqVars.isEmpty()) {
                Enumeration enums = request.getParameterNames();
                while (enums.hasMoreElements()) {
                    String paraName = enums.nextElement().toString();
                    reqVars.put(paraName, request.getParameter(paraName));
                }
            }
            params.put(REQUEST_PARAMETERS, JSON.toJSONString(reqVars));
            String ip = getIpAddr(request);
            LOGGER.debug("请求参数 : " + JSON.toJSONString(params));
            LOGGER.debug("请求URL : " + request.getRequestURL());
            LOGGER.debug("请求URI : " + requestUri);
            LOGGER.debug("请求IP : " + (ip == null ? "" : ip));
            LOGGER.debug("请求方法 : " + request.getMethod());
            LOGGER.debug("控制器方法 : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
            sysLog.setRequestParams(strLengthHandler(JSON.toJSONString(params)));
            sysLog.setRequestIp(ip);
            sysLog.setRequestMethod(request.getMethod());
            sysLog.setRequestUrl(strLengthHandler(request.getRequestURL().toString()));
            sysLog.setMappingMethod(pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
            sysLog.setRequestApi(strLengthHandler(requestUri));
            //处理token
            String token = request.getHeader(AuthConstants.HEADER_TOKEN);
            if (!StringUtils.isEmpty(token)) {
                sysLog = tokenSetter(sysLog, token);
            }
            Integer statusCode = null;
            String resultMsg = "";
            try {
                ResultBean o = (ResultBean) pjp.proceed();
                // 处理完请求，返回内容
                statusCode = o.getCode();
                resultMsg = o.getMsg();
                return o;
            } catch (Throwable e) {
                LOGGER.error(e.getMessage(), e);
                return ExceptionHandleUtils.handle(e);
            } finally {
                long endTime = System.currentTimeMillis();
                long consumeTime = endTime - beginTime;
                LOGGER.debug("请求时间：{}ms", consumeTime);
                sysLog.setResponseTime(Float.valueOf(String.valueOf(consumeTime)));
                LOGGER.debug("状态码 : {}", statusCode);
                sysLog.setResponseCode(statusCode);
                sysLog.setResponseMsg(strLengthHandler(resultMsg));
                sysLog.setCreateTime(new Date());
                //存入kafka
                kafkaSender.send(sysLog);
            }
        }
    }

    private SysLog tokenSetter(SysLog sysLog, String token) {
        sysLog.setToken(token);
        if (redisOperator.hasKey(AuthConstants.TOKEN_CACHE_PREFIX + token)) {
            UserLoginInfo userLoginInfo = (UserLoginInfo) redisOperator.getData(AuthConstants.TOKEN_CACHE_PREFIX + token);
            sysLog.setUserName(userLoginInfo.getUsername());
        }
        return sysLog;
    }

    private String strLengthHandler(String str) {
        return str.length() > 500 ? str.substring(0, 490) + "..." : str;
    }

    private static boolean isBaseType(Object object) {
        Class className = object.getClass();
        return className.equals(Integer.class) ||
                className.equals(Byte.class) ||
                className.equals(Long.class) ||
                className.equals(Double.class) ||
                className.equals(Float.class) ||
                className.equals(Character.class) ||
                className.equals(Short.class) ||
                className.equals(Boolean.class);
    }

    private static boolean isBaseTypeOrString(Object object) {
        Class className = object.getClass();
        return isBaseType(object) ||
                className.equals(String.class) ||
                className.equals(StringBuilder.class) ||
                className.equals(StringBuffer.class);
    }

    private static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            LOGGER.error("GET IP ERROR ", e);
        }

        return ip;
    }
}
