package com.klaystakingservice.common.security.cookie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class CookieUtil {

    @Value("${spring.profiles.active}")
    private String activeProfile;
    private static final String LOCAL_PROFILE = "local";

    public Cookie createCookie(String cookieName, String value, long expireTime){
        log.debug("===== createCookie start =======");

        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(true);
        token.setMaxAge( (int) (expireTime / 1000L));
        token.setPath("/");
        token = setSecureWhenIsNotLocalProfile(token);

        log.debug("token.getMaxAge : "+ token.getMaxAge());
        return token;
    }

    private Cookie setSecureWhenIsNotLocalProfile(Cookie token) {

        if(isNotLocalProfile())
            token.setSecure(true);
        return token;
    }
    private boolean isNotLocalProfile() {
        return !LOCAL_PROFILE.equals(activeProfile);
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        log.debug("===== getCookie start ======");
        final Cookie[] cookies = req.getCookies();

        if(isEmpty(cookies))
            return null;

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }

        return null;
    }

    private boolean isEmpty(Cookie[] cookies) {
        return cookies == null;
    }
}