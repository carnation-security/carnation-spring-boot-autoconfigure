package com.violetfreesia.carnation.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查是否具有某个角色
 *
 * @author violetfreesia
 * @date 2021-04-27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckStringRole {
    /**
     * 访问具有该注解的方法应该具有的角色
     *
     * @return 角色集合
     */
    String[] value() default {};
}
