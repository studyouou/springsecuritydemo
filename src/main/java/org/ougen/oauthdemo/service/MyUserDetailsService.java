//package org.ougen.oauthdemo.service;
//
//import org.ougen.oauthdemo.domain.MyUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.stereotype.Component;
//
///**
// * @author OuGen
// * @date 2019/8/7
// */
//@Component
//@Configuration
//@Order(100)
//public class MyUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private PasswordEncoder passwordEncode;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        MyUser myUser = new MyUser();
//        myUser.setPassword(passwordEncode.encode(s));
//        System.out.println(myUser.getPassword());
//        return new User("ougen",myUser.getPassword(),myUser.isEnabled(),myUser.isAccountNonExpired(),
//                myUser.isCredentialsNonExpired(),myUser.isAccountNonLocked(),AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//    }
//
//
//
//}
