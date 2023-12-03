package com.books.configurations;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.books.config.JwtTokenGeneratorFilter;
import com.books.config.JwtTokenValidatorFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@CrossOrigin("*")
public class AppConfig implements WebMvcConfigurer{

	
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("*")
	                .allowedMethods("GET", "POST", "PUT", "DELETE")
	                .allowedHeaders("*");
	               
	    }
	    
	
	
	
	@Bean
	HttpHeaders httpHeaders() {
		return new HttpHeaders();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	 SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {

		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).cors(cors -> {

			cors.configurationSource(new CorsConfigurationSource() {

				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration cfg = new CorsConfiguration();

					cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
					cfg.setAllowedMethods(Collections.singletonList("*"));
					cfg.setAllowCredentials(true);
					cfg.setAllowedHeaders(Collections.singletonList("*"));
					cfg.setExposedHeaders(Arrays.asList("Authorization"));
					return cfg;
				}
			});

		}).authorizeHttpRequests(auth -> {auth
             .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
             .requestMatchers("/books/*", "/books","/swagger-ui.html").permitAll()
             .requestMatchers(HttpMethod.GET,"/discussion/getall").permitAll()
             .anyRequest().authenticated();
		}).csrf(csrf -> csrf.disable())
			.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
			.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults());
				
		return http.build();

	}
	
}
