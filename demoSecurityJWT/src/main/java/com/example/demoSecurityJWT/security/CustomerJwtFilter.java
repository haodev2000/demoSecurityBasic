package com.example.demoSecurityJWT.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demoSecurityJWT.utils.JwtUtilsHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomerJwtFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtUtilsHelper jwtUtilsHelper;
	
	@Autowired
	CustomUserDetailService customUserDetailService;

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		String token = getTokenFromRequest(request);
//		if (token !=  null) {
//			if(jwtUtilsHelper.verifyToken(token)) {
//			
//				// Đã an toàn cho phép truy cập vào API 
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("","", new ArrayList<>());
//				SecurityContext securityContext = SecurityContextHolder.getContext();
//				securityContext.setAuthentication(usernamePasswordAuthenticationToken);
//				
//			}
//		}
//		filterChain.doFilter(request, response);
//	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    String token = getTokenFromRequest(request);
	    
	    // Ensure the token is present and valid
	    if (token != null && jwtUtilsHelper.verifyToken(token)) {
	        // Extract username from the token
	        String username = jwtUtilsHelper.generateToken(token);

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            // Load user details from UserDetailsService
	            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
	            
	            // Check if the token is still valid with respect to user details
	            if (jwtUtilsHelper.verifyToken(token)) {
	                // Create authentication token
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities());

	                // Set the authentication in the security context
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }
	    }
	    
	    filterChain.doFilter(request, response);
	}

	
	
	private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        String jwtToken = null;
        
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
        	jwtToken = bearerToken.substring(7, bearerToken.length());
        	
        }

        return jwtToken;
    }

}
