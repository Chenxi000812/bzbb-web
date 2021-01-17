package com.chenxi.bzbb.conf;
import com.chenxi.bzbb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}123")
                .roles("ADMIN");
        auth.userDetailsService(userService);
    }

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    UserService userService;

    @Autowired
    PersistentTokenRepository persistentTokenRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.apply(smsCodeAuthenticationSecurityConfig).and().authorizeRequests()
                .antMatchers("/assets/**","/loginpage","/app/**","/img/**","/sms/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/loginpage")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index")
                .failureForwardUrl("/loginpage")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/loginpage")
                .and()
                .rememberMe()
                .userDetailsService(userService)
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(259200)
                .and()
                .csrf()
                .disable();

    }


}
