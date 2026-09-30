package com.orderflow.orderflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.orderflow.orderflow.security.JwtAuthenticationFilter;
import com.orderflow.orderflow.security.JwtService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;

@Configuration
public class SecurityConfig {
	private final JwtService jwtService;

	public SecurityConfig(JwtService jwtService) {
	    this.jwtService = jwtService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
	        throws Exception {

	    JwtAuthenticationFilter jwtFilter =
	            new JwtAuthenticationFilter(jwtService);

	    http
	    .csrf(csrf -> csrf.disable())
	    .sessionManagement(session ->
	        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    )
	    .exceptionHandling(exception ->
	        exception
	            .authenticationEntryPoint(
	                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
	            )
	            .accessDeniedHandler((request, response, accessDeniedException) ->
	                response.setStatus(HttpStatus.FORBIDDEN.value())
	            )
	    )
	    .authorizeHttpRequests(auth -> auth
	    		.requestMatchers(
	    			    "/swagger-ui.html",
	    			    "/swagger-ui/**",
	    			    "/v3/api-docs/**"
	    			).permitAll()
	        .requestMatchers(
	            org.springframework.http.HttpMethod.POST,
	            "/auth/login",
	            "/users"
	        ).permitAll()

	        .requestMatchers(
	            org.springframework.http.HttpMethod.POST,
	            "/products"
	        ).hasRole("ADMIN")

	        .requestMatchers(
	            org.springframework.http.HttpMethod.PUT,
	            "/products/**"
	        ).hasRole("ADMIN")

	        .requestMatchers(
	            org.springframework.http.HttpMethod.DELETE,
	            "/products/**"
	        ).hasRole("ADMIN")

	        .anyRequest().authenticated()
	    )
	    .addFilterBefore(
	        jwtFilter,
	        UsernamePasswordAuthenticationFilter.class
	    );

	    return http.build();
	}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}