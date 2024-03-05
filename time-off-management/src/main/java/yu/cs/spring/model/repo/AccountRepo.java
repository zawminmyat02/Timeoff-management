package yu.cs.spring.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import yu.cs.spring.model.entity.Account;

public interface AccountRepo extends JpaRepositoryImplementation<Account, Integer>{

	Optional<Account> findByEmail(String username);
	
	  @Query("SELECT a FROM Account a WHERE a.role = 1")
	   List<Account> findAllMembers();

}
