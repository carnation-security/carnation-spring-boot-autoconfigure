package com.violetfreesia.carnation.resolver;

import com.violetfreesia.carnation.context.SecurityContextHolder;
import com.violetfreesia.carnation.support.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Authentication argument resolver.
 *
 * @author violetfreesia
 * @date 2021-04-29
 */
@Slf4j
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return (UserInfo.class.isAssignableFrom(parameterType));
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        return SecurityContextHolder.getContext().getUserInfo();
    }

}
