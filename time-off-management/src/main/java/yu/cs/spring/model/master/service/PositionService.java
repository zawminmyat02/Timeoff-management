package yu.cs.spring.model.master.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk.PositionCode;
import yu.cs.spring.model.master.repo.PositionRepo;

@Service
public class PositionService {

	@Autowired
	private PositionRepo repo;

	public List<Position> findAll() {
		return repo.findAll();

	}

	public void save(Position entity) {
		repo.save(entity);
	}

	 public List<PositionCode> getPositionCodesByDepartment(String department) {
	        List<Position> positions = repo.findByDepartmentCode(department);
	        List<PositionCode> codes= positions.stream().map(position -> position.getId().getPositionCode()).collect(Collectors.toList());
	             
	        return codes;
	    }


}
