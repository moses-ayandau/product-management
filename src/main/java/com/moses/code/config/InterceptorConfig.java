package com.moses.code.config;

import com.moses.code.interceptors.AuthInterceptor;
import com.moses.code.interceptors.ProductInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    ProductInterceptor productInterceptor;
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(productInterceptor).addPathPatterns("/api");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/cart/**", "/api/orders/**", "/api/cartitem/**", "/api/products/add");

    }
}