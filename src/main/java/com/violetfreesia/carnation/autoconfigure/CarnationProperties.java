package com.violetfreesia.carnation.autoconfigure;

import com.violetfreesia.carnation.config.ICarnationProperties;
import com.violetfreesia.carnation.support.TokenPosition;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

import java.util.concurrent.TimeUnit;

/**
 * @author violetfreesia
 * @date 2021-04-26
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties("carnation")
public class CarnationProperties implements ICarnationProperties {

    /**
     * token过期时间
     */
    private Integer tokenExpireIn = 24 * 3600;

    /**
     * 刷新token过期时间
     */
    private Integer refreshTokenExpireIn = 30 * 24 * 3600;

    /**
     * token过期时间单位
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * token前缀
     */
    private String tokenPrefix = "carnation.token.";

    /**
     * refresh_token前缀
     */
    private String refreshTokenPrefix = "carnation.refresh_token.";

    /**
     * token名
     */
    private String tokenName = "Authorization";

    /**
     * token的获取位置
     */
    private TokenPosition tokenPosition = TokenPosition.HEADER;

    /**
     * 是否开启刷新token
     */
    private Boolean enableRefreshToken = true;

    /**
     * 开启跨域(开启跨域后拦截器会被注册在第一个)
     */
    private boolean enableCors = false;

    /**
     * 拦截器的注册顺序
     */
    private Integer filterOrder = Ordered.LOWEST_PRECEDENCE;
}
