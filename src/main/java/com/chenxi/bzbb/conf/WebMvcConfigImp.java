package com.chenxi.bzbb.conf;

import com.chenxi.bzbb.conf.Interceptors.IpInterceptor;
import com.chenxi.bzbb.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigImp implements WebMvcConfigurer {
    @Autowired
    AppService appService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        IpInterceptor.addAllowIps(appService.queryAllipLimit());
        InterceptorRegistration registration = registry.addInterceptor(new IpInterceptor());
        registration.addPathPatterns("/loginpage","/index","/GoodManage","/admin/**","/OrderManage");
    }
}
