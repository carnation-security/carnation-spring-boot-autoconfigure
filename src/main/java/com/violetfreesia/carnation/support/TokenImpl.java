package com.violetfreesia.carnation.support;

import lombok.NonNull;

import java.io.Serializable;

/**
 * @author violetfreesia
 * @date 2021-04-27
 */
public class TokenImpl implements Token {

    private final String token;

    private final String refreshToken;

    public TokenImpl(@NonNull String token) {
        this.token = token;
        this.refreshToken = null;
    }

    public TokenImpl(@NonNull String token, @NonNull String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * 获取RefreshTokenInfo
     *
     * @param userId 用户编号
     * @return 返回RefreshTokenInfo
     */
    @Override
    public RefreshTokenInfo getRefreshTokenInfo(@NonNull Serializable userId) {
        return new RefreshTokenInfoImpl(getToken(), userId);
    }
}
