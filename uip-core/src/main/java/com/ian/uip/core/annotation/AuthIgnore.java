package com.ian.uip.core.annotation;

import java.lang.annotation.*;

/**
 * 权限验证忽略该注解方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {

}
