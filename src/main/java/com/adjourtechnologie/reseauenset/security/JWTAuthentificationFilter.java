package com.adjourtechnologie.reseauenset.security;

import com.adjourtechnologie.reseauenset.model.Utilisateur;
import com.adjourtechnologie.reseauenset.security.util.JwtAuthHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        /*
        // si les données sont envoyez sur un format clef valeur on fait
        // c a dire au format www url encoder
        String username = request.getParameter("username");
        etc.
         */

        try {
            Utilisateur utilisateur = new ObjectMapper().readValue(request.getInputStream(), Utilisateur.class);
            //System.err.println(utilisateur.getPassword() + " " + utilisateur.getUsername());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(utilisateur.getUsername(), utilisateur.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        Utilisateur user = (Utilisateur) authResult.getPrincipal();

        JwtAuthHelper.createdAuthJwt(response, user, request);
    }
}
