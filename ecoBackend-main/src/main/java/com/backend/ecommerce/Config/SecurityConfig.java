package com.backend.ecommerce.Config;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
private final UserDetailsService userDetailsService;
 private final JwtProvider jwtProvider;

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws  Exception {
            httpSecurity.csrf(AbstractHttpConfigurer::disable)
                    .cors(cors-> corsConfigurationSource())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth

                            // Public APIs (Signup/Login/Product listing is public)
                            .requestMatchers(
                                    "/api/auth/**",
                                    "/api/products",       // product list
                                    "/api/products/search",
                                    "/api/categories/**"
                            ).permitAll()

                            // USER protected APIs
                            .requestMatchers(
                                    "/api/user/**",
                                    "/api/cart/**",
                                    "/api/orders/**",
                                    "/api/wishlist/**"
                            ).hasRole("USER")

                            // SELLER APIs (Add/Update/Delete products)
                            .requestMatchers(
                                    "/api/seller/**",
                                    "/api/seller/products/**",
                                    "/api/seller/orders/**"
                            ).hasRole("SELLER")

                            // ADMIN APIs
                            .requestMatchers(
                                    "/api/admin/**",
                                    "/api/admin/users/**",
                                    "/api/admin/products/**",
                                    "/api/admin/orders/**"
                            ).hasRole("ADMIN")

                            // Any other request needs authentication
                            .anyRequest().authenticated()

                    ).addFilterBefore(new JwtValidator(userDetailsService,jwtProvider), BasicAuthenticationFilter.class);

  return httpSecurity.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration cfs = new CorsConfiguration();
            cfs.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
            cfs.setAllowedMethods(Collections.singletonList("*"));
            cfs.setAllowCredentials(true);
            cfs.setAllowedHeaders(Collections.singletonList("*"));
            cfs.setExposedHeaders(Arrays.asList("Authorization"));
            cfs.setMaxAge(3600L);
            return cfs;

        };
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
