package com.violetfreesia.carnation.support;

import com.violetfreesia.carnation.util.CarnationUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author violetfreesia
 * @date 2021-04-29
 */
public class RefreshTokenInfoGetterImpl implements RefreshTokenInfoGetter {

    private final StringRedisTemplate redisTemplate;

    public RefreshTokenInfoGetterImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public RefreshTokenInfo getRefreshTokenInfo(String refreshTokenKey) {
        String refreshTokenInfoJsonString = redisTemplate.opsForValue().get(refreshTokenKey);
        if (refreshTokenInfoJsonString == null) {
            return null;
        }
        return CarnationUtil.parseObject(refreshTokenInfoJsonString, RefreshTokenInfoImpl.class);
    }
}
