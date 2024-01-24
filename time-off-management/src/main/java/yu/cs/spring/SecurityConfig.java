package yu.cs.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import yu.cs.spring.security.AppUserDetialsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig implements WebMvcConfigurer {
	
	

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();	
	}
	
	@Bean
	AuthenticationProvider authenticationProvider(AppUserDetialsService userDetailsService, PasswordEncoder encoder) {
		var dao = new DaoAuthenticationProvider(encoder);
		dao.setUserDetailsService(userDetailsService);
		return dao;
	}
		
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(request -> {
			request.requestMatchers("/resources/**","/login","style/css/**").permitAll()
			.anyRequest().authenticated();
			
		}).formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/"));
	
		
		
		return http.build();
	}
	
}
