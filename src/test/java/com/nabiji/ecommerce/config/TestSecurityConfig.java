package com.nabiji.ecommerce.config;

import com.nabiji.ecommerce.security.JwtAuthenticationEntryPoint;
import com.nabiji.ecommerce.security.JwtAuthenticationFilter;
import com.nabiji.ecommerce.security.JwtTokenProvider;
import com.nabiji.ecommerce.security.UserDetailsServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(JwtTokenProvider.class)
@TestConfiguration
public class TestSecurityConfig {


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }
}