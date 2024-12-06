package com.finance.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configures the security settings for the application.
 * This includes setting up a password encoder, defining security filter chains,
 * and configuring authentication management.
 * Utilizes Spring Security features to manage authentication and authorization.
 */
@Configuration
public class SecurityConfig {

    /**
     * Provides a PasswordEncoder bean that uses the BCrypt hashing algorithm.
     *
     * @return a PasswordEncoder instance using BCrypt for encoding passwords
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the {@link HttpSecurity} to modify
     * @return a {@link SecurityFilterChain} that defines the security configuration
     * @throws Exception if an error occurs while configuring the security settings
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/users/signup", "/api/v1/users/login").permitAll()
                        .requestMatchers("/api/v1/users/user").authenticated())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

//    /**
//     * Configures the authentication manager with in-memory authentication having a predefined user.
//     *
//     * @param auth the {@link AuthenticationManagerBuilder} used to set up the authentication
//     * @throws Exception if an error occurs when setting up the authentication
//     */
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("username")
//                .password(passwordEncoder().encode("password"));
//    }

}
