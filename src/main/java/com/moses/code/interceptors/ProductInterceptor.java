package com.moses.code.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ProductInterceptor implements HandlerInterceptor {

    private final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            long startTime = System.currentTimeMillis();
            startTimeThreadLocal.set(startTime);
            System.out.println("Incoming request!");
            System.out.println("Method Type: " + request.getMethod());
            System.out.println("Request URL: " + request.getRequestURI());
            System.out.println("Start Time: " + startTime);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            long endTime = System.currentTimeMillis();
            Long startTime = startTimeThreadLocal.get();
            if (startTime != null) {
                long duration = endTime - startTime;
                System.out.println("Request Method: " + request.getMethod());
                System.out.println("Request URL: " + request.getRequestURI());
                System.out.println("Time Taken: " + duration + " ms");
            } else {
                System.out.println("Start time was not recorded. Unable to calculate time taken.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            startTimeThreadLocal.remove(); // Clean up ThreadLocal to prevent memory leaks
        }
    }
}
