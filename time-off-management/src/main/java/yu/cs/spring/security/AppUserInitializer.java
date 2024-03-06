package yu.cs.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import yu.cs.spring.model.entity.Account;
import yu.cs.spring.model.entity.Account.Role;
import yu.cs.spring.model.repo.AccountRepo;

@Component
public class AppUserInitializer {

	@Autowired
	private AccountRepo userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Transactional
	@EventListener(classes =  ContextRefreshedEvent.class)
	public void createAdmin() {
		if(userRepo.count()==0) {
			var account = new Account();
			account.setName("Admin");
			account.setEmail("admin@gmail.com");
			account.setPassword(encoder.encode("adminpwd"));
			account.setRole(Role.Admin);
			account.setPhone("09778204234");
			account.setSalary(10000000);
			userRepo.save(account);
		}
	}
}
