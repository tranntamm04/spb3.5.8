package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.example.service.impl.AccountDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final AccountDetailsServiceImpl accountDetailsService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    public SecurityConfig(
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtRequestFilter jwtRequestFilter,
            AccountDetailsServiceImpl accountDetailsService,
            OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.accountDetailsService = accountDetailsService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(accountDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .exceptionHandling(ex ->
                ex.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.PATCH, "/api/products/*/stock").permitAll()
                .requestMatchers(
                    "/login", "/register", "/random", "/forgot-password",
                        "/verify-otp",
                        "/reset-password", "/mail-template", "/css/**", "/js/**", "/images/**",
                        "/oauth2/**",
                        "/login/oauth2/**",


                        "/api/customer/**",

                    "/api/products",
                    "/api/products/{id}",
                    "/api/products/*/ratings",

                    "/api/promotion/**",

                    "/api/rating/*",

                    "/api/bill/revenue",

                    "/api/chatbot/chat"
                ).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(
                    "/api/bill/**", "/change-password", "/api/bill/*", "/api/customer/profile"
                ).authenticated()
                .requestMatchers(
                    "/api/productType/**",
                    "/api/products/**", "/api/employees/*", "/api/employees/**",
                    "/api/bill/listBill", "/api/bill/listBillDetail", "/api/rating/*"
                ).hasRole("ADMIN")
                .anyRequest().authenticated()
            )
                .oauth2Login(oauth -> oauth
                        .successHandler(oAuth2LoginSuccessHandler)
                );
        ;

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }
}
