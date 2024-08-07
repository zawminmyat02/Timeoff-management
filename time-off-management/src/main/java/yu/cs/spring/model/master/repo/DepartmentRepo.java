package yu.cs.spring.model.master.repo;

import java.util.List;

import yu.cs.spring.model.BaseRepository;
import yu.cs.spring.model.master.entity.Department;

public interface DepartmentRepo extends BaseRepository<Department, String> {

	long countByCode(String value);
	
	boolean existsByCode(String code);

	Department findByName(String userDepartment);

	List<Department> findByCodeContaining(String code);

}
