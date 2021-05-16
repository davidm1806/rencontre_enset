package com.adjourtechnologie.reseauenset.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImplementation userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsServiceImplementation userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();


        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
//        http.authorizeRequests().antMatchers("/api/v1/login/**", "/api/v1/forAny/**").permitAll();
//
//        http.authorizeRequests().antMatchers("/appRoles/**").hasAnyAuthority("ADMIN", "SUPERADMIN");
//
//
//        /*
//        autorities communities management
//         */
//
//        http.authorizeRequests().antMatchers("/api/v1/for-root/**").hasAnyAuthority("ADMIN");
//        http.authorizeRequests().antMatchers("/api/v1/master_ctrl/**").hasAnyAuthority("CHEF_SERVICE", "ADMIN");
//        http.authorizeRequests().antMatchers("/api/v1/guichetier-ctl/**").hasAnyAuthority("GUICHETIER","CHEF_SERVICE","ADMIN");
//        http.authorizeRequests().antMatchers("/api/v1/forChauffeur/**").hasAnyAuthority("CHAUFFEUR");
//
//
//        http.authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests().anyRequest().permitAll();

        http.addFilter(new JWTAuthentificationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
