package com.violetfreesia.carnation.support;

import lombok.NonNull;

import java.util.UUID;

/**
 * @author violetfreesia
 * @date 2021-04-29
 */
public class TokenGeneratorImpl implements TokenGenerator {

    @Override
    public Token generateToken(@NonNull UserInfo<?, ?> userInfo, boolean enableRefreshToken) {
        String token = UUID.randomUUID().toString();
        token = token.replace("-", "");
        if (enableRefreshToken) {
            StringBuilder builder = new StringBuilder(token);
            String refreshToken = builder.reverse().toString();
            return new TokenImpl(token, refreshToken);
        }
        return new TokenImpl(token);
    }

    @Override
    public String toRefreshToken(@NonNull String token) {
        return new StringBuilder(token).reverse().toString();
    }

    @Override
    public String toToken(@NonNull String refreshToken) {
        return new StringBuilder(refreshToken).reverse().toString();
    }
}
