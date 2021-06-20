package com.violetfreesia.carnation.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @author violetfreesia
 * @date 2021-04-27
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenInfoImpl implements RefreshTokenInfo {

    private String token;

    private Serializable userId;

    public RefreshTokenInfoImpl(@NonNull String token, @NonNull Serializable userId) {
        this.token = token;
        this.userId = userId;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public Serializable getUserId() {
        return userId;
    }
}
