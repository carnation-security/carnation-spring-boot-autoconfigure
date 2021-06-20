package com.violetfreesia.carnation.interceptor;

import com.violetfreesia.carnation.exception.NotAllowedNullException;
import com.violetfreesia.carnation.exception.NotSupportTypeException;
import com.violetfreesia.carnation.support.Role;
import com.violetfreesia.carnation.support.StringRole;
import com.violetfreesia.carnation.support.UserInfo;
import com.violetfreesia.carnation.util.CarnationAssert;
import lombok.NonNull;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;

/**
 * @author violetfreesia
 * @date 2021-04-28
 */
public class RoleCheckerImpl implements RoleChecker {

    @Override
    public boolean checkRole(Annotation roleCheckerAnnotation, @NonNull UserInfo<?, ?> userInfo) {
        if (roleCheckerAnnotation == null) {
            return true;
        }
        Object roles = AnnotationUtils.getValue(roleCheckerAnnotation);
        CarnationAssert.notNull(roles, new NotAllowedNullException("注解中不存在Value字段"));
        if (roles.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(roles); i++) {
                Object role = Array.get(roles, i);
                if (role instanceof String) {
                    if (StringRole.of((String) role).equals(userInfo.getRole())) {
                        return true;
                    }
                } else if (role instanceof Role && role.getClass().isEnum()) {
                    if (userInfo.getRole().equals(role)) {
                        return true;
                    }
                } else {
                    throw new NotSupportTypeException("角色类型不支持: 只支持字符串和枚举类型的角色");
                }
            }
        }
        return false;
    }
}
