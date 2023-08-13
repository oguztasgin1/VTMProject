package com.vtm.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    JwtTokenFilter getJwtTokenFilter(){
        return new JwtTokenFilter();
    }

    private static final String[] WHITELIST = {
            "/auth/**",
            "/company/createcompany",
            "/actuator/**",
            "/test",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/bus/v3/api-docs/**",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/webjars/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
    };

    private static final String[] BLACKLIST = {
            "/vehicle/**",
            "/region/**",
            "/fleet/**",
            "/group/**",
            "/userprofile/**",
    };

    private static final String[] GREYLIST = {
            "/vehicle/vehicletreebyuserid",
            "/vehicle/getbyvehicleid",
            "/vehicle/getallvehiclebycompanyidanduserid",
            "/vehicle/getallvehiclebycompanyid",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .requestMatchers(WHITELIST)
                .permitAll()
                .requestMatchers(GREYLIST)
                .hasAnyAuthority("STANDARD","ADMIN", "MANAGER")
                .requestMatchers(BLACKLIST)
                .hasAnyAuthority("ADMIN", "MANAGER")
                .anyRequest()
                .authenticated();


         httpSecurity.csrf().disable();
        // httpSecurity.formLogin();
        httpSecurity.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
