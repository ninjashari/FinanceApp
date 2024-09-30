package com.finance.app.financeapp.security;

import com.finance.app.financeapp.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that processes JWT authentication for each HTTP request.
 * It checks the "Authorization" header for a valid JWT token, extracts the username from the token,
 * and if the user is authenticated, sets the authentication in the security context holder.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Lazy
    private UserService userService;

    /**
     * Processes the JWT authentication for incoming requests. It checks the "Authorization" header for a valid JWT token, extracts the username from the token,
     * and if the user is authenticated, sets the authentication in the security context holder.
     *
     * @param request      the HTTP request
     * @param response     the HTTP response
     * @param filterChain  the filter chain
     * @throws ServletException if an error occurs during the filtering process
     * @throws IOException      if an I/O error occurs during the filtering process
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.findByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}