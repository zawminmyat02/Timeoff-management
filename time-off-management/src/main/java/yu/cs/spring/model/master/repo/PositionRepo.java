package yu.cs.spring.model.master.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import yu.cs.spring.model.BaseRepository;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk;

public interface PositionRepo extends BaseRepository<Position, PositionPk> {

	int countById(PositionPk id);

	List<Position> findByDepartmentCode(String department);
	
	@Query("SELECT p FROM Position p JOIN p.employees e WHERE e.code = :employeeCode")
	Position findPositionByEmployeeCode(String employeeCode);

}
