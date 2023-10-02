package com.Ecommerce.Ecommerceapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class webSecurityfilter {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtfilter;
    private final LogoutHandler logoutHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .requiresChannel(channel->channel.requestMatchers("/**")
                        .requiresSecure().anyRequest())
                .authorizeHttpRequests()
                .requestMatchers
                        ("/auth/**",
                                "/seller/**",
                                "/user/**",
                                "/cart/**",
                                "/order/**",
                                "/product/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(
                        (request, response, authentication) -> SecurityContextHolder.clearContext());
        return http.build();
    }


}
