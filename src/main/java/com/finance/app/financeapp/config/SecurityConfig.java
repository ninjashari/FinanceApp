package com.finance.app.financeapp.config;

import com.finance.app.financeapp.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures security settings for the application including JWT authentication,
 * password encoding, and authentication management.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructs a new instance of SecurityConfig with the provided JwtAuthenticationFilter.
     *
     * @param jwtAuthenticationFilter the JWT authentication filter to be used in the security configuration
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity configuration to be modified
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during the configuration process
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for stateless JWT-based security
                .authorizeHttpRequests(auth -> auth.requestMatchers("/users/login", "/users/register").permitAll()  // Public endpoints
                        .anyRequest().authenticated())  // Protect all other endpoints
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // JWT filter before username/password filter
                .build();
    }

    /**
     * Creates a BCryptPasswordEncoder bean that will be used for encoding passwords.
     *
     * @return an instance of BCryptPasswordEncoder for password encoding
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Define BCrypt password encoder
    }

    /**
     * Provides the authentication manager bean using the specified authentication configuration.
     *
     * @param authenticationConfiguration the authentication configuration to use for building the authentication manager
     * @return the configured AuthenticationManager instance
     * @throws Exception if an error occurs during the configuration process
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();  // AuthenticationManager bean for login authentication
    }
}
