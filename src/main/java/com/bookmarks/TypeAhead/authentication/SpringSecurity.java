package com.bookmarks.TypeAhead.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.cors(cors-> cors.disable());
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(
                authorizeRequests ->
                authorizeRequests.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
