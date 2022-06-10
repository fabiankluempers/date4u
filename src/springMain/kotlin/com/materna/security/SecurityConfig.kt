package com.materna.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
            .authorizeHttpRequests {
                it
                    .antMatchers("/", "/main.js").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin {
                it
                    .permitAll()
                    .loginPage("/login/status")
                    .loginProcessingUrl("/perform_login")
                    .failureUrl("/login/status")
                    .defaultSuccessUrl("/login/status", true)
            }
            .build()
}
