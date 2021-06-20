package com.violetfreesia.carnation.handler;

import com.violetfreesia.carnation.exception.CarnationException;
import com.violetfreesia.carnation.util.CarnationUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author violetfreesia
 * @date 2021-04-27
 */
public class AuthFailureHandlerImpl implements AuthFailureHandler {
    @Override
    public void onFailure(HttpServletRequest request, HttpServletResponse response, CarnationException exception) throws IOException {
        response.setContentType("text/json;charset=UTF-8");
        ErrorDetail errorDetail = new ErrorDetail(exception.getMessage());
        PrintWriter writer = response.getWriter();
        writer.write(CarnationUtil.toJsonString(errorDetail));
        writer.close();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetail {
        private String errMsg;
    }
}
