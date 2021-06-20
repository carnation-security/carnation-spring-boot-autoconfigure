package com.violetfreesia.carnation.support;

import com.violetfreesia.carnation.exception.EmptyStringException;
import com.violetfreesia.carnation.exception.PermissionNotAllowedNullException;
import com.violetfreesia.carnation.util.CarnationAssert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.*;

/**
 * 角色的字符串实现类
 *
 * @author violetfreesia
 * @date 2021-04-26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringRole implements Role<String, String> {

    private String value;

    private Set<StringPermission> permissions;

    public StringRole(@NonNull String value) {
        CarnationAssert.notBlank(value, new EmptyStringException("角色值不能为空字符串"));
        this.value = value;
        this.permissions = new HashSet<>();
    }

    public StringRole(@NonNull String value, StringPermission... permissions) {
        this(value);
        final List<StringPermission> stringPermissions = Arrays.asList(permissions);
        CarnationAssert.notExistNull(stringPermissions, new PermissionNotAllowedNullException("权限列表中包含为Null的权限"));
        this.permissions.addAll(stringPermissions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StringRole that = (StringRole) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Set<? extends Permission<String>> getPermissions() {
        return this.permissions;
    }

    public static StringRole of(String value) {
        return new StringRole(value);
    }

}
