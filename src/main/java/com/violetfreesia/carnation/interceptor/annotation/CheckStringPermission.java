package com.violetfreesia.carnation.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author violetfreesia
 * @date 2021-04-28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckStringPermission {
    /**
     * 访问被具有注解的方法应该具有的权限
     *
     * @return 权限集合
     */
    String[] value() default {};
}
