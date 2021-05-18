package com.adjourtechnologie.reseauenset.security.util;

import com.adjourtechnologie.reseauenset.security.SecurityPrams;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CookieUtil {
    public static void create(HttpServletResponse httpServletResponse, String name, String value, Boolean secure, Integer maxAge) {
        try{
            Cookie cookie = new Cookie(name, URLEncoder.encode( value, "UTF-8" ));
            cookie.setSecure(secure);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(maxAge);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
        }catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void clear(HttpServletResponse httpServletResponse,HttpServletRequest request, String name) throws IOException {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        httpServletResponse.sendRedirect(request.getContextPath() + "/cltrPublic/sign-in");
    }

    public static String getValue(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        return cookie != null ? cookie.getValue() : null;
    }
}
