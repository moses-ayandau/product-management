package com.moses.code.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ProductInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ProductInterceptor.class);
    private final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            long startTime = System.currentTimeMillis();
            startTimeThreadLocal.set(startTime);

            logger.info("Incoming Request: [Method: {}, URI: {}, Start Time: {}]",
                    request.getMethod(),
                    request.getRequestURI(),
                    startTime);
        } catch (Exception e) {
            logger.error("Error during preHandle", e);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            long currentTime = System.currentTimeMillis();
            Long startTime = startTimeThreadLocal.get();

            if (startTime != null) {
                long processingTime = currentTime - startTime;
                logger.info("Request Processing Time: [Method: {}, URI: {}, Processing Time: {} ms]",
                        request.getMethod(),
                        request.getRequestURI(),
                        processingTime);
            } else {
                logger.warn("Start time was not recorded. Unable to calculate processing time.");
            }
        } catch (Exception e) {
            logger.error("Error during postHandle", e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            long endTime = System.currentTimeMillis();
            Long startTime = startTimeThreadLocal.get();

            if (startTime != null) {
                long duration = endTime - startTime;
                logger.info("Request Completed: [Method: {}, URI: {}, Total Time Taken: {} ms]",
                        request.getMethod(),
                        request.getRequestURI(),
                        duration);
            } else {
                logger.warn("Start time was not recorded. Unable to calculate total request duration.");
            }

            if (ex != null) {
                logger.error("Exception occurred after request completion", ex);
            }
        } catch (Exception e) {
            logger.error("Error during afterCompletion", e);
        } finally {
            startTimeThreadLocal.remove();
        }
    }
}
