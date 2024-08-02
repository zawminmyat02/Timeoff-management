package yu.cs.spring.model.master.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import yu.cs.spring.model.BaseRepository;
import yu.cs.spring.model.master.entity.Employee;

public interface EmployeeRepo extends BaseRepository<Employee, String>{

	@Query("SELECT e FROM Employee e WHERE e.account.username = :username")
    Employee findByAccountUsername(@Param("username") String username);
	
	Employee findByCode(String code);
	
	boolean existsByEmail(String email);
	
	long countByEmail(String value);

	List<Employee> findByDepartmentName(String department);
}
