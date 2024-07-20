package yu.cs.spring.model.master.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import yu.cs.spring.model.BaseRepository;
import yu.cs.spring.model.master.entity.EmployeeCodeSeq;

public interface EmployeeCodeSeqRepo extends BaseRepository<EmployeeCodeSeq, String>{
	@Query("SELECT e FROM EmployeeCodeSeq e WHERE e.department = :department")
    Optional<EmployeeCodeSeq> findByDepartment(@Param("department") String department);
}
