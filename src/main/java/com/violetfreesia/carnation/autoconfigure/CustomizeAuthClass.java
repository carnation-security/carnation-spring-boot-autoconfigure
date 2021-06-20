package com.violetfreesia.carnation.autoconfigure;

import com.violetfreesia.carnation.exception.CarnationAutoConfigureException;
import com.violetfreesia.carnation.support.UserInfo;
import lombok.Getter;
import lombok.NonNull;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author violetfreesia
 * @date 2021-06-20
 */
@Getter
public class CustomizeAuthClass {

    /**
     * 全局角色检查注解
     */
    private final Class<? extends Annotation> roleCheckAnnotation;

    /**
     * 全局权限检查注解
     */
    private final Class<? extends Annotation> permissionCheckAnnotation;

    /**
     * 用户信息的实际类型
     */
    private final Class<? extends UserInfo<?, ?>> userInfoType;

    public CustomizeAuthClass(@NonNull Class<? extends Annotation> roleCheckAnnotation,
                              @NonNull Class<? extends Annotation> permissionCheckAnnotation,
                              @NonNull Class<? extends UserInfo<?, ?>> userInfoType) {
        checkAuthAnnotation(roleCheckAnnotation);
        checkAuthAnnotation(permissionCheckAnnotation);
        this.roleCheckAnnotation = roleCheckAnnotation;
        this.permissionCheckAnnotation = permissionCheckAnnotation;
        this.userInfoType = userInfoType;
    }

    private void checkAuthAnnotation(Class<? extends Annotation> annotationClass) {
        Retention retention = annotationClass.getAnnotation(Retention.class);
        if (retention == null || retention.value() != RetentionPolicy.RUNTIME) {
            throw new CarnationAutoConfigureException("注解类[" +
                    annotationClass.getName() + "]的作用域必须为[RetentionPolicy.RUNTIME]");
        }
        Target target = annotationClass.getAnnotation(Target.class);
        if (target == null || !Arrays.stream(target.value())
                .collect(Collectors.toSet()).contains(ElementType.METHOD)) {
            throw new CarnationAutoConfigureException("注解类[" +
                    annotationClass.getName() + "]的作用目标必须包含[ElementType.METHOD]");
        }

        try {
            Method value = annotationClass.getDeclaredMethod("value");
            if (!value.getReturnType().isArray()) {
                throw new CarnationAutoConfigureException("注解类[" +
                        annotationClass.getName() + "]的[value()]属性必须为数组");
            }
        } catch (NoSuchMethodException e) {
            throw new CarnationAutoConfigureException("注解类[" +
                    annotationClass.getName() + "]必须包含[value()]属性");
        }

    }
}
