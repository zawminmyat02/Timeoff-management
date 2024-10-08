package yu.cs.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import yu.cs.spring.model.master.entity.Account.Role;
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
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionExpiredStrategy("/login?sessionExpired=true");
    }

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(request -> {
			request.requestMatchers("/resources/**", "/login", "images/**", "css/**", "bootstrap/css/**",
					"bootstrap/js/**", "/change-password", "/checkOverlap/**", "/changeStatus/**", "/home").permitAll()
					.requestMatchers("/home", "/checkOverlap/**")
					.hasAnyAuthority(Role.Admin.name(), Role.Employee.name(), Role.HOD.name())
					.requestMatchers("/employees/**", "/positions/codes/**")
					.hasAnyAuthority(Role.HOD.name(), Role.Admin.name())
					.requestMatchers("departments/**", "/positions/**").hasAuthority(Role.Admin.name()).anyRequest()
					.authenticated();

		}).formLogin(form -> form.loginPage("/login").loginProcessingUrl("/signup").defaultSuccessUrl("/home", true)
				.successHandler(authenticationSuccessHandler()))
		  .sessionManagement(session -> session
	                .maximumSessions(1)
	                .expiredSessionStrategy(sessionInformationExpiredStrategy())); // Use custom session expired strategy
	            

		http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

		http.logout(a -> a.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessHandler(new CustomLogoutSuccessHandler()) // Use custom logout handler
				.logoutSuccessUrl("/").deleteCookies("JSESSIONID").invalidateHttpSession(true));

		return http.build();
	}

}
