package yu.cs.spring.model.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yu.cs.spring.model.master.repo.AccountRepo;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepo repo;
	
	public boolean userNameExistAlready(String email) {
		return repo.existsByUsername(email);
	}

}
