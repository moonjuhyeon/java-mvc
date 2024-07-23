package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class HandlerExecution {

    private final Object handler;
    private final Method method;

    public HandlerExecution(final Object handler, final Method method) {
        this.handler = handler;
        this.method = method;
    }

    public HandlerExecution(final Object handler, final String methodName) {
        this.handler = handler;
        this.method = getHandlerMethod(handler, methodName);
    }

    private Method getHandlerMethod(final Object handler, final String methodName) {
        try {
            return handler.getClass()
                    .getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException("No such method : " + methodName);
        }
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(handler, request, response);
    }
}
