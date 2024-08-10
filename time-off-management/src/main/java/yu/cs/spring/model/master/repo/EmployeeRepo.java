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
	
	List<Employee> findByCodeContaining(String code);
	
	@Query("SELECT e.sickLeaves, e.casualLeaves, e.maternityLeaves, e.unpaidLeaves FROM Employee e JOIN e.leaveApplications la WHERE e.code = :employeeCode AND la.id = :id")
		List<Object[]> findLeaveBalancesByEmployeeCodeAndLeaveApplicationId(@Param("employeeCode") String employeeCode, @Param("id") long id);

    @Query("SELECT e.sickLeaves, e.casualLeaves, e.maternityLeaves,e.unpaidLeaves FROM Employee e WHERE e.code = :employeeCode")
    List<Object[]> findLeaveBalancesByEmployeeCode(String employeeCode);
    
    // Method to check if an Employee with a given code exists in a specific department
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.code = :code AND e.department.code = :departmentCode")
    boolean existsByCodeAndDepartment(@Param("code") String code, @Param("departmentCode") String departmentCode);

}
