package org.ougen.oauthdemo.filter;

import org.ougen.oauthdemo.owntoken.SmsAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author OuGen
 * @date 2019/8/10
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String MOBILE_KEY = "mobile";

    private String mobileParameter = MOBILE_KEY;
    private boolean postOnly = true;

    public SmsAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/mobile", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String mobie = obtainMobie(httpServletRequest);

        if (mobie == null) {
            mobie = "";
        }

        mobie = mobie.trim();
        SmsAuthenticationToken token = new SmsAuthenticationToken(mobie);

        setDetails(httpServletRequest,token);

        return getAuthenticationManager().authenticate(token);
    }

    protected String obtainMobie(HttpServletRequest request) {
        return request.getParameter("mobie");
    }

    protected void setDetails(HttpServletRequest request,
                              SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
