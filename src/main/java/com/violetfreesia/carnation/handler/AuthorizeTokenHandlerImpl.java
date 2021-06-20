package com.violetfreesia.carnation.handler;

import com.violetfreesia.carnation.config.CarnationConfiguration;
import com.violetfreesia.carnation.exception.TokenInvalidException;
import com.violetfreesia.carnation.support.UserInfo;
import com.violetfreesia.carnation.util.CarnationAssert;
import com.violetfreesia.carnation.util.CarnationUtil;
import lombok.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Optional;

/**
 * token校验器默认实现
 *
 * @author violetfreesia
 * @date 2021-04-27
 */
public class AuthorizeTokenHandlerImpl implements AuthorizeTokenHandler {

    private final StringRedisTemplate redisTemplate;

    public AuthorizeTokenHandlerImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<UserInfo<?, ?>> checkToken(@NonNull String token, @NonNull CarnationConfiguration carnationConfiguration) {
        String tokenKey = CarnationUtil.joinKey(carnationConfiguration.getCarnationProperties().getTokenPrefix(), token);
        String jsonString = redisTemplate.opsForValue().get(tokenKey);
        CarnationAssert.notNull(jsonString, new TokenInvalidException("无效的Token: " + token));
        UserInfo<?, ?> userInfo = CarnationUtil.parseObject(jsonString, carnationConfiguration.getUserInfoType());
        return Optional.ofNullable(userInfo);
    }
}
