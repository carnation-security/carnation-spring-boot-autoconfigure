package com.violetfreesia.carnation.support;

import com.violetfreesia.carnation.exception.PermissionNotAllowedNullException;
import com.violetfreesia.carnation.util.CarnationAssert;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 默认字符串的用户信息
 *
 * @author violetfreesia
 * @date 2021-04-26
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultUserInfo implements UserInfo<String, String> {

    private StringRole role;

    private Set<StringPermission> permissions;

    private Serializable userId;


    public DefaultUserInfo(@NonNull Serializable userId, @NonNull StringRole role) {
        this.userId = userId;
        this.role = role;
        this.permissions = new HashSet<>();
    }

    public DefaultUserInfo(@NonNull Serializable userId, @NonNull StringRole role, StringPermission... permissions) {
        this(userId, role);
        List<StringPermission> stringPermissions = Arrays.asList(permissions);
        CarnationAssert.notExistNull(stringPermissions, new PermissionNotAllowedNullException("权限列表中包含为Null的权限"));
        this.permissions.addAll(stringPermissions);
    }

}
