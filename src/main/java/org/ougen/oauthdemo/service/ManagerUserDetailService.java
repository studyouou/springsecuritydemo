package org.ougen.oauthdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author OuGen
 * @date 2019/8/12
 */
@Configuration
public class ManagerUserDetailService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Order(1)
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password(passwordEncoder.encode("123456")).authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password(passwordEncoder.encode("123456")).authorities("USER").build());
        return manager;
    }
}
