package com.smartcontactmanager.scm.security;

import com.smartcontactmanager.scm.exception.InvalidRequestException;
import com.smartcontactmanager.scm.security.helper.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.smartcontactmanager.scm.exception.ErrorCodes.AUTHORIZATION_HEADER_NOT_PRESENT;
import static com.smartcontactmanager.scm.exception.ErrorCodes.INVALID_BASIC_AUTH;

@Component
public class CustomFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader: " + authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            // trying to login
            String base64Credential = authorizationHeader.substring(6);
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credential);
            String[] userDerivedDetails = new String(decodedBytes, StandardCharsets.UTF_8).split(":");
            String email = userDerivedDetails[0];
            String passwordFromRequest = userDerivedDetails[1];
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            String passwordFromDb = userDetails.getPassword();
            if (passwordEncoder.matches(passwordFromRequest, passwordFromDb)) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                );
            } else {
                throw new InvalidRequestException(INVALID_BASIC_AUTH, "invalid basic auth");
            }
        } else if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // trying to access a resource which requires bearer token
            String token = authorizationHeader.substring(7);
            String email = jwtUtil.extractEmail(token);
            jwtUtil.validateToken(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw new RuntimeException();
            }
        }
        filterChain.doFilter(request, response);
    }
}
