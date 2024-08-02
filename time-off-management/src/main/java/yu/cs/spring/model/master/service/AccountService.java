package yu.cs.spring.model.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import yu.cs.spring.model.master.entity.Account;
import yu.cs.spring.model.master.repo.AccountRepo;

@Service
public class AccountService {

	@Autowired
	private AccountRepo repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean userNameExistAlready(String email) {
		return repo.existsByUsername(email);
	}

	public void changePassword(String username, String newPassword) {
		Account account = repo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		account.setPassword(passwordEncoder.encode(newPassword));
		account.setActivated(true);
		repo.save(account);
	}
	
	public String getNameByUserName(String email) {
		return repo.getNameByUsername(email);
	}

	  public String getDepartmentByUserName(String username) {
		  Account account = repo.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		  if (account != null && account.getEmployee() != null && account.getEmployee().getDepartment() != null) {
	            return account.getEmployee().getDepartment().getName();
	        }
	        return null; // or handle this case appropriately
	    }
}
