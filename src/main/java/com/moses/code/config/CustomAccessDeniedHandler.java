//package com.moses.code.config;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
//            throws IOException {
//        log.error("Access denied: {}", accessDeniedException.getMessage());
//        System.out.println("CustomAuthEntryPoint invoked");
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.getWriter().write("{\"error\": \"Forbidden\", \"message\": \"" + accessDeniedException.getMessage() + "\"}");
//    }
//}
