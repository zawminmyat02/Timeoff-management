package yu.cs.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import yu.cs.spring.model.repo.AccountRepo;

@Service
public class AppUserDetialsService implements UserDetailsService{

	@Autowired
	private AccountRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepo.findByEmail(username)
				.map(user -> User.withUsername(username)
						.password(user.getPassword())
						.authorities(AuthorityUtils.createAuthorityList(user.getRole().name()))
						.build())
				.orElseThrow(() -> new UsernameNotFoundException("There is no user with login id %s.".formatted(username)));
	}

}
