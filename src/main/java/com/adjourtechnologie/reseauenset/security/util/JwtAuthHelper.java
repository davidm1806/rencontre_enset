package com.adjourtechnologie.reseauenset.security.util;

import com.adjourtechnologie.reseauenset.model.Utilisateur;
import com.adjourtechnologie.reseauenset.security.SecurityPrams;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthHelper {

    public static void createdAuthJwt(HttpServletResponse response, Utilisateur user, HttpServletRequest request) {
        List<String> roles = new ArrayList<>();
        user.getAuthorities().forEach(a -> {
            roles.add(a.getAuthority());
        });

        String jwt = JWT.create()
                .withIssuer(request.getRequestURI())
                .withSubject(user.getUsername())
                .withClaim("name", user.getPseudo())
                .withClaim("activated", user.isActived())
                .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                //.withExpiresAt(new Date(System.currentTimeMillis() + SecurityPrams.EXPIRATION_ONE_DAY))
                .sign(Algorithm.HMAC256(SecurityPrams.SECRET));

        //CookieUtil.create(response, SecurityPrams.JWT_HEADER_NAME,SecurityPrams.HEADER_PREFIX+jwt,true, 1800 /*30min*/ );
        response.addHeader(SecurityPrams.JWT_HEADER_NAME, jwt);
    }
}
