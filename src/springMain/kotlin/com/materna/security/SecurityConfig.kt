package com.materna.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
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
                it.antMatchers("/", "/main.js").permitAll()
                it.anyRequest().authenticated()
            }
            .formLogin {
                it.permitAll()
                it.loginProcessingUrl("/perform_login")
                it.successForwardUrl("/login_success")
                it.failureForwardUrl("/login_failure")
            }
            .build()
}
