package com.adjourtechnologie.reseauenset.security;

import com.adjourtechnologie.reseauenset.security.util.CookieUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin("*")
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        } else if (request.getRequestURI().equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(SecurityPrams.JWT_HEADER_NAME);
        //String jwtToken = CookieUtil.getValue(request, SecurityPrams.JWT_HEADER_NAME);



        //System.out.println("Token = " + jwtToken);

        if (jwtToken == null || !jwtToken.startsWith(SecurityPrams.HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtToken.substring(SecurityPrams.HEADER_PREFIX.length());

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SecurityPrams.SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

        /*if (!decodedJWT.getClaim("activated").asBoolean() || decodedJWT.getClaim("loocked").asBoolean()) {
            filterChain.doFilter(request, response);
            return;
        }*/

        // System.out.println("JWT=" + jwt);
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
        //   System.out.println("username = " + username);
        //  System.out.println("roles= " + roles);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r));
        });

        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(user);
        filterChain.doFilter(request, response);

    }
}
