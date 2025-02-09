package com.edu.pe.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.edu.pe.application.service.UsuarioService;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioService userDetailsService;
    
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
	    /*
	     Enumeration<String> headerNames = request.getHeaderNames();
	     while (headerNames.hasMoreElements()) {
	        String headerName = headerNames.nextElement();
	        String headerValue = request.getHeader(headerName);
	        System.out.println(headerName + ": " + headerValue); 
	    } 
	     */
    	    
        String requestTokenHeader = request.getHeader("authorization");
        String username = null;
        String jwtToken = null;
        System.out.println("requestTokenHeader: " + requestTokenHeader);
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("No se puede encontrar el token JWT");
            } catch (ExpiredJwtException e) {
                System.out.println("Token JWT ha expirado");
            }
        } else {
            logger.warn("JWT Token no inicia con la palabra Bearer");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else {
            System.out.println("El token no es valido");
        }
        chain.doFilter(request, response);
    }
}
