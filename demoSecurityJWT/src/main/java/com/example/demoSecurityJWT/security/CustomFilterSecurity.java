package com.example.demoSecurityJWT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class CustomFilterSecurity {
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	CustomerJwtFilter customerJwtFilter;
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
		
		
		
		return authenticationManagerBuilder.build();
	}
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}
	

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		 http
		 .csrf().disable()
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 .and()
		 .authorizeHttpRequests()
		 .requestMatchers("/api/**")
		 .permitAll()
		 .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
		 .requestMatchers("/api/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
		 .anyRequest()
		 .authenticated()
		 .and()
		 .httpBasic();
		 
		 
		 http.addFilterBefore(customerJwtFilter, UsernamePasswordAuthenticationFilter.class);
		 
		 return http.build();
		
//		  http
//	        .csrf().disable()
//	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	        .and()
//	        .authorizeHttpRequests()
//	        .requestMatchers("/api/**").permitAll()
//	        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
//	        .requestMatchers("/api/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//	        .anyRequest().authenticated()
//	        .and()
//	        .addFilterBefore(customerJwtFilter, UsernamePasswordAuthenticationFilter.class);
//	    
//	    return http.build();
         
    }
	
	
//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		
//		 http
//	        .cors().disable()
//	        .csrf().disable()
//	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	        .and()
//	        .authorizeHttpRequests()
//	        .requestMatchers("/api/**").permitAll() // Allow access to /login without authentication
//	        .anyRequest().authenticated() // All other requests require authentication
//	        .and()
//	        .httpBasic();
//		 
//		 return http.build();
//         
//    }
	

}
