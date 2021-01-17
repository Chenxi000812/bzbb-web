package com.chenxi.bzbb.conf;

import com.chenxi.bzbb.custom.security.CustomAuthenticationFailureHandler;
import com.chenxi.bzbb.custom.security.CustomAuthenticationSuccessHandler;
import com.chenxi.bzbb.custom.security.SmsCodeAuthenticationFilter;
import com.chenxi.bzbb.custom.security.SmsCodeAuthenticationProvider;
import com.chenxi.bzbb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private UserService userService;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http){
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        smsCodeAuthenticationFilter.setRememberMeServices(new PersistentTokenBasedRememberMeServices("bzbb",userService,persistentTokenRepository));
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserService(userService);
        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterBefore(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
