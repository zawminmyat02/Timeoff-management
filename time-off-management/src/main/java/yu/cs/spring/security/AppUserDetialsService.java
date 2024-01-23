package yu.cs.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import yu.cs.spring.model.repo.UserRepo;

@Service
public class AppUserDetialsService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepo.findByName(username)
				.map(user -> User.withUsername(username)
						.password(user.getPassword())
						.authorities("Admin")
						.build())
				.orElseThrow(() -> new UsernameNotFoundException("There is no user with login id %s.".formatted(username)));
	}

}
