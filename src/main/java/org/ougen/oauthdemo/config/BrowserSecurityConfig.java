package org.ougen.oauthdemo.config;

import org.ougen.oauthdemo.filter.SmsAuthenticationFilter;
import org.ougen.oauthdemo.filter.SmsCodeFilter;
import org.ougen.oauthdemo.handler.MyFailHandler;
import org.ougen.oauthdemo.handler.MySuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author OuGen
 * @date 2019/8/7
 */
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncode(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MySuccessHandler successHandler;

//    @Autowired
//    private SmsAuthenticationFilter smsAuthenticationFilter;

    @Autowired
    private SmsCodeFilter smsCodeFilter;

    @Autowired
    private MySuccessHandler mySuccessHandler;

    @Autowired
    private MyFailHandler myFailHandler;

    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .formLogin().loginPage("/login.html")// 表单登录
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .successHandler(mySuccessHandler)
                .failureHandler(myFailHandler)
                .and()
                .authorizeRequests()// 授权配置
                .antMatchers("/login.html","/login","/css/**","/code/sms").permitAll()
                .anyRequest()// 所有请求
                .authenticated()
                .and()
                .csrf().disable().apply(smsAuthenticationConfig);// 都需要认证
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
