package yu.cs.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import yu.cs.spring.model.entity.User;
import yu.cs.spring.model.repo.UserRepo;

@Component
public class AppUserInitializer {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Transactional
	@EventListener(classes =  ContextRefreshedEvent.class)
	public void createAdmin() {
		if(userRepo.count()==0) {
			var user = new User();
			user.setName("Admin");
			user.setPassword(encoder.encode("adminpwd"));
			userRepo.save(user);
		}
	}
}
