//package com.moses.code.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
//    private static final Logger logger = LoggerFactory.getLogger(CustomAuthEntryPoint.class);
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException authException) throws IOException, ServletException {
//
//        logger.error("Unauthorized error: {}", authException.getMessage());
//
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        errorDetails.put("error", "Unauthorized");
//        errorDetails.put("message", authException.getMessage());
//        errorDetails.put("path", request.getServletPath());
//
//        objectMapper.writeValue(response.getOutputStream(), errorDetails);
//    }
//}