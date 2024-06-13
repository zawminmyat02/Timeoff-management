package yu.cs.spring.model.master.repo;

import java.util.List;

import yu.cs.spring.model.BaseRepository;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk;

public interface PositionRepo extends BaseRepository<Position, PositionPk> {

	int countById(PositionPk id);

	List<Position> findByDepartmentCode(String department);

}
