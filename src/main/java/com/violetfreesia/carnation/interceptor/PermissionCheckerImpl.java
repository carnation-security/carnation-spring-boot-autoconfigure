package com.violetfreesia.carnation.interceptor;

import com.violetfreesia.carnation.exception.NotAllowedNullException;
import com.violetfreesia.carnation.exception.NotSupportTypeException;
import com.violetfreesia.carnation.support.Permission;
import com.violetfreesia.carnation.support.StringPermission;
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
public class PermissionCheckerImpl implements PermissionChecker {

    @Override
    public boolean checkPermission(Annotation permissionCheckerAnnotation, @NonNull UserInfo<?, ?> userInfo) {
        if (permissionCheckerAnnotation == null) {
            return true;
        }
        Object permissions = AnnotationUtils.getValue(permissionCheckerAnnotation);
        CarnationAssert.notNull(permissions, new NotAllowedNullException("注解中不存在Value字段"));
        if (permissions.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(permissions); i++) {
                Object permission = Array.get(permissions, i);
                if (permission instanceof String) {
                    StringPermission permissionObj = StringPermission.of((String) permission);
                    if (userInfo.allPermissions().contains(permissionObj)) {
                        return true;
                    }
                } else if (permission instanceof Permission && permission.getClass().isEnum()) {
                    if (userInfo.allPermissions().contains(permission)) {
                        return true;
                    }
                } else {
                    throw new NotSupportTypeException("权限类型不支持: 只支持字符串和枚举类型的权限");
                }
            }
        }
        return false;
    }
}
