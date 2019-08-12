package org.ougen.oauthdemo.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author OuGen
 * @date 2019/8/10
 */
public class SmsAuthenticationException extends AuthenticationException {
    public SmsAuthenticationException(String msg) {
        super(msg);
    }
}
