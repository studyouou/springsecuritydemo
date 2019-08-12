package org.ougen.oauthdemo.provider;

import org.ougen.oauthdemo.owntoken.SmsAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author OuGen
 * @date 2019/8/10
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailService;

    public void setUserDetailService(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        UserDetails userDetails = userDetailService.loadUserByUsername((String) smsAuthenticationToken.getPrincipal());
        SmsAuthenticationToken smsAuthenticationToken1 = new SmsAuthenticationToken(userDetails,userDetails.getAuthorities());
        smsAuthenticationToken1.setDetails(smsAuthenticationToken.getDetails());
        return smsAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
