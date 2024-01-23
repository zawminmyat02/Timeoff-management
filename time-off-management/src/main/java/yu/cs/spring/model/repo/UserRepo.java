package yu.cs.spring.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import yu.cs.spring.model.entity.User;

public interface UserRepo extends JpaRepositoryImplementation<User, Integer>{

	Optional<User> findByName(String username);

}
