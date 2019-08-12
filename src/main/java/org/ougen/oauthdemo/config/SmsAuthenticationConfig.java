package org.ougen.oauthdemo.config;

import org.ougen.oauthdemo.filter.SmsAuthenticationFilter;
import org.ougen.oauthdemo.handler.MyFailHandler;
import org.ougen.oauthdemo.handler.MySuccessHandler;
import org.ougen.oauthdemo.provider.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author OuGen
 * @date 2019/8/10
 */

@Component
public class SmsAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    private MySuccessHandler mySuccessHandler;

    @Autowired
    private MyFailHandler myFailHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationFailureHandler(myFailHandler);
        smsAuthenticationFilter.setAuthenticationSuccessHandler(mySuccessHandler);

        SmsAuthenticationProvider provider = new SmsAuthenticationProvider();
        provider.setUserDetailService(userDetailsService);

        builder.authenticationProvider(provider).addFilterBefore(smsAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
    }
}
