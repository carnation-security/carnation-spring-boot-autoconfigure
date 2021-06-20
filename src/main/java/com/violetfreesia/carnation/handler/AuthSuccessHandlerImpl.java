package com.violetfreesia.carnation.handler;

import com.violetfreesia.carnation.support.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author violetfreesia
 * @date 2021-04-27
 */
public class AuthSuccessHandlerImpl implements AuthSuccessHandler {
    @Override
    public void onSuccess(HttpServletRequest request, HttpServletResponse response, UserInfo<?, ?> userInfo) {
    }
}
