//package com.leon.config;
//
//import com.leon.config.interceptor.JwtInterceptorAdmin;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptorAdmin())
//                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
//                .excludePathPatterns("/login", "/register", "/files","/students/**","/teachers/**","/courses/**","/role/**","/menu/**","/menu","/resInfos/**");
////        registry.addInterceptor(jwtInterceptorStudent())
////                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
////                .excludePathPatterns("/login", "/register", "/files","/students/**","/teachers/**","/courses/**");
////        registry.addInterceptor(jwtInterceptorTeacher())
////                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
////                .excludePathPatterns("/login", "/register", "/files","/students/**","/teachers/**","/courses/**");
//    }
//
//    @Bean
//    public JwtInterceptorAdmin jwtInterceptorAdmin() {
//        return new JwtInterceptorAdmin();
//    }
////    @Bean
////    public JwtInterceptorStudent jwtInterceptorStudent() {
////        return new JwtInterceptorStudent();
////    }
////    @Bean
////    public JwtInterceptorTeacher jwtInterceptorTeacher() {
////        return new JwtInterceptorTeacher();
////    }
//
//}
