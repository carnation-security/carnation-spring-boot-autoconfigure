package com.violetfreesia.carnation.autoconfigure;

import com.violetfreesia.carnation.authenticate.Authenticator;
import com.violetfreesia.carnation.authorize.AuthorizeFilter;
import com.violetfreesia.carnation.config.CarnationConfiguration;
import com.violetfreesia.carnation.handler.*;
import com.violetfreesia.carnation.interceptor.*;
import com.violetfreesia.carnation.interceptor.annotation.CheckStringPermission;
import com.violetfreesia.carnation.interceptor.annotation.CheckStringRole;
import com.violetfreesia.carnation.resolver.UserInfoArgumentResolver;
import com.violetfreesia.carnation.support.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author violetfreesia
 * @date 2021-06-18
 */
@ConditionalOnWebApplication
@EnableConfigurationProperties(CarnationProperties.class)
public class CarnationAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticateTokenHandler.class)
    public AuthenticateTokenHandler authenticateTokenHandler(StringRedisTemplate redisTemplate) {
        return new AuthenticateTokenHandlerImpl(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(AuthorizeTokenHandler.class)
    public AuthorizeTokenHandler authorizeTokenHandler(StringRedisTemplate redisTemplate) {
        return new AuthorizeTokenHandlerImpl(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(RefreshTokenInfoGetter.class)
    public RefreshTokenInfoGetter refreshTokenInfoGetter(StringRedisTemplate redisTemplate) {
        return new RefreshTokenInfoGetterImpl(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(AuthFailureHandler.class)
    public AuthFailureHandler authFailureHandlerImpl() {
        return new AuthFailureHandlerImpl();
    }

    @Bean
    @ConditionalOnMissingBean(AuthSuccessHandler.class)
    public AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandlerImpl();
    }

    @Bean
    @ConditionalOnMissingBean(TokenGenerator.class)
    public TokenGenerator tokenGenerator() {
        return new TokenGeneratorImpl();
    }

    @Bean
    @ConditionalOnMissingBean(PermissionChecker.class)
    public PermissionChecker permissionChecker() {
        return new PermissionCheckerImpl();
    }

    @Bean
    @ConditionalOnMissingBean(RoleChecker.class)
    public RoleChecker roleChecker() {
        return new RoleCheckerImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ExcludeUrlPatterns.class)
    public ExcludeUrlPatterns excludeUrlPatterns() {
        return ExcludeUrlPatterns.of();
    }

    @Bean
    @ConditionalOnMissingBean(CorsConfigurationSource.class)
    public CorsConfigurationSource corsConfigurationSource() {
        // 跨域配置
        CorsConfiguration corsConfig = new CorsConfiguration();
        // 原始域放行
        corsConfig.addAllowedOrigin("*");
        // 放行哪些原始域(请求方式)
        corsConfig.addAllowedMethod("*");
        // 放行哪些原始域(头部信息)
        corsConfig.addAllowedHeader("*");
        // 暴露哪些头部信息
        UrlBasedCorsConfigurationSource configurationSource
                = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", corsConfig);
        return configurationSource;
    }

    @Bean
    @ConditionalOnMissingBean(CustomizeAuthClass.class)
    public CustomizeAuthClass customizeAuthClass() {
        return new CustomizeAuthClass(CheckStringRole.class, CheckStringPermission.class, DefaultUserInfo.class);
    }

    @Bean
    public CarnationConfiguration carnationConfiguration(CarnationProperties properties,
                                                         AuthenticateTokenHandler authenticateTokenHandler,
                                                         AuthorizeTokenHandler authorizeTokenHandler,
                                                         RefreshTokenInfoGetter refreshTokenInfoGetter,
                                                         AuthFailureHandler authFailureHandler,
                                                         AuthSuccessHandler authSuccessHandler,
                                                         TokenGenerator tokenGenerator,
                                                         PermissionChecker permissionChecker,
                                                         RoleChecker roleChecker,
                                                         ExcludeUrlPatterns excludeUrlPatterns,
                                                         CorsConfigurationSource corsConfigurationSource,
                                                         CustomizeAuthClass customizeAuthClass) {
        return CarnationConfiguration.builder()
                .carnationProperties(properties)
                .authorizeTokenHandler(authorizeTokenHandler)
                .authenticateTokenHandler(authenticateTokenHandler)
                .refreshTokenInfoGetter(refreshTokenInfoGetter)
                .authFailureHandler(authFailureHandler)
                .authSuccessHandler(authSuccessHandler)
                .tokenGenerator(tokenGenerator)
                .permissionChecker(permissionChecker)
                .roleChecker(roleChecker)
                .excludeUrlPatterns(excludeUrlPatterns)
                .corsConfigurationSource(corsConfigurationSource)
                .roleCheckAnnotation(customizeAuthClass.getRoleCheckAnnotation())
                .permissionCheckAnnotation(customizeAuthClass.getPermissionCheckAnnotation())
                .userInfoType(customizeAuthClass.getUserInfoType())
                .build();
    }

    @Bean
    public FilterRegistrationBean<AuthorizeFilter> authorizeFilter(CarnationConfiguration carnationConfiguration) {
        FilterRegistrationBean<AuthorizeFilter> registrationBean = new FilterRegistrationBean<>();

        AuthorizeFilter authorizeFilter = new AuthorizeFilter(carnationConfiguration);

        registrationBean.setFilter(authorizeFilter);
        registrationBean.setOrder(carnationConfiguration.getCarnationProperties().isEnableCors() ?
                1 : carnationConfiguration.getCarnationProperties().getFilterOrder());

        return registrationBean;
    }

    @Bean
    public Authenticator authenticator(CarnationConfiguration carnationConfiguration) {
        return new Authenticator(carnationConfiguration);
    }

    @Bean
    public CarnationAuthInterceptor authInterceptor(CarnationConfiguration carnationConfiguration) {
        return new CarnationAuthInterceptor(carnationConfiguration);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserInfoArgumentResolver());
    }
}
