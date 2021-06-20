package com.violetfreesia.carnation.support;

import com.violetfreesia.carnation.exception.EmptyStringException;
import com.violetfreesia.carnation.util.CarnationAssert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;

/**
 * 权限的字符串值实现
 *
 * @author violetfreesia
 * @date 2021-04-26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringPermission implements Permission<String> {

    private String value;

    public StringPermission(@NonNull String value) {
        CarnationAssert.notBlank(value, new EmptyStringException("权限值不能为空字符串"));
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StringPermission that = (StringPermission) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public static StringPermission of(String value) {
        return new StringPermission(value);
    }
}
