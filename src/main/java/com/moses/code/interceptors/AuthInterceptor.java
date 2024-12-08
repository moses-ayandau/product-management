package com.moses.code.interceptors;

import com.moses.code.service.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private IUserService userService;

    @Value("${auth.token}")
    private String hardcodedJwtToken;

    private final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        startTimeThreadLocal.set(startTime);

        logger.info("Incoming Request: [Method: {}, URI: {}, Headers: {}]",
                request.getMethod(),
                request.getRequestURI(),
                request.getHeaderNames());

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            logger.debug("Hardcoded JWT Token: {}", hardcodedJwtToken);
            logger.debug("Extracted Token: {}", token);

            if (hardcodedJwtToken.equals(token)) {
                logger.info("Authorization successful for token: {}", token);
                return true;
            } else {
                logger.warn("Authorization failed: Invalid token provided");
            }
        } else {
            logger.warn("Authorization header missing or does not start with 'Bearer'");
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Invalid or missing token");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long endTime = System.currentTimeMillis();
        Long startTime = startTimeThreadLocal.get();

        if (startTime != null) {
            long duration = endTime - startTime;
            logger.info("Request Completed: [Method: {}, URI: {}, Status: {}, Processing Time: {} ms]",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration);
        } else {
            logger.warn("Start time was not recorded. Unable to calculate processing time.");
        }

        if (ex != null) {
            logger.error("Exception occurred after completion", ex);
        }

        startTimeThreadLocal.remove();
    }
}
