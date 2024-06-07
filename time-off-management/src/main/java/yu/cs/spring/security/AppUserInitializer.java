package yu.cs.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import yu.cs.spring.model.master.entity.Account;
import yu.cs.spring.model.master.entity.Account.Role;
import yu.cs.spring.model.master.repo.AccountRepo;

@Configuration
public class AppUserInitializer {
	
	@Autowired
	private AccountRepo repo;
	@Autowired
	private PasswordEncoder encoder;

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			if(repo.count() != 0L) {
				var admin = new Account();
				admin.setName("Admin User");
				admin.setUsername("admin");
				admin.setPassword(encoder.encode("adminpass"));
				admin.setRole(Role.Admin);
				admin.setActivated(true);
				repo.save(admin);
			}
		};
	}
}
