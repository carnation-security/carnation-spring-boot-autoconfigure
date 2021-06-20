package com.violetfreesia.carnation.handler;

import com.violetfreesia.carnation.config.ICarnationProperties;
import com.violetfreesia.carnation.support.RefreshTokenInfo;
import com.violetfreesia.carnation.support.UserInfo;
import com.violetfreesia.carnation.util.CarnationUtil;
import lombok.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 认证token处理器的默认实现类
 *
 * @author violetfreesia
 * @date 2021-04-26
 */
public class AuthenticateTokenHandlerImpl implements AuthenticateTokenHandler {

    private final StringRedisTemplate redisTemplate;

    public AuthenticateTokenHandlerImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveToken(@NonNull String tokenKey, @NonNull UserInfo<?, ?> userInfo,
                          @NonNull String refreshTokenKey, @NonNull RefreshTokenInfo refreshTokenInfo,
                          @NonNull ICarnationProperties properties) {

        String userInfoJsonString = CarnationUtil.toJsonString(userInfo);
        String refreshTokenInfoJsonString = CarnationUtil.toJsonString(refreshTokenInfo);

        redisTemplate.opsForValue().set(tokenKey, userInfoJsonString,
                properties.getTokenExpireIn(), properties.getTimeUnit());
        redisTemplate.opsForValue().set(refreshTokenKey, refreshTokenInfoJsonString,
                properties.getRefreshTokenExpireIn(), properties.getTimeUnit());
    }

    /**
     * Token的保存方式
     *
     * @param tokenKey   tokenKey
     * @param userInfo   tokenKey对应的UserInfo
     * @param properties 全局配置项
     */
    @Override
    public void saveToken(@NonNull String tokenKey, @NonNull UserInfo<?, ?> userInfo,
                          @NonNull ICarnationProperties properties) {

        String userInfoJsonString = CarnationUtil.toJsonString(userInfo);

        redisTemplate.opsForValue().set(tokenKey, userInfoJsonString,
                properties.getTokenExpireIn(), properties.getTimeUnit());
    }

    @Override
    public void deleteToken(@NonNull String tokenKey) {
        redisTemplate.delete(tokenKey);
    }
}
