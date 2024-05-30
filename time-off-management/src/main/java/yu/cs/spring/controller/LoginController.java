package yu.cs.spring.controller;

import java.util.function.Function;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/")
	String start() {
		return "login";
	}
	
		
	@GetMapping("/home")
	String home() {
		
		Function<String, Boolean> hasAuthority = authority -> 
			SecurityContextHolder.getContext().getAuthentication().
			getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));
			
		
		if(hasAuthority.apply("Admin")) {
			return "admin-home";
		}
		
		return "user-home";
		
	}
	
}